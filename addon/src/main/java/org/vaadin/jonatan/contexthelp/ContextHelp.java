package org.vaadin.jonatan.contexthelp;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.AbstractExtension;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.UI;
import org.vaadin.jonatan.contexthelp.widgetset.client.ui.ContextHelpServerRpc;
import org.vaadin.jonatan.contexthelp.widgetset.client.ui.ContextHelpState;
import org.vaadin.jonatan.contexthelp.widgetset.client.ui.Placement;

/**
 * The ContextHelp component offers contextual help for fields or groups of
 * fields. By default, the help bubbles are opened by the user by pressing the
 * F1 key. You can also specify to programmatically open certain help bubbles
 * (e.g. via a button click) or to have the help bubble follow focus.
 * 
 * @author Jonatan Kronqvist
 * 
 */
public class ContextHelp extends AbstractExtension {
	private static final long serialVersionUID = 3852216539762314709L;

	private static int helpComponentIdCounter = 0;

	private HelpProvider helpProvider;

    private ContextHelpServerRpc rpc = new ContextHelpServerRpc() {

		@Override
		public void selectComponent(String id) {
			getState().selectedComponentId = id;
            String helpHTML = helpProvider.getHtmlForId(id);
            if (id != null && !id.equals("") && helpHTML != null) {
                getState().helpHTML = helpHTML;
                Placement placement = helpProvider.getPlacementForId(id);
                getState().placement = placement == null ? Placement.AUTO : placement;
                getState().hidden = false;
            }
		}

		@Override
		public void setHidden(boolean hidden) {
			getState().hidden = hidden;
		}
    };

	
	public ContextHelp() {
		helpProvider = new DefaultHelpProvider();
		registerRpc(rpc);
	}
	
    @Override
    public ContextHelpState getState() {
        return (ContextHelpState) super.getState();
    }

	/**
	 * Registers a help text for a given component. The help text is in HTML
	 * format and can be formatted as such and styled with inline CSS. It is
	 * also possible to provide classnames for elements in the HTML and provide
	 * the CSS rules in the Vaadin theme.
	 * 
	 * Note that if this method is to be used only if you do not use a custom
	 * {@link HelpProvider}.
	 * 
	 * @param component
	 *            the component for which to register the help text.
	 * @param help
	 *            the help text in HTML.
	 */
	public void addHelpForComponent(Component component, String help) {
		if (helpProvider instanceof DefaultHelpProvider) {
			if (component.getId() == null) {
				component.setId(generateComponentId());
			}
			((DefaultHelpProvider) helpProvider).addHelpForId(
					component.getId(), help);
		}
	}

	/**
	 * Registers a help text for a given component. The help text is in HTML
	 * format and can be formatted as such and styled with inline CSS. It is
	 * also possible to provide classnames for elements in the HTML and provide
	 * the CSS rules in the Vaadin theme.
	 * 
	 * Note that if this method is to be used only if you do not use a custom
	 * {@link HelpProvider}.
	 * 
	 * @param component
	 *            the component for which to register the help text.
	 * @param help
	 *            the help text in HTML.
	 * @param placement
	 *            where the help bubble should be placed.
	 */
	public void addHelpForComponent(Component component, String help,
			Placement placement) {
		addHelpForComponent(component, help);
		setPlacement(component, placement);
	}

	private String generateComponentId() {
		return "help_" + helpComponentIdCounter++;
	}

	/**
	 * Programmatically show the help bubble for a given component.
	 * 
	 * @param component
	 *            the component for which to show the help bubble.
	 */
	public void showHelpFor(Component component) {
        if (component.getId() != null) {
            if (component instanceof Field) {
                ((Field)component).focus();
            }
			getState().selectedComponentId = component.getId();
            String helpHTML = helpProvider.getHtmlForId(component.getId());
            if (helpHTML != null) {
                getState().helpHTML = helpHTML;
            }
            Placement placement = helpProvider.getPlacementForId(component.getId());
            getState().placement = placement == null ? Placement.AUTO : placement;
			getState().hidden = false;
		}
	}

	/**
	 * Programmatically hide the help bubble.
	 */
	public void hideHelp() {
		getState().selectedComponentId = null;
		getState().hidden = true;
	}

	/**
	 * This setting causes the help bubble to be displayed when a field is
	 * focused, and removed when the focus moves away from the field. If focus
	 * moves to a new field, the help bubble for this new field is shown.
	 * 
	 * @param followFocus
	 *            true (enabled) or false (disabled).
	 */
	public void setFollowFocus(boolean followFocus) {
		getState().followFocus = followFocus;
		getState().selectedComponentId = "";
	}

	/**
	 * @return whether the help bubble follows focus or is opened with the F1
	 *         key.
	 */
	public boolean isFollowFocus() {
		return getState().followFocus;
	}

	/**
	 * Specifies where the help bubble should be placed in relation to the
	 * component. This is only active for the specified component. The default
	 * is to place the help bubble to the right of components and if it doesn't
	 * fit there, ContextHelp attempts to place it below followed by above the
	 * component.
	 * 
	 * Note that if this method is to be used only if you do not use a custom
	 * {@link HelpProvider}.
	 * 
	 * @param component
	 *            the component for which to define the placement of the help
	 *            bubble.
	 * @param placement
	 *            the placement of the help bubble.
	 */
	public void setPlacement(Component component, Placement placement) {
		if (helpProvider instanceof DefaultHelpProvider) {
			((DefaultHelpProvider) helpProvider).setPlacementOfId(
					component.getId(), placement);
		}
	}

	/**
	 * Sets the key that is used for opening the help bubble. Use the key codes
	 * found in {@link ShortcutAction.KeyCode}
	 * 
	 * @param helpKey
	 *            the key code for the key that opens the help bubble.
	 */
	public void setHelpKey(int helpKey) {
		getState().helpKey = helpKey;
	}

	/**
	 * @return the key code for the key that opens the help bubble.
	 */
	public int getHelpKey() {
		return getState().helpKey;
	}

	/**
	 * @return whether the bubble is automatically hidden when the field is
	 *         unfocused.
	 */
	public boolean isHideOnBlur() {
		return getState().hideOnBlur;
	}

	/**
	 * Set whether the bubble should be automatically hidden when the field is
	 * unfocused.
	 * 
	 * This feature is still EXPERIMENTAL, as hiding of the bubble lags in some
	 * instances when the component it is attached to is removed (navigated away
	 * from) if you don't hide the bubble manually before switching views.
	 * 
	 * @param hideOnBlur
	 */
	public void setHideOnBlur(boolean hideOnBlur) {
		getState().hideOnBlur = hideOnBlur;
	}

	/**
	 * Sets the {@link HelpProvider} to be used for looking up help texts for
	 * components. By default the {@link DefaultHelpProvider} is used.
	 * 
	 * @param helpProvider
	 *            the HelpProvider.
	 */
	public void setHelpProvider(HelpProvider helpProvider) {
		this.helpProvider = helpProvider;
	}

	/**
	 * @return The HelpProvider instance that is used for looking up help text
	 *         for components.
	 */
	public HelpProvider getHelpProvider() {
		return helpProvider;
	}

    /**
     * Add this extension to the target UI.
     *
     * @param ui
     *            the connector to attach this extension to
     */
    public void extend(UI ui) {
        super.extend(ui);
    }
}
