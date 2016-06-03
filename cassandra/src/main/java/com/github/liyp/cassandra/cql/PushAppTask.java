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

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.io.Serializable;
import java.util.Date;

@Table(keyspace = "cloud", name = "push_app_task", readConsistency = "ONE",
        writeConsistency = "ONE")
public class PushAppTask implements Serializable {

    private static final long serialVersionUID = -8340788709870645932L;

    @PartitionKey
    @Column(name = "account_id")
    private long accountId;

    /**
     * push message id
     */
    @ClusteringColumn
    @Column(name = "msg_id")
    private String msgId;

    @Column(name = "app_key")
    private String appKey;

    @Column(name = "msg_type")
    private String msgType;

    /**
     * push message payload
     */
    private String payload;

    private Date time;

    /**
     * expired time (seconds)
     */
    private Integer expiry;

    /**
     * number of retries
     */
    private Integer retries;

    private String method;

    @Column(name = "oa_task_id")
    private String oaTaskId;

    @Column(name = "oa_task_type")
    private String oaTaskType;

    /**
     * push message status code
     */
    @Column(name = "status_code")
    private Integer statusCode;

    public PushAppTask() {
    }

    public PushAppTask(long accountId, String msgId, String appKey,
                       String msgType, String payload, Date time, Integer expiry,
                       Integer retries, String method, String oaTaskId, String oaTaskType,
                       Integer statusCode) {
        super();
        this.accountId = accountId;
        this.msgId = msgId;
        this.appKey = appKey;
        this.msgType = msgType;
        this.payload = payload;
        this.time = time;
        this.expiry = expiry;
        this.retries = retries;
        this.method = method;
        this.oaTaskId = oaTaskId;
        this.oaTaskType = oaTaskType;
        this.statusCode = statusCode;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getExpiry() {
        return expiry;
    }

    public void setExpiry(Integer expiry) {
        this.expiry = expiry;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOaTaskId() {
        return oaTaskId;
    }

    public void setOaTaskId(String oaTaskId) {
        this.oaTaskId = oaTaskId;
    }

    public String getOaTaskType() {
        return oaTaskType;
    }

    public void setOaTaskType(String oaTaskType) {
        this.oaTaskType = oaTaskType;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PushMessageForApp [accountId=");
        builder.append(accountId);
        builder.append(", msgId=");
        builder.append(msgId);
        builder.append(", appKey=");
        builder.append(appKey);
        builder.append(", msgType=");
        builder.append(msgType);
        builder.append(", payload=");
        builder.append(payload);
        builder.append(", time=");
        builder.append(time);
        builder.append(", expiry=");
        builder.append(expiry);
        builder.append(", retries=");
        builder.append(retries);
        builder.append(", method=");
        builder.append(method);
        builder.append(", oaTaskId=");
        builder.append(oaTaskId);
        builder.append(", oaTaskType=");
        builder.append(oaTaskType);
        builder.append(", statusCode=");
        builder.append(statusCode);
        builder.append("]");
        return builder.toString();
    }

}
