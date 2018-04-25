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

import javax.enterprise.event.Event;

import org.kie.workbench.common.stunner.client.widgets.presenters.session.impl.SessionEditorPresenter;
import org.kie.workbench.common.stunner.core.client.error.DiagramClientErrorHandler;
import org.kie.workbench.common.stunner.core.client.i18n.ClientTranslationService;
import org.kie.workbench.common.stunner.core.client.session.impl.EditorSession;
import org.kie.workbench.common.stunner.project.client.editor.event.OnDiagramFocusEvent;
import org.kie.workbench.common.stunner.project.client.editor.event.OnDiagramLoseFocusEvent;
import org.kie.workbench.common.stunner.project.client.screens.ProjectMessagesListener;
import org.kie.workbench.common.stunner.project.client.service.ClientProjectDiagramService;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.client.workbench.events.ChangeTitleWidgetEvent;
import org.uberfire.client.workbench.type.ClientResourceType;
import org.uberfire.client.workbench.widgets.common.ErrorPopupPresenter;
import org.uberfire.ext.editor.commons.client.file.popups.SavePopUpPresenter;

class ProjectDiagramEditorStub extends AbstractProjectDiagramEditor<ClientResourceType> {

    static final String EDITOR_ID = "ProjectDiagramEditorStub";

    public ProjectDiagramEditorStub(View view,
                                    PlaceManager placeManager,
                                    ErrorPopupPresenter errorPopupPresenter,
                                    Event<ChangeTitleWidgetEvent> changeTitleNotificationEvent,
                                    SavePopUpPresenter savePopUpPresenter,
                                    ClientResourceType resourceType,
                                    ClientProjectDiagramService projectDiagramServices,
                                    SessionEditorPresenter<EditorSession> presenter,
                                    ProjectEditorMenuSessionItems menuSessionItems,
                                    Event<OnDiagramFocusEvent> onDiagramFocusEvent,
                                    Event<OnDiagramLoseFocusEvent> onDiagramLostFocusEvent,
                                    ProjectMessagesListener projectMessagesListener,
                                    DiagramClientErrorHandler diagramClientErrorHandler,
                                    ClientTranslationService translationService) {
        super(view,
              placeManager,
              errorPopupPresenter,
              changeTitleNotificationEvent,
              savePopUpPresenter,
              resourceType,
              projectDiagramServices,
              presenter,
              menuSessionItems,
              onDiagramFocusEvent,
              onDiagramLostFocusEvent,
              projectMessagesListener,
              diagramClientErrorHandler,
              translationService);
    }

    @Override
    protected int getCanvasWidth() {
        return 100;
    }

    @Override
    protected int getCanvasHeight() {
        return 100;
    }

    @Override
    protected String getEditorIdentifier() {
        return EDITOR_ID;
    }
}
