package top.cyixlq.addresspickerdialoglibrary.base;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends View> T getView(@IdRes final int id) {
        return itemView.findViewById(id);
    }

    public BaseViewHolder setText(@IdRes final int id, final String text) {
        this.<TextView>getView(id).setText(text);
        return this;
    }

    public BaseViewHolder setTextColor(@IdRes final int id, @ColorInt final int color) {
        this.<TextView>getView(id).setTextColor(color);
        return this;
    }

    public BaseViewHolder setImageResource(@IdRes final int id, @DrawableRes final int res) {
        this.<ImageView>getView(id).setImageResource(res);
        return this;
    }

    public BaseViewHolder setVisibility(@IdRes final int id, final int visibility) {
        this.<View>getView(id).setVisibility(visibility);
        return this;
    }
}
