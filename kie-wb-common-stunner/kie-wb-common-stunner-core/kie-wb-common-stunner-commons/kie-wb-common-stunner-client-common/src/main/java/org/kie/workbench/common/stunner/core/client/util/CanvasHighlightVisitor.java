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

package org.kie.workbench.common.stunner.core.client.util;

import com.google.gwt.logging.client.LogConfiguration;
import com.google.gwt.user.client.Timer;
import org.kie.workbench.common.stunner.core.client.canvas.CanvasHandler;
import org.kie.workbench.common.stunner.core.client.shape.Shape;
import org.kie.workbench.common.stunner.core.client.shape.ShapeState;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.ContentTraverseCallback;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.content.ViewTraverseProcessorImpl;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.tree.TreeWalkTraverseProcessor;
import org.kie.workbench.common.stunner.core.graph.processing.traverse.tree.TreeWalkTraverseProcessorImpl;
import org.uberfire.mvp.Command;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Visits the graph and highlights elements while visiting. Just for development use.
 */
public class CanvasHighlightVisitor {

    private static Logger LOGGER = Logger.getLogger( CanvasHighlightVisitor.class.getName() );
    private static final int TIMER_DELAY = 500;

    private CanvasHandler canvasHandler;
    private final List<Shape> shapes = new LinkedList<Shape>();

    public CanvasHighlightVisitor() {
    }

    public void run( final CanvasHandler canvasHandler,
                     final Command callback ) {
        this.canvasHandler = canvasHandler;
        prepareVisit( () -> animate( 0, () -> {
            CanvasHighlightVisitor.this.log( Level.FINE, "CanvasHighlightVisitor - FINISHED" );
            if ( null != callback ) {
                callback.execute();
                CanvasHighlightVisitor.this.canvasHandler = null;
                CanvasHighlightVisitor.this.shapes.clear();
            }
        } ) );
    }

    private void animate( final int index, final Command callback ) {
        if ( index < shapes.size() ) {
            final Shape shape = shapes.get( index );
            shape.applyState( ShapeState.HIGHLIGHT );
            final Timer t = new Timer() {

                @Override
                public void run() {
                    animate( index + 1, callback );

                }

            };
            t.schedule( TIMER_DELAY );

        } else {
            callback.execute();

        }

    }

    @SuppressWarnings( "unchecked" )
    private void prepareVisit( final Command command ) {
        final Graph graph = canvasHandler.getDiagram().getGraph();
        new ViewTraverseProcessorImpl( new TreeWalkTraverseProcessorImpl()
                .useStartingNodesPolicy( TreeWalkTraverseProcessor.StartingNodesPolicy.NO_INCOMING_VIEW_EDGES )
        ).traverse( graph, new ContentTraverseCallback<View<?>, Node<View, Edge>, Edge<View<?>, Node>>() {
            @Override
            public void startGraphTraversal( Graph<DefinitionSet, Node<View, Edge>> graph ) {
            }

            @Override
            public void startEdgeTraversal( Edge<View<?>, Node> edge ) {
                addShape( edge.getUUID() );
            }

            @Override
            public void endEdgeTraversal( Edge<View<?>, Node> edge ) {
            }

            @Override
            public void startNodeTraversal( Node<View, Edge> node ) {
                addShape( node.getUUID() );
            }

            @Override
            public void endNodeTraversal( Node<View, Edge> node ) {
            }

            @Override
            public void endGraphTraversal() {
                command.execute();
            }

            private void addShape( String uuid ) {
                final Shape shape = canvasHandler.getCanvas().getShape( uuid );
                if ( null != shape ) {
                    shapes.add( shape );
                }
            }

        } );

    }

    private void log( final Level level, final String message ) {
        if ( LogConfiguration.loggingIsEnabled() ) {
            LOGGER.log( level, message );
        }
    }

}
