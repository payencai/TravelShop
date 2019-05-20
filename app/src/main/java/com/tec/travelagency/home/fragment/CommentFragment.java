package com.tec.travelagency.home.fragment;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tec.travelagency.R;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.home.entity.CommentBean;
import com.tec.travelagency.http.HttpProxy;
import com.tec.travelagency.http.ICallBack;
import com.tec.travelagency.utils.LogUtil;
import com.tec.travelagency.utils.ToaskUtil;
import com.tec.travelagency.widget.StarLinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2018/9/12 14:25
 * 邮箱：771548229@qq..com
 */
@SuppressLint("ValidFragment")
public class CommentFragment extends AbsBaseFragment<CommentBean> implements View.OnClickListener {

    StarLinearLayout Star;
    TextView scale;
    private String requestListUrl;
    private String requestScoreUrl;
    private String id;

    private int page = 1;
    private boolean isLoadMore = false;

    public CommentFragment(String requestUrl, String id, String requestScoreUrl) {
        this.requestListUrl = requestUrl;
        this.id = id;
        this.requestScoreUrl = requestScoreUrl;
    }

    @Override
    public void onRecyclerViewInitialized() {
        if (TextUtils.isEmpty(requestListUrl) || TextUtils.isEmpty(id)) {
            throw new RuntimeException("this requestListUrl value is NULL");
        }
        page = 1;
        isLoadMore = false;
        requestDate();
    }

    @Override
    public void onPullRefresh() {
        page = 1;
        isLoadMore = false;
        requestDate();
    }

    @Override
    public void onLoadMore() {
        page++;
        isLoadMore = true;
        requestDate();
    }

    @Override
    public void startLoadData() {

    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.comment_fragment_header_layout, null);
        Star = ((StarLinearLayout) view.findViewById(R.id.Star));
        scale = ((TextView) view.findViewById(R.id.scale));
        requestScoreData();
        return view;
    }

    private void requestScoreData() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        HttpProxy.obtain().get(requestScoreUrl, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                LogUtil.Log("requestScoreUrl", result);
                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        double data = object.getDouble("data");
                        scale.setText(data + "/5分");
                        Star.setScore((float) data);
                    } else {
                        ToaskUtil.showToast(getContext(), message);
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

    @Override
    protected List<Cell> getCells(List<CommentBean> list) {
        return null;
    }

    private void requestDate() {
        Map<String, Object> parame = new HashMap<>();
        parame.put("id", id);
        parame.put("page", page);
        HttpProxy.obtain().get(requestListUrl, parame, new ICallBack() {
            @Override
            public void OnLtSuccess(String result) {
                mSwipeRefreshLayout.setRefreshing(false);
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
                LogUtil.Log("responseData", result);

                try {
                    JSONObject object = new JSONObject(result);
                    int resultCode = object.getInt("resultCode");
                    String message = object.getString("message");
                    if (resultCode == 0) {
                        JSONObject data = object.getJSONObject("data");
                        JSONArray beanList = data.getJSONArray("beanList");
                        int length = beanList.length();
                        List<CommentBean> list = new LinkedList<>();
                        Gson gson = new Gson();

                        int pageNum = data.getInt("pageNum");

                        for (int i = 0; i < length; i++) {
                            JSONObject item = beanList.getJSONObject(i);
                            CommentBean bean = gson.fromJson(item.toString(), CommentBean.class);
                            list.add(bean);
                        }
                        if (list.size() == 0 && pageNum != 1) {
                            ToaskUtil.showToast(getContext(), "真的到底了");
                            return;
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mBaseAdapter.setData(list);
                        } else {
                            mBaseAdapter.setDataByRemove(list);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLtFailure(String error) {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (isLoadMore) {
                    mBaseAdapter.hideLoadMore();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }


}
