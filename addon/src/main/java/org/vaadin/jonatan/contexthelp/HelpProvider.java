package org.vaadin.jonatan.contexthelp;

import org.vaadin.jonatan.contexthelp.widgetset.client.ui.Placement;

public interface HelpProvider {

	/**
	 * Returns a HTML-formatted string for the specified ID.
	 * 
	 * @param id
	 *            the id of the text to provide.
	 * @return the HTML formatted help text for the provided ID, null if none
	 *         found.
	 */
	public String getHtmlForId(String id);

	/**
	 * Returns the placement of the help text that is to be used for the
	 * component with the provided ID.
	 * 
	 * @param id
	 *            the id of the component for which to provide the placement.
	 * @return the placement of the help text for the component with the
	 *         provided ID, null if none specified.
	 */
	public Placement getPlacementForId(String id);
}
