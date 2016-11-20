package org.vanguardmatrix.engine.datatypes;

import com.google.code.linkedinapi.schema.Person;

import org.brickred.socialauth.Profile;
import org.brickred.socialauth.util.BirthDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.AppPreferences;

import java.io.Serializable;

/**
 * Created by Uzair on 12/12/2015.
 */
public class SocailSignedUserObject implements Serializable {
    private int userid;
    private String email;
    private String firstname;
    private String lastname;
    private String dob;
    private String gender;
    private int location_id;
    private String city;
    private String country;
    private String link;
    private String profilePic;
    private String socialMediaName;


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getEmail() {
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
    public static Profile facebookImporterOAuth(JSONObject jsonObject) {
        Profile socailSignedUserObject = new Profile();
        JSONObject mainJsonObject = null;
        JSONObject locationObj = null;
        try {
            mainJsonObject = jsonObject;

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            locationObj = mainJsonObject.getJSONObject("location");
        } catch (JSONException e) {

        }
        try {
            socailSignedUserObject.setValidatedId(mainJsonObject.getString("id"));
        } catch (Exception e) {
            //      e.printStackTrace();
        }
        try {
            String[] CompleteName = mainJsonObject.getString("name").split("\\s+");
            socailSignedUserObject.setFirstName(CompleteName[0]);
            socailSignedUserObject.setLastName(CompleteName[1]);

        } catch (Exception e) {
            //        e.printStackTrace();
        }
        try {
            socailSignedUserObject.setRawResponse(mainJsonObject.getString("link"));
        } catch (Exception e) {
            //          e.printStackTrace();
        }
        try {
            socailSignedUserObject.setGender(mainJsonObject.getString("gender"));
        } catch (Exception e) {
//            e.printStackTrace();

        }
        try {

            //socailSignedUserObject.set(mainJsonObject.getString("birthday"));
        } catch (Exception e) {
            //e.printStackTrace();

        }
        try {
         //   socailSignedUserObject.setLocat(locationObj.getInt("id"));
        } catch (Exception e) {
            //e.printStackTrace();
            //  socailSignedUserObject.setLocation_id(linkedInProfile.getLocation().);
        }
        try {
            String[] CompleteAddress = locationObj.getString("name").split(",");
            //socailSignedUserObject.setCountry(CompleteAddress[0]);
            socailSignedUserObject.setCountry(CompleteAddress[1]);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        try {
            socailSignedUserObject.setProfileImageURL(mainJsonObject.getString("picture"));
        } catch (Exception e) {
            //  e.printStackTrace();
        }
        try {
            socailSignedUserObject.setEmail("");
        } catch (Exception e) {
            //socailSignedUserObject.setEmail();

        }

        return socailSignedUserObject;

    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getSocialMediaName() {
        return socialMediaName;
    }

    public void setSocialMediaName(String socialMediaName) {
        this.socialMediaName = socialMediaName;
    }
}
