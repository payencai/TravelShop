package com.tec.travelagency.home.entity;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/9/28 11:07
 * 邮箱：771548229@qq..com
 */
public interface IHotelNewBean extends Serializable{

    public String getId() ;

    public void setId(String id);

    public String getName();

    public void setName(String name);

    public String getContactNumber();

    public void setContactNumber(String contactNumber);

    public String getProvince();

    public void setProvince(String province);

    public String getCity();

    public void setCity(String city);

    public String getDistrict();

    public void setDistrict(String district);

    public String getLongitude();

    public void setLongitude(String longitude);

    public String getLatitude();

    public void setLatitude(String latitude);

    public String getAddress();

    public void setAddress(String address);

    public String getAddressDetail();

    public void setAddressDetail(String addressDetail);

    public String getHotelRating();

    public void setHotelRating(String hotelRating);

    public String getInstr();

    public void setInstr(String instr);

    public String getOneWord();

    public void setOneWord(String oneWord) ;

    public String getImage1();

    public void setImage1(String image1);

    public String getImage1Url();

    public void setImage1Url(String image1Url);

    public String getImage2();

    public void setImage2(String image2);

    public String getImage2Url();
    public void setImage2Url(String image2Url);

    public String getImage3();

    public void setImage3(String image3);

    public String getImage3Url();

    public void setImage3Url(String image3Url);

    public String getImage4();

    public void setImage4(String image4);

    public String getImage4Url();

    public void setImage4Url(String image4Url);

    public String getImage5();

    public void setImage5(String image5);

    public String getImage5Url();

    public void setImage5Url(String image5Url);

    public String getImage6();

    public void setImage6(String image6);

    public String getImage6Url();

    public void setImage6Url(String image6Url);

    public String getCreateTime();

    public void setCreateTime(String createTime);

    public String getIsDeleted();

    public void setIsDeleted(String isDeleted);

    public String getIsUsed();

    public void setIsUsed(String isUsed);

}
