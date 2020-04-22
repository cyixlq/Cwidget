package top.cyixlq.addresspickerdialoglibrary.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

import top.cyixlq.addresspickerdialoglibrary.R;
import top.cyixlq.addresspickerdialoglibrary.bean.PageData;

public abstract class BaseAddressDialogFragment<T> extends DialogFragment {

    private RecyclerView mRvAddress;
    private TextView mTvTitle;
    private TabLayout mTabLayout;
    private BaseAddressAdapter<T> mAdapter;

    private SparseArray<PageData<T>> mPageDataMap; // 每个级别的页面数据
    private OnEventListener<T> mListener; // 事件监听回调

    private String mTitle = ""; // 标题文字
    private String mDfTabText = "请选择"; // 默认还未选中地区时，tab显示的文字
    private int mMaxLevel = 3; // 最多有几级

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.user_layout_address_bottom_sheet_dialog, container, false);
        mRvAddress = view.findViewById(R.id.user_rv_dialog_list);
        mTvTitle = view.findViewById(R.id.user_tv_dialog_title);
        mTabLayout = view.findViewById(R.id.user_tb_dialog_tab);
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mAdapter == null) {
            throw new RuntimeException("You should call setAdapter() before show()");
        }
        mRvAddress.setAdapter(mAdapter);
        mTvTitle.setText(mTitle);
        mPageDataMap = new SparseArray<>();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final int level = tab.getPosition();
                PageData<T> pageData = mPageDataMap.get(level);
                if (pageData == null) {
                    pageData = new PageData<>();
                    mPageDataMap.put(level, pageData);
                }
                List<T> list = mPageDataMap.get(level).getItems(); // 获取当前级别页面数据中的地址列表
                if (list != null) {   // 如果列表为null就通过执行回调来获取，否则直接复用
                    mAdapter.setData(list);
                    final int lastSelectedIndex = mPageDataMap.get(level).getSelectedIndex();
                    if (lastSelectedIndex >= 0) mRvAddress.smoothScrollToPosition(lastSelectedIndex);
                } else if (mListener != null) {
                    // 先获取父级页面数据PageData，然后拿父级选中的id
                    final PageData<T> parentPageData = mPageDataMap.get(level - 1);
                    final Object parentId;
                    if (parentPageData != null) { // 没有上一级
                        parentId = parentPageData.getSelectedId();
                    } else {
                        parentId = null;
                    }
                    // 通过回调获取的地区列表数据
                    List<T> items = mListener.onNeedAddressList(level, parentId);
                    pageData.setItems(items);
                    mAdapter.setData(items);
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        mAdapter.setOnItemClickListener(new BaseAddressAdapter.OnItemClickListener<T>() {
            @Override
            public void onItemClick(int nowClickPosition, Object addressId, String addressName) {
                final int level = mTabLayout.getSelectedTabPosition(); // 当前级别
                changeTabSelect(level, nowClickPosition, addressId, addressName);
                if (level < mMaxLevel - 1 && level == mTabLayout.getTabCount() - 1) {
                    mTabLayout.addTab(createTab(), true);
                    mRvAddress.smoothScrollToPosition(0);
                }
            }
        });
        mTabLayout.addTab(createTab(), true);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() == null) return;
        Window window = getDialog().getWindow();
        if (window == null) return;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = 800;
        layoutParams.horizontalMargin = 0;
        View decorView = window.getDecorView();
        decorView.setPadding(0,0,0,0);
        final int gravity = getGravity() == null ? Gravity.BOTTOM : getGravity();
        window.setGravity(gravity);
        final int resource = getBackgroundResource() == null ?
                R.drawable.bg_dialog_bottom : getBackgroundResource();
        window.setBackgroundDrawableResource(resource);
        final int animation = getWindowAnimation() == null ?
                R.style.DialogBottom : getWindowAnimation();
        window.setWindowAnimations(animation);
    }

    private void changeTabSelect(final int level, final int nowClickPosition,
                                 final Object addressId, final String addressName) {
        final PageData<T> currentPageData = mPageDataMap.get(level);
        final T item = currentPageData.getItems().get(nowClickPosition); // 当前点击的地区实体
        // 当前界面数据上一次选中的下标
        final int lastSelectedIndex = currentPageData.getSelectedIndex();
        // 如果上一个点击位置和下一个点击位置相同，则不做改变
        if (nowClickPosition == lastSelectedIndex) {
            return;
        }

        currentPageData.setSelectedId(addressId);
        currentPageData.setSelectedIndex(nowClickPosition);
        currentPageData.setSelectedAddressText(addressName);
        setTabText(level, addressName);

        final int count = mTabLayout.getTabCount();
        if (level < count - 1) { // 如果不是最后一个，而且又重新选择了级别地区，移除后面的Tab
            for (int i = count - 1; i > level; i--) {
                mPageDataMap.remove(i);
                mTabLayout.removeTabAt(i);
            }
        } else if(level == mMaxLevel - 1) { // 如果是最后一个级别地区选择完，拼接最终的地址字符串回调出去
            StringBuilder address = new StringBuilder();
            for (int i = 0; i < mPageDataMap.size(); i++) {
                address.append(mPageDataMap.get(i).getSelectedAddressText());
            }
            mListener.onGotResult(address.toString(), currentPageData.getItems().get(nowClickPosition));
        }
        // 更新RecyclerView
        mAdapter.setChecked(item, true);
        mAdapter.notifyItemChanged(nowClickPosition);
        if (lastSelectedIndex >= 0) {
            mAdapter.setChecked(currentPageData.getItems().get(lastSelectedIndex), false);
            mAdapter.notifyItemChanged(lastSelectedIndex);
        }
    }

    private TabLayout.Tab createTab() {
        return mTabLayout.newTab().setText(mDfTabText);
    }

    private void setTabText(final int tabPosition, final String tabText) {
        TabLayout.Tab tab = mTabLayout.getTabAt(tabPosition);
        if (tab != null) {
            tab.setText(tabText);
        }
    }

    // -----------------------------  以下是对外公开方法与接口  --------------------------

    /**
     *  设置未选中地址时，Tab默认显示的文字
     * @param dfText 默认文字
     */
    public void setDfTabText(final String dfText) {
        this.mDfTabText = dfText;
    }

    /**
     *  设置地址选择弹出框的标题
     * @param title 标题文字
     */
    public void setTitle(final String title) {
        if (mTvTitle != null)
            mTvTitle.setText(title);
        this.mTitle = title;
    }

    /**
     *  设置自定义的适配器
     * @param adapter 继承自BaseAdapter的适配器
     */
    public void setAdapter(BaseAddressAdapter<T> adapter) {
        if (adapter == null) {
            throw new NullPointerException("adapter can not set null!");
        }
        this.mAdapter = adapter;
    }

    /**
     *  设置最大有几级地区，例如省，市，县就是3级，所以传入3
     * @param max 最大级数
     */
    public void setMaxLevel(final int max) {
        this.mMaxLevel = max;
    }

    /**
     *  设置事件监听回调
     * @param listener 监听回调
     */
    public void setOnEventListener(OnEventListener<T> listener) {
        this.mListener = listener;
    }

    public interface OnEventListener<T>{
        List<T> onNeedAddressList(final int level, final Object parentID);
        void onGotResult(final String address, final T theLastItem);
    }

    public abstract Integer getGravity();
    public abstract Integer getWindowAnimation();
    public abstract Integer getBackgroundResource();
    public abstract void initView();
}
