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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server implements AutoCloseable {

    private final int port;
    private volatile boolean running = true;

    private final Map<String, Object> services = new ConcurrentHashMap<>();

    Server(int port) {
        this.port = port;
    }

    public void serve(Class interf, Object imlp) {
        services.put(interf.getName(), imlp);
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(port);
            while(running) {
                Socket socket = ss.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Call call = (Call) in.readObject();
                Object retObj = call.call(services);
                out.writeObject(retObj);
                out.close();
                in.close();
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        running = false;
    }

    public static void main(String[] args) {
        Server server = new Server(12345);
        server.serve(Hello.class, new HelloImpl());
        server.start();
    }
}
