package org.vaadin.jonatan.contexthelp.widgetset.client.ui;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Created on 25.1.2013 10:07
 *
 * @author jonatan
 */
public class ContextHelpEvent {

    public interface BubbleShownHandler extends EventHandler {
        void onBubbleShown(BubbleShownEvent event);
    }

    public static class BubbleShownEvent extends GwtEvent<BubbleShownHandler> {
        public static GwtEvent.Type<BubbleShownHandler> TYPE = new GwtEvent.Type<BubbleShownHandler>();

        private String componentId;
        private String helpHtml;

        @Override
        public Type<BubbleShownHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(BubbleShownHandler bubbleShownHandler) {
            bubbleShownHandler.onBubbleShown(this);
        }

        public BubbleShownEvent(String componentId, String helpHtml) {
            this.componentId = componentId;
            this.helpHtml = helpHtml;
        }

        public String getComponentId() {
            return componentId;
        }

        public String getHelpHtml() {
            return helpHtml;
        }
    }

    public interface BubbleHiddenHandler extends EventHandler {
        void onBubbleHidden(BubbleHiddenEvent event);
    }

    public static class BubbleHiddenEvent extends GwtEvent<BubbleHiddenHandler> {
        public static GwtEvent.Type<BubbleHiddenHandler> TYPE = new GwtEvent.Type<BubbleHiddenHandler>();

        @Override
        public Type<BubbleHiddenHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(BubbleHiddenHandler bubbleShownHandler) {
            bubbleShownHandler.onBubbleHidden(this);
        }
    }

    public interface BubbleMovedHandler extends EventHandler {
        void onBubbleMoved(BubbleMovedEvent event);
    }

    public static class BubbleMovedEvent extends GwtEvent<BubbleMovedHandler> {
        public static GwtEvent.Type<BubbleMovedHandler> TYPE = new GwtEvent.Type<BubbleMovedHandler>();

        private String componentId;

        @Override
        public Type<BubbleMovedHandler> getAssociatedType() {
            return TYPE;
        }

        @Override
        protected void dispatch(BubbleMovedHandler bubbleShownHandler) {
            bubbleShownHandler.onBubbleMoved(this);
        }

        public BubbleMovedEvent(String componentId) {
            this.componentId = componentId;
        }

        public String getComponentId() {
            return componentId;
        }
    }
}
