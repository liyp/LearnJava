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
