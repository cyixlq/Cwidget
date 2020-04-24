package top.cyixlq.widget.addresspickerdialog.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.cyixlq.widget.common.BaseViewHolder;

public abstract class BaseAddressAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    @LayoutRes
    private int layoutId;
    private ArrayList<T> items;
    private OnItemClickListener<T> listener;

    public BaseAddressAdapter(@LayoutRes final int layoutId) {
        this.layoutId = layoutId;
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BaseViewHolder vh, int i) {
        final T item = items.get(i);
        convert(vh, item);
        if (this.listener != null) {
            vh.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(vh.getAdapterPosition(),
                            getAddressId(item), getAddressName(item));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setData(List<T> list) {
        if (list != null) {
            this.items.clear();
            this.items.addAll(list);
            this.notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.listener = listener;
    }

    public abstract void convert(BaseViewHolder holder, T item);
    public abstract Object getAddressId(T item);
    public abstract String getAddressName(T item);
    public abstract void setChecked(T item, boolean isChecked);

    public interface OnItemClickListener<T> {
        void onItemClick(final int position, final Object addressId, final String addressName);
    }
}
