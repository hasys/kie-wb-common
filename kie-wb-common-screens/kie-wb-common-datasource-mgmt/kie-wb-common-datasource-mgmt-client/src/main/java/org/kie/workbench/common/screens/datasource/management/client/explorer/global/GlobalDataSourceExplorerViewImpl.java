/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.screens.datasource.management.client.explorer.global;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.gwt.FlowPanel;
import org.jboss.errai.ui.shared.api.annotations.DataField;
import org.jboss.errai.ui.shared.api.annotations.Templated;
import org.kie.workbench.common.screens.datasource.management.client.explorer.common.DefExplorerContent;

@Dependent
@Templated
public class GlobalDataSourceExplorerViewImpl
        extends Composite
        implements GlobalDataSourceExplorerView {

    @Inject
    @DataField( "datasource-explorer-container")
    private FlowPanel container;

    private Presenter presenter;

    public GlobalDataSourceExplorerViewImpl() {
    }

    @Override
    public void init( final Presenter presenter ) {
        this.presenter = presenter;
    }

    @Override
    public void setDataSourceDefExplorer( final DefExplorerContent defExplorerContent ) {
        container.add( defExplorerContent );
    }
}