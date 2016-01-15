package com.github.liyp.cassandra.originaldriver;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Created by liyunpeng on 1/14/16.
 */

@Accessor
public interface MapAccessor {

    @Query("select c_map from test.tbl where id = :id")
    ResultSet getMapColumn(@Param("id") String id);

}
