package top.cyixlq.addresspickerdialog.view

import top.cyixlq.addresspickerdialog.adapter.AddressAdapter2
import top.cyixlq.addresspickerdialog.bean.AddressItem2
import top.cyixlq.widget.addresspickerdialog.base.BaseLevelDialogFragment

class Address2Dialog : BaseLevelDialogFragment<AddressItem2>() {

    init {
        setAdapter(AddressAdapter2())
    }

}