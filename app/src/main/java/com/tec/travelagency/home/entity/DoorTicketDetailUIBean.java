package com.tec.travelagency.home.entity;

/**
 * 作者：凌涛 on 2018/9/1 17:52
 * 邮箱：771548229@qq..com
 */
public class DoorTicketDetailUIBean {
    

    private TicketBean ticket;
    private TicketSpecificationBean ticketSpecification;

    public TicketBean getTicket() {
        return ticket;
    }

    public void setTicket(TicketBean ticket) {
        this.ticket = ticket;
    }

    public TicketSpecificationBean getTicketSpecification() {
        return ticketSpecification;
    }

    public void setTicketSpecification(TicketSpecificationBean ticketSpecification) {
        this.ticketSpecification = ticketSpecification;
    }

    public static class TicketBean {
     

        private String id;
        private String name;
        private String type;
        private String typeName;
        private int price;
        private String isCanCancel;
        private String oneDayValidity;
        private String scenicSpotId;
        private String travelAgencyId;
        private String isDeleted;
        private String createTime;
        private String sequence;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getIsCanCancel() {
            return isCanCancel;
        }

        public void setIsCanCancel(String isCanCancel) {
            this.isCanCancel = isCanCancel;
        }

        public String getOneDayValidity() {
            return oneDayValidity;
        }

        public void setOneDayValidity(String oneDayValidity) {
            this.oneDayValidity = oneDayValidity;
        }

        public String getScenicSpotId() {
            return scenicSpotId;
        }

        public void setScenicSpotId(String scenicSpotId) {
            this.scenicSpotId = scenicSpotId;
        }

        public String getTravelAgencyId() {
            return travelAgencyId;
        }

        public void setTravelAgencyId(String travelAgencyId) {
            this.travelAgencyId = travelAgencyId;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getSequence() {
            return sequence;
        }

        public void setSequence(String sequence) {
            this.sequence = sequence;
        }
    }

    public static class TicketSpecificationBean {
        /**
         * id : 0881c9ec-450a-4b47-b4af-7f740585d214
         * reservation : 手动阀手动阀
         * fee : 手动阀手动阀手动阀
         * use : 撒旦发射点发射点发射点
         * refund : 撒旦发射点发射点发生
         */

        private String id;
        private String reservation;
        private String fee;
        private String use;
        private String refund;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReservation() {
            return reservation;
        }

        public void setReservation(String reservation) {
            this.reservation = reservation;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getUse() {
            return use;
        }

        public void setUse(String use) {
            this.use = use;
        }

        public String getRefund() {
            return refund;
        }

        public void setRefund(String refund) {
            this.refund = refund;
        }
    }
}
