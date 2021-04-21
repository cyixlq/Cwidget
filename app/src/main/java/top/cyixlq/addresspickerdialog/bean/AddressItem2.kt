package top.cyixlq.addresspickerdialog.bean

data class AddressItem2(
    val id: Int,
    val name: String,
    val children: MutableList<AddressItem2>
)
