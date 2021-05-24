package org.sunbird.common;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Application Configuration File.
 */
public class AppConfig {

    private static Config defaultConf = ConfigFactory.load();
    private static Config envConf = ConfigFactory.systemEnvironment();
    public static Config config = defaultConf.withFallback(envConf);

}
