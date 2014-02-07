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
package org.elasticsoftware.elasticactors.examples.spring;

import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.test.TestActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring Context (no XML required)
 *
 * @author Leonard Wolters
 */
@Configuration
@EnableSpringConfigured
@ComponentScan("org.elasticsoftware.elasticactors.examples.spring")
public class ApplicationContextConfiguration {

    @Bean
    public ActorSystem actorSystem() {
        TestActorSystem testActorSystem = new TestActorSystem();
        testActorSystem.initialize();
        return testActorSystem.getActorSystem();
    }
}
