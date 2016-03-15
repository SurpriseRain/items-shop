package com.jlsoft.utils.config;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Resources extends Object {

    private static ClassLoader defaultClassLoader;

    private Resources() {
    }

    /**
     * Returns the default classloader (may be null).
     *
     * @return The default classloader
     */
    public static ClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    /**
     * Sets the default classloader
     *
     * @param defaultClassLoader - the new default ClassLoader
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        Resources.defaultClassLoader = defaultClassLoader;
    }

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(getClassLoader(), resource);
    }

    /**
     * Returns a resource on the classpath as a Stream object
     *
     * @param loader The classloader used to load the resource
     * @param resource The resource to find
     * @return The resource
     * @throws IOException If the resource cannot be found or read
     */
    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        }
        return in;
    }

    /**
     * Gets a URL as an input stream
     *
     * @param urlString - the URL to get
     * @return An input stream with the data from the URL
     * @throws IOException If the resource cannot be found or read
     */
    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    private static ClassLoader getClassLoader() {
        if (defaultClassLoader != null) {
            return defaultClassLoader;
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }
}
