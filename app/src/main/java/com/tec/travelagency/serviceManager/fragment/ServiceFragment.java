package com.tec.travelagency.serviceManager.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.tec.travelagency.R;
import com.tec.travelagency.common.activity.MineActivity;
import com.tec.travelagency.common.rv.abs.AbsBaseFragment;
import com.tec.travelagency.common.rv.base.Cell;
import com.tec.travelagency.serviceManager.entity.ChatList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：凌涛 on 2018/8/8 10:58
 * 邮箱：771548229@qq..com
 */
public class ServiceFragment extends AbsBaseFragment<ChatList> {
    @Override
    public void onRecyclerViewInitialized() {
        addDividerItem(0);
        requestData();
    }


    @Override
    public void onPullRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void startLoadData() {

    }

    @Override
    protected List<Cell> getCells(List<ChatList> list) {
        return null;
    }

    private void requestData() {
//        List<ChatList> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            list.add(new ChatList());
//        }
//        mBaseAdapter.setData(list);

        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        for (String s : conversations.keySet()) {
            Log.d("requestData", "requestData: key:"+s+",value:"+conversations.get(s));
        }

    }

    @Override
    public View addToolbar() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.toobar_head_layout, null);
        ((TextView) view.findViewById(R.id.title)).setText("旅行社");
        ImageView mineImg = (ImageView) view.findViewById(R.id.mine);
        view.findViewById(R.id.back).setVisibility(View.GONE);
        mineImg.setVisibility(View.VISIBLE);
        mineImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MineActivity.class));
            }
        });
        return view;
    }
}
