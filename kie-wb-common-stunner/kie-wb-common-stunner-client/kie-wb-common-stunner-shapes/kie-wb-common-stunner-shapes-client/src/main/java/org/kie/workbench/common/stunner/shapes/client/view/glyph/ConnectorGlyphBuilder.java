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

package org.kie.workbench.common.stunner.shapes.client.view.glyph;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;

import com.ait.lienzo.client.core.shape.Group;
import org.kie.workbench.common.stunner.core.client.shape.view.glyph.AbstractGlyphBuilder;
import org.kie.workbench.common.stunner.core.client.shape.view.glyph.Glyph;
import org.kie.workbench.common.stunner.shapes.def.ConnectorGlyphDef;

@ApplicationScoped
public class ConnectorGlyphBuilder extends AbstractGlyphBuilder<Group, ConnectorGlyphDef<Object>> {

    private static Logger LOGGER = Logger.getLogger(ConnectorGlyphBuilder.class.getName());

    @Override
    public Class<?> getType() {
        return ConnectorGlyphDef.class;
    }

    @Override
    public Glyph<Group> build() {
        final String color = glyphDefinition.getColor();
        return new ConnectorGlyph(color, width, height);
    }
}
