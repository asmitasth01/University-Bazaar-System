package example.com.bazaar.bean;


import android.net.Uri;

import com.google.firebase.storage.StorageReference;

/**
 * Created by sumanshrestha on 11/24/16.
 */

public class UserInfo {

    private String name;
    private String userName;
    private String email;
    private String phoneNumber;
    private String password;
    private String address;
    private String profilePic_imageURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePic_imageURL() {
        return profilePic_imageURL;
    }

    public void setProfilePic_imageURL(String profilePic_imageURL) {
        this.profilePic_imageURL = profilePic_imageURL;
    }

    public UserInfo(String name, String userName, String email, String phoneNumber, String password, String address, String profilePic_imageURL) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.address = address;
        this.profilePic_imageURL = profilePic_imageURL;

    }
    public UserInfo() {

    }
}
