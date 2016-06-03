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
package com.github.liyp.cassandra.readafterwrite;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileOpt {

    private static String fileName = "out.log";

    private static Writer out;

    static void init() throws IOException {
        out = new OutputStreamWriter(new DataOutputStream(System.out));
    }

    static void close() throws IOException {
        if (out != null) {
            out.close();
        }
    }

    public static void line(String msg) {
        try {
            out.write(msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void line(String format, Object... args) {
        String msg = String.format(format, args);
        line(msg);
    }

}
