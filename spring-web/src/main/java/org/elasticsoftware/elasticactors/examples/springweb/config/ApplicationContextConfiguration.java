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
package org.elasticsoftware.elasticactors.examples.springweb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsoftware.elasticactors.Asynchronous;
import org.elasticsoftware.elasticactors.test.configuration.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.PathExtensionContentNegotiationStrategy;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring Context (no XML required)
 *
 * @author Leonard Wolters
 */
@Configuration
@EnableSpringConfigured
@EnableAsync(annotation = Asynchronous.class, mode = AdviceMode.ASPECTJ)
@Import({TestConfiguration.class})
@ComponentScan({"org.elasticsoftware.elasticactors.base", "org.elasticsoftware.elasticactors.examples.springweb"})
public class ApplicationContextConfiguration {

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ObjectMapper objectMapper) {
        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setUseNotAcceptableStatusCode(true);

        // set content negotiation manager
        Map<String,MediaType> mediaTypes = new HashMap<>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        PathExtensionContentNegotiationStrategy strategy = new PathExtensionContentNegotiationStrategy(mediaTypes);
        ContentNegotiationManager manager = new ContentNegotiationManager(strategy);
        viewResolver.setContentNegotiationManager(manager);

        // set views
        List<View> views = new ArrayList<>();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        view.setExtractValueFromSingleKeyModel(true);
        view.setObjectMapper(objectMapper);
        views.add(view);
        viewResolver.setDefaultViews(views);

        return viewResolver;
    }

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
