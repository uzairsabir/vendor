package eu.siacs.conversation.utils;

import java.io.Serializable;

/**
 * Created by Uzair on 9/24/2016.
 */
public class FriendNames implements Serializable {
    private String displayName="";
    private String displayAvatar="";
    private String username="";

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayAvatar() {
        return displayAvatar;
    }

    public void setDisplayAvatar(String displayAvatar) {
        this.displayAvatar = displayAvatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
