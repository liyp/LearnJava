package com.github.liyp.cassandra.readafterwrite;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

@Accessor
public interface RAWBeanAccessor {

    @Query("SELECT * FROM test.raw_bean WHERE pk = :str")
    public Result<RAWBean> getBean(@Param("pk") String pk);

    @Query("SELECT * FROM test.raw_bean")
    public Result<RAWBean> getAll();
}
