package com.guyj.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.guyj.listener.EasyOnItemChildCheckChangeListener;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnItemChildLongClickListener;
import com.guyj.listener.EasyOnItemChildTouchListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class ViewHolder extends RecyclerView.ViewHolder
{
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static ViewHolder createViewHolder(Context context, View itemView)
    {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends TextView> T getTextView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends ImageView> T getImageView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends CheckBox> T getCheckBox(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends RadioButton> T getRadioButton(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends ProgressBar> T getProgressBar(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public <T extends SeekBar> T getSeekBar(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }



    public View getConvertView()
    {
        return mConvertView;
    }

    public Context getContext(){
        return mContext;
    }




    /****以下为辅助方法*****/
    
    /**
     * 设置ItemView是否显示，true显示，false隐藏
     * @param isVisible
     */
    public void setItemViewVisibility(boolean isVisible){
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)itemView.getLayoutParams();
        if (isVisible){
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            itemView.setVisibility(VISIBLE);
        }else{
            itemView.setVisibility(GONE);
            param.height = 0;
            param.width = 0;
        }
        itemView.setLayoutParams(param);
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        if (getView(viewId) instanceof TextView){
            TextView tv = getView(viewId);
            tv.setText(text);
        }else{
            throw new ClassCastException("ViewHolder setText 传入的控件ID 非 TextView 的子类");
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId)
    {
        if (getView(viewId) instanceof ImageView){
            ImageView view = getView(viewId);
            view.setImageResource(resId);
        }else{
            throw new ClassCastException("ViewHolder setImageResource 传入的控件ID 非 ImageView 的子类");
        }
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap)
    {
        if (getView(viewId) instanceof ImageView){
            ImageView view = getView(viewId);
            view.setImageBitmap(bitmap);
        }else{
            throw new ClassCastException("ViewHolder setImageBitmap 传入的控件ID 非 ImageView 的子类");
        }
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable)
    {
        if (getView(viewId) instanceof ImageView){
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
        }else{
            throw new ClassCastException("ViewHolder setImageDrawable 传入的控件ID 非 ImageView 的子类");
        }
        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color)
    {
        if (getView(viewId) instanceof View){
            View view = getView(viewId);
            view.setBackgroundColor(color);
        }else{
            throw new ClassCastException("ViewHolder setBackgroundColor 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes)
    {
        if (getView(viewId) instanceof View){
            View view = getView(viewId);
            view.setBackgroundResource(backgroundRes);
        }else{
            throw new ClassCastException("ViewHolder setBackgroundRes 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor)
    {
        if (getView(viewId) instanceof TextView){
            TextView view = getView(viewId);
            view.setTextColor(textColor);
        }else{
            throw new ClassCastException("ViewHolder setTextColor 传入的控件ID 非 TextView 的子类");
        }
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes)
    {
        if (getView(viewId) instanceof TextView){
            TextView view = getView(viewId);
            view.setTextColor(mContext.getResources().getColor(textColorRes));
        }else{
            throw new ClassCastException("ViewHolder setTextColorRes 传入的控件ID 非 TextView 的子类");
        }
        return this;
    }

    public ViewHolder setAlpha(int viewId, float value)
    {
        if (getView(viewId) instanceof View){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            {
                getView(viewId).setAlpha(value);
            } else {
                // Pre-honeycomb hack to set Alpha value
                AlphaAnimation alpha = new AlphaAnimation(value, value);
                alpha.setDuration(0);
                alpha.setFillAfter(true);
                getView(viewId).startAnimation(alpha);
            }
        }else{
            throw new ClassCastException("ViewHolder setAlpha 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible)
    {
        if (getView(viewId) instanceof View) {
            View view = getView(viewId);
            view.setVisibility(visible ? VISIBLE : GONE);
        }else{
            throw new ClassCastException("ViewHolder setVisible 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    /**
     * @hide
     */
    @IntDef({VISIBLE, INVISIBLE, GONE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {}

    public ViewHolder setVisible(int viewId,@Visibility int visible)
    {
        if (getView(viewId) != null) {
            View view = getView(viewId);
            view.setVisibility(visible);
        }else {
            throw new ClassCastException("ViewHolder setVisible 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder linkify(int viewId)
    {
        if (getView(viewId) instanceof TextView) {
            TextView view = getView(viewId);
            Linkify.addLinks(view, Linkify.ALL);
        }else {
            throw new ClassCastException("ViewHolder linkify 传入的控件ID 非 TextView 的子类");
        }
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds) {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress)
    {
        if (getView(viewId) instanceof ProgressBar) {
            ProgressBar view = getView(viewId);
            view.setProgress(progress);
        }else{
            throw new ClassCastException("ViewHolder setProgress 传入的控件ID 非 ProgressBar 的子类");
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max)
    {
        if (getView(viewId) instanceof ProgressBar) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
            view.setProgress(progress);
        }else{
            throw new ClassCastException("ViewHolder setProgress 传入的控件ID 非 ProgressBar 的子类");
        }
        return this;
    }

    public ViewHolder setMax(int viewId, int max)
    {
        if (getView(viewId) instanceof ProgressBar) {
            ProgressBar view = getView(viewId);
            view.setMax(max);
        }else{
            throw new ClassCastException("ViewHolder setMax 传入的控件ID 非 ProgressBar 的子类");
        }
        return this;
    }

    public ViewHolder setRating(int viewId, float rating)
    {
        if (getView(viewId) instanceof RatingBar) {
            RatingBar view = getView(viewId);
            view.setRating(rating);
        }else {
            throw new ClassCastException("ViewHolder setRating 传入的控件ID 非 RatingBar 的子类");
        }
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max)
    {
        if (getView(viewId) instanceof RatingBar) {
            RatingBar view = getView(viewId);
            view.setMax(max);
            view.setRating(rating);
        }else {
            throw new ClassCastException("ViewHolder setRating 传入的控件ID 非 RatingBar 的子类");
        }
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag)
    {
        if (getView(viewId) != null) {
            View view = getView(viewId);
            view.setTag(tag);
        }else {
            throw new ClassCastException("ViewHolder setTag 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag)
    {
        if (getView(viewId) != null) {
            View view = getView(viewId);
            view.setTag(key, tag);
        }else {
            throw new ClassCastException("ViewHolder setTag 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked)
    {
        if (getView(viewId) instanceof Checkable) {
            Checkable view = (Checkable) getView(viewId);
            view.setChecked(checked);
        }else {
            throw new ClassCastException("ViewHolder setChecked 传入的控件ID 非 Checkable 的子类");
        }
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        if (getView(viewId) != null) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
        }else {
	        throw new ClassCastException("ViewHolder setOnClickListener 传入的控件ID 非 View 的子类");
        }
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener)
    {
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    view.setOnTouchListener(listener);
	    }else {
		    throw new ClassCastException("ViewHolder setOnTouchListener 传入的控件ID 非 View 的子类");
	    }
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener)
    {
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    view.setOnLongClickListener(listener);
	    }else {
		    throw new ClassCastException("ViewHolder setOnLongClickListener 传入的控件ID 非 View 的子类");
	    }
        return this;
    }

    /**
     * 绑定itemChild 单击事件,长按，触摸，开关状态改变事件
     */
    private EasyOnItemChildClickListener easyOnItemChildClickListener;
    private EasyOnItemChildLongClickListener easyOnItemChildLongClickListener;
    private EasyOnItemChildTouchListener easyOnItemChildTouchListener;
    private EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener;

    public void zSetEasyOnItemChildClickListener(EasyOnItemChildClickListener easyOnItemChildClickListener){
        this.easyOnItemChildClickListener=easyOnItemChildClickListener;
    }
    public void zSetEasyOnItemChildLongClickListener(EasyOnItemChildLongClickListener easyOnItemChildLongClickListener){
        this.easyOnItemChildLongClickListener=easyOnItemChildLongClickListener;
    }
    public void zSetEasyOnItemChildTouchListener(EasyOnItemChildTouchListener easyOnItemChildTouchListener){
        this.easyOnItemChildTouchListener=easyOnItemChildTouchListener;
    }
    public void zSetEasyOnItemChildCheckChangeListener(EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener){
        this.easyOnItemChildCheckChangeListener=easyOnItemChildCheckChangeListener;
    }

    /**
     * holder中直接绑定相应id的view
     * @param viewId
     * @return
     */
    public ViewHolder setOnItemChildClickListener(int viewId){
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    view.setOnClickListener(new View.OnClickListener() {
			    @Override
			    public void onClick(View v) {
				    if (null != easyOnItemChildClickListener && getLayoutPosition() == getAdapterPosition() && getAdapterPosition() != -1) {
					    easyOnItemChildClickListener.onClick(v, getLayoutPosition());
				    }
			    }
		    });
	    }
        return this;
    }
    public ViewHolder setOnItemChildLongClickListener(int viewId){
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    view.setOnLongClickListener(new View.OnLongClickListener() {
			    @Override
			    public boolean onLongClick(View v) {
				    if (null != easyOnItemChildLongClickListener && getLayoutPosition() == getAdapterPosition() && getAdapterPosition() != -1) {
					    return easyOnItemChildLongClickListener.onLongClick(v, getLayoutPosition());
				    }
				    return false;
			    }
		    });
	    }
        return this;
    }
    public ViewHolder setOnItemChildTouchListener(int viewId){
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    view.setOnTouchListener(new View.OnTouchListener() {
			    @Override
			    public boolean onTouch(View v, MotionEvent event) {
				    if (null != easyOnItemChildTouchListener && getLayoutPosition() == getAdapterPosition() && getAdapterPosition() != -1) {
					    return easyOnItemChildTouchListener.onTouch(v, event, getLayoutPosition());
				    }
				    return false;
			    }
		    });
	    }
        return this;
    }

    public ViewHolder setOnItemChildCheckChangeListener(int viewId){
	    if (getView(viewId) != null) {
		    View view = getView(viewId);
		    if (view instanceof CompoundButton) {
			    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				    @Override
				    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					    if (null != easyOnItemChildCheckChangeListener && getLayoutPosition() == getAdapterPosition() && getAdapterPosition() != -1) {
						    easyOnItemChildCheckChangeListener.onCheckedChanged(buttonView, getLayoutPosition(), isChecked);
					    }
				    }
			    });
		    }
	    }
        return this;
    }

}
