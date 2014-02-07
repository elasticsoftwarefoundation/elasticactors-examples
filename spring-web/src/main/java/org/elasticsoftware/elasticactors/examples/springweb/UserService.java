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
import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Pretty straightforward service implementation to manage users
 *
 * @author Leonard Wolters
 */
@Service
@Named("userService")
public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);

    @Inject
    private ActorSystem actorSystem;

    /**
     * Retrieves a user
     *
     * @param uid
     * @return
     */
    public User getUser(String uid) {
        Assert.notNull(uid);
        try {
            ActorRef actorRef = actorSystem.actorOf(uid, UserActor.class);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Adds a new user
     *
     * @param uid
     * @param firstName
     * @param lastName
     * @return
     */
    public User addUser(String uid, String firstName, String lastName) {
        Assert.notNull(uid);
        try {
            ActorRef actorRef = actorSystem.actorOf(uid, UserActor.class);
            actorRef.tell(new UserMessage(firstName, lastName), null);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Updates an existing user
     *
     * @param uid
     * @param firstName
     * @param lastName
     * @return
     */
    public User updateUser(String uid, String firstName, String lastName) {
        Assert.notNull(uid);
        try {
            ActorRef actorRef = actorSystem.actorFor(uid);
            actorRef.tell(new UserMessage(firstName, lastName), null);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }
        return null;
    }
}
