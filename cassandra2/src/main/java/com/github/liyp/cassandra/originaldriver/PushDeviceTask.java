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
package com.github.liyp.cassandra.originaldriver;

import java.io.Serializable;
import java.util.Date;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(keyspace = "cloud", name = "push_device_task", readConsistency = "ONE",
        writeConsistency = "ONE")
public class PushDeviceTask implements Serializable {

    private static final long serialVersionUID = -8140739153977238506L;

    @PartitionKey
    @Column(name = "device_id")
    private String deviceId;

    /** push message id */
    @ClusteringColumn
    @Column(name = "msg_id")
    private String msgId;

    /** push message payload */
    private String payload;

    @Column(name = "msg_type")
    private String msgType;

    private Date time;

    /** expired time (seconds) */
    private Integer expiry;

    /** number of retries */
    private Integer retries;

    private String method;

    @Column(name = "firmware_id")
    private String firmwareId;

    @Column(name = "oa_task_id")
    private String oaTaskId;

    @Column(name = "oa_task_type")
    private String oaTaskType;

    /** push message status code */
    @Column(name = "status_code")
    private Integer statusCode;

    public PushDeviceTask() {
    }

    public PushDeviceTask(String deviceId, String msgId, String payload,
            String msgType, Date time, Integer expiry, Integer retries,
            String method, String firmwareId, String oaTaskId,
            String oaTaskType, Integer statusCode) {
        super();
        this.deviceId = deviceId;
        this.msgId = msgId;
        this.payload = payload;
        this.msgType = msgType;
        this.time = time;
        this.expiry = expiry;
        this.retries = retries;
        this.method = method;
        this.firmwareId = firmwareId;
        this.oaTaskId = oaTaskId;
        this.oaTaskType = oaTaskType;
        this.statusCode = statusCode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
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

    public String getFirmwareId() {
        return firmwareId;
    }

    public void setFirmwareId(String firmwareId) {
        this.firmwareId = firmwareId;
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
        builder.append("PushDeviceTask [deviceId=");
        builder.append(deviceId);
        builder.append(", msgId=");
        builder.append(msgId);
        builder.append(", payload=");
        builder.append(payload);
        builder.append(", msgType=");
        builder.append(msgType);
        builder.append(", time=");
        builder.append(time);
        builder.append(", expiry=");
        builder.append(expiry);
        builder.append(", retries=");
        builder.append(retries);
        builder.append(", method=");
        builder.append(method);
        builder.append(", firmwareId=");
        builder.append(firmwareId);
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
