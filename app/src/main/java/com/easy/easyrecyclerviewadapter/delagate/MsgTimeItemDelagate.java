package com.easy.easyrecyclerviewadapter.delagate;


import com.easy.easyrecyclerviewadapter.R;
import com.easy.easyrecyclerviewadapter.bean.ChatMessage;
import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgTimeItemDelagate implements ItemViewDelegate<Object>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.main_chat_time_msg;
    }

    @Override
    public boolean isForViewType(Object item, int position)
    {
        return ChatMessage.TIME_MSG==((ChatMessage)item).getTypeMsg();
    }

    @Override
    public void convert(ViewHolder holder, Object chatMessage, int position)
    {
        holder.setText(R.id.chat_time_content, ((ChatMessage)chatMessage).getName());
    }
}
