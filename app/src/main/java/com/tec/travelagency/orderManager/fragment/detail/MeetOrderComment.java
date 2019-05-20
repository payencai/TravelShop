package com.tec.travelagency.orderManager.fragment.detail;

/**
 * 作者：凌涛 on 2018/11/27 10:28
 * 邮箱：771548229@qq..com
 */
public class MeetOrderComment {

    /**
     * id : d4a97088-5212-4931-88e1-d117a79ee222
     * name : dlo26m6c3no
     * image : journey/2018112014380973
     * score : 5.0
     * content : vv就拒绝
     * conferenceRoomId : 32940247-eb9b-4ae5-adee-1c2361c6f6a8
     * orderId : 86a0c356ebf3402587972113da4e366c
     * state : 1
     * createTime : 2018-11-26 00:00:00
     */

    private String id;
    private String name;
    private String image;
    private double score;
    private String content;
    private String conferenceRoomId;
    private String orderId;
    private int state;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConferenceRoomId() {
        return conferenceRoomId;
    }

    public void setConferenceRoomId(String conferenceRoomId) {
        this.conferenceRoomId = conferenceRoomId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
