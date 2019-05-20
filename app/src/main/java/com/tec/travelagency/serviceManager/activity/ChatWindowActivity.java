package com.tec.travelagency.serviceManager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tec.travelagency.R;
import com.tec.travelagency.base.BaseActivity;
import com.tec.travelagency.common.rv.base.RVBaseAdapter;
import com.tec.travelagency.common.rv.base.RVBaseViewHolder;
import com.tec.travelagency.orderManager.activity.AllOderActivity;
import com.tec.travelagency.serviceManager.entity.ChatContent;
import com.tec.travelagency.widget.FButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatWindowActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.mine)
    ImageView mine;
    @BindView(R.id.chatContentRv)
    RecyclerView chatContentRv;
    @BindView(R.id.sendContent)
    EditText sendContent;
    @BindView(R.id.sendBtn)
    FButton sendBtn;
    private RVBaseAdapter<ChatContent> mAdapter;

    @Override
    protected int getContentId() {
        return R.layout.activity_chat_window;
    }

    @Override
    protected void initView() {
        title.setText("你是个好人");
        mine.setVisibility(View.VISIBLE);
        mine.setImageResource(R.mipmap.agency_order_white);
        initRv();
    }

    private void initRv() {
        mAdapter = new RVBaseAdapter<ChatContent>() {
            @Override
            protected void onViewHolderBound(RVBaseViewHolder holder, int position) {

            }
        };
        List<ChatContent> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 0) {
                list.add(new ChatContent("这是我的疑问，麻烦给我个回复", 0));
            } else {
                list.add(new ChatContent("好的，我会详细跟您解释一下的", 1));
            }
        }
        mAdapter.setData(list);
        chatContentRv.setLayoutManager(new LinearLayoutManager(this));
        chatContentRv.setAdapter(mAdapter);
    }


    @OnClick({R.id.back, R.id.mine, R.id.sendBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mine:
                startActivity(new Intent(this, AllOderActivity.class));
                break;
            case R.id.sendBtn:
                break;
        }
    }

}
