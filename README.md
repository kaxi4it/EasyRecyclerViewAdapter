# EasyRecyclerViewAdapter
参考整合zhy&amp;cym的适配器部分内容，使用方式延续zhy的base-adapter方式，部分调整，参见demo</br>
#### 前言
---
网上的RecyclerView通用适配器都已完善的很好了，而我只是在原有的基础上，添砖加瓦罢了</br>
我所发布的EasyRecyclerViewAdapter是基于zhy大神的base-adapter所修改而成</br>
并已完全剥离了对Listview的适配器支持，只支持RecyclerView</br>
最主要的修改部分为zhy大神的adapter默认绑定了item的单击和长按时间，并且设置点击事件时，必须直接绑定相应listener，而我将item的默认绑定事件取消了，如果需要设置item相关事件时，只需要直接绑定viewId即可</br>
最近参考了下cym大神的adapter，把item的加载动画部分也收罗了进来</br>
最后因为比较钟情于用户无感知的自动加载更多的方式，所以又添加了一个新的AutoLoadMoreAdapter</br>

#### 一 效果图
---
暂时并没有效果图

#### 二 使用方法
---
![最新版本号](https://jitpack.io/v/kaxi4it/EasyRecyclerViewAdapter.svg)
在你root的gradle中添加引用
```java
allprojects {
    repositories {
    ...
    maven { url "https://jitpack.io" }
    }
 }
```
然后在module的gradle中添加引用
```java
dependencies {
    compile 'com.github.kaxi4it:EasyRecyclerViewAdapter:1.9'
}
```
##### CommonAdapter的使用
由于是基于zhy的base-adapter所修改，所以使用方式和zhy的demo是一致的，唯一的区别是点击事件的绑定方法，需要如下操作：
```java
mAdapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas){
    @Override
    protected void convert(ViewHolder holder, String s, int position)    {
        holder.setText(R.id.id_item_list_title, "text");
        //绑定单击，长按，触摸，开关事件
        holder.setOnItemChildClickListener(R.id.id_item_list_title);
        holder.setOnItemChildLongClickListener(R.id.id_item_list_title);
        holder.setOnItemChildTouchListener(R.id.id_item_list_title);
        holder.setOnItemChildCheckChangeListener(R.id.id_item_list_title);
    }
};
```
将相应的事件绑定在对应viewId后，可在您所需要的地方通过adapter的相关方法来使用：
```java
//单击事件
mAdapter.setEasyOnItemChildClickListener(new EasyOnItemChildClickListener(){
    @Override
    public void onClick(View view, int position) {
        switch(view.getId()){
            case R.id.id_item_list_title:
                // do sth
            break;
        }
    }
});
```
```java
//长按事件
mAdapter.setEasyOnItemChildLongClickListener(new EasyOnItemChildLongClickListener() {
    @Override
    public boolean onLongClick(View view, int position) {
        return false;
    }
});
```
```java
//触摸事件
mAdapter.setEasyOnItemChildTouchListener(new EasyOnItemChildTouchListener() {
    @Override
    public boolean onTouch(View v, MotionEvent event, int position) {
        return false;
    }
});
```
```java
//开关事件
mAdapter.setEasyOnItemChildCheckChangeListener(new EasyOnItemChildCheckChangeListener() {
    @Override
    public void onCheckedChanged(CompoundButton childView, int position, boolean isChecked) {
        // do sth
    }
});
```
关于动画部分借鉴的cym的代码部分和使用方式，其中分为`ALPHAIN`, `SCALEIN`, `SLIDEIN_BOTTOM`, `SLIDEIN_LEFT`, `SLIDEIN_RIGHT` 五种动画方式，淡入动画，缩放动画，底部滑入，左侧滑入，右侧滑入，可自行选择，`isFirstOnly()`是设置动画是否只在第一次显示item时有效，默认为true
```java
//动画效果
mAdapter.openLoadAnimation(MultiItemTypeAdapter.SCALEIN);
//是否只在第一次显示时有效
mAdapter.isFirstOnly(false);
```
#####  AutoLoadMoreAdapter的使用
AutoLoadMoreAdapter的使用方式，延续了CommonAdapter的风格，默认添加了一个loadMore的回调方法来实现自动加载更多的逻辑部分：
```java
mAdapter = new AutoLoadMoreAdapter<String>(this, R.layout.item_list, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, String s, int position)
            {
                holder.setText(R.id.id_item_list_title, s);
                holder.setOnItemChildClickListener(R.id.id_item_list_title);
            }

            @Override
            protected void loadMore() {
                //调用加载更多的网络请求
                Toast.makeText(RecyclerViewActivity.this,"加载更多",Toast.LENGTH_SHORT).show();
            }
        };
```
当遇到网络请求加载更多出现异常等失败的情况下，默认情况下是不会再调用自动加载更多的方法，那么此时需要用户手动重置恢复自动加载更多的回调：
```java
mAdapter.resetLoadMoreState();
```
开放了用户自定义加载更多的提前量，默认设置为4，大于0会提前加载、等于0仅在最后一条加载更多，小于0则关闭了自动加载更多：
```java
mAdapter.setAdvanceCount();
```
#### 三 最终章
---
按照如上方式正确操作后，就可以使用EasyRecyclerViewAdapter了
