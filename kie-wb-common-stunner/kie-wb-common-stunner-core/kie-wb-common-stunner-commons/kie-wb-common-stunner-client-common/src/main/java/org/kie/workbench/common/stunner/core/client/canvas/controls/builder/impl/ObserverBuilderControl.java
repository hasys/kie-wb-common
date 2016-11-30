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

package org.kie.workbench.common.stunner.core.client.canvas.controls.builder.impl;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Dependent;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.kie.workbench.common.stunner.core.client.api.ClientDefinitionManager;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.command.CanvasCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.ElementBuilderControl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request.ElementBuildRequest;
import org.kie.workbench.common.stunner.core.client.canvas.controls.builder.request.ElementBuildRequestImpl;
import org.kie.workbench.common.stunner.core.client.canvas.controls.event.BuildCanvasShapeEvent;
import org.kie.workbench.common.stunner.core.client.canvas.event.selection.CanvasElementSelectedEvent;
import org.kie.workbench.common.stunner.core.client.canvas.util.CanvasLayoutUtils;
import org.kie.workbench.common.stunner.core.client.command.CanvasCommandManager;
import org.kie.workbench.common.stunner.core.client.command.Session;
import org.kie.workbench.common.stunner.core.client.service.ClientFactoryService;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.graph.processing.index.bounds.GraphBoundsIndexer;
import org.kie.workbench.common.stunner.core.graph.util.GraphUtils;
import org.kie.workbench.common.stunner.core.rule.model.ModelCardinalityRuleManager;
import org.kie.workbench.common.stunner.core.rule.model.ModelContainmentRuleManager;

import static org.uberfire.commons.validation.PortablePreconditions.*;

@Dependent
@Observer
public class ObserverBuilderControl extends AbstractElementBuilderControl
        implements ElementBuilderControl<AbstractCanvasHandler> {

    private static Logger LOGGER = Logger.getLogger( ObserverBuilderControl.class.getName() );

    private final Event<CanvasElementSelectedEvent> elementSelectedEvent;

    protected ObserverBuilderControl() {
        this( null, null, null, null, null, null, null, null, null, null );
    }

    @Inject
    public ObserverBuilderControl( final ClientDefinitionManager clientDefinitionManager,
                                   final ClientFactoryService clientFactoryServices,
                                   final @Session CanvasCommandManager<AbstractCanvasHandler> canvasCommandManager,
                                   final GraphUtils graphUtils,
                                   final ModelContainmentRuleManager modelContainmentRuleManager,
                                   final ModelCardinalityRuleManager modelCardinalityRuleManager,
                                   final CanvasCommandFactory canvasCommandFactory,
                                   final GraphBoundsIndexer graphBoundsIndexer,
                                   final CanvasLayoutUtils canvasLayoutUtils,
                                   final Event<CanvasElementSelectedEvent> elementSelectedEvent ) {
        super( clientDefinitionManager, clientFactoryServices, canvasCommandManager, graphUtils,
                modelContainmentRuleManager, modelCardinalityRuleManager, canvasCommandFactory,
                graphBoundsIndexer, canvasLayoutUtils );
        this.elementSelectedEvent = elementSelectedEvent;
    }

    @SuppressWarnings( "unchecked" )
    void onBuildCanvasShape(@Observes BuildCanvasShapeEvent buildCanvasShapeEvent) {
        checkNotNull("buildCanvasShapeEvent", buildCanvasShapeEvent);
        if (null == canvasHandler) {
            return;
        }

        final CanvasHandler context = buildCanvasShapeEvent.getCanvasHandler();
        if ( null != context && context.equals( canvasHandler ) ) {
            final Object definition = buildCanvasShapeEvent.getDefinition();
            final double x = buildCanvasShapeEvent.getX();
            final double y = buildCanvasShapeEvent.getY();
            final double _x = x >= 0 ? x - canvasHandler.getCanvas().getAbsoluteX() : -1;
            final double _y = y >= 0 ? y - canvasHandler.getCanvas().getAbsoluteY() : -1;
            final ElementBuildRequest<AbstractCanvasHandler> request = new ElementBuildRequestImpl(_x, _y, definition);
            ObserverBuilderControl.this.build( request, new BuildCallback() {
                @Override
                public void onSuccess( final String uuid ) {
                    canvasHandler.getCanvas().draw();
                    elementSelectedEvent.fire( new CanvasElementSelectedEvent( canvasHandler, uuid ) );
                }

                @Override
                public void onError( final ClientRuntimeError error ) {
                    LOGGER.log( Level.SEVERE, error.toString() );
                }
            } );
        }
    }
}
