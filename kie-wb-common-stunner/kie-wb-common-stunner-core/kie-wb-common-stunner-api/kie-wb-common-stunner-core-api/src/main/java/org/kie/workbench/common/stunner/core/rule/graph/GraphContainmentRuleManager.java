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

package org.kie.workbench.common.stunner.core.rule.graph;

import org.kie.workbench.common.stunner.core.graph.Element;
import org.kie.workbench.common.stunner.core.graph.content.definition.Definition;
import org.kie.workbench.common.stunner.core.rule.ContainmentRuleManager;
import org.kie.workbench.common.stunner.core.rule.RuleViolations;

/**
 * Manager for containment rules specific for Stunner's graph domain.
 */
public interface GraphContainmentRuleManager extends ContainmentRuleManager {

    /**
     * It checks containment rules and evaluates if the given candidate role can be added or removed
     * into/from another node.
     * @param parent The parent node.
     * @param candidate The roles for the candidate child node.
     */
    RuleViolations evaluate( Element<?> parent, Element<? extends Definition<?>> candidate );

}
