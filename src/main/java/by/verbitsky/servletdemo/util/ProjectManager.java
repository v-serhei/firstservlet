package by.verbitsky.servletdemo.util;

import java.util.ResourceBundle;

public class ProjectManager {
    private static volatile ProjectManager instance;
    private static ResourceBundle bundle;
    private static final String RESOURCE_NAME = "jsp/config";


    private ProjectManager() {
        bundle = ResourceBundle.getBundle(RESOURCE_NAME);
    }

    public static ProjectManager getInstance() {
        ProjectManager localManager = ProjectManager.instance;
        if (instance == null) {
            synchronized (ProjectManager.class) {
                localManager = instance;
                if (localManager == null) {
                    instance = new ProjectManager();
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
