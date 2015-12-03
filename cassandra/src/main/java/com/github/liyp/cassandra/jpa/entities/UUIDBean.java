package com.github.liyp.cassandra.jpa.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "uuid_table")
public class UUIDBean {

    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;

    private Long id;

    private String name;

    public UUIDBean() {
    }

    public UUIDBean(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long lid) {
        this.id = lid;
    }

}
