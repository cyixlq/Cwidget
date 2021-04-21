package top.cyixlq.addresspickerdialog.adapter

import android.view.View
import top.cyixlq.addresspickerdialog.bean.AddressItem2
import top.cyixlq.widget.R
import top.cyixlq.widget.addresspickerdialog.base.BaseLevelAdapter
import top.cyixlq.widget.common.BaseViewHolder

class AddressAdapter2 : BaseLevelAdapter<AddressItem2>(R.layout.user_item_address_bottom_sheet_dialog) {

    override fun getTabText(item: AddressItem2?): String = item?.name ?: ""

    override fun hasMore(item: AddressItem2?): Boolean = !item?.children.isNullOrEmpty() // 当前的下一级列表不为null也不是empty就代表还有更多

    override fun convertItem(holder: BaseViewHolder?, item: AddressItem2?, isSelected: Boolean) {
        if (holder == null || item == null) return
        holder.setText(R.id.user_tv_address_dialog, item.name)
        if (isSelected) {
            holder.setTextColor(R.id.user_tv_address_dialog, -0xe07c01)
                .setVisibility(R.id.user_iv_address_dialog, View.VISIBLE)
        } else {
            holder.setTextColor(R.id.user_tv_address_dialog, -0x1000000)
                .setVisibility(R.id.user_iv_address_dialog, View.GONE)
        }
    }
}