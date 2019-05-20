package com.tec.travelagency.home.entity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/9/5 12:04
 * 邮箱：771548229@qq..com
 */
public class DataEntry implements Serializable {
    public String imageUrl;
    public String desc;


    public DataEntry(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
