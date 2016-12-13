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

package org.kie.workbench.common.stunner.client.lienzo.components.palette.view.element;

import org.kie.workbench.common.stunner.client.lienzo.components.palette.view.LienzoPaletteView;
import org.kie.workbench.common.stunner.core.client.components.palette.model.PaletteItem;
import org.kie.workbench.common.stunner.core.client.components.palette.view.AbstractPaletteItemView;
import org.kie.workbench.common.stunner.core.client.components.palette.view.PaletteItemView;

public abstract class AbstractLienzoPaletteItemView<I extends PaletteItem, V>
        extends AbstractPaletteItemView<I, V>
        implements PaletteItemView<I, V> {

    protected final LienzoPaletteView paletteView;

    public AbstractLienzoPaletteItemView(final I item,
                                         final LienzoPaletteView paletteView) {
        super(item);
        this.paletteView = paletteView;
    }

    protected void batch() {
        getPaletteView().draw();
    }

    protected LienzoPaletteView getPaletteView() {
        return paletteView;
    }
}
