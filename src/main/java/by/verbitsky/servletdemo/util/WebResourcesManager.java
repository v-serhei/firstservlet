package by.verbitsky.servletdemo.util;

import java.util.ResourceBundle;

public class WebResourcesManager {
    private static volatile WebResourcesManager instance;
    private static ResourceBundle bundle;
    private static final String RESOURCE_NAME = "jsp/config";


    private WebResourcesManager() {
        bundle = ResourceBundle.getBundle(RESOURCE_NAME);
    }

    public static WebResourcesManager getInstance() {
        WebResourcesManager localManager = WebResourcesManager.instance;
        if (instance == null) {
            synchronized (WebResourcesManager.class) {
                localManager = instance;
                if (localManager == null) {
                    instance = new WebResourcesManager();
                    localManager = instance;
                }
            }
        }
        return localManager;
    }

    public String getProperty (String propertyName) {
        String result = bundle.getString(propertyName);
        return result;
    }
}
