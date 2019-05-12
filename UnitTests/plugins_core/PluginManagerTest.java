package plugins_core;

import MyNewPlugin.NewPlugin;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class PluginManagerTest {
    // you should explicitly build module plugins before running this test
    @Test
    public void unit1() throws IllegalAccessException, InvocationTargetException, IOException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        NewPlugin newPlugin = new NewPlugin();
        newPlugin.getClass().getClassLoader().loadClass("MyNewPlugin.NewPlugin").getMethod("doUseful").invoke(newPlugin);

        PluginManager manager = new PluginManager("file:///Users/Ivan/Documents/MIPT/6_term/BIT_Java/JavaLanguage/out/production/plugins/");
        Plugin plugin1 = manager.load("NewPlugin", "NewPlugin");
        plugin1.doUseful();
    }
}