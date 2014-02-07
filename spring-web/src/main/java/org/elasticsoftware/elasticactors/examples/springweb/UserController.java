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
package org.elasticsoftware.elasticactors.examples.springweb;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;

/**
 * Simple (Web) controller for demo purposes only
 *
 * @author Leonard Wolters
 */
@Controller
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    @Inject
    private UserService userService;

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS})
    public User getUser(@PathVariable String uid) {
        log.info(String.format("Retrieving user[%s]", uid));
        return userService.getUser(uid);
    }

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.POST})
    public User addUser(@PathVariable String uid,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName) {
        log.info(String.format("Add new user[%s] -> %s, %s", uid, firstName, lastName));
        return userService.addUser(uid, firstName, lastName);
    }

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.PUT})
    public User updateUser(@PathVariable String uid,
                             @RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String lastName) {
        log.info(String.format("Updating user[%s] -> %s, %s", uid, firstName, lastName));
        return userService.updateUser(uid, firstName, lastName);
    }
}
