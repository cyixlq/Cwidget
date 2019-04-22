package top.cyixlq.addresspickerdialoglibrary.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

public abstract class CustomBaseDialog extends Dialog {

    protected Context context;

    public CustomBaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    protected abstract Integer getLayout();
    protected abstract Integer getGravity();
    protected abstract Integer getBackgroundRes();
    protected abstract Integer getWindowAnimations();
    protected abstract void initView();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != null)
            setContentView(getLayout());
        Window window = getWindow();
        if (window != null) {
            View decorView = window.getDecorView();
            decorView.setPadding(0,0,0,0);
            if (getGravity() != null)
                window.setGravity(getGravity());
            else
                window.setGravity(Gravity.CENTER);

            if (getWindowAnimations() != null)
                window.setWindowAnimations(getWindowAnimations());
            if (getBackgroundRes() != null)
                decorView.setBackgroundResource(getBackgroundRes());
        }
        initView();
    }

    protected void setClickListener(int id, View.OnClickListener listener) {
        findViewById(id).setOnClickListener(listener);
    }
}
