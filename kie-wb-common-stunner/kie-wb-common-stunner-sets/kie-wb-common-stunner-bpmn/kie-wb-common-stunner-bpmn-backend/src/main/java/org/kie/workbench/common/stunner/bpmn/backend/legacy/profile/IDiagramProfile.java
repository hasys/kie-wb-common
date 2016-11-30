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
package org.kie.workbench.common.stunner.bpmn.backend.legacy.profile;

import java.util.Collection;

import javax.servlet.ServletContext;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.emf.ecore.resource.Resource;
import org.kie.workbench.common.stunner.bpmn.backend.legacy.repository.Repository;

/**
 * A profile for the editor to choose which stencilset
 * and which plugins should be loaded.
 *
 * @author Antoine Toulme
 */
public interface IDiagramProfile {

    /**
     * @return the name of the profile
     * it will be passed by the user when opening the editor.
     */
    String getName();

    /**
     * @return the title of the profile.
     */
    String getTitle();

    /**
     * @return the stencil set used by the profile.
     */
    String getStencilSet();

    /**
     * @return the stencil set extensions used by the profile
     */
    Collection<String> getStencilSetExtensions();

    String getSerializedModelExtension();

    /**
     * @return the stencil url used by the profile.
     */
    String getStencilSetURL();

    /**
     * @return stencil namespace url.
     */
    String getStencilSetNamespaceURL();

    /**
     * @return stencil set extension url used by the profile.
     */
    String getStencilSetExtensionURL();

    /**
     * @return the plugins to load for the profile.
     */
    Collection<String> getPlugins();

    /**
     * @return a classLoader to transform the json into the final model.
     */
    IDiagramMarshaller createMarshaller();

    /**
     * @return an unmarshaller to transform the model into the json.
     */
    IDiagramUnmarshaller createUnmarshaller();

    String getRepositoryGlobalDir();

    String getRepositoryGlobalDir( String uuid );

    /**
     * @return the local history enabled.
     */
    String getLocalHistoryEnabled();

    /**
     * @return the local history timeout.
     */
    String getLocalHistoryTimeout();

    /**
     * @return the store svg on save option.
     */
    String getStoreSVGonSaveOption();

    /**
     * @return the repository.
     */
    Repository getRepository();

    /**
     * Parser to produce the final model to be saved.
     *
     * @author Antoine Toulme
     */
    interface IDiagramMarshaller {

        /**
         * @param jsonModel the model
         * @return the string representation of the serialized model.
         */
        String parseModel( String jsonModel, String preProcessingData ) throws Exception;

        Definitions getDefinitions(String jsonModel, String preProcessingData) throws Exception;

        Resource getResource( String jsonModel, String preProcessingData ) throws Exception;
    }

    /**
     * Parser to produce the final model to be saved.
     *
     * @author Tihomir Surdilovic
     */
    interface IDiagramUnmarshaller {

        /**
         * @param xmlModel xml model
         * @param profile  process profile.
         * @return the json model
         */
        String parseModel( String xmlModel, IDiagramProfile profile, String preProcessingData ) throws Exception;
    }

    void init( ServletContext context );
}
