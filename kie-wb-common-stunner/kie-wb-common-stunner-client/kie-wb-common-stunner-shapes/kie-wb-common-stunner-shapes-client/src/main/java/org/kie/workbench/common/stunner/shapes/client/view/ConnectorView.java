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

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.MultiPathDecorator;
import com.ait.lienzo.client.core.shape.OrthogonalPolyLine;
import com.ait.lienzo.client.core.types.Point2DArray;
import com.ait.lienzo.shared.core.types.ColorName;

public class ConnectorView extends BasicConnectorView<ConnectorView> {

    private static final double DECORATOR_WIDTH = 10;
    private static final double DECORATOR_HEIGHT = 15;

    public ConnectorView(final double... points) {
        this(createLine(points));
    }

    private ConnectorView(final Object[] line) {
        super((OrthogonalPolyLine) line[0],
                (MultiPathDecorator) line[1],
                (MultiPathDecorator) line[2]);
    }

    private static Object[] createLine(final double... points) {
        // The head decorator must be not visible, as connectors are unidirectional.
        // TODO: Remove this when decorators can be nullified for WiresConnectors and just nullify this instance.
        final MultiPath head = new MultiPath()
                .M(1, 2)
                .L(0, 2)
                .L(1 / 2, 0)
                .Z()
                .setFillAlpha(0)
                .setStrokeAlpha(0);
        final MultiPath tail = new MultiPath()
                .M(DECORATOR_WIDTH, DECORATOR_HEIGHT)
                .L(0, DECORATOR_HEIGHT)
                .L(DECORATOR_WIDTH / 2, 0)
                .Z()
                .setFillColor(ColorName.BLACK)
                .setFillAlpha(1);
        final OrthogonalPolyLine line =
                new OrthogonalPolyLine(Point2DArray.fromArrayOfDouble(points))
                        .setCornerRadius(5)
                        .setDraggable(true);
        line.setHeadOffset(head.getBoundingBox().getHeight());
        line.setTailOffset(tail.getBoundingBox().getHeight());
        final MultiPathDecorator headDecorator = new MultiPathDecorator(head);
        final MultiPathDecorator tailDecorator = new MultiPathDecorator(tail);
        return new Object[]{line, headDecorator, tailDecorator};
    }
}
