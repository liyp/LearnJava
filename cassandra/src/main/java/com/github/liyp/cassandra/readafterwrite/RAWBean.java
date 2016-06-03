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
package com.github.liyp.cassandra.readafterwrite;

import java.util.Date;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

@Table(name = "raw_bean", keyspace = "test")
public class RAWBean {

    @PartitionKey
    @Column(name = "pk")
    private String partitionKey;

    @ClusteringColumn
    @Column(name = "ck")
    private String clusteringKey;

    private String data;

    private Date date;

    public RAWBean(String partitionKey, String clusteringKey, String data,
            Date date) {
        super();
        this.partitionKey = partitionKey;
        this.clusteringKey = clusteringKey;
        this.data = data;
        this.date = date;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getClusteringKey() {
        return clusteringKey;
    }

    public void setClusteringKey(String clusteringKey) {
        this.clusteringKey = clusteringKey;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RAWBean [partitionKey=");
        builder.append(partitionKey);
        builder.append(", clusteringKey=");
        builder.append(clusteringKey);
        builder.append(", data=");
        builder.append(data);
        builder.append(", date=");
        builder.append(date);
        builder.append("]");
        return builder.toString();
    }

}
