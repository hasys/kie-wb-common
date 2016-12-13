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

package org.kie.workbench.common.stunner.shapes.client.view.animatiion;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.shapes.client.BasicShape;
import org.kie.workbench.common.stunner.shapes.client.view.BasicShapeView;

public final class BasicShapeDecoratorAnimation extends BasicDecoratorAnimation<BasicShape> {

    public BasicShapeDecoratorAnimation(final String color,
                                        final double strokeWidth,
                                        final double strokeAlpha) {
        super(color, strokeWidth, strokeAlpha);
    }

    @Override
    Shape getDecorator() {
        return getView().getShape();
    }

    private BasicShapeView<?> getView() {
        return (BasicShapeView<?>) getSource().getShapeView();
    }
}
