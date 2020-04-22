package top.cyixlq.addresspickerdialoglibrary.adapter;

import android.view.View;

import top.cyixlq.addresspickerdialoglibrary.R;
import top.cyixlq.addresspickerdialoglibrary.base.BaseAddressAdapter;
import top.cyixlq.addresspickerdialoglibrary.base.BaseViewHolder;
import top.cyixlq.addresspickerdialoglibrary.bean.AddressItem;

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
