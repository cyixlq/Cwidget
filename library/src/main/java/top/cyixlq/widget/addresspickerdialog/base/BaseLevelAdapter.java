package top.cyixlq.widget.addresspickerdialog.base;

import androidx.annotation.LayoutRes;

import top.cyixlq.widget.common.BaseAdapter;
import top.cyixlq.widget.common.BaseViewHolder;

public abstract class BaseLevelAdapter<T> extends BaseAdapter<T> {

    private OnItemClickListener<T> listener;

    public BaseLevelAdapter(@LayoutRes final int layoutId) {
        super(layoutId);
    }

    @Override
    public void convert(BaseViewHolder holder, T item) {
        if (this.listener != null) {
            holder.itemView.setOnClickListener(v -> {
                final int position = holder.getAdapterPosition();
                listener.onItemClick(position, getItem(position));
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(final int position, final T parent);
    }
}
