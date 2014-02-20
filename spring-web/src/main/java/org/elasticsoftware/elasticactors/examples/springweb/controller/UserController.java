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
package org.elasticsoftware.elasticactors.examples.springweb.controller;

import org.apache.log4j.Logger;
import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.ActorSystem;
import org.elasticsoftware.elasticactors.base.actors.ActorDelegate;
import org.elasticsoftware.elasticactors.base.actors.ReplyActor;
import org.elasticsoftware.elasticactors.examples.springweb.actor.User;
import org.elasticsoftware.elasticactors.examples.springweb.dto.UserDTO;
import org.elasticsoftware.elasticactors.examples.springweb.message.Create;
import org.elasticsoftware.elasticactors.examples.springweb.message.Retrieve;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

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
    private ActorSystem actorSystem;

    @ResponseBody
    @RequestMapping(value = "/user/{uid}", method = {RequestMethod.GET, RequestMethod.HEAD, RequestMethod.OPTIONS})
    public DeferredResult<UserDTO> getUser(@PathVariable String uid) throws Exception {
        log.info(String.format("Retrieving user[%s]", uid));
        final DeferredResult<UserDTO> result = new DeferredResult<>();
        ActorRef replyRef = actorSystem.tempActorOf(ReplyActor.class, new ActorDelegate<UserDTO>() {
            @Override
            public ActorDelegate<UserDTO> getBody() {
                return this;
            }

            @Override
            public void onReceive(ActorRef sender, UserDTO message) throws Exception {
                result.setResult(message);
            }
        });
        ActorRef user = actorSystem.actorOf(uid, User.class);
        user.tell(new Retrieve(uid), replyRef);
        return result;
    }
}
