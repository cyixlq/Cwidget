package top.cyixlq.widget.addresspickerdialog.base

import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import top.cyixlq.widget.R
import top.cyixlq.widget.addresspickerdialog.bean.PageData
import top.cyixlq.widget.common.BaseDialogFragment
import java.util.*

open class BaseLevelDialogFragment<T> : BaseDialogFragment() {

    override val layoutId: Int = R.layout.user_layout_address_bottom_sheet_dialog

    private lateinit var mRv: RecyclerView
    private lateinit var mTvTitle: TextView
    private lateinit var mTabLayout: TabLayout
    private lateinit var mTvCancel: TextView
    private lateinit var mTvOk: TextView
    private var mAdapter: BaseLevelAdapter<T>? = null


    private var title = "" // 标题
    private var defaultTabText = "请选择"
    private var isAutoSelectNext = true // 选择完当前内容后，新生成的Tab是否自动徐那种
    private var showCancel = false // 是否展示取消TV
    private var showOk = false // 是否展示确定TV
    private var maxLevel = -1 // 如果Adapter中hasMore方法一直返回true，那么将以达到MaxLevel为满足条件进行回调
    private val mPageDataMap = SparseArray<PageData<T>>()
    private var eventListener: EventListener<T>? = null
    private val onGotDataListener: GotDataListener<T> = object : GotDataListener<T> {
        override fun onGotData(level: Int, data: MutableList<T>) {
            val pageData = getSafePageData(level)
            pageData.items = data
            mAdapter?.setItemSelect(pageData.selectIndex, true)
            mAdapter?.setData(data)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tempAdapter = mAdapter ?: throw RuntimeException("你应该先setAdapter，然后再调用show方法")
        mRv = findViewById(R.id.user_rv_dialog_list)
        mRv.adapter = tempAdapter
        mTvTitle = findViewById(R.id.user_tv_dialog_title)
        mTvTitle.text = title
        mTvCancel = findViewById(R.id.tvCancel)
        mTvOk = findViewById(R.id.tvOk)
        if (showCancel) {
            mTvCancel.visibility = View.VISIBLE
            mTvCancel.setOnClickListener { dismiss() }
        }
        if (showOk) {
            mTvOk.visibility = View.VISIBLE
            mTvOk.setOnClickListener { getResultNow() }
        }
        mTabLayout = findViewById(R.id.user_tb_dialog_tab)
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val level = tab?.position ?: 0
                val pageData = getSafePageData(level)
                val list = pageData.items
                if (list != null) {
                    mAdapter?.setData(list)
                    mAdapter?.setItemSelect(pageData.selectIndex, true)
                    if (pageData.selectIndex in 0 until list.size) {
                        mRv.smoothScrollToPosition(pageData.selectIndex)
                    }
                } else { // 需要通过回调请求数据
                    val parentPageData = mPageDataMap[level - 1]
                    val parentNode =
                        if (parentPageData != null && parentPageData.selectIndex in 0 until parentPageData.items.size) {
                            parentPageData.items[parentPageData.selectIndex]
                        } else null
                    eventListener?.onNeedData(level, parentNode, onGotDataListener)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        tempAdapter.setOnItemClickListener { position, item ->
            val level = mTabLayout.selectedTabPosition
            changeTabContent(level, position, item)
        }
        mTabLayout.addTab(createTab(), true)
        val pageData = getSafePageData(mTabLayout.selectedTabPosition)
        pageData.selectIndex = -1
        mAdapter?.setItemSelect(pageData.selectIndex, true)
    }

    /**
     *  @param level 当前Tab的层级
     *  @param position 当前RV点击的Position
     */
    private fun changeTabContent(level: Int, position: Int, currentItem: T) {
        val currentPageData = mPageDataMap[level] ?: return
        val lastPosition = currentPageData.selectIndex
        if (lastPosition == position) return // 如果这次点击位置和上一次一致，那么不需要继续往下执行了
        setTabText(level, mAdapter?.getTabText(currentItem) ?: "")
        currentPageData.selectIndex = position
        mAdapter?.setItemSelect(position, true)
        if (lastPosition in 0 until currentPageData.items.size) {
            mAdapter?.setItemSelect(lastPosition, false)
        }
        if (level < mTabLayout.tabCount - 1) { // 如果不是最后一个，而且又重新选择了级别地区，移除后面的Tab
            // 如果不是最后一个，而且又重新选择了级别地区，移除后面的Tab
            for (i in mTabLayout.tabCount - 1 downTo level + 1) {
                mPageDataMap.remove(i)
                mTabLayout.removeTabAt(i)
            }
        } else if (needGotResult(currentItem, level)) { // 达到了结果回调条件，那么回调获得结果接口
            getResultNow()
        }
        // 如果没有达到回调结果条件，那么就添加Tab
        if (!needGotResult(currentItem, level)) {
            val nextPageData = getSafePageData(level + 1)
            nextPageData.selectIndex = -1
            mTabLayout.addTab(createTab(), isAutoSelectNext)
            mRv.smoothScrollToPosition(0)
        }
    }

    private fun getSafePageData(level: Int): PageData<T> {
        var pageData = mPageDataMap[level]
        if (pageData == null) {
            pageData = PageData()
            mPageDataMap.put(level, pageData)
        }
        return pageData
    }

    // 是否达到了回调结果的条件
    private fun needGotResult(currentItem: T, level: Int): Boolean {
        // 需要先判断mAdapter有没有更多数据，有的话再判断有没有设置过maxLevel，并且有没有到maxLevel，没有的话直接进行后面的判断
        return mAdapter?.hasMore(currentItem) == false || (maxLevel != -1 && level == maxLevel - 1)
    }

    private fun createTab(): TabLayout.Tab = mTabLayout.newTab().setText(defaultTabText)

    // 设置当前Tab的文字
    private fun setTabText(level: Int, text: String) {
        val tab = mTabLayout.getTabAt(level) ?: return
        tab.text = text
    }

    override fun getBackgroundDrawableResource(): Int = R.drawable.bg_dialog_bottom
    override fun getHeight(): Int = 1000
    override fun getWidth(): Int = WindowManager.LayoutParams.MATCH_PARENT
    override fun getWindowAnimations(): Int = R.style.DialogBottom

    /* ---------------------------------- 对外公开方法 ----------------------------- */
    // 设置RV的Adapter，用来自定义RV中Item的样式
    fun setAdapter(adapter: BaseLevelAdapter<T>) {
        this.mAdapter = adapter
    }

    // 设置事件监听回调
    fun setOnEventListener(listener: EventListener<T>) {
        this.eventListener = listener
    }

    // 设置最大级别到几级
    fun setMaxLevel(max: Int) {
        this.maxLevel = max
    }

    // 设置Dialog标题
    fun setTitle(title: String) {
        this.title = title
    }

    // 设置Tab的默认Text
    fun setDefaultTabText(text: String) {
        this.defaultTabText = text
    }


    // 立即获取结果，忽视maxLevel和hasMore的条件，用于不定级选择
    fun getResultNow() {
        val nodes = LinkedList<T>()
        for (i in 0 until mPageDataMap.size()) {
            val pageData = mPageDataMap[i]
            val selectIndex = pageData.selectIndex
            if (selectIndex in 0 until pageData.items.size)
                nodes.add(pageData.items[pageData.selectIndex])
        }
        eventListener?.onGotResult(nodes, nodes.last)
    }

    // 是否显示取消TextView
    fun showCancel(isShow: Boolean) {
        this.showCancel = isShow
    }

    // 是否显示确定TextView
    fun showOk(isShow: Boolean) {
        this.showOk = isShow
    }

    // 是否自动选择下一个Tab
    fun setIsAutoSelectNextTab(isAuto: Boolean) {
        this.isAutoSelectNext = isAuto
    }

    interface GotDataListener<T> {
        fun onGotData(level: Int, data: MutableList<T>)
    }

    interface EventListener<T> {
        fun onNeedData(level: Int, parentNode: T?, onGotDataListener: GotDataListener<T>)
        fun onGotResult(nodes: MutableList<T>, lastNode: T)
    }

}