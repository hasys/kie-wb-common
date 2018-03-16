/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.project.client.editor;

import java.util.Optional;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.guvnor.common.services.project.client.context.WorkspaceProjectContext;
import org.guvnor.common.services.project.client.security.ProjectController;
import org.guvnor.common.services.project.model.WorkspaceProject;
import org.guvnor.messageconsole.client.console.widget.button.AlertsButtonMenuItemBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.client.widgets.presenters.session.SessionPresenterFactory;
import org.kie.workbench.common.stunner.core.client.api.SessionManager;
import org.kie.workbench.common.stunner.core.client.error.DiagramClientErrorHandler;
import org.kie.workbench.common.stunner.core.client.i18n.ClientTranslationService;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ClearSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.CopySelectionSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.CutSelectionSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.DeleteSelectionSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ExportToBpmnSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ExportToJpgSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ExportToPdfSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ExportToPngSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.PasteSelectionSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.RedoSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.SessionCommandFactory;
import org.kie.workbench.common.stunner.core.client.session.command.impl.SwitchGridSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.UndoSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.ValidateSessionCommand;
import org.kie.workbench.common.stunner.core.client.session.command.impl.VisitGraphSessionCommand;
import org.kie.workbench.common.stunner.project.client.editor.event.OnDiagramFocusEvent;
import org.kie.workbench.common.stunner.project.client.editor.event.OnDiagramLoseFocusEvent;
import org.kie.workbench.common.stunner.project.client.screens.ProjectMessagesListener;
import org.kie.workbench.common.stunner.project.client.service.ClientProjectDiagramService;
import org.kie.workbench.common.widgets.client.menu.FileMenuBuilderImpl;
import org.kie.workbench.common.widgets.metadata.client.validation.AssetUpdateValidator;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.type.ClientResourceType;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.ext.editor.commons.client.file.popups.SavePopUpPresenter;
import org.uberfire.ext.editor.commons.client.history.VersionRecordManager;
import org.uberfire.mocks.EventSourceMock;
import org.uberfire.mvp.Command;
import org.uberfire.workbench.model.menu.MenuItem;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class AbstractProjectDiagramEditorTest {

    @Mock
    protected AbstractProjectDiagramEditor.View view;

    @Mock
    protected PlaceManager placeManager;

    @Mock
    protected ErrorPopupPresenter errorPopupPresenter;

    @Mock
    protected EventSourceMock<ChangeTitleWidgetEvent> changeTitleNotificationEvent;

    @Mock
    protected SavePopUpPresenter savePopUpPresenter;

    @Mock
    protected ClientProjectDiagramService clientProjectDiagramService;

    @Mock
    protected SessionManager sessionManager;

    @Mock
    protected SessionPresenterFactory sessionPresenterFactory;

    @Mock
    protected EventSourceMock<OnDiagramFocusEvent> onDiagramFocusEvent;

    @Mock
    protected EventSourceMock<OnDiagramLoseFocusEvent> onDiagramLostFocusEvent;

    @Mock
    protected VersionRecordManager versionRecordManager;

    @Spy
    @InjectMocks
    protected FileMenuBuilderImpl fileMenuBuilder;

    @Mock
    protected ProjectDiagramEditorMenuItemsBuilder projectMenuItemsBuilder;

    @Mock
    protected ProjectController projectController;

    @Mock
    protected WorkspaceProjectContext workbenchContext;

    @Mock
    protected SessionCommandFactory sessionCommandFactory;

    @Mock
    protected ProjectMessagesListener projectMessagesListener;

    protected ClientResourceType resourceType;

    @Mock
    protected DiagramClientErrorHandler diagramClientErrorHandler;

    @Mock
    protected ClientTranslationService translationService;

    @Mock
    protected AlertsButtonMenuItemBuilder alertsButtonMenuItemBuilder;

    @Mock
    protected MenuItem alertsButtonMenuItem;

    abstract class ClientResourceTypeMock implements ClientResourceType {

    }

    protected AbstractProjectDiagramEditor<ClientResourceTypeMock> presenter;

    @Before
    public void setUp() {
        doReturn(mock(SwitchGridSessionCommand.class)).when(sessionCommandFactory).newSwitchGridCommand();
        doReturn(mock(VisitGraphSessionCommand.class)).when(sessionCommandFactory).newVisitGraphCommand();
        doReturn(mock(ClearSessionCommand.class)).when(sessionCommandFactory).newClearCommand();
        doReturn(mock(DeleteSelectionSessionCommand.class)).when(sessionCommandFactory).newDeleteSelectedElementsCommand();
        doReturn(mock(UndoSessionCommand.class)).when(sessionCommandFactory).newUndoCommand();
        doReturn(mock(RedoSessionCommand.class)).when(sessionCommandFactory).newRedoCommand();
        doReturn(mock(ValidateSessionCommand.class)).when(sessionCommandFactory).newValidateCommand();
        doReturn(mock(ExportToPngSessionCommand.class)).when(sessionCommandFactory).newExportToPngSessionCommand();
        doReturn(mock(ExportToJpgSessionCommand.class)).when(sessionCommandFactory).newExportToJpgSessionCommand();
        doReturn(mock(ExportToPdfSessionCommand.class)).when(sessionCommandFactory).newExportToPdfSessionCommand();
        doReturn(mock(ExportToBpmnSessionCommand.class)).when(sessionCommandFactory).newExportToBpmnSessionCommand();
        doReturn(mock(CopySelectionSessionCommand.class)).when(sessionCommandFactory).newCopySelectionCommand();
        doReturn(mock(PasteSelectionSessionCommand.class)).when(sessionCommandFactory).newPasteSelectionCommand();
        doReturn(mock(CutSelectionSessionCommand.class)).when(sessionCommandFactory).newCutSelectionCommand();

        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newClearItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newVisitGraphItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newSwitchGridItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newDeleteSelectionItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newUndoItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newRedoItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newValidateItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newExportsItem(any(Command.class), any(Command.class), any(Command.class), any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newPasteItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newCopyItem(any(Command.class));
        doReturn(mock(MenuItem.class)).when(projectMenuItemsBuilder).newCutItem(any(Command.class));

        when(alertsButtonMenuItemBuilder.build()).thenReturn(alertsButtonMenuItem);

        resourceType = mockResourceType();
        presenter = createDiagramEditor();
        presenter.init();
    }

    protected ClientResourceType mockResourceType() {
        return mock(ClientResourceTypeMock.class);
    }

    protected ClientResourceType getResourceType() {
        return resourceType;
    }

    protected AbstractProjectDiagramEditor createDiagramEditor() {
        return new AbstractProjectDiagramEditor<ClientResourceTypeMock>(view,
                                                                        placeManager,
                                                                        errorPopupPresenter,
                                                                        changeTitleNotificationEvent,
                                                                        savePopUpPresenter,
                                                                        (ClientResourceTypeMock) getResourceType(),
                                                                        clientProjectDiagramService,
                                                                        sessionManager,
                                                                        sessionPresenterFactory,
                                                                        sessionCommandFactory,
                                                                        projectMenuItemsBuilder,
                                                                        onDiagramFocusEvent,
                                                                        onDiagramLostFocusEvent,
                                                                        projectMessagesListener,
                                                                        diagramClientErrorHandler,
                                                                        translationService) {
            {
                fileMenuBuilder = AbstractProjectDiagramEditorTest.this.fileMenuBuilder;
                workbenchContext = AbstractProjectDiagramEditorTest.this.workbenchContext;
                projectController = AbstractProjectDiagramEditorTest.this.projectController;
                versionRecordManager = AbstractProjectDiagramEditorTest.this.versionRecordManager;
                alertsButtonMenuItemBuilder = AbstractProjectDiagramEditorTest.this.alertsButtonMenuItemBuilder;
            }

            @Override
            protected int getCanvasWidth() {
                return 0;
            }

            @Override
            protected int getCanvasHeight() {
                return 0;
            }

            @Override
            protected String getEditorIdentifier() {
                return null;
            }
        };
    }

    @Test
    public void testMakeMenuBar() {
        doReturn(Optional.of(mock(WorkspaceProject.class))).when(workbenchContext).getActiveWorkspaceProject();
        doReturn(true).when(projectController).canUpdateProject(any());

        presenter.makeMenuBar();

        verify(fileMenuBuilder).addSave(any(MenuItem.class));
        verify(fileMenuBuilder).addCopy(any(Path.class),
                                        any(AssetUpdateValidator.class));
        verify(fileMenuBuilder).addRename(any(Path.class),
                                          any(AssetUpdateValidator.class));
        verify(fileMenuBuilder).addDelete(any(Path.class),
                                          any(AssetUpdateValidator.class));
        verify(fileMenuBuilder).addNewTopLevelMenu(alertsButtonMenuItem);
    }

    @Test
    public void testMakeMenuBarWithoutUpdateProjectPermission() {
        doReturn(Optional.of(mock(WorkspaceProject.class))).when(workbenchContext).getActiveWorkspaceProject();
        doReturn(false).when(projectController).canUpdateProject(any());

        presenter.makeMenuBar();

        verify(fileMenuBuilder,
               never()).addSave(any(MenuItem.class));
        verify(fileMenuBuilder,
               never()).addCopy(any(Path.class),
                                any(AssetUpdateValidator.class));
        verify(fileMenuBuilder,
               never()).addRename(any(Path.class),
                                  any(AssetUpdateValidator.class));
        verify(fileMenuBuilder,
               never()).addDelete(any(Path.class),
                                  any(AssetUpdateValidator.class));
        verify(fileMenuBuilder).addNewTopLevelMenu(alertsButtonMenuItem);
    }

    @Test
    public void testFormatTitle() {
        String title = "testDiagram";
        when(resourceType.getSuffix()).thenReturn("bpmn");
        when(resourceType.getShortName()).thenReturn("Business Process");

        String formattedTitle = presenter.formatTitle(title);
        assertEquals(formattedTitle,
                     "testDiagram.bpmn - Business Process");
    }
}
