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
package org.elasticsoftware.elasticactors.examples.springweb.actor;

import org.apache.log4j.Logger;
import org.elasticsoftware.elasticactors.Actor;
import org.elasticsoftware.elasticactors.ActorRef;
import org.elasticsoftware.elasticactors.TypedActor;
import org.elasticsoftware.elasticactors.examples.springweb.dto.UserDTO;
import org.elasticsoftware.elasticactors.examples.springweb.message.Retrieve;

import static java.lang.String.format;

/**
 * @author Leonard Wolters
 */
@Actor
public class User extends TypedActor<Retrieve> {
    private static final Logger log = Logger.getLogger(User.class);

    @Override
    public void onReceive(ActorRef sender, Retrieve message) throws Exception {
        log.info(format("onCreate: %s", message.getUid()));

        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("The");
        userDTO.setLastName("Duke");
        sender.tell(userDTO, getSelf());
    }
}
