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
import java.util.List;

/**
 * Created by liyunpeng on 6/25/16.
 */
public class PojoEntity {

    public String str;

    public int i;
    public Integer i1;

    public List<String> strArr;

    public long[] longs;

    public InternalClazz internalClazz;

    public InternalClazz[] internalClazzs;


    public PojoEntity(String str, int i, Integer i1, List<String> strArr, long[] longs, InternalClazz internalClazz,InternalClazz[] internalClazzs) {
        this.str = str;
        this.i = i;
        this.i1 = i1;
        this.strArr = strArr;
        this.longs = longs;
        this.internalClazz = internalClazz;
        this.internalClazzs = internalClazzs;
    }

    static class InternalClazz {
        public Class clazz;

        public InternalClazz(Class clazz) {
            this.clazz = clazz;
        }
    }
}
