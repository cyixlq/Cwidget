# AddressPickerDialog
一个类似于拼多多的地址选择器
效果图：
![效果图.gif](https://upload-images.jianshu.io/upload_images/8654767-9055a2ffa9206627.gif?imageMogr2/auto-orient/strip)

1. 自身没有创建数据库保存全中国地区信息，全部靠回调获取
2. 支持设置最大地区级别，例如：省，市，县，镇就是4级
3. AddressItem实体类默认用int类型作为地区ID，请根据自己业务进行修改
4. 没有实现用户最后选完所有地区一个事件处理，大家开动自己的脑瓜实现吧
