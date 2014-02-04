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

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Pretty straightforward service implementation to manage users
 *
 * @author Leonard Wolters
 */
@Service
public class UserService {
    private ConcurrentHashMap<String, UserState> users = new ConcurrentHashMap<>();

    public UserState getUser(String uid) {
        return users.get(uid);
    }

    public boolean addUser(UserState user) {
        Assert.notNull(user);
        Assert.notNull(user.getUid());
        
        return users.putIfAbsent(user.getUid(), user) == null;
    }

    public UserState updateUser(UserState user) {
        Assert.notNull(user);
        Assert.notNull(user.getUid());

        UserState p = users.get(user.getUid());
        if(p != null) {
            // update user, of course you can also use replace method
            if(StringUtils.hasText(user.getFirstName())) {
                p.setFirstName(user.getFirstName());
            }
            if(StringUtils.hasText(user.getLastName())) {
                p.setLastName(user.getLastName());
            }
            return p;
        }

        // add new user
        users.putIfAbsent(user.getUid(), user);
        return user;
    }
}
