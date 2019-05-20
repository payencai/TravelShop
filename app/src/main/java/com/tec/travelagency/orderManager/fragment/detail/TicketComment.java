package com.tec.travelagency.orderManager.fragment.detail;

/**
 * 作者：凌涛 on 2018/11/23 11:52
 * 邮箱：771548229@qq..com
 */
public class TicketComment {

    /**
     * content : string
     * createTime : 2018-11-23T03:52:20.874Z
     * id : string
     * image : string
     * name : string
     * orderId : string
     * score : 0
     * state : 0
     * ticketId : string
     * ticketType : string
     */

    private String content;
    private String createTime;
    private String id;
    private String image;
    private String name;
    private String orderId;
    private float score;
    private int state;
    private String ticketId;
    private String ticketType;

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

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
}
