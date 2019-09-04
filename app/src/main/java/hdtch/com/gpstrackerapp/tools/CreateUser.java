package hdtch.com.gpstrackerapp.tools;

import android.net.Uri;

public class CreateUser {

    private String name;
    private String email;
    private String password;
    private String code;
    private String date;
    private String isSharing;
    private String lat;
    private String lon;
    private String imageUri;

    public CreateUser(){

    }

    public CreateUser(String name, String email, String password, String code, String date, String isSharing, String lat, String lon,  String imageUri){
        this.name = name;
        this.email = email;
        this.password = password;
        this.code = code;
        this.date = date;
        this.isSharing = isSharing;
        this.lat = lat;
        this.lon = lon;
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getIsSharing() {
        return isSharing;
    }

    public void setIsSharing(String isSharing) {
        this.isSharing = isSharing;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
