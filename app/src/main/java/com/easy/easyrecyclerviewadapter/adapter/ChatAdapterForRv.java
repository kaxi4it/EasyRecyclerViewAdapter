package com.easy.easyrecyclerviewadapter.adapter;

import android.content.Context;

import com.easy.easyrecyclerviewadapter.delagate.MsgComingItemDelagate;
import com.easy.easyrecyclerviewadapter.delagate.MsgSendItemDelagate;
import com.easy.easyrecyclerviewadapter.delagate.MsgTimeItemDelagate;
import com.guyj.MultiItemTypeAdapter;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv extends MultiItemTypeAdapter<Object>
{
    public ChatAdapterForRv(Context context, List<Object> datas)
    {
        super(context, datas);

        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new MsgTimeItemDelagate());
    }
}
