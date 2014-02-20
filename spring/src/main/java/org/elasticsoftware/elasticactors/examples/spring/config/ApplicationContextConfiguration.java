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
package org.elasticsoftware.elasticactors.examples.spring.config;

import org.elasticsoftware.elasticactors.test.configuration.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;

/**
 * Spring Context (no XML required)
 *
 * @author Leonard Wolters
 */
@Configuration
@EnableSpringConfigured
@Import({TestConfiguration.class})
@ComponentScan({"org.elasticsoftware.elasticactors.base", "org.elasticsoftware.elasticactors.examples.spring"})
public class ApplicationContextConfiguration {

    // you can define your own beans, or simply use a component scan
}