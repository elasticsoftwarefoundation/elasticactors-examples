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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
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
@ComponentScan("org.elasticsoftware.elasticactors.examples")
public class ApplicationContextConfiguration {

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
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
        view.setObjectMapper(new ObjectMapper());
        views.add(view);
        viewResolver.setDefaultViews(views);

        return viewResolver;
    }

    @Bean
    public ActorSystem actorSystem() {
//        TestActorSystem testActorSystem = new TestActorSystem();
//        testActorSystem.initialize();
//        return testActorSystem.getActorSystem();


        return null;
    }
}
