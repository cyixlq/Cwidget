package top.cyixlq.widget.addresspickerdialog;

import android.view.Gravity;

import androidx.fragment.app.FragmentManager;

import top.cyixlq.widget.R;
import top.cyixlq.widget.addresspickerdialog.adapter.SimpleLevelAdapter;
import top.cyixlq.widget.addresspickerdialog.base.BaseAddressDialogFragment;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;

public class SimpleLevelDialogFragment extends BaseAddressDialogFragment<AddressItem> {

    public SimpleLevelDialogFragment() {
        setAdapter(new SimpleLevelAdapter());
    }

    @Override
    public Integer getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public Integer getWindowAnimation() {
        return R.style.DialogBottom;
    }

    @Override
    public Integer getBackgroundResource() {
        return R.drawable.bg_dialog_bottom;
    }

    @Override
    public void initView() {}

    public void show(FragmentManager manager) {
        super.show(manager, "address");
    }
}
