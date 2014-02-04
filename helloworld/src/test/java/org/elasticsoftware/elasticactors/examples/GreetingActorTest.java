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
package org.elasticsoftware.elasticactors.examples;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.examples.helloworld.Greeting;
import org.elasticsoftware.elasticactors.examples.helloworld.GreetingActor;
import org.elasticsoftware.elasticactors.test.TestActorSystem;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Simple test case for the Hello World Actor
 */
public class GreetingActorTest {

    @Test
    public void testGreeting() throws Exception {
        TestActorSystem testActorSystem = new TestActorSystem();
        testActorSystem.initialize();

        ActorSystem actorSystem = testActorSystem.getActorSystem();

        ActorRef greeter = actorSystem.actorOf("greeter", GreetingActor.class);
        greeter.tell(new Greeting("Joost van de Wijgerd"), null);
        greeter.tell(new Greeting("Leonard Wolters"), null);

        // wait a bit before shutting down
        Thread.sleep(300);
        testActorSystem.destroy();
    }
}
