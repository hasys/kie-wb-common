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
import com.ait.lienzo.client.core.shape.wires.WiresShape;
import org.kie.workbench.common.stunner.core.client.shape.view.HasSize;
import org.kie.workbench.common.stunner.lienzo.util.LienzoPaths;

/**
 * The lienzo view implementation for the Rectangle shape.
 * <p>
 * TODO: Disabling for now the resize for rectangles when they're using a corner radius value different
 * from zero - ARC resize is not implemented yet on lienzo side, and the corners are built using ARCs.
 * See <a>org.kie.workbench.common.stunner.lienzo.util.LienzoPaths#rectangle</a>.
 */
public class RectangleView extends BasicShapeView<RectangleView>
        implements HasSize<RectangleView> {

    private final double corner_radius;

    public RectangleView(final double width,
                         final double height,
                         final double corner) {
        super(create(new MultiPath(), width, height, corner));
        super.setResizable(corner == 0);
        this.corner_radius = corner;
    }

    @Override
    public RectangleView setSize(final double width,
                                 final double height) {
        create(getPath().clear(), width, height, corner_radius);
        updateFillGradient(width, height);
        refresh();
        return this;
    }

    @Override
    public WiresShape setResizable(boolean resizable) {
        return super.setResizable(corner_radius == 0 && resizable);
    }

    /**
     * Append the path parts for a rectangle.
     * @param path The source multipath
     * @param w The rectangle width
     * @param h The rectangle height
     * @param r The rectangle corner radius
     */
    private static MultiPath create(final MultiPath path,
                                    final double w,
                                    final double h,
                                    final double r) {
        return LienzoPaths.rectangle(path, w, h, r);
    }
}
