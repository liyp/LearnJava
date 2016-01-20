package com.github.liyp.owner;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Reloadable;

/**
 * Created by liyunpeng on 1/20/16.
 */

@Config.Sources({
        "classpath:owner.properties"
})
public interface OwnerProperties extends Accessible, Reloadable {

    @Config.Key("key.a")
    String keyA();

    @Config.Key("key.b")
    String keyB();

}
