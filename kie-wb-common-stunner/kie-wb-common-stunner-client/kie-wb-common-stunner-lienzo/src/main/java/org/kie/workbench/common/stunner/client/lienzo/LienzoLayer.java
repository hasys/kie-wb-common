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

package org.kie.workbench.common.stunner.client.lienzo;

import javax.enterprise.context.Dependent;

import com.ait.lienzo.client.core.shape.IPrimitive;
import com.ait.lienzo.client.core.shape.Shape;
import com.ait.lienzo.shared.core.types.DataURLType;
import com.google.gwt.core.client.GWT;
import org.kie.workbench.common.stunner.client.lienzo.canvas.util.LienzoImageDataUtils;
import org.kie.workbench.common.stunner.client.lienzo.shape.view.ViewEventHandlerManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractLayer;
import org.kie.workbench.common.stunner.core.client.canvas.Point2D;
import org.kie.workbench.common.stunner.core.client.shape.view.ShapeView;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEvent;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewEventType;
import org.kie.workbench.common.stunner.core.client.shape.view.event.ViewHandler;
import org.uberfire.mvp.Command;

@Dependent
@Lienzo
public class LienzoLayer extends AbstractLayer<LienzoLayer, ShapeView<?>, Shape<?>> {

    private static final ViewEventType[] SUPPORTED_EVENT_TYPES = new ViewEventType[]{
            ViewEventType.MOUSE_CLICK, ViewEventType.MOUSE_DBL_CLICK, ViewEventType.MOUSE_MOVE
    };

    protected ViewEventHandlerManager eventHandlerManager;
    protected com.ait.lienzo.client.core.shape.Layer layer;

    public LienzoLayer() {
    }

    @Override
    public LienzoLayer initialize(final Object view) {
        this.layer = (com.ait.lienzo.client.core.shape.Layer) view;
        this.eventHandlerManager = new ViewEventHandlerManager(layer, SUPPORTED_EVENT_TYPES);
        return this;
    }

    @Override
    public LienzoLayer addShape(final ShapeView<?> shape) {
        GWT.log("Adding shape " + shape.toString());
        layer.add((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer removeShape(final ShapeView<?> shape) {
        GWT.log("Removing shape " + shape.toString());
        layer.remove((IPrimitive<?>) shape);
        return this;
    }

    @Override
    public LienzoLayer draw() {
        layer.batch();
        return this;
    }

    @Override
    public void clear() {
        layer.clear();
    }

    @Override
    public String toDataURL() {
        return layer.toDataURL(DataURLType.PNG);
    }

    @Override
    public String toDataURL(final int x,
                            final int y,
                            final int width,
                            final int height) {
        return LienzoImageDataUtils.toImageData(getLienzoLayer(), x, y, width, height);
    }

    @Override
    public void onAfterDraw(final Command callback) {
        layer.setOnLayerAfterDraw(layer1 -> callback.execute());
    }

    @Override
    public void destroy() {
        // Clear registered event handers.
        if (null != eventHandlerManager) {
            eventHandlerManager.destroy();
            eventHandlerManager = null;
        }
        // Remove the layer stuff.
        if (null != layer) {
            layer.removeAll();
            layer.removeFromParent();
            layer = null;
        }
    }

    @Override
    public boolean supports(final ViewEventType type) {
        return eventHandlerManager.supports(type);
    }

    @Override
    public LienzoLayer addHandler(final ViewEventType type,
                                  final ViewHandler<? extends ViewEvent> eventHandler) {
        eventHandlerManager.addHandler(type, eventHandler);
        return this;
    }

    @Override
    public LienzoLayer removeHandler(final ViewHandler<? extends ViewEvent> eventHandler) {
        eventHandlerManager.removeHandler(eventHandler);
        return this;
    }

    @Override
    public LienzoLayer enableHandlers() {
        eventHandlerManager.enable();
        return this;
    }

    @Override
    public LienzoLayer disableHandlers() {
        eventHandlerManager.disable();
        return this;
    }

    @Override
    public Shape<?> getAttachableShape() {
        return null;
    }

    public com.ait.lienzo.client.core.shape.Layer getLienzoLayer() {
        return this.layer;
    }

    @Override
    protected Point2D getTranslate() {
        return new Point2D(
                layer.getAbsoluteTransform().getTranslateX(),
                layer.getAbsoluteTransform().getTranslateY()
        );
    }

    @Override
    protected Point2D getScale() {
        return new Point2D(
                layer.getAbsoluteTransform().getScaleX(),
                layer.getAbsoluteTransform().getScaleY()
        );
    }
}
