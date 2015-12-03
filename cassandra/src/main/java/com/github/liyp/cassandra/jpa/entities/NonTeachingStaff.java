package com.github.liyp.cassandra.jpa.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="non_teaching_staff")
public class NonTeachingStaff extends Staff {

    private static final long serialVersionUID = 1L;

    private String areaexpertise;

    public NonTeachingStaff() {
        super();
    }

    public NonTeachingStaff(int sid, String sname, String areaexperitise) {
        super(sid, sname);
        this.setAreaexpertise(areaexperitise);
    }

    public String getAreaexpertise() {
        return areaexpertise;
    }

    public void setAreaexpertise(String areaexpertise) {
        this.areaexpertise = areaexpertise;
    }

}
