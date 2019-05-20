package com.tec.travelagency.orderManager.fragment.detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.App;
import com.tec.travelagency.Constant.PlatformContans;
import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.home.activity.LxsHotelOrderDetailActivity;
import com.tec.travelagency.home.entity.UpdateOrderFragment;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.orderManager.entity.LxsCarOrder;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.CustomPopWindow;
import com.tec.travelagency.widget.KyLoadingBuilder;
import com.tec.travelagency.widget.StarLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class CarOrderDelActivity extends BaseActivity {


    @Override
    protected int getContentId() {
        return R.layout.activity_car_order_detail;
    }

    CarRentalComment mRentalComment;
    CarOrderDetailModel mCarOrderDetailModel;
    String id;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.telephone)
    ImageView tel;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.ll_comment)
    LinearLayout ll_comment;
    @BindView(R.id.hotel_name)
    TextView name;
    @BindView(R.id.addressDetail)
    TextView addressDetail;
    @BindView(R.id.timeInfo)
    TextView timeInfo;
    @BindView(R.id.bedTypeAndOther)
    TextView other;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.checkInPerson)
    TextView checkInPerson;
    @BindView(R.id.telephoneNum)
    TextView checkInNumber;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.orderId)
    TextView orderId;
    @BindView(R.id.paymentTime)
    TextView paymentTime;
    @BindView(R.id.modeOfPayment)
    TextView modeOfPayment;
    @BindView(R.id.cancel_order)
    TextView tv_status;
    @BindView(R.id.statesString)
    TextView statusString;
    @BindView(R.id.cancelReasonText)
    TextView cancelReasonText;
    @BindView(R.id.applyCanceTime)
    TextView applyCanceTime;
    @BindView(R.id.applyCancelLayout)
    LinearLayout applyCancelLayout;
    @BindView(R.id.cancelReasonLayout)
    LinearLayout cancelReasonLayout;
    @BindView(R.id.applyCancelSucceedLayout)
    LinearLayout applyCancelSucceedLayout;
    @BindView(R.id.commentscore)
    TextView commentscore;
    @BindView(R.id.commentContent)
    TextView commentContent;
    @BindView(R.id.commenttime)
    TextView commenttime;
    @BindView(R.id.rv_refund)
    ListView rv_refund;
    RefundAdapter mRefundAdapter;
    List<Refund> mRefunds=new ArrayList<>();
    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
        tv_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancelOrderOrSubmitComments(v, R.layout.pw_canceloder_or_submiycomments);
            }
        });
        getDetail(id);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });
    }
    @SuppressLint("MissingPermission")
    public void callPhone() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + mCarOrderDetailModel.getContactNumber());
        intent.setData(data);
        startActivity(intent);
    }

    private void setView(CarOrderDetailModel carOrderDetailModel) {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("订单详情");
        amount.setText("共￥" + carOrderDetailModel.getAmount());
        name.setText(carOrderDetailModel.getName());
        orderId.setText(carOrderDetailModel.getOrderNum());
        paymentTime.setText(carOrderDetailModel.getPaymentTime());
        if (!TextUtils.isEmpty(carOrderDetailModel.getModeOfPayment()))
            modeOfPayment.setText(carOrderDetailModel.getModeOfPayment());
        checkInPerson.setText(carOrderDetailModel.getContactName());
        checkInNumber.setText(carOrderDetailModel.getContactNumber());
        timeInfo.setText(mCarOrderDetailModel.getUseTime().substring(0, 10) + "    " + "1天");
        String type = carOrderDetailModel.getCarType();
        switch (type) {
            case "1":
                type = "汽油车";
                break;
            case "2":
                type = "电车";
                break;
            case "3":
                type = "柴油车";
                break;
        }
        String isCancel = "";
        if (carOrderDetailModel.getIsCanCancel().equals("1")) {
            isCancel = "可退";
        } else {
            isCancel = "不可退";
        }
        other.setText(carOrderDetailModel.getSeatNumber() + "座|" + type + "|" + isCancel);
        addressDetail.setText(carOrderDetailModel.getAddress());
        if(TextUtils.isEmpty(carOrderDetailModel.getAddressDetail())||"".equals(mCarOrderDetailModel.getAddressDetail())){
            detail.setVisibility(View.GONE);
        }else{
            detail.setText("(" + carOrderDetailModel.getAddressDetail() + ")");
        }
        String status = carOrderDetailModel.getStatus();
        Log.e("status", status);
        switch (status) {
            case "1":
                tv_status.setText("申请取消");
                break;
            case "2":
                statusString.setText("已发起取消订单申请，将在24小时内处理，请耐心等候");
                statusString.setVisibility(View.VISIBLE);
                tv_status.setText("取消中");
                tv_status.setTextColor(getResources().getColor(R.color.color_999));
                tv_status.setEnabled(false);
                break;
            case "3":
                statusString.setVisibility(View.VISIBLE);
               // applyCancelSucceedLayout.setVisibility(View.VISIBLE);
                tv_status.setText("已取消");
                tv_status.setEnabled(false);
                tv_status.setTextColor(getResources().getColor(R.color.color_999));
                break;
            case "4":
                if (carOrderDetailModel.getIsComment().equals("1")) {
                    tv_status.setText("评价");
                } else {
                    ll_comment.setVisibility(View.VISIBLE);
                    statusString.setText("订单已完成，期待你再次预定");
                    statusString.setVisibility(View.VISIBLE);
                    tv_status.setText("已评价");
                    tv_status.setEnabled(false);
                    tv_status.setTextColor(getResources().getColor(R.color.color_999));
                }
                break;
        }
    }

    private void getDetail(String id) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        HttpProxy.obtain().get(PlatformContans.Order.getCarOrderListDetail, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                Log.e("carorder", result);

                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONObject detail = data.getJSONObject("carRenatlOrderOfTravelAgency");
                        mCarOrderDetailModel = new Gson().fromJson(detail.toString(), CarOrderDetailModel.class);
                        setView(mCarOrderDetailModel);
                        if (mCarOrderDetailModel.getStatus().equals("4")) {
                            JSONObject comment = data.getJSONObject("carRentalComment");
                            mRentalComment = new Gson().fromJson(comment.toString(), CarRentalComment.class);
                            commentContent.setText(mRentalComment.getContent());
                            commentscore.setText(mRentalComment.getScore() + "");
                            commenttime.setText(mRentalComment.getCreateTime());
                        }
                        if(mCarOrderDetailModel.getStatus().equals("2")||mCarOrderDetailModel.getStatus().equals("3")|| mCarOrderDetailModel.getStatus().equals("4")){
                            JSONArray refundList=data.getJSONArray("refundList");
                            if(refundList.length()>0){
                                for (int i = 0; i <refundList.length() ; i++) {
                                    JSONObject item =refundList.getJSONObject(i);
                                    Refund refund=new Gson().fromJson(item.toString(),Refund.class);
                                    mRefunds.add(refund);
                                }
                                //  Log.e("refundList",mRefunds.get(1).getRefundReason()+"111");
                                mRefundAdapter=new RefundAdapter(CarOrderDelActivity.this,mRefunds);
                                rv_refund.setAdapter(mRefundAdapter);
                                setListViewHeightBasedOnChildren(rv_refund);
                            }
                        }

                    } else {
                        ToaskUtil.showToast(CarOrderDelActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onLtFailure(String error) {

            }
        });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i,null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.invalidate();
    }

    private void showCancelOrderOrSubmitComments(View view, @LayoutRes int resource) {
        View layoutView = LayoutInflater.from(this).inflate(resource, null);
        CustomPopWindow customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(layoutView)
                .sizeByPercentage(this, 0.8f, 0)
                .setOutsideTouchable(true)
                .enableBackgroundDark(true)
                .setAnimationStyle(R.style.CustomPopWindowStyleFromBottom)
                .setBgDarkAlpha(0.5f)
                .create();
        customPopWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        handlePwView(layoutView, customPopWindow);
    }

    private void handlePwView(View view, final CustomPopWindow customPopWindow) {
        final int[] starLevel = {1};
        final EditText reasonExitView = (EditText) view.findViewById(R.id.reasonExit);
        final TextView wordsAmountText = (TextView) view.findViewById(R.id.wordsAmount);
        LinearLayout gradeLayout = (LinearLayout) view.findViewById(R.id.gradeLayout);
        TextView cancel_title = view.findViewById(R.id.applyCancelPw);
        StarLinearLayout starLinearLayout = view.findViewById(R.id.grad_star);
        if (mCarOrderDetailModel.getStatus().equals("4")) {
            gradeLayout.setVisibility(View.VISIBLE);
            cancel_title.setVisibility(View.GONE);
            wordsAmountText.setText("0/50");
        }
        starLinearLayout.setChangeListener(new StarLinearLayout.ChangeListener() {
            @Override
            public void Change(int level) {
                starLevel[0] = level;
            }
        });
        reasonExitView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                if (mCarOrderDetailModel.getStatus().equals(4))
                    wordsAmountText.setText(length + "/50");
                else
                    wordsAmountText.setText(length + "/100");
            }
        });

        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
            }
        });
        TextView sumbitText = (TextView) view.findViewById(R.id.submit);
        final String status = mCarOrderDetailModel.getStatus();
        if (status.equals("1")) {
            sumbitText.setText("确定申请");
        }
        if (status.equals("4")) {
            sumbitText.setText("提交评价");
        }
        sumbitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customPopWindow.dissmiss();
                if (mCarOrderDetailModel == null) {
                    ToaskUtil.showToast(CarOrderDelActivity.this, "数据加载未完成");
                    return;
                }
                mApplyOrderCancelView = openLoadView("");
                if (status.equals("1"))
                    applyOrderCancel(PlatformContans.Order.applyCancel, mCarOrderDetailModel.getId(),
                            reasonExitView.getEditableText().toString());
                else {
                    Log.e("score", "fdfgfg");
                    applyOrderComment(PlatformContans.Comment.commetCar, mCarOrderDetailModel.getId(),
                            reasonExitView.getEditableText().toString(), starLevel[0]);
                }
            }
        });
    }

    private void applyOrderComment(String url, String id, final String reason, int level) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("orderId", id);
        parame.put("score", level);
        parame.put("content", reason);
        Log.e("pa", parame.toString());
        HttpProxy.obtain().post(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                if (mApplyOrderCancelView != null) {
                    mApplyOrderCancelView.dismiss();
                }
                LogUtil.Log("applyForTravelAgency", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(CarOrderDelActivity.this, "评价成功");
                        //更新订单界面
//                        EventBus.getDefault().post(new UpdateOrderFragment());
//                        finish();
                        tv_status.setText("已评价");
                        tv_status.setTextColor(getResources().getColor(R.color.color_999));
                        tv_status.setEnabled(false);
                    } else {
                        ToaskUtil.showToast(CarOrderDelActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (mApplyOrderCancelView != null) {
                    mApplyOrderCancelView.dismiss();
                }
            }
        });
    }

    private KyLoadingBuilder mApplyOrderCancelView;

    private void applyOrderCancel(String url, String id, final String reason) {
        Map<String, Object> parame = new HashMap<>();
        parame.put("orderId", id);
        parame.put("reason", reason);
        parame.put("categoryId", "3");
        HttpProxy.obtain().post(url, App.getInstance().getUserEntity().getToken(), parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                if (mApplyOrderCancelView != null) {
                    mApplyOrderCancelView.dismiss();
                }
                LogUtil.Log("applyForTravelAgency", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        ToaskUtil.showToast(CarOrderDelActivity.this, "已提交退款申请");
                    } else {
                        ToaskUtil.showToast(CarOrderDelActivity.this, message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (mApplyOrderCancelView != null) {
                    mApplyOrderCancelView.dismiss();
                }
            }
        });
    }


}
