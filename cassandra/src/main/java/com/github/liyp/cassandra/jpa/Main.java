package com.github.liyp.cassandra.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.github.liyp.cassandra.jpa.entities.NonTeachingStaff;
import com.github.liyp.cassandra.jpa.entities.TeachingStaff;
import com.github.liyp.cassandra.jpa.entities.UUIDBean;

public class Main {

    public static final String MYSQL = "mysql_pu";
    public static final String CASSANDRA = "cassandra_pu";

    interface JpaDoable {
        public void doIt(EntityManager em);
    }

    static JpaDoable d0 = new JpaDoable() {

        @Override
        public void doIt(EntityManager em) {
            // Teaching staff entity
            TeachingStaff ts1 = new TeachingStaff(1, "Gopal", "MSc MEd",
                    "Maths");
            TeachingStaff ts2 = new TeachingStaff(2, "Manisha", "BSc BEd",
                    "English");
            // Non-Teaching Staff entity
            NonTeachingStaff nts1 = new NonTeachingStaff(3, "Satish",
                    "Accounts");
            NonTeachingStaff nts2 = new NonTeachingStaff(4, "Krishna",
                    "Office Admin");

            // storing all entities
            em.persist(ts1);
            em.persist(ts2);
            em.persist(nts1);
            em.persist(nts2);
        }
    };

    static void exec(final String puType, JpaDoable doIt) {
        EntityManagerFactory emf = Persistence
                .createEntityManagerFactory(puType);
        EntityManager em = emf.createEntityManager();

        doIt.doIt(em);

        em.close();
        emf.close();
    }

    public static void main(String[] args) {

        exec(MYSQL, new JpaDoable() {

            @Override
            public void doIt(EntityManager em) {
                UUIDBean bean = new UUIDBean();
                bean.setUuid(UUID.randomUUID());
                bean.setId(1L);
                em.persist(bean);

                System.out.println("uuid: " + bean.getUuid());

                UUIDBean bean2 = em.find(UUIDBean.class, bean.getUuid());
                System.out.println("xxx " + bean2.getUuid());
            }
        });

    }

}
