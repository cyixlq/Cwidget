package top.cyixlq.widget.common

import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment : DialogFragment() {

    abstract val layoutId: Int

    protected lateinit var mRootView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(layoutId, container, false)
        if (dialog != null && dialog!!.window != null) {
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return mRootView
    }

    fun <T> findViewById(@IdRes id: Int): T {
        return mRootView.findViewById(id)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (dialog == null) return
        val window = dialog!!.window ?: return
        val attributes = window.attributes
        attributes.width = getWidth()
        attributes.height = getHeight()
        attributes.horizontalMargin = 0F
        window.attributes = attributes
        window.decorView.setPadding(0, 0, 0, 0)
        window.setGravity(getGravity())
        getBackgroundDrawableResource()?.let {
            window.setBackgroundDrawableResource(it)
        }
        getWindowAnimations()?.let {
            window.setWindowAnimations(it)
        }
    }

    open fun getGravity(): Int = Gravity.BOTTOM

    open fun getBackgroundDrawableResource(): Int? = null

    open fun getWidth(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun getHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    open fun getWindowAnimations(): Int? = null

}