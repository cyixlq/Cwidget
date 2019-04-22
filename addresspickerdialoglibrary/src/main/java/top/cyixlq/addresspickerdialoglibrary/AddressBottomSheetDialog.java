package top.cyixlq.addresspickerdialoglibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.TextView;

import java.util.List;

import top.cyixlq.addresspickerdialoglibrary.adapter.AddressAdapter;
import top.cyixlq.addresspickerdialoglibrary.base.CustomBaseBottomSheetDialog;
import top.cyixlq.addresspickerdialoglibrary.bean.AddressItem;

public class AddressBottomSheetDialog extends CustomBaseBottomSheetDialog {

    private TabLayout tabLayout;
    private AddressAdapter addressAdapter;

    private int maxLevel;   // 最大有多少级的地区，可以通过setMaxLevel方法进行自定义
    private SparseArray<List<AddressItem>> levelList;     // 级别列表数据
    private SparseIntArray levelPosition;                 // 选中的列表position
    private SparseIntArray levelIds;                      // 各个级别选择的地址ID
    private String title;  // 标题
    private String tabText = "请选择";                    // 新的Tab默认显示的文本
    private TabSelectChangeListener changeListener;       // Tab的选择被改变的监听

    public AddressBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected Integer getLayout() {
        return R.layout.user_layout_address_bottom_sheet_dialog;
    }

    @Override
    protected Integer getBackgroundRes() {
        return R.drawable.bg_dialog_bottom;
    }

    @Override
    protected Integer getWindowAnimations() {
        return R.style.DialogBottom;
    }

    @Override
    protected void initView() {
        levelList = new SparseArray<>();
        levelPosition = new SparseIntArray();
        levelIds = new SparseIntArray();

        ((TextView)findViewById(R.id.user_tv_dialog_title)).setText(title);
        tabLayout = findViewById(R.id.user_tb_dialog_tab);
        final RecyclerView recyclerView = findViewById(R.id.user_rv_dialog_list);

        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int position = tab.getPosition();
                List<AddressItem> list = levelList.get(position);
                if (null != list && !list.isEmpty()) {   // 如果选中级别的List没有数据就通过执行回调来获取，否则直接复用
                    addressAdapter.setList(list);
                    final int lastClickPositon = levelPosition.get(position, -1);
                    if (lastClickPositon >= 0) recyclerView.smoothScrollToPosition(lastClickPositon);
                } else if (changeListener != null) {
                    // 参数position代表的当前地区级别，父级地区ID应该选当前级别的上一个级别，如果没有默认返回-1
                    changeListener.onSelectChange(position, levelIds.get(position -1, -1));
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        addressAdapter = new AddressAdapter();
        // 列表单项点击事件
        addressAdapter.setOnItemClickListener(new AddressAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final int selectedTabPosition = tabLayout.getSelectedTabPosition();
                levelIds.put(selectedTabPosition, levelList.get(selectedTabPosition).get(position).getId());
                changeSelect(selectedTabPosition, position);
                levelPosition.put(selectedTabPosition, position);
                setTabText(selectedTabPosition, levelList.get(selectedTabPosition).get(position).getAddress());
                if (selectedTabPosition < maxLevel - 1 && selectedTabPosition == tabLayout.getTabCount() - 1) {
                    tabLayout.addTab(createTab(), true);
                    recyclerView.smoothScrollToPosition(0);
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(addressAdapter);
        tabLayout.addTab(createTab(), true);
    }

    // 创建一个请选择的tab并返回
    private TabLayout.Tab createTab() {
        return tabLayout.newTab().setText(tabText);
    }

    // 当点击了RecyclerView条目的时候执行的方法
    private void changeSelect(int selectedTabPosition, int nowClickPosition) {
        // 保存下来的当前列表上一个点击位置.如果找不到该值，默认返回-1
        final int lastPosition = levelPosition.get(selectedTabPosition, -1);
        // 如果上一个点击位置和下一个点击位置相同，则不做改变
        if (nowClickPosition == lastPosition) {
            return;
        }
        // 如果不是最后一个，而且又重新选择了级别地址，移除后面的Tab
        final int count = tabLayout.getTabCount();
        if (selectedTabPosition < count - 1) {
            TabLayout.Tab nowTab = tabLayout.getTabAt(selectedTabPosition);
            if (null != nowTab) nowTab.setText(tabText);
            for (int i = count - 1; i > selectedTabPosition; i--) {
                levelList.remove(i);
                levelPosition.put(i, -1);
                levelIds.put(i, -1);
                tabLayout.removeTabAt(i);
            }
        }
        levelList.get(selectedTabPosition).get(nowClickPosition).setChecked(true);
        addressAdapter.notifyItemChanged(nowClickPosition);
        if (lastPosition >= 0) {
            levelList.get(selectedTabPosition).get(lastPosition).setChecked(false);
            addressAdapter.notifyItemChanged(lastPosition);
        }
    }
    // 设置第几个tab的文字
    private void setTabText(int tabPosition, String text) {
        TabLayout.Tab tab = tabLayout.getTabAt(tabPosition);
        if (null != tab) tab.setText(text);
    }




    // -----------------------------  以下是对外公开方法与接口  --------------------------

    /**
     *  设置Dialog的标题
     * @param title 标题文字
     */
    public void setDialogTitle(String title) {
        this.title = title;
    }

    /**
     *  设置在当前tab下还未选择区域时候tab默认显示的文字
     * @param tabDefaultText 默认显示的文字
     */
    public void setTabDefaultText(String tabDefaultText) {
        this.tabText = tabDefaultText;
    }

    /**
     *  设置地址最大级别（如：省，市，县，镇的话就是最大4级）
     * @param level 最大级别
     */
    public void setMaxLevel(int level) {
        this.maxLevel = level;
    }

    /**
     *  设置当前级别列表需要显示的列表数据
     * @param list 列表数据
     * @param level 地区级别
     */
    public void setCurrentAddressList(List<AddressItem> list, int level) {
        levelList.put(level, list);
        addressAdapter.setList(list);
    }

    /**
     *  设置Dialog中Tab点击切换的监听
     * @param listener tab切换监听实现
     */
    public void setTabSelectChangeListener(@NonNull TabSelectChangeListener listener) {
        this.changeListener = listener;
    }

    /**
     *  自定义的Tab切换监听接口
     */
    public interface TabSelectChangeListener {
        void onSelectChange(int level, int parentId);
    }
}