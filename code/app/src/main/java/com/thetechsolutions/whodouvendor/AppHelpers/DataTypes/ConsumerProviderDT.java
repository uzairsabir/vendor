package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import io.realm.RealmObject;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ConsumerProviderDT extends RealmObject {

    public ConsumerProviderDT() {

    }
    private String provider_friend_number = "";
    private int is_provider = 0;


    public int getIs_provider() {
        return is_provider;
    }

    public void setIs_provider(int is_provider) {
        this.is_provider = is_provider;
    }

    public String getProvider_friend_number() {
        return provider_friend_number;
    }

    public void setProvider_friend_number(String provider_friend_number) {
        this.provider_friend_number = provider_friend_number;
    }
}
