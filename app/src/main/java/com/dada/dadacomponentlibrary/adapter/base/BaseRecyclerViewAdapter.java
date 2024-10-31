package com.dada.dadacomponentlibrary.adapter.base;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * function:RecyclerView的通用基类适配器
 */
public abstract class BaseRecyclerViewAdapter<VB extends ViewBinding, T, VHH extends BaseViewHolder<VB>> extends RecyclerView.Adapter<VHH> {
    protected final String TAG       = this.getClass().getSimpleName();
    protected final Context mContext;
    protected final List<T> mDataList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        this.mContext = context;
        mDataList.clear();
    }

    /**
     * 创建ViewHolder
     * <br/>
     * see:{@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    protected abstract VHH iCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 绑定ViewHolder视图数据
     * <br/>
     * see:{@link RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)}
     */
    protected abstract void iBindViewHolder(VHH holder, int position);

    /**
     * 条目点击监听
     *
     * @param view     被点击条目视图
     * @param position 点击条目位置
     * @param item     条目
     */
    protected void onItemClickListener(VHH holder,View view, int position, @NonNull T item) {

    }

    /**
     * 条目长按监听
     *
     * @param view     被长按条目视图
     * @param position 长按条目位置
     * @param item     条目
     */
    protected boolean onItemLongClickListener(View view, int position, @NonNull T item) {
          return false;
    }

    /**
     * 加载初始刷新数据
     */
    public void refreshDataList(List<T> dataList) {
        if(dataList == null) return;
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    /**
     * 加载更多
     */
    public void loadMoreDataList(List<T> dataList) {
        if(dataList == null || dataList.size() == 0) return;
        int sizeBefore = mDataList.size();
        mDataList.addAll(dataList);
        notifyItemRangeInserted(sizeBefore, dataList.size());
    }

    /**
     * 获取当前列表中的数据对象列表
     */
    public List<T> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 获取指定位置的条目对象
     */
    public T getItem(int position) {
        return (position < mDataList.size() && position >= 0) ? mDataList.get(position) : null;
    }

    /**
     * 移除指定位置的条目对象
     */
    public T removeItem(int position) {
        return removeItem(getItem(position));
    }

    /**
     * 移除条目对象
     */
    public T removeItem(T t) {
        if(t != null) {
            int index = mDataList.indexOf(t);
            t = mDataList.remove(index);
            notifyItemRemoved(index);
        }
        return t;
    }

    @NonNull
    @Override
    public VHH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return this.iCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VHH viewHolder, int position) {
        if(getItem(position) == null) return;
        viewHolder.itemView.setTag(viewHolder.itemView.getId(), position);
        viewHolder.itemView.setOnClickListener(view -> {
            int position1 = (int) view.getTag(view.getId());
            Log.d(TAG, "mInnerItemOnclickListener:"+ position1);
            T item = getItem(position1);
            if(item != null) onItemClickListener(viewHolder,view, position1, getItem(position1));
            if (onItemClickListener != null) onItemClickListener.onItemClick(viewHolder,view, position1, getItem(position1));
        });
        viewHolder.itemView.setOnLongClickListener(mInnerItemOnLongClickListener);
        this.iBindViewHolder(viewHolder, position);
    }

    private final View.OnLongClickListener mInnerItemOnLongClickListener = view -> {
        int position = (int) view.getTag(view.getId( ));
        Log.d(TAG, "mInnerItemOnLongClickListener:" + position);
        T item = getItem(position);
        return item != null && onItemLongClickListener(view, position, item);
    };

    public void setItemClickListener (OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(RecyclerView.ViewHolder holder, View view, int position, @NonNull Object item);
    }

}
