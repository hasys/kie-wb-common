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

package org.kie.workbench.common.stunner.bpmn.definition.property.variables;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.bpmn.forms.meta.definition.VariablesEditor;
import org.kie.workbench.common.stunner.core.definition.annotation.Name;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;

import javax.validation.Valid;

import static org.kie.workbench.common.stunner.bpmn.util.FieldLabelConstants.FIELDDEF_PROCESS_VARIABLES;

@Portable
@Bindable
@PropertySet
public class ProcessData implements BPMNPropertySet {

    @Name
    public static final transient String propertySetName = "Process Data";

    @Property
    @FieldDef( label = FIELDDEF_PROCESS_VARIABLES, property = "value" )
    @VariablesEditor
    @Valid
    private ProcessVariables processVariables;

    public ProcessData() {
        this( new ProcessVariables() );
    }

    public ProcessData( @MapsTo( "processVariables" ) ProcessVariables processVariables ) {
        this.processVariables = processVariables;
    }

    public ProcessData( String processVariables ) {
        this.processVariables = new ProcessVariables( processVariables );
    }

    public String getPropertySetName() {
        return propertySetName;
    }

    public ProcessVariables getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables( ProcessVariables processVariables ) {
        this.processVariables = processVariables;
    }

}
