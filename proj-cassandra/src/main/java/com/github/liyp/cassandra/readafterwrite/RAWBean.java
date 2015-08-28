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
