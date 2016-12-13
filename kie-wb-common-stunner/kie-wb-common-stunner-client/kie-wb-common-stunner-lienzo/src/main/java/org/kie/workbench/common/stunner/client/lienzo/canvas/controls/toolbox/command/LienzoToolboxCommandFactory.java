/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.client.lienzo.canvas.controls.toolbox.command;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import com.ait.lienzo.client.core.shape.Shape;
import org.kie.workbench.common.stunner.client.lienzo.util.SVGUtils;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.ToolboxCommandFactory;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.actions.MoveShapeDownToolboxCommand;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.actions.MoveShapeUpToolboxCommand;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.actions.RemoveToolboxCommand;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.builder.NewConnectorCommand;
import org.kie.workbench.common.stunner.core.client.canvas.controls.toolbox.command.builder.NewNodeCommand;

@ApplicationScoped
public class LienzoToolboxCommandFactory extends ToolboxCommandFactory {

    private final Instance<RemoveToolboxCommand> removeToolboxCommands;
    private final Instance<MoveShapeUpToolboxCommand> moveShapeUpToolboxCommands;
    private final Instance<MoveShapeDownToolboxCommand> moveShapeDownToolboxCommands;

    @Inject
    public LienzoToolboxCommandFactory(final Instance<RemoveToolboxCommand> removeToolboxCommands,
                                       final Instance<MoveShapeUpToolboxCommand> moveShapeUpToolboxCommands,
                                       final Instance<MoveShapeDownToolboxCommand> moveShapeDownToolboxCommands,
                                       final Instance<NewNodeCommand> newNodeCommands,
                                       final Instance<NewConnectorCommand> newConnectorCommands) {
        super(newNodeCommands, newConnectorCommands);
        this.removeToolboxCommands = removeToolboxCommands;
        this.moveShapeUpToolboxCommands = moveShapeUpToolboxCommands;
        this.moveShapeDownToolboxCommands = moveShapeDownToolboxCommands;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RemoveToolboxCommand<?> newRemoveToolboxCommand() {
        final RemoveToolboxCommand<Shape<?>> c = removeToolboxCommands.get();
        c.setIcon(SVGUtils.createSVGIcon(SVGUtils.getTrashIcon()));
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MoveShapeUpToolboxCommand<?> newMoveShapeUpToolboxCommand() {
        final MoveShapeUpToolboxCommand<Shape<?>> c = moveShapeUpToolboxCommands.get();
        c.setIcon(SVGUtils.createSVGIcon(SVGUtils.getMoveUpIcon()));
        return c;
    }

    @Override
    @SuppressWarnings("unchecked")
    public MoveShapeDownToolboxCommand<?> newMoveShapeDownToolboxCommand() {
        final MoveShapeDownToolboxCommand<Shape<?>> c = moveShapeDownToolboxCommands.get();
        c.setIcon(SVGUtils.createSVGIcon(SVGUtils.getMoveDownIcon()));
        return c;
    }
}
