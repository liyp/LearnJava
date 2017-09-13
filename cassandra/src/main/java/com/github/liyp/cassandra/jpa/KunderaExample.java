/*
 * Copyright © 2017 liyp (liyp.yunpeng@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.liyp.cassandra.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.github.liyp.cassandra.jpa.entities.User;

public class KunderaExample {
    public static void main(String[] args) {
        User user = new User();
        user.setUserId("0003");
        user.setFirstName("John");
        user.setLastName("Smith");
        user.setCity("London");

        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory("cassandra_pu"); // cassandra_pu
        EntityManager em = emf.createEntityManager();

        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            user.setUserId(String.format("%04d", i));
            // em.remove(user);
            em.persist(user);
        }
        System.out.println("####### time: "
                + (System.currentTimeMillis() - time));
        em.close();
        emf.close();
    }
}