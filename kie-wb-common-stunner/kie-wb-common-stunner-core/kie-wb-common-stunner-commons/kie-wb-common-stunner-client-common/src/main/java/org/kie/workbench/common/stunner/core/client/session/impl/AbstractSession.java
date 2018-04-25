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

package org.kie.workbench.common.stunner.core.client.session.impl;

import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvas;
import org.kie.workbench.common.stunner.core.client.canvas.AbstractCanvasHandler;
import org.kie.workbench.common.stunner.core.client.canvas.controls.CanvasControl;
import org.kie.workbench.common.stunner.core.client.command.RequiresCommandManager;
import org.kie.workbench.common.stunner.core.client.session.ClientSession;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.util.UUID;
import org.uberfire.mvp.Command;

public abstract class AbstractSession<C extends AbstractCanvas, H extends AbstractCanvasHandler>
        implements ClientSession<C, H> {

    private final String uuid;

    protected AbstractSession() {
        this.uuid = UUID.uuid();
    }

    // TODO: Error handling on callback.
    public abstract void load(Metadata metadata,
                              Command callback);

    public abstract void pause();

    public abstract void open();

    public abstract void destroy();

    @SuppressWarnings("unchecked")
    protected void onControlRegistered(final CanvasControl control) {
        if (control instanceof CanvasControl.SessionAware) {
            ((CanvasControl.SessionAware) control).bind(this);
        }
    }

    @SuppressWarnings("unchecked")
    protected void onControlDestroyed(final CanvasControl control) {
        if (control instanceof CanvasControl.SessionAware) {
            ((CanvasControl.SessionAware) control).unbind();
        }
        if (control instanceof RequiresCommandManager) {
            ((RequiresCommandManager) control).setCommandManagerProvider(null);
        }
    }

    @Override
    public String getSessionUUID() {
        return uuid;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractSession)) {
            return false;
        }
        AbstractSession that = (AbstractSession) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid == null ? 0 : ~~uuid.hashCode();
    }
}
