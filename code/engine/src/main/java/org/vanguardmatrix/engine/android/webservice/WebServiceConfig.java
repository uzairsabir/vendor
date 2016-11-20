package org.vanguardmatrix.engine.android.webservice;

import android.content.Context;

import org.vanguardmatrix.engine.android.Constants;

public class WebServiceConfig {
    public static applicationMode appMode = applicationMode.PRODUCTION;

    ;
    public static Context activityContext;

    public static String getBaseURL() {
        if (WebServiceConfig.appMode == WebServiceConfig.applicationMode.DEVELOPMENT)
            return Constants.XMPP_BASE_URL;
        else
            return Constants.XMPP_BASE_URL;
    }

    public static enum applicationMode {
        DEVELOPMENT, PRODUCTION
    }

}
