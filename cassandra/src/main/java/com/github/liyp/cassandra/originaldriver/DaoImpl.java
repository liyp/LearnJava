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
package com.github.liyp.cassandra.originaldriver;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.Date;
import java.util.UUID;

public class DaoImpl {

    private Cluster cluster;
    private Session session;
    private MappingManager mappingManager;

    public DaoImpl(String addresses, String keyspace) {
        cluster = Cluster.builder().addContactPoints(addresses).build();
        session = cluster.connect(keyspace);
        mappingManager = new MappingManager(session);
    }

    public MappingManager getMappingManager() {
        return mappingManager;
    }

    public void importData(int num) {
        Mapper<Bean> mapper = mappingManager.mapper(Bean.class);
        long time = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            Bean bean1 = new Bean(UUID.randomUUID(), Integer.toString(i / 100),
                    900000L + i, new Date());
            mapper.save(bean1);
        }
        System.out.println((System.currentTimeMillis() - time));
    }

    public void testAccessor() {
        BeanAccessor acc = mappingManager.createAccessor(BeanAccessor.class);
        UUID uuid = UUID.randomUUID();
        ResultSet r = acc.insertBean(uuid, new Date());
        System.out.println(r.getExecutionInfo().getQueryTrace());
        SimpleBean sb = acc.getStr(uuid);
        System.out.println(sb);
    }

    public void testMapper4Accessor() {
        BeanAccessor acc = mappingManager.createAccessor(BeanAccessor.class);
        int cnt = 10000;
        while (cnt-- >= 0) {
            SimpleBean bean = acc.getStr(UUID
                    .fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
            System.out.println(bean);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testBeanMapper() {
        Mapper<SimpleBean> mapper = mappingManager.mapper(SimpleBean.class);
        int cnt = 10000;
        while (cnt-- >= 0) {
            SimpleBean bean = mapper.get(UUID
                    .fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
            System.out.println(bean);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testPushBean() {
        PushAccessor pushAcc = mappingManager
                .createAccessor(PushAccessor.class);
        Result<PushAppTask> rs = pushAcc.getAllPushAppTask();
        for (PushAppTask pat : rs) {
            System.out.println(pat.getAccountId());
        }
    }

    public void testCassandraUpdateDelay(int cnt) {
        Mapper<SimpleBean> mapper = mappingManager.mapper(SimpleBean.class);
        for (int i = 1; i <= cnt * 100; i++) {
            SimpleBean bean = new SimpleBean();
            bean.setId(UUID.fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
            bean.setStr(String.valueOf(i));
            mapper.save(bean);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SimpleBean bean2 = mapper.get(UUID
                    .fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
            if (!bean2.getStr().equals(bean.getStr())) {
                System.out.println("\nupdate delay ...| " + bean + " | "
                        + bean2);
            }
            if (i % 1000 == 0) {
                System.out.print(i + ".");
            }
        }
    }

    public void testMapAccessor() {
        MapAccessor mapAcc = mappingManager.createAccessor(MapAccessor.class);
        ResultSet rs = mapAcc.getMapColumn("id002");
        System.out.println(rs.iterator().hasNext());
        System.out.println(rs.one().getMap("c_map", String.class, Object.class));
    }

    public void testChangeChema() {
        BeanAccessor acc = mappingManager.createAccessor(BeanAccessor.class);

        UUID uuid = UUID.randomUUID();
        ResultSet r = acc.insertBean(uuid, new Date());
        System.out.println(r.getExecutionInfo().getQueryTrace());
        while (true) {
            try {
                Thread.sleep(2000);
                SimpleBean sb = acc.getStr(uuid);
                System.out.println(sb);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("=====: "+cluster.getMetadata().exportSchemaAsString());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        DaoImpl dao = new DaoImpl("127.0.0.1", "test");
        try {
            dao.testChangeChema();
        } finally {
            dao.cluster.close();
        }
    }
}
