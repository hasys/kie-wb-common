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

package org.kie.workbench.common.stunner.core.graph.command.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.command.CommandResult;
import org.kie.workbench.common.stunner.core.command.exception.BadCommandArgumentsException;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.rule.*;
import org.kie.workbench.common.stunner.core.rule.impl.violations.CardinalityMaxRuleViolation;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith( MockitoJUnitRunner.class )
public class DeleteNodeCommandTest extends AbstractGraphCommandTest {

    private static final String UUID = "nodeUUID";

    private Node node;
    private DeleteNodeCommand tested;

    @Before
    public void setup() throws Exception {
        super.init( 500, 500 );
        node = mockNode( UUID );
        graphNodes.add( node );
        when( graphIndex.getNode( eq( UUID ) )).thenReturn( node );
        this.tested = new DeleteNodeCommand( UUID );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testAllow() {
        CommandResult<RuleViolation> result = tested.allow( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.INFO, result.getType() );
        verify( cardinalityRuleManager, times( 1 ) ).evaluate( eq( graph ), eq( node ), eq( RuleManager.Operation.DELETE ) );
        verify( edgeCardinalityRuleManager, times( 0 ) ).evaluate( any( Edge.class ), any( Node.class ),
                any( List.class ), any( EdgeCardinalityRule.Type.class ), any( RuleManager.Operation.class ) );
        verify( containmentRuleManager, times( 0 ) ).evaluate( any( Element.class ), any( Element.class ) );
        verify( connectionRuleManager, times( 0 ) ).evaluate( any( Edge.class ), any( Node.class ), any( Node.class ) );
        verify( dockingRuleManager, times( 0 ) ).evaluate( any( Element.class ), any( Element.class ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testAllowNoRules() {
        when( graphCommandExecutionContext.getRulesManager() ).thenReturn( null );
        CommandResult<RuleViolation> result = tested.allow( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.INFO, result.getType() );
        verify( cardinalityRuleManager, times( 0 ) ).evaluate( eq( graph ), eq( node ), eq( RuleManager.Operation.DELETE ) );
        verify( edgeCardinalityRuleManager, times( 0 ) ).evaluate( any( Edge.class ), any( Node.class ),
                any( List.class ), any( EdgeCardinalityRule.Type.class ), any( RuleManager.Operation.class ) );
        verify( containmentRuleManager, times( 0 ) ).evaluate( any( Element.class ), any( Element.class ) );
        verify( connectionRuleManager, times( 0 ) ).evaluate( any( Edge.class ), any( Node.class ), any( Node.class ) );
        verify( dockingRuleManager, times( 0 ) ).evaluate( any( Element.class ), any( Element.class ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testNotAllowed() {
        final RuleViolations FAILED_VIOLATIONS = new DefaultRuleViolations()
                .addViolation( new CardinalityMaxRuleViolation( "target", "candidate", 1, 2 ) );
        when( cardinalityRuleManager.evaluate(any( Graph.class ), any( Node.class ), any( RuleManager.Operation.class ) ) )
                .thenReturn( FAILED_VIOLATIONS );
        CommandResult<RuleViolation> result = tested.allow( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.ERROR, result.getType() );
    }


    @Test( expected = BadCommandArgumentsException.class )
    @SuppressWarnings( "unchecked" )
    public void testNodeNotPresentOnStorage() {
        graphNodes.clear();
        when( graphIndex.getNode( eq( UUID ) )).thenReturn( null );
        CommandResult<RuleViolation> result = tested.allow( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.ERROR, result.getType() );
    }

    @Test( expected = BadCommandArgumentsException.class )
    @SuppressWarnings( "unchecked" )
    public void testNodeNotPresentOnIndex() {
        when( graphIndex.getNode( eq( UUID ) )).thenReturn( null );
        CommandResult<RuleViolation> result = tested.allow( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.ERROR, result.getType() );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testExecute() {
        CommandResult<RuleViolation> result = tested.execute( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.INFO, result.getType() );
        verify( graph, times( 1 ) ).removeNode(  eq( UUID ) );
        verify( graphIndex, times( 1 ) ).removeNode(  eq( node ) );
        verify( graphIndex, times( 0 ) ).removeEdge(  any( Edge.class ) );
        verify( graphIndex, times( 0 ) ).addEdge( any( Edge.class ) );
        verify( graphIndex, times( 0 ) ).addNode( any( Node.class ) );
    }

    @Test
    @SuppressWarnings( "unchecked" )
    public void testExecuteCheckFailed() {
        final RuleViolations FAILED_VIOLATIONS = new DefaultRuleViolations()
                .addViolation( new CardinalityMaxRuleViolation( "target", "candidate", 1, 2 ) );
        when( cardinalityRuleManager.evaluate(any( Graph.class ), any( Node.class ), any( RuleManager.Operation.class ) ) )
                .thenReturn( FAILED_VIOLATIONS );
        CommandResult<RuleViolation> result = tested.execute( graphCommandExecutionContext );
        assertEquals( CommandResult.Type.ERROR, result.getType() );
        verify( graphIndex, times( 0 ) ).removeNode(  any( Node.class ) );
        verify( graphIndex, times( 0 ) ).removeEdge( any( Edge.class ) );
        verify( graphIndex, times( 0 ) ).addEdge( any( Edge.class ) );
        verify( graphIndex, times( 0 ) ).addNode( any( Node.class ) );
    }

}
