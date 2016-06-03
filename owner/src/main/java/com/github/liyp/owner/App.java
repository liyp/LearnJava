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
package com.github.liyp.owner;

import org.aeonbits.owner.ConfigFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Stack;

public class App {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("\nCurrent ClassLoader chain: "
                + getCurrentClassloaderDetail());

        String confDirString = System.getProperty("conf.dir",
                "src" + File.separator + "main" + File.separator + "resources");
        File confDir = new File(confDirString);
        ClassLoader loader = new URLClassLoader(
                new URL[]{confDir.toURI().toURL()});
        Thread.currentThread().setContextClassLoader(loader);

        ClassLoader cl = ClassLoader.getSystemClassLoader();
        for (URL url : ((URLClassLoader) cl).getURLs()) {
            System.out.println(url);
        }

        System.out.println();

        for (URL url : ((URLClassLoader) Thread.currentThread().getContextClassLoader()).getURLs()) {
            System.out.println(url);
        }

        System.out.println();

        for (URL url : ((URLClassLoader) OwnerProperties.class.getClassLoader()).getURLs()) {
            System.out.println(url);
        }

        OwnerProperties cfg = ConfigFactory.create(OwnerProperties.class);
        System.out.println(cfg);

        System.out.println(System.currentTimeMillis());
    }

    public static String getCurrentClassloaderDetail() {

        StringBuffer classLoaderDetail = new StringBuffer();

        Stack<ClassLoader> classLoaderStack = new Stack<ClassLoader>();

        ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

        classLoaderDetail.append("\n-----------------------------------------------------------------\n");

        // Build a Stack of the current ClassLoader chain

        while (currentClassLoader != null) {

            classLoaderStack.push(currentClassLoader);

            currentClassLoader = currentClassLoader.getParent();

        }

        // Print ClassLoader parent chain

        while (classLoaderStack.size() > 0) {

            ClassLoader classLoader = classLoaderStack.pop();

            // Print current

            classLoaderDetail.append(classLoader);

            if (classLoaderStack.size() > 0) {

                classLoaderDetail.append("\n--- delegation ---\n");

            } else {

                classLoaderDetail.append(" **Current ClassLoader**");

            }

        }

        classLoaderDetail
                .append("\n-----------------------------------------------------------------\n");

        return classLoaderDetail.toString();

    }
}
