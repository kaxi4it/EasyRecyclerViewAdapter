package com.guyj;

import android.animation.Animator;
import android.content.Context;
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
import java.util.ArrayList;
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
    public MultiItemTypeAdapter setDuration(int duration) {
        this.mDuration = duration;
        return this;
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
        holder.zSetEasyOnItemChildClickListener(easyOnItemChildClickListener);
        holder.zSetEasyOnItemChildLongClickListener(easyOnItemChildLongClickListener);
        holder.zSetEasyOnItemChildTouchListener(easyOnItemChildTouchListener);
        holder.zSetEasyOnItemChildCheckChangeListener(easyOnItemChildCheckChangeListener);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

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
        if (null==mDatas){
            mDatas=new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void setDatasWithoutNotify(List<T> list){
        if (null==mDatas){
            mDatas=new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(list);
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
    private MultiItemTypeAdapter addAnimation(RecyclerView.ViewHolder holder) {
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
        return this;
    }

    /**
     * set anim to start when loading
     *
     * @param anim
     * @param index
     */
    protected MultiItemTypeAdapter startAnim(Animator anim, int index) {
        anim.setDuration(mDuration).start();
        anim.setInterpolator(mInterpolatorDecelerated);
        return this;
    }

    /**
     * Set the view animation type.
     *
     * @param animationType One of {@link #ALPHAIN}, {@link #SCALEIN}, {@link #SLIDEIN_BOTTOM}, {@link #SLIDEIN_LEFT}, {@link #SLIDEIN_RIGHT}.
     */
    public MultiItemTypeAdapter openLoadAnimation(@AnimationType int animationType) {
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
        return this;
    }

    /**
     * Set Custom ObjectAnimator
     *
     * @param animation ObjectAnimator
     */
    public MultiItemTypeAdapter openLoadAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mCustomAnimation = animation;
        return this;
    }

    /**
     * To open the animation when loading
     */
    public MultiItemTypeAdapter openLoadAnimation() {
        this.mOpenAnimationEnable = true;
        return this;
    }

    /**
     * {@link #addAnimation(RecyclerView.ViewHolder)}
     *
     * @param firstOnly true just show anim when first loading false show anim when load the data every time
     */
    public MultiItemTypeAdapter isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
        return this;
    }

}
