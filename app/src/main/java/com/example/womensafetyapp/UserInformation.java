package com.example.womensafetyapp;

class UserInformation {
    String id;
    String n1;
    String e1;
    String ep1;
    String p1;

    String m1;

    public UserInformation(String id, String n1, String e1, String ep1, String p1, String m1) {
        this.id = id;
        this.n1 = n1;
        this.e1 = e1;
        this.ep1 = ep1;
        this.p1 = p1;
        this.m1=m1;
    }

    public String getM1() {
        return m1;
    }

    public void setM1(String m1) {
        this.m1 = m1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getE1() {
        return e1;
    }

    public void setE1(String e1) {
        this.e1 = e1;
    }

    public String getEp1() {
        return ep1;
    }

    public void setEp1(String ep1) {
        this.ep1 = ep1;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }
}

