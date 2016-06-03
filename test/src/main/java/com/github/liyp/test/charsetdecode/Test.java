/*
 * Copyright © 2016 liyp (liyp.yunpeng@gmail.com)
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
package com.github.liyp.test.charsetdecode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Test {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        String content = "Suta%2DMBP%EF%BC%88192%2E168%2E1%2E100%EF%BC%89%E4%BD%BF%E7%94%A8%E7%AE%A1%E7%90%86%E5%91%98%E5%AF%86%E7%A0%81%E7%99%BB%E5%BD%95%E4%BA%86%E8%B7%AF%E7%94%B1%E5%99%A8%E7%AE%A1%E7%90%86%E7%95%8C%E9%9D%A2%E3%80%82";
        String content = "Suta-MBP（192.168.1.100）使用管理员密码登录了路由器管理界面。";
        content = URLDecoder.decode(content, "utf-8");
        System.out.println(content);
        
        content = URLEncoder.encode("测试APNS内容解码", "utf-8");
        System.out.println(content);
    }

}
