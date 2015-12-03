/**
 * Copyright (c) 2014, TP-Link Co,Ltd.
 * Author:  liyunpeng <liyunpeng@tp-link.net>
 * Updated: 2015-7-19
 */

package com.github.liyp.cassandra.originaldriver;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface PushAccessor {

    @Query("SELECT * FROM cloud.push_app_task")
    Result<PushAppTask> getAllPushAppTask();

    @Query("SELECT count(*) FROM cloud.push_app_task "
            + "WHERE oa_task_type = :oaTaskType AND "
            + "oa_task_id = :oaTaskId ALLOW FILTERING")
    ResultSet getCntInPushAppTask(@Param("oaTaskType") String oaTaskType,
            @Param("oaTaskId") String oaTaskId);

    @Query("SELECT count(*) FROM cloud.push_device_task "
            + "WHERE oa_task_type = :oaTaskType AND "
            + "oa_task_id = :oaTaskId ALLOW FILTERING")
    ResultSet getCntInPushDeviceTask(@Param("oaTaskType") String oaTaskType,
            @Param("oaTaskId") String oaTaskId);

    @Query("SELECT * FROM cloud.push_app_task WHERE status_code = ?")
    Result<PushAppTask> getPushAppTask(int statusCode);

    @Query("SELECT * FROM cloud.push_app_task "
            + "WHERE account_id = :accountId AND msg_type = :msgType AND "
            + "status_code = :statusCode ALLOW FILTERING")
    Result<PushAppTask> getPushAppTaskByMsgType(
            @Param("accountId") long accountId,
            @Param("msgType") String msgType,
            @Param("statusCode") int statusCode);

    @Query("SELECT * FROM cloud.push_device_task WHERE status_code = ?")
    Result<PushDeviceTask> getPushDeviceTask(int statusCode);

    @Query("SELECT * FROM cloud.push_device_task "
            + "WHERE device_id = :deviceId AND msg_type = :msgType AND "
            + "status_code = :statusCode ALLOW FILTERING")
    Result<PushDeviceTask> getPushDeviceTaskByMsgType(
            @Param("deviceId") String deviceId,
            @Param("msgType") String msgType,
            @Param("statusCode") int statusCode);

    @Query("SELECT * FROM cloud.push_device_task "
            + "WHERE oa_task_type = :oaTaskType AND "
            + "oa_task_id = :oaTaskId ALLOW FILTERING")
    Result<PushDeviceTask> getPushDeviceTaskByOATask(
            @Param("oaTaskType") String oaTasktype,
            @Param("oaTaskId") String oaTaskId);

    @Query("SELECT count(*) FROM cloud.push_app_task "
            + "WHERE oa_task_type = :oaTaskType AND "
            + "oa_task_id = :oaTaskId AND "
            + "status_code = :statusCode ALLOW FILTERING")
    ResultSet getStatusCntInPushAppTask(@Param("oaTaskType") String oaTaskType,
            @Param("oaTaskId") String oaTaskId,
            @Param("statusCode") int statusCode);

    @Query("SELECT count(*) FROM cloud.push_device_task "
            + "WHERE oa_task_type = :oaTaskType AND "
            + "oa_task_id = :oaTaskId AND "
            + "status_code = :statusCode ALLOW FILTERING")
    ResultSet getStatusCntInPushDeviceTask(
            @Param("oaTaskType") String oaTaskType,
            @Param("oaTaskId") String oaTaskId,
            @Param("statusCode") int statusCode);

    @Query("UPDATE cloud.push_app_task SET status_code = :statusCode "
            + "WHERE account_id = :accountId AND msg_id = :msgId")
    ResultSet updatePushAppTaskStatusById(@Param("accountId") long accountId,
            @Param("msgId") String msgId, @Param("statusCode") int statusCode);

    @Query("UPDATE cloud.push_device_task SET status_code = :statusCode "
            + "WHERE device_id = :deviceId AND msg_id = :msgId")
    ResultSet updatePushDeviceTaskStatusById(
            @Param("deviceId") String deviceId, @Param("msgId") String msgId,
            @Param("statusCode") int statusCode);

}
