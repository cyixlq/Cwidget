package top.cyixlq.addresspickerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.cyixlq.addresspickerdialoglibrary.AddressBottomSheetDialog;
import top.cyixlq.addresspickerdialoglibrary.bean.AddressItem;

public class MainActivity extends AppCompatActivity {

    private AddressBottomSheetDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDialog = new AddressBottomSheetDialog(this);
        mDialog.setMaxLevel(4);  // 设置地址最大级别
        mDialog.setDialogTitle("配送至"); // 设置Dialog标题
        mDialog.setTabDefaultText("请选择地区"); // 设置Tab默认显示的提示文字
        mDialog.setTabSelectChangeListener(new AddressBottomSheetDialog.EventListener() {
            @Override
            public void onTabSelectChange(int level, Object parentId) {
                int myId;
                // parentId注意判空，如果为空代表没有父级地区
                if (parentId == null) myId = 0;
                else myId = (int) parentId;
                mDialog.setCurrentAddressList(getAddressList(level, myId), level);
            }

            @Override
            public void onResult(String address, AddressItem lastAddressItem) {
                // address代表地址字符串，如某某省某某市某某县某某镇
                Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                // lastAddressItem代表选择的最后一个级别的地区实体
                Log.d("My_Tag", lastAddressItem.getAddress());
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
            }
        });
    }

    /**
     * 模拟获取地址列表的方法
     *
     * @param level    当前要选择的地区级别
     * @param parentId 父级地址ID
     * @return List<AddressItem> 返回地区数据
     */
    private List<AddressItem> getAddressList(int level, int parentId) {
        Log.d("My_Tag", "parentId ===>>> " + parentId);
        String text;
        switch (level) {
            case 0:
                text = "省级";
                break;
            case 1:
                text = "市级";
                break;
            case 2:
                text = "县级";
                break;
            case 3:
                text = "镇级";
                break;
            default:
                text = "未知";
                break;
        }
        List<AddressItem> list = new ArrayList<>();
        for (int i = 1; i < 31; i++) {
            AddressItem item = new AddressItem();
            item.setAddress(text + i);
            item.setId(i);
            item.setChecked(false);
            list.add(item);
        }
        return list;
    }
}
