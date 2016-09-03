package com.white.bihudaily.base;

import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.white.bihudaily.R;
import com.white.bihudaily.bean.AdapterBean;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author White
 * Date 2016/8/16
 * Time 11:50
 */
public abstract class BaseRVAdapter<T extends AdapterBean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    // 数据源
    protected List<T> mData;

    protected View mHeaderView;

    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_TITLE = 1;
    protected static final int TYPE_FOOTER = 2;
    protected static final int TYPE_HEADER = 3;
    protected static final int TYPE_NO_IMG_ITEM = 4;
    protected static final int TYPE_EMPTY_VIEW = 5;

    private int mFooterState;
    public static final int STATE_LOADING = -1;
    public static final int STATE_NO_MORE = -2;
    public static final int STATE_NO_FOOTER = -3;

    public BaseRVAdapter(List<T> data) {
        this.mData = data;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
//        mData.clear();
        T headerBean = createBean(AdapterBean.TYPE_HEADER);
        mData.add(0, headerBean);
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    /**
     * 通过反射创建一个header 或 footer bean
     *
     * @param type header footer
     * @return
     */
    private T createBean(int type) {
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass()
                    .getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            T bean = clazz.newInstance();
            bean.setShowType(type);
            return bean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
            return getItemViewHolder(itemView);
        } else if (viewType == TYPE_TITLE) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(getTitleLayout(), parent, false);
            return getTitleViewHolder(itemView);
        } else if (viewType == TYPE_FOOTER) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_item, parent, false);
            return new FooterViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            return new HeaderViewHolder(mHeaderView);
        } else {
            return createOtherTypeViewHolder(parent, viewType);
        }

    }

    protected abstract RecyclerView.ViewHolder createOtherTypeViewHolder(ViewGroup parent, int viewType);

    protected abstract RecyclerView.ViewHolder getTitleViewHolder(View itemView);

    protected abstract int getTitleLayout();

    protected abstract RecyclerView.ViewHolder getItemViewHolder(View itemView);

    protected abstract int getItemLayout();


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 添加底部，显示加载更多
     */
    public void addFooter() {
        T footerBean = createBean(AdapterBean.TYPE_FOOTER);
        mData.add(footerBean);
        setFooterState(STATE_LOADING);
        notifyDataSetChanged();
    }

    /**
     * 移除底部显示
     */
    public void removerFooter() {
        int lastPosition = mData.size() - 1;
        int itemViewType = getItemViewType(lastPosition);
        if (itemViewType == TYPE_FOOTER) {
            mData.remove(lastPosition);
            setFooterState(STATE_NO_FOOTER);
            notifyDataSetChanged();
        }
    }

    /**
     * 当前是否显示底部
     *
     * @return true：正在加载
     */
//    public boolean isShowFooter() {
//        int lastPosition = mData.size() - 1;
//        return getItemViewType(lastPosition) == TYPE_FOOTER;
//    }
    public int getFooterState() {
        return mFooterState;
    }

    public void setFooterState(int footerState) {
        this.mFooterState = footerState;
    }

    /**
     * 添加数据
     *
     * @param data
     */
    public void addDataList(List<T> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 替换数据
     *
     * @param data
     */
    public void replaceData(List<T> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearData() {
        mData.clear();
        mHeaderView = null;
        notifyDataSetChanged();
    }

    protected abstract int getItemType(int position);

    @Override
    public int getItemViewType(int position) {
        return getItemType(position);
    }


    public class FooterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_loading)
        TextView tvLoadingItem;
        @BindView(R.id.progressBar)
        ContentLoadingProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
