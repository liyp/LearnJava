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
package cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * Created by liyunpeng on 10/10/16.
 */
public class CacheMain {

    private static Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1, TimeUnit.DAYS)
                                                      .build();

    public static void main(String[] args) {
        String vv = "";
        try {
            vv = cache.get("a", new Callable<String>() {
                public String call() throws Exception {
                    return null;
                }
            });
        } catch (ExecutionException e) {
            vv = "";
        }
        System.out.println(vv);
    }
}
