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
package me.liyp.rpc;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Call implements Serializable {

    private static final long serialVersionUID = 2354606662582788557L;

    private String _interface;
    private String method;
    private Class[] argTypes;
    private Object[] args;

    public Call(String _interface, String method, Class[] argTypes, Object[] args) {
        this._interface = _interface;
        this.method = method;
        this.argTypes = argTypes;
        this.args = args;
    }

    Object call(Map<String, Object> services) {
        Object service = services.get(_interface);
        if (service == null) return null;
        try {
            Class klass = Class.forName(_interface);
            Method m = klass.getMethod(method, argTypes);
            return m.invoke(service, args);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put(Hello.class.getName(), new HelloImpl());

        Call call = new Call("me.liyp.rpc.Hello", "hello", new Class[] {String.class}, new Object[] {"test"});
        Object ret = call.call(map);
        System.out.println(ret);
    }

}
