package com.tec.travelagency.orderManager.fragment.detail;

/**
 * 作者：凌涛 on 2018/11/23 11:50
 * 邮箱：771548229@qq..com
 */
public class CarRentalComment {

    /**
     * carRentalId : string
     * content : string
     * createTime : 2018-11-23T03:49:51.670Z
     * id : string
     * image : string
     * name : string
     * orderId : string
     * score : 0
     * state : 0
     */

    private String carRentalId;
    private String content;
    private String createTime;
    private String id;
    private String image;
    private String name;
    private String orderId;
    private float score;
    private int state;

    public String getCarRentalId() {
        return carRentalId;
    }

    public void setCarRentalId(String carRentalId) {
        this.carRentalId = carRentalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
