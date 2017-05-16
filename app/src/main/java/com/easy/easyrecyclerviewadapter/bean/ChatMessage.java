package com.easy.easyrecyclerviewadapter.bean;

import com.easy.easyrecyclerviewadapter.R;

import java.util.ArrayList;
import java.util.List;

public class ChatMessage
{
	private int icon;
	private String name;
	private String content;
	private String createDate;



	private int typeMsg;

	public final static int RECIEVE_MSG = 0;
	public final static int SEND_MSG = 1;
	public final static int TIME_MSG = 2;

	public ChatMessage(int icon, String name, String content,
					   String createDate, int type_msg)
	{
		this.icon = icon;
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.typeMsg = type_msg;
	}

	public int getTypeMsg() {
		return typeMsg;
	}

	public void setTypeMsg(int typeMsg) {
		this.typeMsg = typeMsg;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public int getIcon()
	{
		return icon;
	}

	public void setIcon(int icon)
	{
		this.icon = icon;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(String createDate)
	{
		this.createDate = createDate;
	}

	@Override
	public String toString()
	{
		return "ChatMessage [icon=" + icon + ", name=" + name + ", content="
				+ content + ", createDate=" + createDate +""+ "]";
	}

	public static List<ChatMessage> MOCK_DATAS = new ArrayList<>();

	static {
		ChatMessage msg = null;
		msg = new ChatMessage(R.drawable.xiaohei, "时间", "where are you ",
				null, TIME_MSG);
//		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, SEND_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, RECIEVE_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, SEND_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, RECIEVE_MSG);
		MOCK_DATAS.add(msg);

		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, RECIEVE_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, SEND_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, RECIEVE_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.renma, "renma", "where are you ",
				null, SEND_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, RECIEVE_MSG);
		MOCK_DATAS.add(msg);
		msg = new ChatMessage(R.drawable.xiaohei, "xiaohei", "where are you ",
				null, SEND_MSG);
		MOCK_DATAS.add(msg);
	}


}
