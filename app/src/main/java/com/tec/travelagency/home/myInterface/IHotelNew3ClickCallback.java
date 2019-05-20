package com.tec.travelagency.home.myInterface;

import android.view.View;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/10/8 15:00
 * 邮箱：771548229@qq..com
 * HotelNew3Bean  的item 点击的回调，做打电话用
 */
public interface IHotelNew3ClickCallback extends Serializable {

    public void call(String number,View view);

}
