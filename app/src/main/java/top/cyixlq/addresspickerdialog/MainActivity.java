package top.cyixlq.addresspickerdialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import top.cyixlq.addresspickerdialoglibrary.SimpleAddressDialogFragment;
import top.cyixlq.addresspickerdialoglibrary.base.BaseAddressDialogFragment;
import top.cyixlq.addresspickerdialoglibrary.bean.AddressItem;

public class MainActivity extends AppCompatActivity {

    private SimpleAddressDialogFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mDialog = new SimpleAddressDialogFragment();
        mDialog.setMaxLevel(4);  // 设置地址最大级别
        mDialog.setTitle("配送至"); // 设置Dialog标题
        mDialog.setOnEventListener(new BaseAddressDialogFragment.OnEventListener<AddressItem>() {
            @Override
            public List<AddressItem> onNeedAddressList(int level, Object parentID) {
                final int id = parentID == null ? 0 : (int) parentID;
                return getAddressList(level, id);
            }

            @Override
            public void onGotResult(String address, AddressItem theLastItem) {
                Toast.makeText(MainActivity.this, address, Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                // lastAddressItem代表选择的最后一个级别的地区实体
                Log.d("My_Tag", theLastItem.getAddress());
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show(getSupportFragmentManager());
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
