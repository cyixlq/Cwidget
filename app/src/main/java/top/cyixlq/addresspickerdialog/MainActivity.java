package top.cyixlq.addresspickerdialog;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.cyixlq.addresspickerdialog.bean.AddressItem2;
import top.cyixlq.addresspickerdialog.view.Address2Dialog;
import top.cyixlq.widget.addresspickerdialog.SimpleAddressDialogFragment;
import top.cyixlq.widget.addresspickerdialog.base.BaseLevelDialogFragment;
import top.cyixlq.widget.addresspickerdialog.bean.AddressItem;
import top.cyixlq.widget.calendar.DatePickerDialogFragment;
import top.cyixlq.widget.calendar.bean.SelectRule;


public class MainActivity extends AppCompatActivity {

    private SimpleAddressDialogFragment mDialog;
    // 第二种无级别限制的层级选择Dialog
    private Address2Dialog mDialog2;
    private DatePickerDialogFragment mDatePickerDialog;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    private BaseLevelDialogFragment.GotDataListener<AddressItem> listener;
    private BaseLevelDialogFragment.GotDataListener<AddressItem2> listener2;
    private int level1;
    private int level2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.setIndeterminate(true); // 是否形成一个加载动画  true表示不明确加载进度形成转圈动画  false 表示明确加载进度
        progressDialog.setCancelable(false);

        mDialog = new SimpleAddressDialogFragment();
        mDialog.setMaxLevel(4);  // 设置地址最大级别
        mDialog.setTitle("配送至"); // 设置Dialog标题
        mDialog.showOk(true);
        mDialog.setOnEventListener(new BaseLevelDialogFragment.EventListener<AddressItem>() {

            @Override
            public void onGotResult(@NonNull List<AddressItem> nodes, AddressItem lastNode) {
                final StringBuilder address = new StringBuilder();
                for (AddressItem item : nodes) {
                    address.append(item.getAddress());
                }
                Toast.makeText(MainActivity.this, "选择的地址：" + address.toString() + "\n最后一个地址是：" + lastNode.getAddress(), Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }

            @Override
            public void onNeedData(int level, AddressItem parentNode, @NonNull BaseLevelDialogFragment.GotDataListener<AddressItem> onGotDataListener) {
                level1 = level;
                if (MainActivity.this.listener == null)
                    MainActivity.this.listener = onGotDataListener;
                showLoading();
                pool.execute(() -> {
                    // 模拟网络加载数据
                    try {
                        Thread.sleep(1000);
                        List<AddressItem> list = getAddressList(level);
                        // 一定要切换到主线程进行回调
                        handler.post(() -> {
                            MainActivity.this.listener.onGotData(level1, list);
                            dismissLoading();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        handler.post(() -> {
                            Toast.makeText(MainActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                            dismissLoading();
                        });
                    }
                });
            }
        });
        findViewById(R.id.btn).setOnClickListener(v -> mDialog.show(getSupportFragmentManager()));

        mDialog2 = new Address2Dialog();
        mDialog.setTitle("配送至"); // 设置Dialog标题
        mDialog2.showOk(true);
        mDialog2.setOnEventListener(new BaseLevelDialogFragment.EventListener<AddressItem2>() {

            @Override
            public void onGotResult(@NonNull List<AddressItem2> nodes, AddressItem2 lastNode) {
                final StringBuilder address = new StringBuilder();
                for (AddressItem2 item : nodes) {
                    address.append(item.getName());
                }
                Toast.makeText(MainActivity.this, "选择的地址：" + address.toString() + "\n最后一个地址是：" + lastNode.getName(), Toast.LENGTH_SHORT).show();
                mDialog2.dismiss();
            }

            @Override
            public void onNeedData(int level, AddressItem2 parentNode, @NonNull BaseLevelDialogFragment.GotDataListener<AddressItem2> onGotDataListener) {
                level2 = level;
                if (MainActivity.this.listener2 == null)
                    MainActivity.this.listener2 = onGotDataListener;
                showLoading();
                if (parentNode != null) {
                    onGotDataListener.onGotData(level, parentNode.getChildren());
                    dismissLoading();
                    return;
                }
                // 如果父级为null，那么就需要进行网络请求
                pool.execute(() -> {
                    // 模拟网络加载数据
                    try {
                        Thread.sleep(1000);
                        List<AddressItem2> list = getAddressList2(level);
                        // 一定要切换到主线程进行回调
                        handler.post(() -> {
                            // 因为我这里是模拟的网络请求，直接可以在此方法中访问到onGotDataListener，对于不能在此方法中进行回调的建议如下所示，使用全局变量保存然后进行回调
                            MainActivity.this.listener2.onGotData(level2, list);
                            dismissLoading();
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        handler.post(() -> {
                            Toast.makeText(MainActivity.this, "数据加载失败！", Toast.LENGTH_SHORT).show();
                            dismissLoading();
                        });
                    }
                });
            }
        });
        findViewById(R.id.btn2).setOnClickListener(v -> mDialog2.show(getSupportFragmentManager(), "address2"));


        mDatePickerDialog = new DatePickerDialogFragment();
        final SelectRule rule = new SelectRule();
        rule.setCanSelectAfterToday(false); // 设置是否可以选择超过今天的时间
        rule.setEnable(true); // 设置是否开启时间选择功能
        rule.setMaxDayCount(1); // 设置最多可以选择多少天
        mDatePickerDialog.setSelectRule(rule);
        mDatePickerDialog.setOnTimeSelectListener((start, end) -> {
            final String msg = "开始时间：" + start.toString() + "\n" + "结束时间：" + end.toString();
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
        findViewById(R.id.btnDate).setOnClickListener(v -> mDatePickerDialog.show(getSupportFragmentManager()));
    }

    private void showLoading() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void dismissLoading() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }


    /**
     * 模拟获取地址列表的方法
     *
     * @param level 当前要选择的地区级别
     * @return List<AddressItem> 返回地区数据
     */
    private List<AddressItem> getAddressList(int level) {
        final String text;
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
            list.add(item);
        }
        return list;
    }

    private List<AddressItem2> getAddressList2(int level) {
        if (level == 4) return new ArrayList<>();
        final String text;
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
        List<AddressItem2> list = new ArrayList<>();
        final int randomSize = new Random().nextInt(8) + 3;
        for (int i = 1; i < randomSize; i++) {
            AddressItem2 item = new AddressItem2(
                    i, text + i, getAddressList2(level + 1)
            );
            list.add(item);
        }
        return list;
    }
}
