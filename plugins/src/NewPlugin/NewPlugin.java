package NewPlugin;


import plugins_core.Plugin;

public class NewPlugin implements Plugin {
    @Override
    public void doUseful() {
        System.out.println("rootPluginDirectory class with name <<NewPlugin>> is loaded by: <<" + getClass().getClassLoader() + ">>");
    }
}
