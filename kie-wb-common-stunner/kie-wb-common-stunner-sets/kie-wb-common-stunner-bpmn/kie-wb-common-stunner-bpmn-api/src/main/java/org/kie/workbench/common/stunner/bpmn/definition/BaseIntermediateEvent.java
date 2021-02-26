/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.bpmn.definition;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;

import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOModel;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.CircleDimensionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.util.HashUtil;

public abstract class BaseIntermediateEvent
        implements BPMNViewDefinition,
                   DataIOModel {

    @Labels
    protected final Set<String> labels = new HashSet<>();

    @Property
    @FormField
    @Valid
    protected String name;

    @Property
    @FormField
    @Valid
    protected String documentation;

    @Property
    @Valid
    protected BackgroundSet backgroundSet;

    @Property
    protected FontSet fontSet;

    @Property
    protected CircleDimensionSet dimensionsSet;

    @Property
    @FormField(afterElement = "executionSet")
    @Valid
    protected DataIOSet dataIOSet;

    public BaseIntermediateEvent() {
        initLabels();
    }

    public BaseIntermediateEvent(final String name,
                                 final String documentation,
                                 final BackgroundSet backgroundSet,
                                 final FontSet fontSet,
                                 final CircleDimensionSet dimensionsSet,
                                 final DataIOSet dataIOSet) {
        this();
        this.name = name;
        this.documentation = documentation;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.dimensionsSet = dimensionsSet;
        this.dataIOSet = dataIOSet;
    }

    protected abstract void initLabels();

    public String getName() {
        return name;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public void setBackgroundSet(BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setFontSet(FontSet fontSet) {
        this.fontSet = fontSet;
    }

    public CircleDimensionSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet(CircleDimensionSet dimensionsSet) {
        this.dimensionsSet = dimensionsSet;
    }

    public DataIOSet getDataIOSet() {
        return dataIOSet;
    }

    public void setDataIOSet(DataIOSet dataIOSet) {
        this.dataIOSet = dataIOSet;
    }

    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         Objects.hashCode(name),
                                         Objects.hashCode(documentation),
                                         Objects.hashCode(backgroundSet),
                                         Objects.hashCode(fontSet),
                                         Objects.hashCode(dimensionsSet),
                                         Objects.hashCode(dataIOSet),
                                         Objects.hashCode(labels));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof BaseIntermediateEvent) {
            BaseIntermediateEvent other = (BaseIntermediateEvent) o;
            return Objects.equals(name, other.name) &&
                    Objects.equals(documentation, other.documentation) &&
                    Objects.equals(backgroundSet, other.backgroundSet) &&
                    Objects.equals(fontSet, other.fontSet) &&
                    Objects.equals(dimensionsSet, other.dimensionsSet) &&
                    Objects.equals(dataIOSet, other.dataIOSet) &&
                    Objects.equals(labels, other.labels);
        }
        return false;
    }
}
