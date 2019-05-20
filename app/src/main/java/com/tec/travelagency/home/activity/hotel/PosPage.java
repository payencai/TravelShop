package com.tec.travelagency.home.activity.hotel;

/**
 * 作者：凌涛 on 2018/12/5 15:40
 * 邮箱：771548229@qq..com
 */
public class PosPage {
    int pos;
    int page;

    public PosPage(int pos, int page) {
        this.pos = pos;
        this.page = page;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
