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

package org.kie.workbench.common.stunner.core.client.shape.view.event;

import java.util.LinkedList;
import java.util.List;

import com.google.gwt.event.shared.HandlerRegistration;

public class HandlerRegistrationImpl implements HandlerRegistration {
    private final List<HandlerRegistration> m_list = new LinkedList<>();

    public HandlerRegistrationImpl() {
    }

    public HandlerRegistrationImpl( HandlerRegistration handler, HandlerRegistration... handlers ) {
        this.register( handler );
        int len$ = handlers.length;
        for ( int i$ = 0; i$ < len$; ++i$ ) {
            HandlerRegistration h = handlers[ i$ ];
            this.register( h );
        }

    }

    public final int size() {
        return this.m_list.size();
    }

    public final boolean isEmpty() {
        return this.m_list.isEmpty();
    }

    public final HandlerRegistrationImpl destroy() {
        int size = this.size();
        for ( int i = 0; i < size; ++i ) {
            this.m_list.get(i).removeHandler();
        }
        return this.clear();
    }

    public final HandlerRegistration register( HandlerRegistration handler ) {
        if ( null != handler && !this.m_list.contains( handler ) ) {
            this.m_list.add( handler );
        }
        return handler;
    }

    public final boolean isRegistered( HandlerRegistration handler ) {
        return null != handler && this.size() > 0 && this.m_list.contains( handler );
    }

    public final HandlerRegistrationImpl deregister( HandlerRegistration handler ) {
        if ( null != handler ) {
            if ( this.size() > 0 && this.m_list.contains( handler ) ) {
                this.m_list.remove( handler );
            }
            handler.removeHandler();
        }
        return this;
    }

    public final HandlerRegistrationImpl clear() {
        this.m_list.clear();
        return this;
    }

    public void removeHandler() {
        this.destroy();
    }
}
