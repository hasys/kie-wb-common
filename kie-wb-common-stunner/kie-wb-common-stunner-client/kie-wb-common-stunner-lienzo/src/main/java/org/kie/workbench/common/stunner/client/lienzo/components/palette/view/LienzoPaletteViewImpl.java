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

package org.kie.workbench.common.stunner.client.lienzo.components.palette.view;

import javax.enterprise.context.Dependent;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element.LienzoPaletteElementView;
import org.kie.workbench.common.stunner.lienzo.palette.AbstractPalette;
import org.kie.workbench.common.stunner.lienzo.palette.Palette;

@Dependent
public class LienzoPaletteViewImpl
        extends AbstractLienzoPaletteView<LienzoPaletteViewImpl>
        implements LienzoPaletteView<LienzoPaletteViewImpl, LienzoPaletteElementView> {

    @Override
    protected AbstractPalette<? extends AbstractPalette> buildPalette() {
        return new Palette();
    }
}
