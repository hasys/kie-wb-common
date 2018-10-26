/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.bpmn.backend.converters.tostunner.properties;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Property;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.bpmn.definition.property.cm.CaseFileVariables;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CaseFileVariableReaderTest {

    private List<Property> _properties;

    @Mock
    protected Property _property1;
    @Mock
    protected Property _property2;
    @Mock
    protected Property _property3;
    @Mock
    protected Property _property4;

    @Mock
    protected ItemDefinition _definition;

    @Before
    public void setup() {
        _properties = new ArrayList<>();
        _properties.add(_property1);
        _properties.add(_property2);
        _properties.add(_property3);
        _properties.add(_property4);

        when(_property1.getName()).thenReturn(CaseFileVariables.CASE_FILE_PREFIX + "CFV1");
        when(_property1.getId()).thenReturn(CaseFileVariables.CASE_FILE_PREFIX + "CFV1");
        when(_property1.getItemSubjectRef()).thenReturn(_definition);

        when(_property2.getName()).thenReturn(null);
        when(_property2.getId()).thenReturn(CaseFileVariables.CASE_FILE_PREFIX + "CFV2");
        when(_property2.getItemSubjectRef()).thenReturn(_definition);

        when(_definition.getStructureRef()).thenReturn("Boolean");

        when(_property3.getName()).thenReturn("PV1");
        when(_property3.getId()).thenReturn("PV1");

        when(_property4.getName()).thenReturn(null);
        when(_property4.getId()).thenReturn("PV2");
    }

    @Test
    public void getCaseFileVariables() {
        String caseFileVariables = CaseFileVariableReader.getCaseFileVariables(_properties);
        assertEquals(caseFileVariables, "CFV1:Boolean,CFV2:Boolean");
    }

    @Test
    public void isCaseFileVariable() {
        boolean isCaseFile1 = CaseFileVariableReader.isCaseFileVariable(_property1);
        assertEquals(isCaseFile1, true);

        boolean isCaseFile2 = CaseFileVariableReader.isCaseFileVariable(_property2);
        assertEquals(isCaseFile2, true);

        boolean isCaseFile3 = CaseFileVariableReader.isCaseFileVariable(_property3);
        assertEquals(isCaseFile3, false);

        boolean isCaseFile4 = CaseFileVariableReader.isCaseFileVariable(_property4);
        assertEquals(isCaseFile4, false);
    }
}
