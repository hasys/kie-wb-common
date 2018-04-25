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

package org.kie.workbench.common.stunner.bpmn.project.client.editor;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Typed;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.IsWidget;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;
import org.gwtbootstrap3.client.ui.DropDownMenu;
import org.gwtbootstrap3.client.ui.constants.ButtonSize;
import org.gwtbootstrap3.client.ui.constants.IconPosition;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Pull;
import org.gwtbootstrap3.client.ui.constants.Toggle;
import org.kie.workbench.common.stunner.bpmn.project.client.resources.BPMNClientConstants;
import org.kie.workbench.common.stunner.client.widgets.menu.MenuUtils;
import org.kie.workbench.common.stunner.core.client.service.ClientRuntimeError;
import org.kie.workbench.common.stunner.core.client.session.command.AbstractClientSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.ClientSessionCommand;
import org.kie.workbench.common.stunner.project.client.editor.ProjectDiagramEditorMenuItemsBuilder;
import org.kie.workbench.common.stunner.project.client.editor.ProjectEditorMenuSessionItems;
import org.kie.workbench.common.widgets.client.menu.FileMenuBuilder;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuItem;

@Dependent
@Typed(BPMNProjectEditorMenuSessionItems.class)
public class BPMNProjectEditorMenuSessionItems extends ProjectEditorMenuSessionItems {

    private Command onMigrate;

    @Inject
    public BPMNProjectEditorMenuSessionItems(final ProjectDiagramEditorMenuItemsBuilder itemsBuilder,
                                             final BPMNEditorSessionCommands sessionCommands) {
        super(itemsBuilder,
              sessionCommands);
        this.onMigrate = () -> {
        };
    }

    public BPMNProjectEditorMenuSessionItems setOnMigrate(final Command onMigrate) {
        this.onMigrate = onMigrate;
        return this;
    }

    @Override
    public void populateMenu(final FileMenuBuilder menu) {
        super.populateMenu(menu);
        menu.addNewTopLevelMenu(newFormsGenerationMenuItem(() -> executeFormsCommand(getBPMNCommands().getGenerateProcessFormsSessionCommand()),
                                                           () -> executeFormsCommand(getBPMNCommands().getGenerateDiagramFormsSessionCommand()),
                                                           () -> executeFormsCommand(getBPMNCommands().getGenerateSelectedFormsSessionCommand())))
                .addNewTopLevelMenu(newMigrateMenuItem(onMigrate));
    }

    @Override
    public void destroy() {
        super.destroy();
        onMigrate = null;
    }

    public MenuItem newMigrateMenuItem(final Command migrateCommand) {
        return MenuUtils.buildItem(
                new Button() {{
                    setSize(ButtonSize.SMALL);
                    setText(getTranslationService().getValue(BPMNClientConstants.EditorMigrateActionMenu));
                    addClickHandler(clickEvent -> migrateCommand.execute());
                }});
    }

    public MenuItem newFormsGenerationMenuItem(final Command generateProcessForm,
                                               final Command generateAllForms,
                                               final Command generateSelectedForms) {
        final DropDownMenu menu = new DropDownMenu() {{
            setPull(Pull.RIGHT);
        }};

        menu.add(new AnchorListItem(getTranslationService().getValue(BPMNClientConstants.EditorGenerateProcessForm)) {{
            setIcon(IconType.LIST_ALT);
            setIconPosition(IconPosition.LEFT);
            setTitle(getTranslationService().getValue(BPMNClientConstants.EditorGenerateProcessForm));
            addClickHandler(event -> generateProcessForm.execute());
        }});
        menu.add(new AnchorListItem(getTranslationService().getValue(BPMNClientConstants.EditorGenerateAllForms)) {{
            setIcon(IconType.LIST_ALT);
            setIconPosition(IconPosition.LEFT);
            setTitle(getTranslationService().getValue(BPMNClientConstants.EditorGenerateAllForms));
            addClickHandler(event -> generateAllForms.execute());
        }});
        menu.add(new AnchorListItem(getTranslationService().getValue(BPMNClientConstants.EditorGenerateSelectionForms)) {{
            setIcon(IconType.LIST_ALT);
            setIconPosition(IconPosition.LEFT);
            setTitle(getTranslationService().getValue(BPMNClientConstants.EditorGenerateSelectionForms));
            addClickHandler(event -> generateSelectedForms.execute());
        }});
        final IsWidget group = new ButtonGroup() {{
            add(new Button() {{
                setToggleCaret(true);
                setDataToggle(Toggle.DROPDOWN);
                setIcon(IconType.LIST_ALT);
                setSize(ButtonSize.SMALL);
                setTitle(getTranslationService().getValue(BPMNClientConstants.EditorFormGenerationTitle));
            }});
            add(menu);
        }};
        return MenuUtils.buildItem(group);
    }

    private void executeFormsCommand(final AbstractClientSessionCommand command) {
        loadingStarts();
        command.execute(new ClientSessionCommand.Callback<ClientRuntimeError>() {
            @Override
            public void onSuccess() {
                loadingCompleted();
            }

            @Override
            public void onError(final ClientRuntimeError error) {
                BPMNProjectEditorMenuSessionItems.this.onError(error.getMessage());
            }
        });
    }

    private BPMNEditorSessionCommands getBPMNCommands() {
        return (BPMNEditorSessionCommands) getCommands();
    }
}
