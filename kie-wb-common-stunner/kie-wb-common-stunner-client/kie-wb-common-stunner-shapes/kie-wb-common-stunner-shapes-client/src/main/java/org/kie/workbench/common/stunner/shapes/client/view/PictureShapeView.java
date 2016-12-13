/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.shapes.client.view;

import com.ait.lienzo.client.core.shape.MultiPath;
import com.ait.lienzo.client.core.shape.Picture;
import com.ait.lienzo.client.core.shape.wires.WiresShape;

import static org.kie.workbench.common.stunner.shapes.client.util.BasicShapesUtils.*;

/**
 * The lienzo view implementation for the Picture shape.
 * Note that this view impl does not support resize.
 */
public class PictureShapeView<T extends PictureShapeView>
        extends BasicShapeView<T> {

    private Picture picture;

    public PictureShapeView(final String uri,
                            final double width,
                            final double height) {
        super(new MultiPath()
                .rect(0, 0, width, height)
                .setStrokeAlpha(0)
                .setFillAlpha(0));
        this.picture = new Picture(uri);
        scalePicture(picture, width, height);
        addChild(picture);
        super.setResizable(false);
    }

    @Override
    protected void doDestroy() {
        super.doDestroy();
        picture.removeFromParent();
    }

    //
    @Override
    public WiresShape setResizable(final boolean resizable) {
        return super.setResizable(false);
    }
}
