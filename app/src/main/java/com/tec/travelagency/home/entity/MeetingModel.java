package com.tec.travelagency.home.entity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tec.travelagency.R;
import com.tec.travelagency.common.activity.LoginActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseCell;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.home.activity.HotelDetailActivity;
import com.tec.travelagency.home.activity.meeting.MeetingDetailActivity;
import com.tec.travelagency.home.activity.meeting.MeetingUpActivity;
import com.tec.travelagency.widget.StarLinearLayout;

import java.io.Serializable;

public class MeetingModel extends RVBaseCell implements Serializable {

    /**
     * id : 1315c000-cea6-4f71-b7ec-12db6e2dcaeb
     * name : 测试1
     * starLevel : 5
     * oneWord : 水电费水电费123
     * address : 浙江省杭州市江干区天城路1号
     * addressDetail : 峰哥发过火凤凰士大夫
     * longitude : 120.213116
     * latitude : 30.290998
     * province : 浙江省
     * city : 杭州市
     * district : 江干区
     * image1 : journey/2018102915142718
     * image2 : journey/2018102915142726
     * image3 :
     * image4 :
     * image5 :
     * image6 :
     * image1Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018102915142718?Expires=1540956532&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=a9DZVLnoyay8%2FCEgAleiTa3sQM0%3D
     * image2Url : http://xinlvmeng.oss-cn-shenzhen.aliyuncs.com/journey/2018102915142726?Expires=1540956532&OSSAccessKeyId=LTAIPJcqlCr74QtJ&Signature=h1JJOKBsgwtla9U%2FndPuGgv%2Bam4%3D
     * image3Url : null
     * image4Url : null
     * image5Url : null
     * image6Url : null
     * contactNumber : 15487875454
     * sequence : null
     * createTime : 2018-10-29 15:14:29
     * isUsed : 1
     * isDeleted : 1
     * commentData : {"score":5,"number":0,"rate":1}
     * typeNum : 0
     */

    private String id;
    private String name;
    private String starLevel;
    private String oneWord;
    private String address;
    private String addressDetail;
    private String longitude;
    private String latitude;
    private String province;
    private String city;
    private String district;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image1Url;
    private String image2Url;
    private String image3Url;
    private String image4Url;
    private String image5Url;
    private String image6Url;
    private String contactNumber;
    private int sequence;
    private String createTime;
    private String isUsed;
    private String isDeleted;
    private boolean isAdapot;

    public boolean isAdapot() {
        return isAdapot;
    }

    public void setAdapot(boolean adapot) {
        isAdapot = adapot;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    private CommentDataBean commentData;
    private int typeNum;
    public int itemType=0;
    public MeetingModel(Object o) {
        super(null);
    }

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

    public String getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(String starLevel) {
        this.starLevel = starLevel;
    }

    public String getOneWord() {
        return oneWord;
    }

    public void setOneWord(String oneWord) {
        this.oneWord = oneWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = image6;
    }

    public String getImage1Url() {
        return image1Url;
    }

    public void setImage1Url(String image1Url) {
        this.image1Url = image1Url;
    }

    public String getImage2Url() {
        return image2Url;
    }

    public void setImage2Url(String image2Url) {
        this.image2Url = image2Url;
    }

    public String getImage3Url() {
        return image3Url;
    }

    public void setImage3Url(String image3Url) {
        this.image3Url = image3Url;
    }

    public String getImage4Url() {
        return image4Url;
    }

    public void setImage4Url(String image4Url) {
        this.image4Url = image4Url;
    }

    public String getImage5Url() {
        return image5Url;
    }

    public void setImage5Url(String image5Url) {
        this.image5Url = image5Url;
    }

    public String getImage6Url() {
        return image6Url;
    }

    public void setImage6Url(String image6Url) {
        this.image6Url = image6Url;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public CommentDataBean getCommentData() {
        return commentData;
    }

    public void setCommentData(CommentDataBean commentData) {
        this.commentData = commentData;
    }

    public int getTypeNum() {
        return typeNum;
    }

    public void setTypeNum(int typeNum) {
        this.typeNum = typeNum;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view);
        return holder;
    }
    public interface OnItemClickListener{
        void onClick(Context context,int position);
    }
    private OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener=mOnItemClickListener;
    }
    @Override
    public void onBindViewHolder(final RVBaseViewHolder holder, final int position, final Context context, RVBaseAdapter adapter) {
        ImageView imageView = holder.getImageView(R.id.hotelHead);

        Glide.with(context).load(image1Url).into(imageView);
        holder.setText(R.id.name, name);
        holder.setText(R.id.address, province + city + district + addressDetail);
        holder.getTextView(R.id.hotel_des).setText(oneWord);
        ((StarLinearLayout) holder.getView(R.id.star)).setScore((float) commentData.getScore());
        TextView score = holder.getTextView(R.id.score);
        TextView evaluate = holder.getTextView(R.id.evaluate);
        score.setText(commentData.getScore() + "分");
        evaluate.setText(commentData.getNumber() + "条评论");
        final ImageView callImgView = holder.getImageView(R.id.callImg);
        callImgView.setVisibility(View.GONE);
        holder.getView(R.id.item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mOnItemClickListener.onClick(context,position);
                Log.e("click",context.toString());
                //context.startActivity(new Intent(context,D.class));
                MeetingDetailActivity.startDetail( context, MeetingModel.this);
            }
        });

    }

    public static class CommentDataBean implements Serializable{
        /**
         * score : 5.0
         * number : 0
         * rate : 1.0
         */

        private double score;
        private int number;
        private double rate;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }
    }
}
