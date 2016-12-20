package com.easy.easyrecyclerviewadapter.delagate;


import com.easy.easyrecyclerviewadapter.R;
import com.easy.easyrecyclerviewadapter.bean.ChatMessage;
import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ViewHolder;

/**
 * Created by zhy on 16/6/22.
 */
public class MsgSendItemDelagate implements ItemViewDelegate<Object>
{

    @Override
    public int getItemViewLayoutId()
    {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(Object item, int position)
    {
        return ChatMessage.SEND_MSG==((ChatMessage)item).getTypeMsg();
    }

    @Override
    public void convert(ViewHolder holder, Object chatMessage, int position)
    {
        holder.setText(R.id.chat_send_content, ((ChatMessage)chatMessage).getContent());
        holder.setText(R.id.chat_send_name, ((ChatMessage)chatMessage).getName());
        holder.setImageResource(R.id.chat_send_icon, ((ChatMessage)chatMessage).getIcon());
        holder.setOnItemChildClickListener(R.id.chat_send_content);
        holder.setOnItemChildLongClickListener(R.id.chat_send_icon);
    }
}
