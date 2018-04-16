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

package org.kie.workbench.common.stunner.project.test.client;

import java.util.Collection;

import com.google.gwt.core.client.ScriptInjector;
import org.junit.Test;
import org.kie.workbench.common.screens.home.model.HomeModel;
import org.kie.workbench.common.stunner.project.client.home.HomeProducer;

import static org.jboss.errai.ioc.client.container.IOC.getBeanManager;

public class InitialTest extends AbstractErraiCDITest {

    @Override
    public String getModuleName() {
        return "org.kie.workbench.common.stunner.project.test.InitialTest";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        ScriptInjector.fromUrl("js/jquery-1.12.0.min.cache.js").setWindow(ScriptInjector.TOP_WINDOW).inject();
        super.gwtSetUp();
    }

    @Test
    public void testIt() {
        System.out.println("Hello world");
        Collection collection = getBeanManager().lookupBeans(Object.class);
        collection.forEach(System.out::println);
        System.out.println("Amount of beans: " + collection.size());
        HomeProducer homeScreen = getBeanManager().lookupBean(HomeProducer.class).getInstance();

        HomeModel model = homeScreen.get();
        System.out.println(model.getWelcome());
        System.out.println(model.getDescription());
        System.out.println(model.getBackgroundImageUrl());
        System.out.println("Hello final world");
    }
}
