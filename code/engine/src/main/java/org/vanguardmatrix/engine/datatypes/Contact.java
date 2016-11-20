package org.vanguardmatrix.engine.datatypes;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.vanguardmatrix.engine.android.Constants;
import org.vanguardmatrix.engine.parser.ParseDate;
import org.vanguardmatrix.engine.utils.UtilityFunctions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Contact {

    public static final String ATTR_FIRST_NAME = "firstName";
    public static final String ATTR_LAST_NAME = "lastName";
    public static final String ATTR_GENDER = "gender";
    public static final String ATTR_DOB = "dob";
    public static final String ATTR_EMAIL = "email";
    public static final String ATTR_PHONE_NUMBER = "phoneNumber";
    public static final String ATTR_CITY = "city";
    public static final String ATTR_COUNTRY = "country";
    public static final String ATTR_COVER_IMAGE_URL = "coverImage";
    public static final String ATTR_IMAGE_URL = "profileImage";
    public static final String ATTR_STATUS_MESSAGE = "status";
    public static final String ATTR_SOCIAL_URLS = "socialUrls";
    public static final String ATTR_PHONE_BOOK_NAME = "phoneBookName";
    public static final String TABLE_NAME = "contacts";
    public static final String P_IS_FOLLOWED = "is_followed";
    public static final String ATTR_USER_NAME = "username";
    public static final String ATTR_USER_MOBILE_NO = "u_mobile_no";
    private String userName = "UserName";
    private String fullName = "";
    private PhoneNumber phoneNumber;
    private String photoURI = "";
    private String statusUpdateTime = "";
    private boolean friend = false;
    private String firstName = "";
    private String lastName = "";
    private String dob = "";
    private Date birthDay = null;
    private String city = "";
    private String country = "";
    private String email = "";
    private String profileImage = "";
    private String coverImage = "";
    private String gender = "";
    private String socialUrls = "";
    private String phoneBookName = "";
    private String status = "";
    private boolean isAppFriend = false;
    private String is_followed = "0";
    private String rawNumber = "";
    private String appUserName = "";
    private String u_mobile_no = "";

    // public static final String ATTR_PHOTO_URI = "photoURI";
    private boolean isSelected = false;

    public Contact() {
    }

    // public void PrivateObject(Object attribute){
    // this.((Object)attribute.getClass()) = attribute;
    // }

    // public void set(Object attribute, Class type) {
    // try {
    // Class.forName("Contact").getMethod("set" + attribute, type)
    // .invoke(Contact.class, attribute);
    // } catch (IllegalAccessException e) {
    // e.printStackTrace();
    // } catch (IllegalArgumentException e) {
    // e.printStackTrace();
    // } catch (InvocationTargetException e) {
    // e.printStackTrace();
    // } catch (NoSuchMethodException e) {
    // e.printStackTrace();
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // }

//    public static Contact getContactFromNumber(String absoluteNumber) {
//
////        Contact contact = DatabaseManager.getSingleContact(
////                Contact.getNumberOnly(absoluteNumber), HomeScreenOld.activity);
////        if (contact == null) {
////            Log.e("Contact", "getContactFromNumber == null");
////            contact = ProfileModel.getUserProfileData(absoluteNumber,
////                    HomeScreenOld.activity);
////        } else {
////            Log.e("Contact", "getuserName()==:" + contact.getuserName()
////                    + " contact fullname " + contact.getfullName());
////        }
//        // if (contact != null)
//        // return contact;
//        // else {
//        // Contact c = new Contact();
//        // c.setfirstName(PhoneNumber.getFormatedNumber(absoluteNumber));
//        // c.setuserName(PhoneNumber.getFormatedNumber(absoluteNumber));
//        // c.setphoneNumber(PhoneNumber.getFormatedNumber(absoluteNumber));
//        // c.setfriend(true);
//        // c.setisAppFriend(true);
//        // c.setuserName(PhoneNumber.getFormatedNumber(absoluteNumber));
//        // return c;
//        // }
//       // return contact;
//
//    }

    public static String getNumberOnly(String phoneNumber) {
        String x = "";
        if (phoneNumber.contains("@")) {
            x = phoneNumber.substring(0, phoneNumber.indexOf("@"));
        } else
            x = phoneNumber;
        return x;
    }

    public static String getFormattedNumber(String phoneNumber) {

        phoneNumber = phoneNumber.trim();

        if (phoneNumber.startsWith("00"))
            ;// ignoring as valid global for App
        else if (phoneNumber.startsWith("+")) {
            phoneNumber = "00" + phoneNumber.substring(1);
            // phoneNumber.replace("+", "0");
        } else if (phoneNumber.startsWith("0")) {

            if (!(phoneNumber.charAt(1) == '0')) {
                // Log.e("awp", "0+!0 checking matched");
                phoneNumber = "00" + Constants.myCountryCodeInt
                        + phoneNumber.substring(1);
                // phoneNumber.replaceFirst("0", App_Constants.myCountryCode);
                // phoneNumber = "0" + phoneNumber;
            } else {
                // Log.e("awp", "0+!0 checking didn't matched");
                // shouldn't appear as phoneNumber matches with startsWith("00")
            }
        } else if (phoneNumber.length() > 9) {
            phoneNumber = "00" + phoneNumber;
        }
        // Log.e("App____CONTACT_NUMBER", ":::::::::::::" + phoneNumber
        // + " using CountryCode=" + App_Constants.myCountryCodeInt);

        return phoneNumber;
    }

    public static String get_userName_2show(String userName) {
        if (userName != null) {
            if (userName.contains("@"))
                userName = userName.split("@")[0];

            userName = PhoneNumber.getFormatedNumber(userName);

        }
        return userName;
    }

    public static Contact importFromJson(JSONObject json) {
        Contact r = new Contact();
        try {
            r.setuserName(json.getString(Contact.ATTR_USER_NAME));
            Log.e("userNumber", json.getString(Contact.ATTR_USER_MOBILE_NO));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setU_mobile_no(json.getString(Contact.ATTR_USER_MOBILE_NO));

        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setfirstName(json.getString(Contact.ATTR_FIRST_NAME));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setemail(json.getString(Contact.ATTR_EMAIL));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setgender(json.getString(Contact.ATTR_GENDER));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setcity(json.getString(Contact.ATTR_CITY));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setcountry(json.getString(Contact.ATTR_COUNTRY));
        } catch (JSONException e) {
            // e.printStackTrace();
        }

        try {
            r.setlastName(json.getString(Contact.ATTR_LAST_NAME));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setphoneNumber(json.getString(Contact.ATTR_USER_MOBILE_NO));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setprofileImage(json.getString(Contact.ATTR_IMAGE_URL));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setstatus(json.getString(Contact.ATTR_STATUS_MESSAGE));
        } catch (JSONException e) {
            // e.printStackTrace();
        }
        try {
            r.setcoverImage(json.getString(Contact.ATTR_COVER_IMAGE_URL));
        } catch (JSONException e) {
            // e.printStackTrace();
        }

        try {
            r.setdob(json.getString(Contact.ATTR_DOB));
        } catch (JSONException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return r;
    }

    public Map<String, Object> export(Contact obj) {

        Map<String, Object> map = new HashMap<String, Object>();

        for (Field field : obj.getClass().getDeclaredFields()) {
            // field.setAccessible(true); // if you want to modify private
            // fields
            try {
                System.out.println(field.getName() + " - " + field.getType()
                        + " - " + field.get(obj));

                map.put(field.getName(), field.get(obj));

                try {
                    Class.forName("org.vanguardmatrix.engine.datatypes.Contact")
                            .getMethod("set" + field.getName(), field.getType())
                            .invoke(Contact.class, field.get(obj));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                System.out.println("mapped " + field.getName() + "="
                        + map.get(field.getName()));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return map;

    }

    public Contact importt(Map<String, Object> obj) {

        // Map<String, String> map = new HashMap<String, String>();
        Contact c = new Contact();

        for (Field field : obj.getClass().getDeclaredFields()) {
            // field.setAccessible(true); // if you want to modify private
            // fields
            try {
                System.out.println(field.getName() + " - " + field.getType()
                        + " - " + field.get(obj));

                // c.set(field.getName(), field.get(obj));
                // map.put(field.getName(), field.get(obj));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return c;

    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String name) {
        this.userName = name;
    }

    public PhoneNumber getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String phoneNumber) {

        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public String getstatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setstatusUpdateTime(String statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public boolean getfriend() {
        return friend;
    }

    public void setfriend(boolean friend) {
        this.friend = friend;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getdob() {
        return ParseDate.getStringFromDate(getbirthDay(),
                ParseDate.FORMAT_yyyyMMdd);
    }

    public Date getbirthDay() {
        return this.birthDay;
    }

    public void setdob(String age) {

        // this.birthDay = ParseDate.getDateFromString(age, "yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {

            this.birthDay = format.parse(age);
            // Log.e("Contact . birth", format.format(this.birthDay));
            this.dob = format.format(this.birthDay);// this.birthDay.toString();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getcity() {
        return city;
    }

    public void setcity(String city) {
        this.city = city;
    }

    public String getcountry() {
        return country;
    }

    public void setcountry(String country) {
        this.country = country;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

    public String getprofileImage() {

        // if (App_Config.isEmpty(imageURL))
        // imageURL = getPhotoURI();

        return Constants.IMAGES_BASE_URL + profileImage;
    }

    public String getprofileImage_withoutbaseurl() {

        // if (App_Config.isEmpty(imageURL))
        // imageURL = getPhotoURI();

        return profileImage;
    }

    public String getprofileImage_thumb() {

        // if (App_Config.isEmpty(imageURL))
        // imageURL = getPhotoURI();

        // return Constants.ejabberdApiUrl + "/getthumbnail/?usernam;e="
        // + getphoneNumber().getAbsoluteNumber() + "&type=profile";

        return Constants.IMAGES_BASE_URL + profileImage + "/thumb";

    }

    public void setprofileImage(String imageURL) {
        this.profileImage = imageURL;
    }

    public String getcoverImage() {
        return Constants.IMAGES_BASE_URL + coverImage;
    }

    public String getcoverImage_withoutbaseurl() {
        return coverImage;
    }

    public void setcoverImage(String coverImageURL) {
        this.coverImage = coverImageURL;
    }

    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public String getsocialUrls() {
        return socialUrls;
    }

    public void setsocialUrls(String socialUrls) {
        this.socialUrls = socialUrls;
    }

    public String getfullName() {

        if (!UtilityFunctions.isEmpty(getphoneBookName()))
            fullName = getphoneBookName();
        else
            fullName = getfirstName() + " " + getlastName();

        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getphoneBookName() {
        return phoneBookName;
    }

    public void setphoneBookName(String phoneBookName) {
        this.phoneBookName = phoneBookName;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }

    public boolean getisAppFriend() {
        return isAppFriend;
    }

    public void setisAppFriend(boolean isAppFriend) {

        this.isAppFriend = isAppFriend;
    }

    public String getRawNumber() {
        return rawNumber;
    }

    public void setRawNumber(String rawNumber) {
        this.rawNumber = rawNumber;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getphotoURI() {
        return photoURI;
    }

    public void setphotoURI(String photoURI) {
        this.photoURI = photoURI;
    }

    public String getIs_followed() {
        return is_followed;
    }

    public void setIs_followed(String is_followed) {
        this.is_followed = is_followed;
    }

    public String getAppUserName() {
        return this.firstName + " " + this.lastName;
    }

    public String getU_mobile_no() {
        return u_mobile_no;
    }

    public void setU_mobile_no(String u_mobile_no) {
        this.u_mobile_no = u_mobile_no;
    }
}
