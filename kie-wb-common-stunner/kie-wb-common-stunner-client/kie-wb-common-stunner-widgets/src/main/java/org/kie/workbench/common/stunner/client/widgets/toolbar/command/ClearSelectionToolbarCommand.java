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

package org.kie.workbench.common.stunner.client.widgets.toolbar.command;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ClearSelectionSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.SessionCommandFactory;
import org.kie.workbench.common.stunner.core.client.session.impl.AbstractClientReadOnlySession;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class ClearSelectionToolbarCommand extends AbstractToolbarSessionCommand<AbstractClientReadOnlySession, ClearSelectionSessionCommand> {

    @Inject
    public ClearSelectionToolbarCommand( final SessionCommandFactory sessionCommandFactory ) {
        super( sessionCommandFactory.newClearSelectionCommand() );
    }

    @Override
    public IconType getIcon() {
        return IconType.BAN;
    }

    @Override
    public String getCaption() {
        return null;
    }

    // TODO: I18n.
    @Override
    public String getTooltip() {
        return "Clear selection";
    }

    @Override
    protected boolean requiresConfirm() {
        return false;
    }

}
