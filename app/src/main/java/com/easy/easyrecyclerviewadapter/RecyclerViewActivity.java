package com.easy.easyrecyclerviewadapter;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;


import com.guyj.AutoLoadMoreAdapter;
import com.guyj.CommonAdapter;
import com.guyj.MultiItemTypeAdapter;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnItemChildCheckChangeListener;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnItemChildLongClickListener;
import com.guyj.listener.EasyOnItemChildTouchListener;
import com.guyj.listener.EasyOnLoadMoreListener;
import com.guyj.wrapper.EmptyWrapper;
import com.guyj.wrapper.HeaderAndFooterWrapper;
import com.guyj.wrapper.LoadMoreWrapper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity
{

    private RecyclerView mRecyclerView;
    private List<String> mDatas;// = new ArrayList<>();
    private AutoLoadMoreAdapter<String> mAdapter;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private EmptyWrapper mEmptyWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

//        initDatas();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new AutoLoadMoreAdapter<String>(this, R.layout.item_list, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, String s, int position)
            {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
                holder.setOnItemChildClickListener(R.id.id_item_list_title);
            }

            @Override
            protected void loadMore() {
                Log.e("loadmore","===========");
                Toast.makeText(RecyclerViewActivity.this,"加载更多",Toast.LENGTH_SHORT).show();
            }
        };
        mAdapter.openLoadAnimation(MultiItemTypeAdapter.SCALEIN);
        mAdapter.isFirstOnly(false);
//        mAdapter.setOnLoadMoreListener(new EasyOnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                Log.e("loadmore","===========");
//                //假设加载失败 那么重置下loadmore状态
////                mAdapter.resetLoadMoreState();
//            }
//        });
        /**
         * new adapter之后初始化，如果不setDatas那么会报异常并提示
         */
        mDatas = new ArrayList<>();
        initDatas();
        mAdapter.setDatas(mDatas);

//        initHeaderAndFooter();

//        initEmptyView();
        mRecyclerView.setAdapter(mAdapter);
//        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
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
//                        for (int i = 0; i < 10; i++)
//                        {
//                            mDatas.add("Add:" + i);
//                        }
//                        mLoadMoreWrapper.notifyDataSetChanged();
//
//                    }
//                }, 1000);
//            }
//        });

//        mRecyclerView.setAdapter(mLoadMoreWrapper);
        mAdapter.setEasyOnItemChildClickListener(new EasyOnItemChildClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "pos = " + position, Toast.LENGTH_SHORT).show();
                mAdapter.notifyItemRemoved(position);
            }
        });
    }

    private void initEmptyView()
    {
        mEmptyWrapper = new EmptyWrapper(mAdapter);
        mEmptyWrapper.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, mRecyclerView, false));
    }

    private void initHeaderAndFooter()
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        TextView t1 = new TextView(this);
        t1.setText("Header 1");
        TextView t2 = new TextView(this);
        t2.setText("Header 2");
        mHeaderAndFooterWrapper.addHeaderView(t1);
        mHeaderAndFooterWrapper.addFootView(t2);
    }

    private void initDatas()
    {
        for (int i = 'A'; i <= 'z'; i++)
        {
            mDatas.add((char) i + "");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {
            case R.id.action_linear:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                break;
            case R.id.action_grid:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                break;
            case R.id.action_staggered:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
        }
//        mRecyclerView.setAdapter(mLoadMoreWrapper);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart()
    {
        super.onStart();


    }

    @Override
    public void onStop()
    {
        super.onStop();


    }
}
