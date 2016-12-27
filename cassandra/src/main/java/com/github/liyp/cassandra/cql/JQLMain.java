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
package com.github.liyp.cassandra.cql;

import java.util.Date;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Using;


/**
 * Created by liyunpeng on 12/11/15.
 */
public class JQLMain {

    static Cluster cluster = Cluster.builder().addContactPoint("172.31.1.151").build();
    static Session session = cluster.connect();

    void insert(PushAppTask task) {
        Insert insert = QueryBuilder.insertInto("cloud", "push_app_task")
                .value("account_id", task.getAccountId())
                .value("msg_id", task.getMsgId())
                .value("app_key", task.getAppKey())
                .value("expiry", task.getExpiry())
                .value("method", task.getMethod())
                .value("msg_type", task.getMsgType())
                .value("oa_task_id", task.getOaTaskId())
                .value("oa_task_type", task.getOaTaskType())
                .value("payload", task.getPayload())
                .value("retries", task.getRetries())
                .value("status_code", task.getStatusCode())
                .value("time", task.getTime());
        Using ttlUsing = QueryBuilder.ttl(20000);
        Statement statement = insert.using(ttlUsing);
        System.out.println("## QUERY STRING: \n" + insert.getQueryString());
        System.out.println(statement.toString());
        ResultSet result;
        result = session.execute(statement);
        System.out.println(result.getExecutionInfo());
        result = session.execute(statement.toString());
        System.out.println(result.wasApplied());

    }

    public static void main(String[] args) {

        PushAppTask task = new PushAppTask();
        task.setAccountId(100);
        task.setMsgId("msgId1212");
        task.setStatusCode(0);
        task.setTime(new Date());
        new JQLMain().insert(task);

        cluster.close();
    }
}
