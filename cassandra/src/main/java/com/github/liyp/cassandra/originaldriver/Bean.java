package com.github.liyp.cassandra.originaldriver;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

/**
 * create table test.bean ( id uuid primary key, str text, l bigint, date
 * timestamp );
 */
@Table(name = "bean", keyspace = "test")
public class Bean {

    @PartitionKey
    private UUID id;

    private String str;

    private Long l;

    private Date date;

    public Bean() {
    }

    public Bean(UUID id, String str, Long l, Date date) {
        super();
        this.id = id;
        this.str = str;
        this.l = l;
        this.date = date;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Long getL() {
        return l;
    }

    public void setL(Long l) {
        this.l = l;
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
        builder.append("Bean [id=");
        builder.append(id);
        builder.append(", str=");
        builder.append(str);
        builder.append(", l=");
        builder.append(l);
        builder.append(", date=");
        builder.append(date);
        builder.append("]");
        return builder.toString();
    }
}
