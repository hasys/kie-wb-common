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

package org.kie.workbench.common.stunner.core.client.canvas.controls;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AbstractCanvasHandlerControlTest {

    @Mock
    private AbstractCanvasHandler handler;

    private AbstractCanvasHandlerControl control;

    @Before
    public void setup() throws Exception {
        this.control = spy(new TestAbstractCanvasHandlerControl());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testEnable() {
        control.enable(handler);

        assertTrue(control.isEnabled());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDisableWithFirstEnabling() {
        control.enable(handler);

        control.disable();

        verify(control).doDisable();

        assertFalse(control.isEnabled());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testDisableWithoutFirstEnabling() {
        control.disable();

        verify(control, never()).doDisable();

        assertFalse(control.isEnabled());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testReentrantDisable() {
        control.enable(handler);

        control.disable();

        verify(control, times(1)).doDisable();

        control.disable();

        verify(control, times(1)).doDisable();

        assertFalse(control.isEnabled());
    }

    private class TestAbstractCanvasHandlerControl extends AbstractCanvasHandlerControl {

        @Override
        protected void doDisable() {

        }

        @Override
        protected void doDestroy() {

        }

        @Override
        public void enable(Object context) {

        }
    }
}
