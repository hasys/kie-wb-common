/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.AbstractDirectionalMultiPointShape;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.shape.wires.LayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresLayoutContainer;
import com.ait.lienzo.client.core.shape.wires.WiresMagnet;
import org.kie.workbench.common.stunner.client.lienzo.shape.view.AbstractConnectorView;
import org.kie.workbench.common.stunner.client.lienzo.shape.view.ViewEventHandlerManager;
import org.kie.workbench.common.stunner.core.client.shape.view.HasEventHandlers;
import org.kie.workbench.common.stunner.core.client.shape.view.HasTitle;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEvent;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEventType;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewHandler;

public abstract class BasicConnectorView<T> extends AbstractConnectorView<T>
        implements
        HasTitle<T>,
        HasEventHandlers<T, Shape<?>> {

    private static final ViewEventType[] SUPPORTED_EVENT_TYPES = new ViewEventType[]{
            ViewEventType.MOUSE_CLICK, ViewEventType.MOUSE_DBL_CLICK, ViewEventType.TOUCH
    };

    protected ViewEventHandlerManager eventHandlerManager;
    protected Text text;
    protected WiresLayoutContainer.Layout textPosition;
    protected double textRotationDegrees;
    private Double strokeWidth;
    private String color;

    public BasicConnectorView(final AbstractDirectionalMultiPointShape<?> line,
                              final MultiPathDecorator headDecorator,
                              final MultiPathDecorator tailDecorator) {
        super(line, headDecorator, tailDecorator);
    }

    public BasicConnectorView(final WiresMagnet headMagnet,
                              final WiresMagnet tailMagnet,
                              final AbstractDirectionalMultiPointShape<?> line,
                              final MultiPathDecorator headDecorator,
                              final MultiPathDecorator tailDecorator) {
        super(headMagnet, tailMagnet, line, headDecorator, tailDecorator);
    }

    protected void init() {
        super.init();
        this.textPosition = WiresLayoutContainer.Layout.CENTER;
        this.textRotationDegrees = 0;
        this.eventHandlerManager = new ViewEventHandlerManager(getLine(), SUPPORTED_EVENT_TYPES);
        enableShowControlsOnMouseEnter();
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return eventHandlerManager.supports(type);
    }

    @Override
    public Shape<?> getAttachableShape() {
        return getLine();
    }

    @Override
    public T addHandler(final ViewEventType type,
                        final ViewHandler<? extends ViewEvent> eventHandler) {
        eventHandlerManager.addHandler(type, eventHandler);
        return (T) this;
    }

    @Override
    public T removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {
        eventHandlerManager.removeHandler(eventHandler);
        return (T) this;
    }

    @Override
    public T disableHandlers() {
        eventHandlerManager.disable();
        return (T) this;
    }

    @Override
    public T enableHandlers() {
        eventHandlerManager.enable();
        return (T) this;
    }

    @Override
    public T setTitle(final String title) {
        if (null != text) {
            text.removeFromParent();
        }
        if (null != title) {
            // TODO
        }
        return (T) this;
    }

    @Override
    public T setTitlePosition(final Position position) {
        if (Position.BOTTOM.equals(position)) {
            this.textPosition = LayoutContainer.Layout.BOTTOM;
        } else if (Position.TOP.equals(position)) {
            this.textPosition = LayoutContainer.Layout.TOP;
        } else if (Position.LEFT.equals(position)) {
            this.textPosition = LayoutContainer.Layout.LEFT;
        } else if (Position.RIGHT.equals(position)) {
            this.textPosition = LayoutContainer.Layout.RIGHT;
        } else if (Position.CENTER.equals(position)) {
            this.textPosition = LayoutContainer.Layout.CENTER;
        }
        return (T) this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T setTitleRotation(double degrees) {
        this.textRotationDegrees = degrees;
        return (T) this;
    }

    @Override
    public T setTitleStrokeColor(final String color) {
        text.setStrokeColor(color);
        return (T) this;
    }

    @Override
    public T setTitleFontFamily(final String fontFamily) {
        text.setFontFamily(fontFamily);
        return (T) this;
    }

    @Override
    public T setTitleFontSize(final double fontSize) {
        text.setFontSize(fontSize);
        return (T) this;
    }

    @Override
    public T setTitleStrokeWidth(final double strokeWidth) {
        text.setStrokeWidth(strokeWidth);
        return (T) this;
    }

    @Override
    public T moveTitleToTop() {
        text.moveToTop();
        return (T) this;
    }

    @Override
    public T setTitleAlpha(final double alpha) {
        text.setAlpha(alpha);
        return (T) this;
    }

    @Override
    public T refreshTitle() {
        return (T) this;
    }

    @Override
    protected void doDestroy() {
        // Clear registered event handlers.
        if (null != eventHandlerManager) {
            eventHandlerManager.destroy();
            eventHandlerManager = null;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        // Nullify.
        this.text = null;
        this.textPosition = null;
        this.strokeWidth = null;
        this.color = null;
    }
}
