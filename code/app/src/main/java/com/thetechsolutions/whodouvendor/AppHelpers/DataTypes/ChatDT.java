package com.thetechsolutions.whodouvendor.AppHelpers.DataTypes;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Uzair on 8/6/2016.
 */
public class ChatDT extends RealmObject {

    public ChatDT() {

    }

    @PrimaryKey
    private int id = 0;

    private String title = "";
   // private RealmList<Message> streets;
  //  private RealmList<Message> message = null;
    private String display_pic = "";
    private String username = "";
    private boolean isRead;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplay_pic() {
        return display_pic;
    }

    public void setDisplay_pic(String display_pic) {
        this.display_pic = display_pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
