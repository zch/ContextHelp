package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.VConsole;
import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class VContextHelp extends HTML implements Paintable,
		NativePreviewHandler {

	private enum Placement {
		RIGHT, LEFT, ABOVE, BELOW, DEFAULT;

		public int getLeft(HelpBubble bubble, Element helpElement) {
			switch (this) {
			case RIGHT:
				return helpElement.getAbsoluteLeft()
						+ helpElement.getOffsetWidth();
			case LEFT:
				return helpElement.getAbsoluteLeft() - bubble.getOffsetWidth();
			case ABOVE:
			case BELOW:
				return helpElement.getAbsoluteLeft()
						+ helpElement.getOffsetWidth() / 2
						- bubble.getOffsetWidth() / 2;
			}
			return 0;
		}

		public int getTop(HelpBubble bubble, Element helpElement) {
			switch (this) {
			case RIGHT:
			case LEFT:
				return helpElement.getAbsoluteTop()
						+ helpElement.getOffsetHeight() / 2
						- bubble.getOffsetHeight() / 2;
			case ABOVE:
				return helpElement.getAbsoluteTop() - bubble.getOffsetHeight();
			case BELOW:
				return helpElement.getAbsoluteTop()
						+ helpElement.getOffsetHeight();
			}
			return 0;
		}
	}

	private static final int SCROLL_UPDATER_INTERVAL = 100;

	/** Set the tagname used to statically resolve widget from UIDL. */
	public static final String TAGNAME = "helprouter";

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-" + TAGNAME;

	/** Component identifier in UIDL communications. */
	String uidlId;

	/** Reference to the server connection object. */
	ApplicationConnection client;

	private boolean followFocus = true;

	private boolean hidden = true;

	private final HelpBubble bubble;

	private final Timer scrollUpdater;

	private int helpKeyCode = 112; // F1 by default

	private boolean hideOnBlur = true;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VContextHelp() {
		super();
		setStylePrimaryName(CLASSNAME);

		Event.addNativePreviewHandler(this);
		suppressHelpForIE();

		bubble = new HelpBubble();
		scrollUpdater = new Timer() {
			public void run() {
				bubble.updatePositionIfNeeded();
			}
		};
	}

	public void updateFromUIDL(final UIDL uidl, ApplicationConnection client) {
		// This call should be made first. Ensure correct implementation,
		// and let the containing layout manage caption, etc.
		if (client.updateComponent(this, uidl, true)) {
			return;
		}

		// Save reference to server connection object to be able to send
		// user interaction later
		this.client = client;

		// Save the UIDL identifier for the component
		uidlId = uidl.getId();

		followFocus = uidl.getBooleanAttribute("followFocus");
		helpKeyCode = uidl.getIntAttribute("helpKey");
		hideOnBlur = uidl.getBooleanAttribute("hideOnBlur");

		hidden = uidl.getBooleanVariable("hidden");

		final String helpText = uidl.getStringAttribute("helpText");
		if (!hidden && helpText != null) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				public void execute() {
					Placement placement = Placement.DEFAULT;
					if (uidl.hasAttribute("placement")) {
						placement = Placement.valueOf(uidl
								.getStringAttribute("placement"));
					}
					bubble.showHelpBubble(uidl, helpText, placement);
				}
			});
		} else {
			hidden = true;
			if (bubble.isShowing()) {
				bubble.hide();
			}
			updateServersideState(false);
		}
	}

	public void onPreviewNativeEvent(NativePreviewEvent event) {
		// Hide if the element has disappeared (views changed)
		if (shouldHideBubble(event)) {
			VConsole.log("onPreviewNativeEvent");
			closeBubble();
		}
		if (!isAttached()) {
			return;
		}
		if (followFocus && getElement() != null) {
			if (isFocusMovingEvent(event)) {
				openBubble();
			}
		} else {
			if (isHelpKeyPressed(event)) {
				openBubble();
				event.cancel();
			} else if (hideOnBlur && isKeyDownOrClick(event)
					&& bubble.isShowing()) {
				closeBubble();
			}
		}
	}

	private boolean shouldHideBubble(NativePreviewEvent event) {
		if (!hideOnBlur && bubble.helpElement != null) {
			return bubble.helpElement.getAbsoluteLeft() < 0
					|| bubble.helpElement.getAbsoluteTop() < 0
					|| !Document.get().getBody()
							.isOrHasChild(bubble.helpElement)
					|| "hidden".equalsIgnoreCase(bubble.helpElement.getStyle()
							.getVisibility())
					|| "none".equalsIgnoreCase(bubble.helpElement.getStyle()
							.getDisplay());
		}
		return false;
	}

	private void openBubble() {
		scrollUpdater.scheduleRepeating(SCROLL_UPDATER_INTERVAL);
		hidden = false;
		updateServersideState(true);
	}

	private void closeBubble() {
		scrollUpdater.cancel();
		hidden = true;
		bubble.hide();
		updateServersideState(true);
	}

	private boolean isFocusMovingEvent(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONMOUSEUP
				|| (event.getTypeInt() == Event.ONKEYUP && event
						.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB);
	}

	private boolean isHelpKeyPressed(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONKEYDOWN
				&& event.getNativeEvent().getKeyCode() == helpKeyCode;
	}

	private boolean isKeyDownOrClick(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONKEYDOWN
				|| event.getTypeInt() == Event.ONCLICK;
	}

	public native void suppressHelpForIE()
	/*-{
		$doc.onhelp = function() {
			return false;
		}
	}-*/;

	private Element findHelpElement(UIDL uidl) {
		String id = uidl.getStringVariable("selectedComponentId");
		if (id == null || id.length() == 0) {
			return null;
		}
		Element helpElement = DOM.getElementById(id);
		if (helpElement != null) {
			Element contentElement = findContentElement(helpElement);
			if (contentElement != null) {
				return contentElement;
			}
		}
		return helpElement;
	}

	private Element findContentElement(Element helpElement) {
		// check whether helpElement has a child element with
		// class="v-XXXX-content" and if this is the case, use the content
		// element for the position calculations below.
		NodeList<Node> children = helpElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.getItem(i).getNodeType() == Node.ELEMENT_NODE) {
				Element e = Element.as(children.getItem(i));
				if (e.getClassName().contains("content")) {
					return e;
				}
			}
		}
		return null;
	}

	public static native Element getFocusedElement()
	/*-{
		return $doc.activeElement;
	}-*/;

	private Element getHelpElement() {
		Element focused = getFocusedElement();
		return findFirstElementInHierarchyWithId(focused);
	}

	private static Element findFirstElementInHierarchyWithId(Element focused) {
		Element elementWithId = focused;
		while (("".equals(elementWithId.getId())
				|| elementWithId.getId() == null || elementWithId.getId()
				.startsWith("gwt-uid"))
				&& elementWithId.getParentElement() != null) {
			elementWithId = elementWithId.getParentElement();
		}
		return elementWithId;
	}

	private void updateServersideState(boolean immediate) {
		if (isAttached()) {
			client.updateVariable(uidlId, "selectedComponentId",
					getHelpElement().getId(), false);
			client.updateVariable(uidlId, "hidden", hidden, immediate);
		}
	}

	/**
	 * Make sure that we remove the bubble when the main widget is removed.
	 * 
	 * @see com.google.gwt.user.client.ui.Widget#onUnload()
	 */
	@Override
	protected void onDetach() {
		bubble.hide();
		super.onDetach();
	}

	private class HelpBubble extends VOverlay {
		private static final int Z_INDEX_BASE = 90000;

		private HTML helpHtml = new HTML();

		private Element helpElement;

		private int elementTop;
		private int elementLeft;

		private Placement placement;

		public HelpBubble() {
			super(false, false, false); // autoHide, modal, dropshadow
			setStylePrimaryName(CLASSNAME + "-bubble");
			setZIndex(Z_INDEX_BASE);
			setWidget(helpHtml);
			// Make sure we are hidden
			hide();
		}

		public void setHelpText(String helpText) {
			helpHtml.setHTML(helpText);
			helpHtml.setStyleName("helpText");
		}

		public void showHelpBubble(UIDL uidl, String helpText,
				Placement placement) {
			this.placement = placement;
			setHelpText(helpText);
			helpElement = findHelpElement(uidl);
			if (helpElement != null) {
				show();
				calculateAndSetPopupPosition();
			}
		}

		public void updatePositionIfNeeded() {
			if (elementLeft != helpElement.getAbsoluteLeft()
					|| elementTop != helpElement.getAbsoluteTop()) {
				calculateAndSetPopupPosition();
			}
		}

		private void calculateAndSetPopupPosition() {
			// Save the current position for checking whether the element has
			// moved == scrolled
			elementLeft = helpElement.getAbsoluteLeft();
			elementTop = helpElement.getAbsoluteTop();

			Placement finalPlacement = placement;
			if (placement == Placement.DEFAULT) {
				finalPlacement = findDefaultPlacement();
			}
			updatePopupStyleForPlacement(finalPlacement);
			setPopupPosition(finalPlacement.getLeft(this, helpElement),
					finalPlacement.getTop(this, helpElement));
		}

		private Placement findDefaultPlacement() {
			// Would the popup go too far to the right?
			if (Placement.RIGHT.getLeft(this, helpElement) + getOffsetWidth() > Document
					.get().getClientWidth()) {
				// Yes, either place it below (if there's room) or above the
				// field
				if (Placement.BELOW.getTop(this, helpElement)
						+ getOffsetHeight() < Document.get().getClientHeight()) {
					return Placement.BELOW;
				} else {
					return Placement.ABOVE;
				}
			}
			// By default, place the popup to the right of the field
			return Placement.RIGHT;
		}

		private void updatePopupStyleForPlacement(Placement placement) {
			for (Placement p : Placement.values()) {
				removeStyleName(p.name().toLowerCase());
			}
			addStyleName(placement.name().toLowerCase());
		}
	}
}
