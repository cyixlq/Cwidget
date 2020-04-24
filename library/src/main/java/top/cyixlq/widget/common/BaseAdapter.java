package top.cyixlq.widget.common;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    @LayoutRes
    private int layoutId;
    private List<T> data;

    public BaseAdapter(@LayoutRes final int layoutId) {
        this.layoutId = layoutId;
        this.data = new ArrayList<>();
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

    public void addItem(T item) {
        if (item != null) {
            this.data.add(item);
            this.notifyItemInserted(this.data.size() - 1);
        }
    }

    public abstract void convert(BaseViewHolder holder, T item);
}
