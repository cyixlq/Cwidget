package top.cyixlq.widget.addresspickerdialog.adapter;

import android.view.View;

import top.cyixlq.widget.R;
import top.cyixlq.widget.addresspickerdialog.base.BaseLevelAdapter;
import top.cyixlq.widget.common.BaseViewHolder;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;

public class SimpleAddressAdapter extends BaseLevelAdapter<AddressItem> {

    public SimpleAddressAdapter() {
        super(R.layout.user_item_address_bottom_sheet_dialog);
    }

    @Override
    public void convertItem(BaseViewHolder holder, AddressItem item, boolean isSelected) {
        holder.setText(R.id.user_tv_address_dialog, item.getAddress());
        if(isSelected) {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff1F83FF)
                    .setVisibility(R.id.user_iv_address_dialog, View.VISIBLE);
        } else {
            holder.setTextColor(R.id.user_tv_address_dialog, 0xff000000)
                    .setVisibility(R.id.user_iv_address_dialog, View.GONE);
        }
    }

    @Override
    public String getTabText(AddressItem item) {
        return item.getAddress();
    }

    @Override
    public boolean hasMore(AddressItem item) {
        return true;
    }
}
