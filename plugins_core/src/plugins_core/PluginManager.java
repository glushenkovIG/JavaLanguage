package plugins_core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;

class PluginManager {
    private final String pluginRootDirectory;

    PluginManager(String pluginRootDirectory) {
        this.pluginRootDirectory = pluginRootDirectory;
    }

    Plugin load(String pluginName, String pluginClassName) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URL [] urls = {new URL(pluginRootDirectory)};
        URLClassLoader loader = new URLClassLoader(urls, getClass().getClassLoader());

        Plugin plugin = (Plugin) loader.loadClass(pluginName + "." + pluginClassName).newInstance();
        loader.close();

        return plugin;
    }
}

