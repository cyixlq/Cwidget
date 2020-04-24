package top.cyixlq.widget.addresspickerdialog.adapter;

import android.view.View;

import top.cyixlq.widget.R;
import top.cyixlq.widget.addresspickerdialog.base.BaseAddressAdapter;
import top.cyixlq.widget.common.BaseViewHolder;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;

public class SimpleAddressAdapter extends BaseAddressAdapter<AddressItem> {

    public SimpleAddressAdapter() {
        super(R.layout.user_item_address_bottom_sheet_dialog);
    }

    @Override
    public void convert(BaseViewHolder holder, AddressItem item) {
        holder.setText(R.id.user_tv_address_dialog, item.getAddress());
        if(item.isChecked()) {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff1F83FF)
                    .setVisibility(R.id.user_iv_address_dialog, View.VISIBLE);
        } else {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff000000)
                    .setVisibility(R.id.user_iv_address_dialog, View.GONE);
        }
    }

    @Override
    public Object getAddressId(AddressItem item) {
        return item.getId();
    }

    @Override
    public String getAddressName(AddressItem item) {
        return item.getAddress();
    }

    @Override
    public void setChecked(AddressItem item, boolean isChecked) {
        item.setChecked(isChecked);
    }
}
