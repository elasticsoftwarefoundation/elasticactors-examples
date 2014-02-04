/*
 * Copyright 2013 - 2014 The Original Authors
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

package org.elasticsoftware.elasticactors.examples.pi;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.examples.pi.actors.Listener;
import org.elasticsoftware.elasticactors.examples.pi.actors.Master;
import org.elasticsoftware.elasticactors.test.TestActorSystem;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

/**
 * @author Joost van de Wijgerd
 */
public class PiApproximatorTest {
    private static final Logger log = Logger.getLogger(PiApproximatorTest.class);
    private TestActorSystem testActorSystem;

    @BeforeMethod(enabled = true)
    public void setUp() {
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure();
        Logger.getLogger("org.elasticsoftware").setLevel(Level.DEBUG);

        testActorSystem = new TestActorSystem();
        testActorSystem.initialize();
    }

    @AfterMethod(enabled = true)
    public void tearDown() {
        if(testActorSystem != null) {
            testActorSystem.destroy();
            testActorSystem = null;
        }
        BasicConfigurator.resetConfiguration();
    }

    @Test(enabled = true)
    public void testInContainer() throws Exception {
        // make sure http system is loaded
        ActorSystem testSystem = testActorSystem.getActorSystem("examples");
        ActorRef listener = testSystem.actorOf("pi/calculate",Listener.class,new Listener.State());
        testSystem.actorOf("master",Master.class,new Master.State(listener,16,10000,10));

        AsyncHttpClient httpClient = new AsyncHttpClient();
        ListenableFuture<Response> responseFuture = httpClient.prepareGet("http://localhost:9080/pi/calculate").execute();
        Response response = responseFuture.get();
        assertEquals(response.getContentType(),"application/json");
        log.info(response.getResponseBody("UTF-8"));
    }
}
