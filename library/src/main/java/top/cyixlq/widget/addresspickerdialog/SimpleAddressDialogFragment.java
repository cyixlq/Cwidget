package top.cyixlq.widget.addresspickerdialog;

import android.support.v4.app.FragmentManager;
import android.view.Gravity;

import top.cyixlq.widget.R;
import top.cyixlq.widget.addresspickerdialog.adapter.SimpleAddressAdapter;
import top.cyixlq.widget.addresspickerdialog.base.BaseAddressDialogFragment;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;

public class SimpleAddressDialogFragment extends BaseAddressDialogFragment<AddressItem> {

    public SimpleAddressDialogFragment() {
        setAdapter(new SimpleAddressAdapter());
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
