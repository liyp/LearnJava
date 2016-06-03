/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
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

import static org.junit.Assert.*;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.liyp.cassandra.jpa.entities.UUIDBean;

public class JPATest {

    static final String MYSQL = "mysql_pu";
    static final String CASSANDRA = "cassandra_pu";
    static EntityManagerFactory emf;
    EntityManager em;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        emf = Persistence.createEntityManagerFactory(MYSQL);
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        emf.close();
    }

    @Before
    public void setUp() throws Exception {
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        em.close();
    }

    @Test
    public void test() {
        UUIDBean bean = new UUIDBean();
        bean.setUuid(UUID.randomUUID());
        bean.setId(1L);
        em.persist(bean);
        System.out.println("uuid: " + bean.getUuid());

        UUIDBean bean2 = em.find(UUIDBean.class, bean.getUuid());
        assertEquals("test for rAw", bean.getId(), bean2.getId());
    }

}
