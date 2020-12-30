package serenity.utility;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.junit.jupiter.api.Test;

public class ConfigReader {

    private static EnvironmentVariables environmentVariables ;
    static {
        environmentVariables =
                SystemEnvironmentVariables.createEnvironmentVariables();
    }

    public static String getProperty(String propertyName){

        return EnvironmentSpecificConfiguration
                .from(environmentVariables)
                .getProperty(propertyName);

    }
}
