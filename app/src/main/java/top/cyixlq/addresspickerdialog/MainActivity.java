package top.cyixlq.addresspickerdialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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
        mDialog.setTabSelectChangeListener(new AddressBottomSheetDialog.TabSelectChangeListener() {
            @Override
            public void onSelectChange(int level, int parentId) {
                mDialog.setCurrentAddressList(getAddressList(level, parentId), level);
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
     * @return
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
