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

import org.elasticsoftware.elasticactors.spring.AnnotationConfigApplicationContext;
import org.testng.annotations.Test;

/**
 * @author Leonard Wolters
 */
public class UserServiceTest {

    @Test
    public void testUserService() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(ApplicationContextConfiguration.class);
        ctx.scan("org.elasticsoftware.elasticactors.examples");
        ctx.refresh();

        UserService userService = ctx.getBean(UserService.class);
        userService.addUser("lwolters", "Leonard", "Wolters");
    }
}
