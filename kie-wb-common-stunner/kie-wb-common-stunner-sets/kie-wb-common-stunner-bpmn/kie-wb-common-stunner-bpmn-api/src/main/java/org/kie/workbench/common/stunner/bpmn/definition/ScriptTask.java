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

package org.kie.workbench.common.stunner.bpmn.definition;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.NonPortable;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.metaModel.FieldDef;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.Documentation;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.Name;
import org.kie.workbench.common.stunner.bpmn.definition.property.general.TaskGeneralSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.ScriptTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.PropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Title;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.Morph;
import org.kie.workbench.common.stunner.core.factory.graph.NodeFactory;
import org.kie.workbench.common.stunner.core.rule.annotation.CanDock;

import javax.validation.Valid;

import static org.kie.workbench.common.stunner.bpmn.util.FieldLabelConstants.FIELDDEF_IMPLEMENTATION_EXECUTION;

@Portable
@Bindable
@Definition( graphFactory = NodeFactory.class, builder = ScriptTask.ScriptTaskBuilder.class )
@CanDock( roles = { "IntermediateEventOnActivityBoundary" } )
@Morph( base = BaseTask.class )
public class ScriptTask extends BaseTask {

    @Title
    public static final transient String title = "Script Task";

    @PropertySet
    @FieldDef( label = FIELDDEF_IMPLEMENTATION_EXECUTION, position = 1 )
    @Valid
    protected ScriptTaskExecutionSet executionSet;

    @NonPortable
    public static class ScriptTaskBuilder extends BaseTaskBuilder<ScriptTask> {

        @Override
        public ScriptTask build() {
            return new ScriptTask( new TaskGeneralSet(new Name( "Task" ), new Documentation( "" )),
                    new ScriptTaskExecutionSet(),
                    new BackgroundSet( COLOR, BORDER_COLOR, BORDER_SIZE ),
                    new FontSet(),
                    new RectangleDimensionsSet( WIDTH, HEIGHT ),
                    new SimulationSet(),
                    new TaskType( TaskTypes.SCRIPT ) );
        }

    }

    public ScriptTask() {
        super( TaskTypes.SCRIPT );
    }

    public ScriptTask( @MapsTo( "general" ) TaskGeneralSet general,
                       @MapsTo( "executionSet" ) ScriptTaskExecutionSet executionSet,
                       @MapsTo( "backgroundSet" ) BackgroundSet backgroundSet,
                       @MapsTo( "fontSet" ) FontSet fontSet,
                       @MapsTo( "dimensionsSet" ) RectangleDimensionsSet dimensionsSet,
                       @MapsTo( "simulationSet" ) SimulationSet simulationSet,
                       @MapsTo( "taskType" ) TaskType taskType ) {
        super( general, backgroundSet, fontSet, dimensionsSet, simulationSet, taskType );
        this.executionSet = executionSet;
    }

    public String getTitle() {
        return title;
    }

    public ScriptTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet( ScriptTaskExecutionSet executionSet ) {
        this.executionSet = executionSet;
    }
}
