package org.kie.workbench.common.stunner.bpmn.backend.converters.tostunner.properties;

import java.util.Optional;

import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageEventDefinition;

public class EventDefinitionReader {
    public static String errorRefOf(ErrorEventDefinition e) {
        return Optional.ofNullable(e.getErrorRef())
                .map(Error::getErrorCode)
                .orElse("");
    }

    public static String messageRefOf(MessageEventDefinition e) {
        return Optional.ofNullable(e.getMessageRef())
                .map(Message::getName)
                .orElse("");
    }



}
