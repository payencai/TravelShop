package com.tec.travelagency.bean;

import java.io.Serializable;

/**
 * 作者：凌涛 on 2018/12/7 15:24
 * 邮箱：771548229@qq..com
 */
public class ServiceModel implements Serializable{

    /**
     * heading : logo
     * nickname : 平台客服
     * headingUrl : https://www.ynshake.com/logo/wufang.png
     * username : DKJ0OB5TRR9
     */

    private String heading;
    private String nickname;
    private String headingUrl;
    private String username;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadingUrl() {
        return headingUrl;
    }

    public void setHeadingUrl(String headingUrl) {
        this.headingUrl = headingUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
