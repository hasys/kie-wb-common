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

package org.kie.workbench.common.stunner.bpmn.backend.legacy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationProvider {

    public static final String CONFIG_FILE = "/designer.configuration";

    private Properties configurationProps = new Properties();

    private static ConfigurationProvider instance;

    private ConfigurationProvider() {
        try {
            InputStream input = this.getClass().getResourceAsStream( CONFIG_FILE );
            if ( input != null ) {
                configurationProps.load( input );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static ConfigurationProvider getInstance() {
        if ( instance == null ) {
            instance = new ConfigurationProvider();
        }
        return instance;
    }

    public String getDesignerContext() {
        return configurationProps.getProperty( "application.context", "/org.jbpm.designer.jBPMDesigner/" );
    }
}
