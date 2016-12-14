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

package org.kie.workbench.common.stunner.core.definition.morph;

import org.jboss.errai.common.client.api.annotations.Portable;

/**
 * The different built-in policies when morphing.
 */
@Portable
public enum MorphPolicy {
    /* Keep all potential properties that match. */
    ALL,
    /* Discard all properties. */
    NONE,
    /* Keep only node's name property. */
    DEFAULT;
}
