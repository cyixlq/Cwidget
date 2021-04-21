package top.cyixlq.widget.addresspickerdialog.base;

import androidx.annotation.LayoutRes;

import top.cyixlq.widget.common.BaseAdapter;
import top.cyixlq.widget.common.BaseViewHolder;

public abstract class BaseLevelAdapter<T> extends BaseAdapter<T> {

    private OnItemClickListener<T> listener;
    private int selectIndex = -1;

    public BaseLevelAdapter(@LayoutRes final int layoutId) {
        super(layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, T item) {
        final int position = holder.getAdapterPosition();
        convertItem(holder, item, position == selectIndex);
        if (this.listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onItemClick(position, item));
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public void setItemSelect(int position, boolean isSelected) {
        if (isSelected) {
            final int lastIndex = selectIndex;
            selectIndex = position;
            if (lastIndex != -1)
                notifyItemChanged(lastIndex);
        }
        if (position != -1)
            notifyItemChanged(position);
    }

    public abstract void convertItem(BaseViewHolder holder, T item, boolean isSelected);
    public abstract String getTabText(T item);
    public abstract boolean hasMore(T item);

    public interface OnItemClickListener<T> {
        void onItemClick(final int position, final T item);
    }
}
