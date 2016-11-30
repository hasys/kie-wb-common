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

package org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.common;

import java.io.IOException;

import org.codehaus.jackson.JsonToken;

public class IntegerFieldParser extends AbstractParser {

    private final String name;
    private final int value;

    public IntegerFieldParser( String name, int value ) {
        this.name = name;
        this.value = value;
    }

    @Override
    protected JsonToken next() throws IOException {
        return tokenCount == 0 ? JsonToken.FIELD_NAME : JsonToken.VALUE_NUMBER_INT;
    }

    @Override
    public String getCurrentName() throws IOException {
        return name;
    }

    @Override
    public String getText() throws IOException {
        throw new RuntimeException( "Should not be called!" );
    }

    @Override
    public int getIntValue() throws IOException {
        return value;
    }

    @Override
    public boolean isConsumed() {
        return tokenCount == 2;
    }

}
