package top.cyixlq.widget.addresspickerdialog;

import androidx.fragment.app.FragmentManager;

import top.cyixlq.widget.addresspickerdialog.adapter.SimpleAddressAdapter;
import top.cyixlq.widget.addresspickerdialog.base.BaseLevelDialogFragment;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;

public class SimpleAddressDialogFragment extends BaseLevelDialogFragment<AddressItem> {

    public SimpleAddressDialogFragment() {
        setAdapter(new SimpleAddressAdapter());
    }

    public void show(FragmentManager manager) {
        super.show(manager, "address");
    }
}