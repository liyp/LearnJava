package com.github.liyp.owner;

import org.aeonbits.owner.Accessible;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Reloadable;

/**
 * Created by liyunpeng on 1/12/16.
 */

public interface SystemEnvProperties extends Config, Accessible, Reloadable {

    @Key("file.separator")
    String fileSeparator();

    @Key("HOME")
    String home();

    @Key("TEST")
    String test();
}
