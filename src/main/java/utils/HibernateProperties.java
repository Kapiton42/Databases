package utils;

import java.util.Properties;

/**
 * Created by kapiton on 20.03.16.
 */
public class HibernateProperties {
    private Properties properties;

    public HibernateProperties(Properties properties) {
        this.properties = properties;
    }

    @Deprecated
    HibernateProperties(){};

    public Properties getProperties() {
        return properties;
    }
}
