package com.iot.petsfinder;

import java.sql.Blob;

public class Doggy
{
    private String date;
    private String phonenum;
    private String detailinfo;
    private String islost;
    private Blob image;

    private int imageId;

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    private  String type;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    private String age;

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    private String gender;

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getDetailinfo() {
        return detailinfo;
    }

    public void setDetailinfo(String detailinfo) {
        this.detailinfo = detailinfo;
    }

    public String getIslost() {
        return islost;
    }

    public void setIslost(String islost) {
        this.islost = islost;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
