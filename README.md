# Cwidget  一个自用的UI控件库，目前控件数量不多，同时为了方便大家便开源了
#### 目录
- [AddressPickerDialog 地址选择器弹框](#addresspickerdialog-地址选择器弹框)
- [DatePickerDialogFragment 日期选择器弹框](#datepickerdialogfragment-日期选择器弹框)

--------

#### AddressPickerDialog 地址选择器弹框
##### 效果图：
![效果图.gif](https://upload-images.jianshu.io/upload_images/8654767-9055a2ffa9206627.gif?imageMogr2/auto-orient/strip)
##### 特点
1. 自身没有创建数据库保存全中国地区信息，全部靠回调获取
2. 支持设置最大地区级别，例如：省，市，县，镇就是4级
3. 泛型实体类，可以快速自定义Adapter，不再需要封装成特定实体类(AddressItem)
4. 用Object作为地区ID以适配不同业务数据，请根据自己业务进行强转
5. 用户最后选完所有地区回调onGotResult方法，第一个参数为地址字符串，第二个参数为选择的最后一个级别地区的实体
##### 使用方法
[参照MainActivity](https://github.com/cyixlq/AddressPickerDialog/blob/master/app/src/main/java/top/cyixlq/addresspickerdialog/MainActivity.java)

[快速自定义Adapter，参照SimpleAddressAdapter](https://github.com/cyixlq/AddressPickerDialog/blob/master/addresspickerdialoglibrary/src/main/java/top/cyixlq/addresspickerdialoglibrary/adapter/SimpleAddressAdapter.java)

[快速自定义DialogFragment，参照SimpleAddressDialogFragment](https://github.com/cyixlq/AddressPickerDialog/blob/master/addresspickerdialoglibrary/src/main/java/top/cyixlq/addresspickerdialoglibrary/SimpleAddressDialogFragment.java)

##### 更新
- 2020年04月22日（前两天我的狗子——汤圆 失踪了，怀疑被偷，心情难受，wcnm偷狗贼，希望汤圆能安全回家）
  1. 改用DialogFragment作为Dialog
  2. 原来的Dialog中数据混杂难懂，进一步封装成PageData
  3. 回调接口更名，更语义化
  4. 可以自定义Adapter来适应自己的地址实体类，不需要重新封装成AddressItem
  5. 更多变更参照上面的使用方法

#### DatePickerDialogFragment 日期选择器弹框
##### 效果图
![效果图](https://user-gold-cdn.xitu.io/2020/4/24/171a9f51d5e5112b?w=480&h=945&f=gif&s=1133795)
##### 特点
1. 可以单独选择一天也可以选择一段时间
2. 其中有个CalendarView，基于RecyclerView实现，通过集成BaseCalendarAdapter可以实现自定义Item布局
3. 通过设置SelectRule可以自定义一些选择规则。例如：是否开启日期选择功能，最大可以选择多少天，是否可以选择超过今天的日子
##### 使用方法
[参照MainActivity](https://github.com/cyixlq/AddressPickerDialog/blob/master/app/src/main/java/top/cyixlq/addresspickerdialog/MainActivity.java)

[快速自定义Adapter，参照SimpleCalendarAdapter](https://github.com/cyixlq/Cwidget/blob/master/library/src/main/java/top/cyixlq/widget/calendar/SimpleCalendarAdapter.java)

[封装成弹框样式，参照DatePickerDialogFragment](https://github.com/cyixlq/Cwidget/blob/master/library/src/main/java/top/cyixlq/widget/calendar/DatePickerDialogFragment.java)