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
@Table(name="teaching_staff")
public class TeachingStaff extends Staff {

    private static final long serialVersionUID = 1L;

    private String qualification;
    private String subjectexpertise;

    public TeachingStaff() {
        super();
    }

    public TeachingStaff(int sid, String sname, String qualification,
            String subjectexpertise) {
        super(sid, sname);
        this.qualification = qualification;
        this.subjectexpertise = subjectexpertise;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSubjectexpertise() {
        return subjectexpertise;
    }

    public void setSubjectexpertise(String subjectexpertise) {
        this.subjectexpertise = subjectexpertise;
    }

}
