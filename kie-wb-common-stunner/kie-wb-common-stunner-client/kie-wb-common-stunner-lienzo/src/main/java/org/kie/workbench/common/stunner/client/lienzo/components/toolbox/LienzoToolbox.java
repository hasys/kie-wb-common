/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.lienzo.components.toolbox;

import org.kie.workbench.common.stunner.core.client.components.toolbox.Toolbox;
import org.kie.workbench.common.stunner.lienzo.toolbox.grid.GridToolbox;

public class LienzoToolbox implements Toolbox {

    private final GridToolbox toolbox;

    public LienzoToolbox( final GridToolbox toolbox ) {
        this.toolbox = toolbox;
    }

    @Override
    public void show() {
        toolbox.show();
    }

    @Override
    public void hide() {
        toolbox.hide();
    }

    @Override
    public void remove() {
        toolbox.remove();
    }

}
