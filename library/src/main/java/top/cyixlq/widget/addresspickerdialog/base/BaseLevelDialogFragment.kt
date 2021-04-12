package top.cyixlq.widget.addresspickerdialog.base

import android.os.Bundle
import android.util.SparseArray
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import top.cyixlq.widget.R
import top.cyixlq.widget.addresspickerdialog.bean.PageData
import top.cyixlq.widget.common.BaseDialogFragment
import java.lang.RuntimeException

class BaseLevelDialogFragment<T> : BaseDialogFragment() {
    override val layoutId: Int = R.layout.user_layout_address_bottom_sheet_dialog

    private lateinit var mRv: RecyclerView
    private lateinit var mTvTitle: TextView
    private lateinit var mTabLayout: TabLayout
    private var mAdapter: BaseLevelAdapter<T>? = null


    private var title = "" // 标题
    private var maxLevel = 4
    private val mPageDataMap = SparseArray<PageData<T>>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tempAdapter = mAdapter ?: throw RuntimeException("你应该先setAdapter，然后再调用show方法")
        mRv = findViewById(R.id.user_rv_dialog_list)
        mRv.adapter = tempAdapter
        mTvTitle = findViewById(R.id.user_tv_dialog_title)
        mTvTitle.text = title
        mTabLayout = findViewById(R.id.user_tb_dialog_tab)
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab?.position ?: return
                var pageData = mPageDataMap[position]
                if (pageData == null) {
                    pageData = PageData()
                    mPageDataMap.put(position, pageData)
                }
                val list =
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun setAdapter(adapter: BaseLevelAdapter<T>) {
        this.mAdapter = adapter
    }



    override fun getHeight(): Int = 800

}