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

package org.kie.workbench.common.stunner.core.rule.impl.model;

import org.kie.workbench.common.stunner.core.rule.DefaultRuleViolations;
import org.kie.workbench.common.stunner.core.rule.DockingRule;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;
import org.kie.workbench.common.stunner.core.rule.impl.AbstractDockingRuleManager;
import org.kie.workbench.common.stunner.core.rule.impl.violations.ContainmentRuleViolation;
import org.kie.workbench.common.stunner.core.rule.model.ModelDockingRuleManager;

import javax.enterprise.context.Dependent;
import java.util.HashSet;
import java.util.Set;

@Dependent
public class ModelDockingRuleManagerImpl extends AbstractDockingRuleManager<String, Set<String>> implements ModelDockingRuleManager {

    private static final String NAME = "Domain Model Docking Rule Manager";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public RuleViolations evaluate( final String targetId,
                                     final Set<String> candidateRoles ) {
        if ( rules.isEmpty() ) {
            return new DefaultRuleViolations();
        }
        final DefaultRuleViolations results = new DefaultRuleViolations();
        for ( DockingRule rule : rules ) {
            if ( rule.getId().equals( targetId ) ) {
                final Set<String> permittedStrings = new HashSet<String>( rule.getPermittedRoles() );
                permittedStrings.retainAll( candidateRoles );
                if ( permittedStrings.size() > 0 ) {
                    return results;
                }
            }
        }
        results.addViolation( new ContainmentRuleViolation( targetId, candidateRoles.toString() ) );
        return results;
    }

}
