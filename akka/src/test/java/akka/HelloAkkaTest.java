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
package akka;

import scala.concurrent.duration.Duration;
import akka.actor.*;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class HelloAkkaTest {

    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        system.shutdown();
        system.awaitTermination(Duration.create("10 seconds"));
    }

    @Test
    public void testSetGreeter() {
        new JavaTestKit(system) {
            {
                final TestActorRef<HelloAkkaJava.Greeter> greeter = TestActorRef
                        .create(system,
                                Props.create(HelloAkkaJava.Greeter.class),
                                "greeter1");

                greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"),
                        getTestActor());

                Assert.assertEquals("hello, testkit",
                        greeter.underlyingActor().greeting);
            }
        };
    }

    @Test
    public void testGetGreeter() {
        new JavaTestKit(system) {
            {

                final ActorRef greeter = system.actorOf(
                        Props.create(HelloAkkaJava.Greeter.class), "greeter2");

                greeter.tell(new HelloAkkaJava.WhoToGreet("testkit"),
                        getTestActor());
                greeter.tell(new HelloAkkaJava.Greet(), getTestActor());

                final HelloAkkaJava.Greeting greeting = expectMsgClass(HelloAkkaJava.Greeting.class);

                new Within(duration("10 seconds")) {
                    protected void run() {
                        Assert.assertEquals("hello, testkit", greeting.message);
                    }
                };
            }
        };
    }
}