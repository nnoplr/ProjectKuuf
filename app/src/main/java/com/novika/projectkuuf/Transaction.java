package com.novika.projectkuuf;

public class Transaction {

    int transId, userId, prodId, prodPrice;
    String date, prodName;

    public Transaction(int transId, int userId, int prodId, String date) {
        this.transId = transId;
        this.userId = userId;
        this.prodId = prodId;
        this.date = date;
    }

    public Transaction(int transId, int userId, int prodId, String date, String prodName, int prodPrice) {
        this.transId = transId;
        this.userId = userId;
        this.prodId = prodId;
        this.date = date;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }


    public Transaction() {

    }
    public Transaction(int transId, String date, String prodName, int prodPrice) {
        this.transId = transId;
        this.userId = userId;
        this.prodId = prodId;
        this.date = date;
        this.prodName = prodName;
        this.prodPrice = prodPrice;
    }



    public int getTransId() {
        return transId;
    }

    public void setTransId(int transId) {
        this.transId = transId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}
