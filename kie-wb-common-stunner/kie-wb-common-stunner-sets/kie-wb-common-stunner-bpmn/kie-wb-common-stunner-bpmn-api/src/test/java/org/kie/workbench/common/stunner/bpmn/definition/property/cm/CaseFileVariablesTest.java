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

package org.kie.workbench.common.stunner.bpmn.definition.property.cm;

import org.junit.Before;
import org.junit.Test;
import org.kie.workbench.common.stunner.bpmn.definition.property.type.VariablesType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class CaseFileVariablesTest {

    private String _value;
    private String _rawValue;

    private CaseFileVariables _tested;

    @Before
    public void setup() {
        _value = "CFV1:Boolean,CFV2:Boolean,CFV3:Boolean";
        _rawValue = CaseFileVariables.CASE_FILE_PREFIX + "CFV1:Boolean," +
                CaseFileVariables.CASE_FILE_PREFIX + "CFV2:Boolean," +
                CaseFileVariables.CASE_FILE_PREFIX + "CFV3:Boolean";
        _tested = new CaseFileVariables(_value);
    }

    @Test
    public void testGetType() {
        assertEquals(new VariablesType(), _tested.getType());
    }

    @Test
    public void testGetValue() {
        assertEquals(_value, _tested.getValue());
    }

    @Test
    public void testGetRawValue() {
        assertEquals(_tested.getRawValue(), _rawValue);
    }

    @Test
    public void testHashCode() {
        assertEquals(_tested.hashCode(), -1359743347);
    }

    @Test
    public void testEquals() {
        CaseFileVariables otherEqual = new CaseFileVariables(_value);
        assertFalse(_tested.equals(null));
        assertEquals(_tested, _tested);
        assertEquals(otherEqual, _tested);
        assertEquals(_tested, otherEqual);

        CaseFileVariables otherNotEqual = new CaseFileVariables();
        assertNotEquals(new Object(), _tested);
        assertNotEquals(_tested, new Object());
        assertNotEquals(otherNotEqual, _tested);
        assertNotEquals(_tested, otherNotEqual);
    }
}
