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

public class VContextHelp extends VOverlay implements Paintable,
		NativePreviewHandler {

	/** Set the tagname used to statically resolve widget from UIDL. */
	public static final String TAGNAME = "helprouter";

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-" + TAGNAME;

	private static final int Z_INDEX_BASE = 90000;

	/** Component identifier in UIDL communications. */
	String uidlId;

	/** Reference to the server connection object. */
	ApplicationConnection client;

	private String selectedComponentIdVariable;

	private boolean followFocus = false;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VContextHelp() {
		super();
		setStylePrimaryName(CLASSNAME);
		setZIndex(Z_INDEX_BASE);

		Event.addNativePreviewHandler(this);
		suppressHelpForIE();

		hide();
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

		if (uidl.getVariableNames().size() == 1) {
			selectedComponentIdVariable = uidl.getVariableNames().iterator()
					.next();
		} else {
			selectedComponentIdVariable = "selectedComponentId";
		}

		followFocus = uidl.getBooleanAttribute("followFocus");

		String helpText = uidl.getStringAttribute("helpText");
		if (helpText != null) {
			showHelpBubble(uidl, helpText);
		} else {
			hide();
		}
	}

	public void onPreviewNativeEvent(NativePreviewEvent event) {
		if (followFocus && getElement() != null) {
			if (isFocusMovingEvent(event)) {
				updateFocusedElement();
			}
		} else {
			if (isF1Pressed(event)) {
				updateFocusedElement();
				event.cancel();
			} else if (isKeyDownOrClick(event)) {
				hide();
			}
		}
	}

	private boolean isFocusMovingEvent(NativePreviewEvent event) {
		return event.getTypeInt() == Event.ONMOUSEUP
				|| event.getTypeInt() == Event.ONKEYUP
				&& event.getNativeEvent().getKeyCode() == KeyCodes.KEY_TAB;
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


	private void showHelpBubble(UIDL uidl, String helpText) {
		setHelpText(helpText);
		Element helpElement = findHelpElement(uidl);
		show();
		setHelpBubblePosition(helpElement);
	}

	private void setHelpBubblePosition(Element helpElement) {
		Element e = getElement();
		e.getStyle().setProperty("position", "absolute");
		e.getStyle().setPropertyPx("left",
				calculateLeftPosition(helpElement, e));
		e.getStyle().setPropertyPx("top", calculateTopPosition(helpElement, e));
	}

	private int calculateTopPosition(Element helpElement, Element e) {
		return helpElement.getAbsoluteTop() + helpElement.getOffsetHeight() / 2
				- e.getOffsetHeight() / 2;
	}

	private int calculateLeftPosition(Element helpElement, Element e) {
		int left = helpElement.getAbsoluteLeft() + helpElement.getOffsetWidth();
		left = makeFitInBrowserWindow(left, helpElement, e);
		return left;
	}

	private int makeFitInBrowserWindow(int left, Element helpElement, Element e) {
		int newPosition = left;
		if (newPosition + e.getOffsetWidth() > Document.get().getClientWidth()) {
			newPosition -= helpElement.getOffsetWidth() / 2;
		}
		return newPosition;
	}

	private Element findHelpElement(UIDL uidl) {
		Element helpElement = DOM.getElementById(uidl
				.getStringVariable(selectedComponentIdVariable));
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

	private void setHelpText(String helpText) {
		HTML helpHtml = new HTML(helpText);
		helpHtml.setStyleName("helpText");
		setWidget(helpHtml);
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
		while ("".equals(elementWithId.getId())) {
			elementWithId = elementWithId.getParentElement();
		}
		return elementWithId;
	}

	private void updateFocusedElement() {
		client.updateVariable(uidlId, selectedComponentIdVariable,
				getHelpElement().getId(), true);
	}

}
