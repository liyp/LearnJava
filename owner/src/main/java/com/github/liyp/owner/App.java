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
