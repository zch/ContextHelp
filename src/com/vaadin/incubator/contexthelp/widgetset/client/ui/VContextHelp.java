package com.vaadin.incubator.contexthelp.widgetset.client.ui;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.ui.HTML;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;
import com.vaadin.terminal.gwt.client.ui.VOverlay;

public class VContextHelp extends VOverlay implements Paintable {

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

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VContextHelp() {
		super();
        setStylePrimaryName(CLASSNAME);
        setZIndex(Z_INDEX_BASE);

		// Grab the F1 key (keyCode == 112)
		Event.addNativePreviewHandler(new NativePreviewHandler() {
			
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				if (event.getTypeInt() == Event.ONKEYDOWN && event.getNativeEvent().getKeyCode() == 112) {
					updateFocusedElement();
					event.cancel();
				} else if (event.getTypeInt() == Event.ONKEYDOWN || event.getTypeInt() == Event.ONCLICK) {
					// Hide the help div on keyups and mouse clicks
					hide();
				}
			}
		});
		suppressHelpForIE();

		hide();
	}
	
	public native void suppressHelpForIE()
	/*-{
		$doc.onhelp = function() {
			return false;
		}
	}-*/;

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
		
		String helpText = uidl.getStringAttribute("helpText");

		if (helpText != null) {
			HTML helpHtml = new HTML(helpText);
			helpHtml.setStyleName("helpText");
			setWidget(helpHtml);
			Element focused = getHelpElement();
			show();
			Element e = getElement();
			e.getStyle().setProperty("position", "absolute");
			int left = focused.getAbsoluteLeft() + focused.getOffsetWidth();
			if (left + e.getOffsetWidth() > Document.get().getClientWidth()) {
				left -= focused.getOffsetWidth()/2;
			}
			e.getStyle().setPropertyPx("left", left);
			e.getStyle().setPropertyPx("top", focused.getAbsoluteTop() + focused.getOffsetHeight()/2 - e.getOffsetHeight()/2);
		}
	}

	public static native Element getFocusedElement()
	/*-{
		return $doc.activeElement;
	}-*/;

	private Element getHelpElement() {
		Element focused = getFocusedElement();
		while ("".equals(focused.getId())) {
			focused = focused.getParentElement();
		}
		return focused;
	}
	
	private void updateFocusedElement() {
		client.updateVariable(uidlId, selectedComponentIdVariable, getHelpElement().getId(), true);
	}

}
