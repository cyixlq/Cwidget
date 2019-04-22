package top.cyixlq.addresspickerdialoglibrary.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public abstract class CustomBaseBottomSheetDialog extends CustomBaseDialog {
    public CustomBaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected Integer getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (null != window) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.horizontalMargin = 0;
            window.setAttributes(layoutParams);
        }

    }
}
