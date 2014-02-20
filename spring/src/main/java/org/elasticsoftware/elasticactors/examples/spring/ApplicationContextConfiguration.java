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
import org.elasticsoftware.elasticactors.Asynchronous;
import org.elasticsoftware.elasticactors.ServiceActor;
import org.elasticsoftware.elasticactors.spring.ActorAnnotationBeanNameGenerator;
import org.elasticsoftware.elasticactors.test.TestActorSystem;
import org.elasticsoftware.elasticactors.test.configuration.BackplaneConfiguration;
import org.elasticsoftware.elasticactors.test.configuration.MessagingConfiguration;
import org.elasticsoftware.elasticactors.test.configuration.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Spring Context (no XML required)
 *
 * @author Leonard Wolters
 */
@Configuration
@EnableSpringConfigured
@EnableAsync(annotation = Asynchronous.class, mode = AdviceMode.ASPECTJ)
//@PropertySource(value = "file:/etc/elasticactors/system.properties")
@ComponentScan(nameGenerator = ActorAnnotationBeanNameGenerator.class,
        includeFilters = {@ComponentScan.Filter(value = {ServiceActor.class}, type = FilterType.ANNOTATION)},
        excludeFilters = {@ComponentScan.Filter(value = {Controller.class}, type = FilterType.ANNOTATION)})
public class ApplicationContextConfiguration {

    @Bean(name = "asyncExecutor")
    public java.util.concurrent.Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() * 3);
        executor.setQueueCapacity(1024);
        executor.setThreadNamePrefix("ASYNCHRONOUS-ANNOTATION-EXECUTOR-");
        executor.initialize();
        return executor;
    }
}
