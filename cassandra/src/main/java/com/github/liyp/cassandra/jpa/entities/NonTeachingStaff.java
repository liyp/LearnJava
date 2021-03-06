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
