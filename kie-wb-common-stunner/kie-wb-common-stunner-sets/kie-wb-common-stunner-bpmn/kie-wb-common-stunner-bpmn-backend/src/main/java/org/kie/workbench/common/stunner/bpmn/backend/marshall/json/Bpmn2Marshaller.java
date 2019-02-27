/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.backend.marshall.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import bpsim.impl.BpsimFactoryImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.lang3.StringEscapeUtils;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.util.Bpmn2Resource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.jboss.drools.impl.DroolsFactoryImpl;
import org.jbpm.designer.bpmn2.impl.Bpmn2JsonUnmarshaller;
import org.jbpm.designer.bpmn2.resource.JBPMBpmn2ResourceFactoryImpl;
import org.jbpm.designer.bpmn2.resource.JBPMBpmn2ResourceImpl;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.oryx.OryxManager;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.BPMN2JsonParser;
import org.kie.workbench.common.stunner.bpmn.backend.marshall.json.parser.ParsingContext;
import org.kie.workbench.common.stunner.core.api.DefinitionManager;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bpmn2Marshaller extends Bpmn2JsonUnmarshaller {

    private static final Logger _logger = LoggerFactory.getLogger(Bpmn2Marshaller.class);
    private final DefinitionManager definitionManager;
    private final OryxManager oryxManager;

    public Bpmn2Marshaller(final DefinitionManager definitionManager,
                           final OryxManager oryxManager) {
        this.definitionManager = definitionManager;
        this.oryxManager = oryxManager;
    }

    public String marshall(final Diagram<Graph, Metadata> diagram,
                           final String preProcessingData) throws IOException {
        JBPMBpmn2ResourceImpl res = marshallToBpmn2Resource(diagram, preProcessingData);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        res.save(outputStream,
                 new HashMap<>());
        return StringEscapeUtils.unescapeHtml4(outputStream.toString("UTF-8"));
    }

    public JBPMBpmn2ResourceImpl marshallToBpmn2Resource(final Diagram<Graph, Metadata> diagram,
                                                         final String preProcessingData) throws IOException {
        DroolsFactoryImpl.init();
        BpsimFactoryImpl.init();
        BPMN2JsonParser parser = createParser(diagram);
        return (JBPMBpmn2ResourceImpl) unmarshall(parser, preProcessingData);
    }

    private BPMN2JsonParser createParser(final Diagram<Graph, Metadata> diagram) {
        return new BPMN2JsonParser(diagram,
                                   new ParsingContext(definitionManager,
                                                      oryxManager));
    }

    /**
     * NOTE: This method has been set protected for Stunner support. Stunner bpmn implementation provides a custom JsonParser that
     * is used instead of the one used in jbpm-designer-backend.
     * <p>
     * Start unmarshalling using the parser.
     * @param parser
     * @param preProcessingData
     * @return the root element of a bpmn2 document.
     * @throws JsonParseException
     * @throws IOException
     */
    protected Bpmn2Resource unmarshall(JsonParser parser,
                                       String preProcessingData) throws IOException {
        Bpmn2Resource currentResource = null;
        try {
            parser.nextToken(); // open the object
            ResourceSet rSet = new ResourceSetImpl();
            rSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("bpmn2",
                                                                             new JBPMBpmn2ResourceFactoryImpl());
            Bpmn2Resource bpmn2 = (Bpmn2Resource) rSet.createResource(URI.createURI("virtual.bpmn2"));
            rSet.getResources().add(bpmn2);
            currentResource = bpmn2;
            if (preProcessingData == null || preProcessingData.length() < 1) {
                preProcessingData = "ReadOnlyService";
            }
            // do the unmarshalling now:
            Definitions def = (Definitions) unmarshallItem(parser,
                                                           preProcessingData);
            def.setExporter(exporterName);
            def.setExporterVersion(exporterVersion);
            revisitUserTasks(def);
            revisitServiceTasks(def);
            revisitMessages(def);
            revisitCatchEvents(def);
            revisitThrowEvents(def);
            revisitLanes(def);
            revisitSubProcessItemDefs(def);
            revisitArtifacts(def);
            revisitGroups(def);
            revisitTaskAssociations(def);
            revisitSendReceiveTasks(def);
            reconnectFlows();
            revisitGateways(def);
            revisitCatchEventsConvertToBoundary(def);
            revisitBoundaryEventsPositions(def);
            createDiagram(def);
            updateIDs(def);
            revisitDataObjects(def);
            revisitAssociationsIoSpec(def);
            revisitWsdlImports(def);
            revisitMultiInstanceTasks(def);
            addSimulation(def);
            revisitItemDefinitions(def);
            revisitProcessDoc(def);
            revisitDI(def);
            revisitSignalRef(def);
            orderDiagramElements(def);
            // return def;
            currentResource.getContents().add(def);
            return currentResource;
        } catch (Exception e) {
            _logger.error(e.getMessage());
            return currentResource;
        } finally {
            parser.close();
            clearResources();
        }
    }


}
