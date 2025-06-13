package com.travel.smartcity.model;

public class UserPersonalDetails {

  private int userId;

  private String gender;

  private String country;

  private String phone;

  private String address;

  public UserPersonalDetails() {}

  public UserPersonalDetails(int userId, String gender, String country, String phone, String address) {
    this.userId = userId;
    this.gender = gender;
    this.country = country;
    this.phone = phone;
    this.address = address;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
