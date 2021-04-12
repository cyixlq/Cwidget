package top.cyixlq.widget.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    @LayoutRes
    private final int layoutId;
    private final List<T> data;

    public BaseAdapter(@LayoutRes final int layoutId) {
        this(layoutId, new ArrayList<>());
    }

    public BaseAdapter(@LayoutRes final int layoutId, final List<T> data) {
        this.layoutId = layoutId;
        this.data = data;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        return new BaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        final T item = data.get(i);
        convert(baseViewHolder, item);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<T> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            this.notifyDataSetChanged();
        }
    }

    public void addData(List<T> data) {
        if (data != null) {
            this.data.addAll(data);
            this.notifyDataSetChanged();
        }
    }

    public T getItem(@IntRange(from = 0) int position) {
        return this.data.get(position);
    }

    public void addItem(T item) {
        if (item != null) {
            this.data.add(item);
            this.notifyItemInserted(this.data.size() - 1);
        }
    }

    public void removeItem(T item) {
        final int index = this.data.indexOf(item);
        if (index != -1) {
            removeAt(index);
        }
    }

    public void removeAt(@IntRange(from = 0) int position) {
        if (position >= this.data.size()) return;
        this.data.remove(position);
        notifyItemRemoved(position);
    }

    public abstract void convert(BaseViewHolder holder, T item);
}
