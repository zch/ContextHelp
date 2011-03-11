package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class VContextHelp extends HTML implements Paintable,
		NativePreviewHandler {

	private enum Placement {
		RIGHT, LEFT, ABOVE, BELOW;
	}

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
	}

	public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
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
		hidden = uidl.getBooleanVariable("hidden");

		String helpText = uidl.getStringAttribute("helpText");
		if (!hidden && helpText != null) {
			bubble.showHelpBubble(uidl, helpText);
		} else {
			hidden = true;
			if (bubble.isShowing()) {
				bubble.hide();
			}
			updateServersideState(false);
		}
	}

	public void onPreviewNativeEvent(NativePreviewEvent event) {
		if (followFocus && getElement() != null) {
			if (isFocusMovingEvent(event)) {
				openBubble();
			}
		} else {
			if (isF1Pressed(event)) {
				openBubble();
				event.cancel();
			} else if (isKeyDownOrClick(event) && bubble.isShowing()) {
				closeBubble();
			}
		}
	}

	private void openBubble() {
		hidden = false;
		updateServersideState(true);
	}

	private void closeBubble() {
		hidden = true;
		bubble.hide();
		updateServersideState(true);
	}

	private boolean isFocusMovingEvent(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONMOUSEUP
				|| (event.getTypeInt() == Event.ONKEYUP && event
						.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB);
	}

	private boolean isF1Pressed(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONKEYDOWN
				&& event.getNativeEvent().getKeyCode() == 112;
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
		Element contentElement = findContentElement(helpElement);
		if (contentElement != null) {
			return contentElement;
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
		while ("".equals(elementWithId.getId())
				|| elementWithId.getId().startsWith("gwt-uid")) {
			elementWithId = elementWithId.getParentElement();
		}
		return elementWithId;
	}

	private void updateServersideState(boolean immediate) {
		client.updateVariable(uidlId, "selectedComponentId", getHelpElement()
				.getId(), false);
		client.updateVariable(uidlId, "hidden", hidden, immediate);
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

		public HelpBubble() {
			super(true, false, false); // autoHide, modal, dropshadow
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

		private void showHelpBubble(UIDL uidl, String helpText) {
			setHelpText(helpText);
			Element helpElement = findHelpElement(uidl);
			if (helpElement != null) {
				show();
				calculateAndSetPopupPosition(helpElement);
			}
		}

		private void calculateAndSetPopupPosition(Element helpElement) {
			// By default, place the popup to the right of the field
			Placement placement = Placement.RIGHT;
			int left = helpElement.getAbsoluteLeft()
					+ helpElement.getOffsetWidth();
			int top = helpElement.getAbsoluteTop()
					+ helpElement.getOffsetHeight() / 2 - getOffsetHeight() / 2;

			// Would the popup go too far to the right?
			if (left + getOffsetWidth() > Document.get().getClientWidth()) {
				// Ok, either place it below or above the field
				left -= helpElement.getOffsetWidth() / 2 + getOffsetWidth() / 2;

				// Check whether there's room below the field
				top = helpElement.getAbsoluteTop()
						+ helpElement.getOffsetHeight();
				if (top + getOffsetHeight() < Document.get().getClientHeight()) {
					// There's room
					placement = Placement.BELOW;
				} else {
					// Place the popup above the field
					top = helpElement.getAbsoluteTop() - getOffsetHeight();
					placement = Placement.ABOVE;
				}

			}

			updatePopupStyleForPlacement(placement);
			setPopupPosition(left, top);
		}

		private void updatePopupStyleForPlacement(Placement placement) {
			for (Placement p : Placement.values()) {
				removeStyleName(p.name().toLowerCase());
			}
			addStyleName(placement.name().toLowerCase());
		}
	}
}
