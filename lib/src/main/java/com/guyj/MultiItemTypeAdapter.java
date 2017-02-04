package com.guyj;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;


import com.guyj.animation.AlphaInAnimation;
import com.guyj.animation.BaseAnimation;
import com.guyj.animation.ScaleInAnimation;
import com.guyj.animation.SlideInBottomAnimation;
import com.guyj.animation.SlideInLeftAnimation;
import com.guyj.animation.SlideInRightAnimation;
import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ItemViewDelegateManager;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnItemChildCheckChangeListener;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnItemChildLongClickListener;
import com.guyj.listener.EasyOnItemChildTouchListener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * Created by guyj
 * On the basis of the zhy&cym
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected EasyOnItemChildClickListener easyOnItemChildClickListener;
    protected EasyOnItemChildLongClickListener easyOnItemChildLongClickListener;
    protected EasyOnItemChildTouchListener easyOnItemChildTouchListener;
    protected EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener;

    protected Handler mHandler=new Handler();
//    protected OnItemClickListener mOnItemClickListener;

    //Animation
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int ALPHAIN = 0x00000001;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SCALEIN = 0x00000002;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_BOTTOM = 0x00000003;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_LEFT = 0x00000004;
    /**
     * Use with {@link #openLoadAnimation}
     */
    public static final int SLIDEIN_RIGHT = 0x00000005;

    @IntDef({ALPHAIN, SCALEIN, SLIDEIN_BOTTOM, SLIDEIN_LEFT, SLIDEIN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    private boolean mFirstOnlyEnable = true;
    private boolean mOpenAnimationEnable = false;
    private Interpolator mInterpolator = new LinearInterpolator();
    private Interpolator mInterpolatorDecelerated = new DecelerateInterpolator();

    //设置动画时间
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    private int mDuration = 300;
    private int mLastPosition = -1;

    private BaseAnimation mCustomAnimation;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
//        setListener(parent, holder, viewType);
        holder.setEasyOnItemChildClickListener(easyOnItemChildClickListener);
        holder.setEasyOnItemChildLongClickListener(easyOnItemChildLongClickListener);
        holder.setEasyOnItemChildTouchListener(easyOnItemChildTouchListener);
        holder.setEasyOnItemChildCheckChangeListener(easyOnItemChildCheckChangeListener);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


//    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
//        if (!isEnabled(viewType)) return;
//        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = viewHolder.getAdapterPosition();
//                    mOnItemClickListener.onItemClick(v, viewHolder , position);
//                }
//            }
//        });
//
//        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = viewHolder.getAdapterPosition();
//                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
//                }
//                return false;
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (null==mDatas){
            throw new RuntimeException("列表数据为null，集合未初始化");
        }else{
            int itemCount = mDatas.size();
            return itemCount;
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> list){
        mDatas=list;
        notifyDataSetChanged();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, RecyclerView.ViewHolder holder,  int position);
//
//        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,  int position);
//    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }

    public void setEasyOnItemChildClickListener(EasyOnItemChildClickListener easyOnItemChildClickListener){
        this.easyOnItemChildClickListener=easyOnItemChildClickListener;
    }
    public void setEasyOnItemChildLongClickListener(EasyOnItemChildLongClickListener easyOnItemChildLongClickListener){
        this.easyOnItemChildLongClickListener=easyOnItemChildLongClickListener;
    }
    public void setEasyOnItemChildTouchListener(EasyOnItemChildTouchListener easyOnItemChildTouchListener){
        this.easyOnItemChildTouchListener=easyOnItemChildTouchListener;
    }
    public void setEasyOnItemChildCheckChangeListener(EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener){
        this.easyOnItemChildCheckChangeListener=easyOnItemChildCheckChangeListener;
    }

    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        addAnimation(holder);
    }

    /**
     * add animation when you want to show time
     *
     * @param holder
     */
    private void addAnimation(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mCustomAnimation != null) {
                    animation = mCustomAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim, holder.getLayoutPosition());
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolatorDecelerated);
    }

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM}, {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public void openLoadAnimation(@AnimationType int animationType) {
        this.mOpenAnimationEnable = true;
        mCustomAnimation = null;
        switch (animationType) {
            case ALPHAIN:
                mSelectAnimation = new AlphaInAnimation();
                break;
            case SCALEIN:
                mSelectAnimation = new ScaleInAnimation();
                break;
            case SLIDEIN_BOTTOM:
                mSelectAnimation = new SlideInBottomAnimation();
                break;
            case SLIDEIN_LEFT:
                mSelectAnimation = new SlideInLeftAnimation();
                break;
            case SLIDEIN_RIGHT:
                mSelectAnimation = new SlideInRightAnimation();
                break;
            default:
                break;
        }
    }

    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public void openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
    }

    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    /**
     * {@link #addAnimation(RecyclerView.ViewHolder)}
     *
     * @param firstOnly true just show anim when first loading false show anim when load the data every time
     */
    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

}
