package com.github.liyp.cassandra.originaldriver;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.util.concurrent.ListenableFuture;

public class BeanDaoImpl {

    private String keyspace = "test";
    private String addresses = "172.29.88.120";// "192.168.137.199";//
                                               // 172.29.88.120

    private Cluster cluster = Cluster.builder().addContactPoints(addresses)
            .build();
    private Session session = cluster.connect(keyspace);
    private MappingManager mappingManager = new MappingManager(session);
    private Mapper<Bean> mapper = mappingManager.mapper(Bean.class);

    public void importData() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            Bean bean1 = new Bean(UUID.randomUUID(),
                    Integer.toString(i / 1000), 900000L + i, new Date());
            this.mapper.save(bean1);
        }
        System.out.println((System.currentTimeMillis() - time));
    }

    public static void main(String[] args) {
        BeanDaoImpl dao = new BeanDaoImpl();

        BeanAccessor acc = dao.mappingManager
                .createAccessor(BeanAccessor.class);

        // 0.
        Result<SimpleBean> simpleBeans = acc.getAll();

        for (SimpleBean sb : simpleBeans) {
            System.out.println(sb);
        }

        // 1.
        ListenableFuture<Result<SimpleBean>> simpleBeansF = acc.getAllAsync();

        // 2.
        // simpleBeans.all();

        SimpleBean bean = acc.getStr(UUID
                .fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
        System.out.println(bean);

        Result<SimpleBean> bean1 = acc.insertBean(
                UUID.fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"),
                new Date());
        System.out.println(bean1.one());

        ResultSet rs = acc.getStrRow(UUID
                .fromString("68dd03b8-3687-4e33-9b6d-7ce424629485"));
        System.out.println(rs.one());

        dao.session.close();
        dao.cluster.close();
    }
}
