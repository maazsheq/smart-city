package com.travel.smartcity.model;

public class PayeeCardDetails {
  private int    id;
  private int    userId;
  private String name;
  private String accountNumber;
  private String expiry;  // MM/YY
  private String cvv;     // 3 digits

  // full constructor
  public PayeeCardDetails(int id, int userId, String name,
                          String accountNumber,
                          String expiry,
                          String cvv) {
    this.id             = id;
    this.userId         = userId;
    this.name           = name;
    this.accountNumber  = accountNumber;
    this.expiry         = expiry;
    this.cvv            = cvv;
  }


  public PayeeCardDetails(int userId, String name,
                          String accountNumber,
                          String expiry,
                          String cvv) {
    this.userId         = userId;
    this.name           = name;
    this.accountNumber  = accountNumber;
    this.expiry         = expiry;
    this.cvv            = cvv;
  }

  // --- getters ---
  public int    getId()             { return id; }
  public int    getUserId()         { return userId; }
  public String getName()           { return name; }
  public String getAccountNumber()  { return accountNumber; }
  public String getExpiry()         { return expiry; }
  public String getCvv()            { return cvv; }

  // --- setters ---
  public void setId(int id)                         { this.id = id; }
  public void setUserId(int userId)                 { this.userId = userId; }
  public void setName(String name)                  { this.name = name; }
  public void setAccountNumber(String accountNumber){ this.accountNumber = accountNumber; }
  public void setExpiry(String expiry)              { this.expiry = expiry; }
  public void setCvv(String cvv)                    { this.cvv = cvv; }
}
