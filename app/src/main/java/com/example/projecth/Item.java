package com.example.projecth;




public class Item {

    private String title;
    private String image;
    private String prc;
    public String iid;
    private String desc;

    public Item(String title, String image, String url,String desc,String iid) {
        this.title = title;
        this.image = image;
        this.prc = url;
        this.desc = desc;
        this.iid = iid;

    }

    public Item(){

    }

    public String getPrc() {
        return prc;
    }

    public void setPrc(String prc) {
        this.prc = prc;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
