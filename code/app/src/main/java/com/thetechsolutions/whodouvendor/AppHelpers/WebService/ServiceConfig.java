package com.thetechsolutions.whodouvendor.AppHelpers.WebService;

/**
 * Created by Uzair on 12/8/2015.
 */
public class ServiceConfig {

    public static ConfigMode runningMode = ConfigMode.PRODUCTION;

    public static String getPublicUrl() {

        String BASE_URL = "http://" + getHostIp();

        return BASE_URL + getSubUrl();
    }

    public static String getPublicUrl4Image() {

        String BASE_URL = "http://" + getHostIp();

        return BASE_URL;
    }

    public static String getHostIp() {

        if (runningMode == null) {
            runningMode = ConfigMode.DEVELOPMENT;
        }
        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            return "128.199.204.214/pingem_live";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            return "thepilgrimageguide.com";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            return "128.199.204.214/pingem";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            return "202.142.175.163";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT_LOCAL)) {
            return "192.168.8.102/pingem";
        } else {
            return "202.142.175.163";
        }
    }

    public static String getSubUrl() {
        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            return "/api/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT_LOCAL)) {
            return "/api/";
        } else {
            return "/api/";
        }
    }

    public static String getImageUrl() {
        if (runningMode.equals(ConfigMode.PRODUCTION)) {
            return getPublicUrl4Image() + "/images/";
        } else if (runningMode.equals(ConfigMode.STATGING)) {
            return getPublicUrl4Image() + "/images/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT)) {
            return getPublicUrl4Image() + "/images/";
        } else if (runningMode.equals(ConfigMode.MAINTENANCE)) {
            return getPublicUrl4Image() + "/images/";
        } else if (runningMode.equals(ConfigMode.DEVELOPMENT_LOCAL)) {
            return getPublicUrl4Image() + "/images/";
        } else {
            return getPublicUrl4Image() + "/images/";
        }
    }

    public enum ConfigMode {
        DEVELOPMENT, MAINTENANCE, PRODUCTION, STATGING, DEVELOPMENT_LOCAL
    }


}
