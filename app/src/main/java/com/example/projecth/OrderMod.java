package com.example.projecth;

import java.util.Date;

public class OrderMod {
    public String keystr;
    public String prodtit;
    public String usid;
    public Date dv;
    public String prc;
    public String pid;
    public String sadd;

    public OrderMod(String keystr, String prodtit, String usid,Date dvs,String prc,String pid,String sadd) {
        this.keystr = keystr;
        this.prodtit = prodtit;
        this.usid = usid;
        this.dv=dvs;
        this.prc = prc;
        this.pid = pid;
        this.sadd = sadd;
    }

    public OrderMod(){

    }

    public String getPrc() {
        return prc;
    }

    public void setPrc(String prc) {
        this.prc = prc;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getSadd() {
        return sadd;
    }

    public void setSadd(String sadd) {
        this.sadd = sadd;
    }

    public Date getDv() {
        return dv;
    }

    public void setDv(Date dv) {
        this.dv = dv;
    }

    public String getKeystr() {
        return keystr;
    }

    public void setKeystr(String keystr) {
        this.keystr = keystr;
    }

    public String getProdtit() {
        return prodtit;
    }

    public void setProdtit(String prodtit) {
        this.prodtit = prodtit;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }



}
