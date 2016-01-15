package com.github.liyp.owner;

import org.aeonbits.owner.Config;

/**
 * Created by liyunpeng on 1/12/16.
 */
public interface SystemEnvProperties extends Config {

    @Key("file.separator")
    String fileSeparator();

    @Key("HOME")
    String home();

    @Key("TEST")
    String test();
}
