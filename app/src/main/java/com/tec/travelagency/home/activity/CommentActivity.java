package com.tec.travelagency.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.fragment.CommentFragment;
import com.tec.travelagency.utils.ToaskUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;

    private int mCommentType = -1;//什么的评论  1为酒店，2为租车，3为路线 4为景区门票,5会议室
    private String id;

    public static void startCommentActivity(Activity activity, int commentType, String id) {
        Intent intent = new Intent(activity, CommentActivity.class);
        intent.putExtra("commentType", commentType);
        intent.putExtra("obtainCommentId", id);
        activity.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        mCommentType = intent.getIntExtra("commentType", -1);
        id = intent.getStringExtra("obtainCommentId");
        if (mCommentType == -1) {
            finish();
            ToaskUtil.showToast(this, "请携带参数类型进来");
            return;
        }
        title.setText("用户点评");
        String url = null;
        String requestScoreUrl = null;
        switch (mCommentType) {
            case 1:
                url = PlatformContans.Comment.getHotelCommentList;
                requestScoreUrl = PlatformContans.Comment.getHotelCommentScoreById;
                break;
            case 2:
                url = PlatformContans.Comment.getCarRentalCommentList;
                requestScoreUrl = PlatformContans.Comment.getCarRentalCommentScoreById;
                break;
            case 3:
                url = PlatformContans.Comment.getRouteCommentList;
                requestScoreUrl = PlatformContans.Comment.getRouteCommentScoreById;
                break;
            case 4:
                url = PlatformContans.Comment.getTicketCommentList;
                requestScoreUrl = PlatformContans.Comment.getTicketCommentScoreById;
                break;
            case 5:
                url=PlatformContans.Comment.getConferenceRoomCommentList;
                requestScoreUrl=PlatformContans.Comment.getConferenceRoomCommentScoreById;
        }
        CommentFragment fragment = new CommentFragment(url, id, requestScoreUrl);
        getSupportFragmentManager().beginTransaction().add(R.id.comment_container, fragment).commit();

    }


    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
