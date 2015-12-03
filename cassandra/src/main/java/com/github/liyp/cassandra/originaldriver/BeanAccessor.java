package com.github.liyp.cassandra.originaldriver;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;
import com.google.common.util.concurrent.ListenableFuture;

@Accessor
public interface BeanAccessor {

    @Query("SELECT * FROM test.bean WHERE str = :str")
    public Result<SimpleBean> getBean(@Param("str") String str);

    @Query("SELECT * FROM test.bean")
    public Result<SimpleBean> getAll();

    @Query("SELECT * FROM test.bean")
    public ListenableFuture<Result<SimpleBean>> getAllAsync();

    @Query("SELECT * FROM test.bean WHERE id = ? ")
    public SimpleBean getStr(UUID id);

    @Query("SELECT * FROM test.bean WHERE id = ? ")
    public ResultSet getStrRow(UUID id);

    @Query("INSERT INTO test.bean (id, date) values(:id, :date)")
    public Result<SimpleBean> insertBean(@Param("id") UUID id,
            @Param("date") Date date);

    @Query("UPDATE test.bean SET str = :str WHERE id = :id")
    ResultSet updateBean(@Param("id") UUID id, @Param("str") String str);

    @Query("SELECT count(*) FROM test.bean")
    public ResultSet getCntInBean();
    
    @Query("alter table bean alter l type int")
    public ResultSet exec();

}
