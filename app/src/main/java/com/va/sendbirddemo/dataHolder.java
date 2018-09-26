package com.va.sendbirddemo;

public class dataHolder {

    String user;
    String messgae;

    public dataHolder(String user, String messgae) {
        this.user = user;
        this.messgae = messgae;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}
