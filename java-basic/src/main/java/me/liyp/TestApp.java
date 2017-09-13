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
package me.liyp;

import java.io.IOException;

public class TestApp {
    static class A {
        synchronized void a(){
            System.out.println("this is A.a");
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class B extends A {
        synchronized void a() {
            System.out.println("this is B.a");
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        final A a = new A();
        final B b = new B();
        new Thread(new Runnable() {
            public void run() {
                a.a();
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                b.a();
            }
        }).start();

    }

    static class AA {
        static void aa() throws IOException, Exception {}
    }
    static class BB extends AA {
        static void aa() throws Exception {}
    }
}
