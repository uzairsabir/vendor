package org.vanguardmatrix.engine.datatypes;

import org.vanguardmatrix.engine.utils.UtilityFunctions;

/**
 * Created by Uzair on 12/12/2015.
 */
public class UserObject {
    private int userid;
    private String email;
    private String firstname;
    private String lastname;
    private String zipcode;
    private String password;
    private String usertype;
    private String userpic;
    private String user_signup_type = "";
    private int user_signup_type_id = 0;
    private String dob;
    private String coverpic;
    private String city;
    private String country;
    private String mobileNo;
    private String username;

    public String getEmail() {
        if (UtilityFunctions.isEmpty(this.email)) {
            return "";
        }
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserpic() {
        return userpic;
    }

    public void setUserpic(String userpic) {
        this.userpic = userpic;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUser_signup_type() {
        return user_signup_type;
    }

    public void setUser_signup_type(String user_signup_type) {
        this.user_signup_type = user_signup_type;
    }


    public int getUser_signup_type_id() {
        return user_signup_type_id;
    }

    public void setUser_signup_type_id(int user_signup_type_id) {
        this.user_signup_type_id = user_signup_type_id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getUsername() {
        if (UtilityFunctions.isEmpty(this.mobileNo)) {
            return this.email;
        }
        return this.mobileNo;
    }
}
