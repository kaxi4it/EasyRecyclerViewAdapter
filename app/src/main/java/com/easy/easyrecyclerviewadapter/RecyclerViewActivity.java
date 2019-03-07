package com.easy.easyrecyclerviewadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.guyj.AutoLoadMoreAdapter;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity
{

    private RecyclerView mRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    private AutoLoadMoreAdapter<String> mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        initDatas();

        mRecyclerView = findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        mAdapter = new AutoLoadMoreAdapter<String>(this, R.layout.item_list, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, String s, int position)
            {
                holder.setText(R.id.id_item_list_title, s + " : " + holder.getAdapterPosition() + " , " + holder.getLayoutPosition());
                holder.setOnItemChildClickListener(R.id.id_item_list_title);
            }

        };

        mAdapter.setEasyOnLoadMoreListener(new EasyOnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(RecyclerViewActivity.this,"加载更多",Toast.LENGTH_SHORT).show();
                initDatas();
                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter.setEasyOnItemChildClickListener(new EasyOnItemChildClickListener()
        {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "重置加载更多+pos = " + position, Toast.LENGTH_SHORT).show();
                mAdapter.resetLoadMoreState();
                mAdapter.notifyItemRemoved(position);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
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
        mRecyclerView.setAdapter(mAdapter);

        return super.onOptionsItemSelected(item);
    }

}
