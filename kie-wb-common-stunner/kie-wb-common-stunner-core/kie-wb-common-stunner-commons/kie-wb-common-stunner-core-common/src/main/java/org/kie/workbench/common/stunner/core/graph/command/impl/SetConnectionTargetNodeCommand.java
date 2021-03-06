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
package org.kie.workbench.common.stunner.core.graph.command.impl;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandExecutionContext;
import org.kie.workbench.common.stunner.core.graph.command.GraphCommandResultBuilder;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnector;
import org.kie.workbench.common.stunner.core.rule.EdgeCardinalityRule;
import org.kie.workbench.common.stunner.core.rule.RuleManager;
import org.kie.workbench.common.stunner.core.rule.RuleViolation;
import org.uberfire.commons.validation.PortablePreconditions;

import java.util.Collection;

/**
 * A Command to set the incoming connection for an edge.
 * Note: if the connector's target is not set, the <code>targetNode</code> can be null.
 */
@Portable
public final class SetConnectionTargetNodeCommand extends AbstractGraphCommand {

    private final String targetNodeUUID;
    private final String edgeUUID;
    private final Integer magnetIndex;

    private String lastTargetNodeUUID;
    private Integer lastMagnetIndex;
    private transient Edge<? extends View, Node> edge;
    private transient Node<? extends View<?>, Edge> sourceNode;
    private transient Node<? extends View<?>, Edge> targetNode;

    @SuppressWarnings( "unchecked" )
    public SetConnectionTargetNodeCommand( @MapsTo( "targetNodeUUID" ) String targetNodeUUID,
                                           @MapsTo( "edgeUUID" ) String edgeUUID,
                                           @MapsTo( "magnetIndex" ) Integer magnetIndex ) {
        this.edgeUUID = PortablePreconditions.checkNotNull( "edgeUUID", edgeUUID );
        this.targetNodeUUID = targetNodeUUID;
        this.magnetIndex = PortablePreconditions.checkNotNull( "magnetIndex", magnetIndex );
    }

    @SuppressWarnings( "unchecked" )
    public SetConnectionTargetNodeCommand( Node<? extends View<?>, Edge> targetNode,
                                           Edge<? extends View, Node> edge,
                                           Integer magnetIndex ) {
        this( null != targetNode ? targetNode.getUUID() : null, edge.getUUID(), magnetIndex );
        this.edge = PortablePreconditions.checkNotNull( "edge", edge );
        this.sourceNode = edge.getSourceNode();
        this.targetNode = targetNode;
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public CommandResult<RuleViolation> execute( final GraphCommandExecutionContext context ) {
        final CommandResult<RuleViolation> results = allow( context );
        if ( !results.getType().equals( CommandResult.Type.ERROR ) ) {
            final Node<?, Edge> targetNode = getTargetNode( context );
            final Edge<? extends View, Node> edge = getEdge( context );
            final Node<? extends View<?>, Edge> lastTargetNode = edge.getTargetNode();
            if ( null != lastTargetNode ) {
                lastTargetNodeUUID = lastTargetNode.getUUID();
                lastTargetNode.getInEdges().remove( edge );
            }
            if ( null != targetNode ) {
                targetNode.getInEdges().add( edge );
            }
            edge.setTargetNode( targetNode );
            ViewConnector connectionContent = ( ViewConnector ) edge.getContent();
            lastMagnetIndex = connectionContent.getTargetMagnetIndex();
            connectionContent.setTargetMagnetIndex( magnetIndex );
        }
        return results;
    }

    @SuppressWarnings( "unchecked" )
    protected CommandResult<RuleViolation> check( final GraphCommandExecutionContext context ) {
        final Node<? extends View<?>, Edge> targetNode = getTargetNode( context );
        final Edge<View<?>, Node> edge = ( Edge<View<?>, Node> ) getEdge( context );
        final GraphCommandResultBuilder resultBuilder = new GraphCommandResultBuilder();
        final Collection<RuleViolation> connectionRuleViolations =
                ( Collection<RuleViolation> ) context.getRulesManager()
                        .connection().evaluate( edge, sourceNode, targetNode ).violations();
        resultBuilder.addViolations( connectionRuleViolations );
        final Node<? extends View<?>, Edge> currentTarget = edge.getTargetNode();
        if ( null != currentTarget ) {
            final Collection<RuleViolation> cardinalityRuleViolations =
                    ( Collection<RuleViolation> ) context.getRulesManager()
                            .edgeCardinality()
                            .evaluate( edge,
                                    currentTarget,
                                    currentTarget.getInEdges(),
                                    EdgeCardinalityRule.Type.INCOMING,
                                    RuleManager.Operation.DELETE )
                            .violations();
            resultBuilder.addViolations( cardinalityRuleViolations );
        }
        if ( null != targetNode ) {
            final Collection<RuleViolation> cardinalityRuleViolations =
                    ( Collection<RuleViolation> ) context.getRulesManager()
                            .edgeCardinality()
                            .evaluate( edge,
                                    targetNode,
                                    targetNode.getInEdges(),
                                    EdgeCardinalityRule.Type.INCOMING,
                                    RuleManager.Operation.ADD )
                            .violations();
            resultBuilder.addViolations( cardinalityRuleViolations );
        }
        return resultBuilder.build();
    }

    @Override
    @SuppressWarnings( "unchecked" )
    public CommandResult<RuleViolation> undo( final GraphCommandExecutionContext context ) {
        final SetConnectionTargetNodeCommand undoCommand =
                new SetConnectionTargetNodeCommand( ( Node<? extends View<?>, Edge> ) getNode( context, lastTargetNodeUUID ),
                        getEdge( context ), lastMagnetIndex );
        return undoCommand.execute( context );
    }

    private Edge<? extends View, Node> getEdge( final GraphCommandExecutionContext context ) {
        if ( null == this.edge ) {
            this.edge = getViewEdge( context, edgeUUID );
        }
        return this.edge;
    }

    @SuppressWarnings( "unchecked" )
    private Node<? extends View<?>, Edge> getSourceNode( final GraphCommandExecutionContext context ) {
        if ( null == sourceNode ) {
            sourceNode = ( Node<? extends View<?>, Edge> ) getEdge( context ).getSourceNode();
        }
        return sourceNode;
    }

    @SuppressWarnings( "unchecked" )
    private Node<? extends View<?>, Edge> getTargetNode( final GraphCommandExecutionContext context ) {
        if ( null == targetNode ) {
            targetNode = ( Node<? extends View<?>, Edge> ) getNode( context, targetNodeUUID );
        }
        return targetNode;
    }

    public Edge<? extends View, Node> getEdge() {
        return edge;
    }

    public Integer getMagnetIndex() {
        return magnetIndex;
    }

    public Node<? extends View<?>, Edge> getTargetNode() {
        return targetNode;
    }

    public Node<? extends View<?>, Edge> getSourceNode() {
        return sourceNode;
    }

    @Override
    public String toString() {
        return "SetConnectionTargetNodeCommand [edge=" + edgeUUID
                + ", candidate=" + ( null != targetNodeUUID ? targetNodeUUID : "null" )
                + ", magnet=" + magnetIndex + "]";
    }

}
