package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import org.vanguardmatrix.engine.parser.ParseString;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class SettingsPreferenceDT extends RealmObject {
    public SettingsPreferenceDT() {

    }

    private int user_id = 0;

    @PrimaryKey
    private String prefernece_id = "";
    private String preferences_type = "";
    private String preferences_value = "";
    private String user_type = "";
    @Index
    private String group = "";
    private String created_at = "";
    private String updated_at = "";

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPrefernece_id() {
        return prefernece_id;
    }

    public void setPrefernece_id(String prefernece_id) {
        this.prefernece_id = prefernece_id;
    }

    public String getPreferences_type() {
        try {
            return ParseString.getInitCap(preferences_type);
        } catch (Exception e) {

        }
        return "";

    }

    public void setPreferences_type(String preferences_type) {
        this.preferences_type = preferences_type;
    }

    public String getPreferences_value() {
        return preferences_value;
    }

    public void setPreferences_value(String preferences_value) {
        this.preferences_value = preferences_value;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
//
//    public String getGroup_name() {
//        return group_name;
//    }
//
//    public void setGroup_name(String group_name) {
//        this.group_name = group_name;
//    }
//
//    public String getGroup_item_name() {
//        return group_item_name;
//    }
//
//    public void setGroup_item_name(String group_item_name) {
//        this.group_item_name = group_item_name;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
}
