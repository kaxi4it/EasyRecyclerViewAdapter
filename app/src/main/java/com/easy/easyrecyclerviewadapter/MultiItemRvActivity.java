package com.easy.easyrecyclerviewadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.easy.easyrecyclerviewadapter.adapter.ChatAdapterForRv2;
import com.easy.easyrecyclerviewadapter.bean.ChatMessage;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnItemChildLongClickListener;
import com.guyj.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class MultiItemRvActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private GridLayoutManager mManager;
    private LoadMoreWrapper mLoadMoreWrapper;
    private List<Object> mDatas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);


        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(mManager=new GridLayoutManager(this,2));
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(mDatas.get(position) instanceof ChatMessage){
                    if (  ((ChatMessage) mDatas.get(position)).getTypeMsg()==ChatMessage.SEND_MSG){
                        return 2;
                    }
                }
                return 1;
            }
        });
        mDatas.addAll(ChatMessage.MOCK_DATAS);
        final ChatAdapterForRv2 adapter = new ChatAdapterForRv2(this, mDatas);

//        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
//        mLoadMoreWrapper.setLoadMoreView(LayoutInflater.from(this).inflate(R.layout.default_loading, mRecyclerView, false));
//        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
//        {
//            @Override
//            public void onLoadMoreRequested()
//            {
//                new Handler().postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        boolean coming = Math.random() > 0.5;
//                        ChatMessage msg = null;
//                        msg = new ChatMessage(coming ? R.drawable.renma : R.drawable.xiaohei, coming ? "人马" : "xiaohei", "where are you " + mDatas.size(),
//                                null, coming?ChatMessage.RECIEVE_MSG:ChatMessage.SEND_MSG);
//                        mDatas.add(msg);
//                        mLoadMoreWrapper.notifyDataSetChanged();
//
//                    }
//                }, 1000);
//            }
//        });

        adapter.setEasyOnItemChildClickListener(new EasyOnItemChildClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                switch (view.getId()){
                    case R.id.chat_send_content:
                        Toast.makeText(MultiItemRvActivity.this, "重置加载状态+Click Send:" + position , Toast.LENGTH_SHORT).show();
                        adapter.resetLoadMoreState();
                        break;
                    case R.id.chat_from_content:
                        Toast.makeText(MultiItemRvActivity.this, "Click From:" + position , Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        });
        adapter.setEasyOnItemChildLongClickListener(new EasyOnItemChildLongClickListener() {
            @Override
            public boolean onLongClick(View view, int position) {
                switch (view.getId()){
                    case R.id.chat_send_icon:
                        Toast.makeText(MultiItemRvActivity.this, "LongClick Send:" + position , Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
//        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mRecyclerView.setAdapter(adapter);
    }


}
