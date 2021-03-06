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

package org.kie.workbench.common.stunner.core.processors;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class AbstractBindableAdapterGenerator extends AbstractAdapterGenerator {

    protected void addFields( String fieldName, Map<String, Object> ctxt, Map<String, String> fieldsMap ) {
        List<ProcessingElement> fieldNamesList = toList( fieldsMap );
        ctxt.put( fieldName + "Size",
                fieldNamesList.size() );
        ctxt.put( fieldName,
                fieldNamesList );
    }

    protected void addMultipleFields( String fieldName, Map<String, Object> ctxt, Map<String, Set<String>> fieldsMap ) {
        List<ProcessingMultipleElement> fieldNamesList = toMultipleList( fieldsMap );
        ctxt.put( fieldName + "Size",
                fieldNamesList.size() );
        ctxt.put( fieldName,
                fieldNamesList );
    }

}
