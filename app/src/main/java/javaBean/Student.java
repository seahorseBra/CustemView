package javaBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by zchao on 2016/3/9.
 */
public class Student {
    @Expose
    private int userID;
    @Expose
    @SerializedName("name")
    private String userName;
    @SerializedName("nickname")
    private String userNickName;
    @SerializedName("bir")
    @Expose
    private Date birthDay;
    private String userAddress;

    public Student() {
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", birthDay=" + birthDay +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }
}
