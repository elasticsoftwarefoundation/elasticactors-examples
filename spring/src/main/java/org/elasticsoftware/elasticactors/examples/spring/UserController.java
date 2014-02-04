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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Leonard Wolters
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS})
    public UserState getUser(@PathVariable String uid) {
        return userService.getUser(uid);
    }

    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.POST})
    public UserState updateUser(@PathVariable String uid,
                           @RequestParam(required = false) String firstName,
                           @RequestParam(required = false) String lastName) {
        UserState user = new UserState(uid);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return userService.updateUser(user);
    }
}
