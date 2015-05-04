package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.vaadin.client.ServerConnector;
import com.vaadin.client.communication.RpcProxy;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.extensions.AbstractExtensionConnector;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.Connect;
import org.vaadin.jonatan.contexthelp.ContextHelp;

@Connect(ContextHelp.class)
public class ContextHelpConnector extends AbstractExtensionConnector implements ContextHelpEvent.BubbleMovedHandler,
        ContextHelpEvent.BubbleHiddenHandler, ContextHelpEvent.BubbleShownHandler {

    ContextHelpServerRpc rpc = RpcProxy.create(ContextHelpServerRpc.class, this);

    VContextHelp contextHelp;

    @Override
    protected void extend(ServerConnector serverConnector) {
        if (contextHelp == null) {
            contextHelp = new VContextHelp();
            contextHelp.addBubbleHiddenHandler(this);
            contextHelp.addBubbleMovedHandler(this);
            contextHelp.addBubbleShownHandler(this);
            contextHelp.setHidden(getState().hidden);
            contextHelp.setFollowFocus(getState().followFocus);
            contextHelp.setHelpKeyCode(getState().helpKey);
            contextHelp.setHideOnBlur(getState().hideOnBlur);
        }
    }

    @Override
    public ContextHelpState getState() {
        return (ContextHelpState) super.getState();
    }

    @Override
    public void onStateChanged(StateChangeEvent stateChangeEvent) {
        super.onStateChanged(stateChangeEvent);
        if (stateChangeEvent.hasPropertyChanged("hidden")) {
            contextHelp.setHidden(getState().hidden);
        } else if (stateChangeEvent.hasPropertyChanged("followFocus")) {
            contextHelp.setFollowFocus(getState().followFocus);
        } else if (stateChangeEvent.hasPropertyChanged("helpKey")) {
            contextHelp.setHelpKeyCode(getState().helpKey);
        } else if (stateChangeEvent.hasPropertyChanged("hideOnBlur")) {
            contextHelp.setHideOnBlur(getState().hideOnBlur);
        }
        if (!getState().hidden && getState().helpHTML != null) {
            showHelpBubbleDeferred();
        } else {
            contextHelp.hideHelpBubble();
        }
    }

    private void showHelpBubbleDeferred() {
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                contextHelp.showHelpBubble(getState().selectedComponentId,
                        getState().helpHTML, getState().placement);
            }
        });
    }

    @Override
    public void onBubbleHidden(ContextHelpEvent.BubbleHiddenEvent event) {
        if (!getState().hidden) {
            rpc.setHidden(true);
        }
    }

    @Override
    public void onBubbleMoved(ContextHelpEvent.BubbleMovedEvent event) {
        rpc.selectComponent(event.getComponentId());
    }

    @Override
    public void onBubbleShown(ContextHelpEvent.BubbleShownEvent event) {
        // TODO: do we need to do anything here?
    }

    // public void updateFromUIDL(final UIDL uidl, ApplicationConnection client)
    // {
    //
    // bubble.updateStyleNames(uidl.getStringAttribute("style"));
    //
    // final String helpText = uidl.getStringAttribute("helpText");
    // if (!hidden && helpText != null) {
    // Scheduler.get().scheduleDeferred(new ScheduledCommand() {
    // public void execute() {
    // Placement placement = Placement.DEFAULT;
    // if (uidl.hasAttribute("placement")) {
    // placement = Placement.valueOf(uidl
    // .getStringAttribute("placement"));
    // }
    // bubble.showHelpBubble(uidl, helpText, placement);
    // }
    // });
    // } else {
    // hidden = true;
    // if (bubble.isShowing()) {
    // bubble.hide();
    // }
    // updateServersideState(false);
    // }
    // }

}
