package com.hm.student.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.hm.student.R
import com.hm.student.interfaces.OnItemSelectedListener
import kotlinx.android.synthetic.main.spinner_layout.view.*


class Spinner(
    context: Context,
    attrs: AttributeSet?
) : CardView(context, attrs) {

    private var selectedItem: MenuItem? = null
    private val popup = PopupMenu(context, this, Gravity.START)

    private var mListener: OnItemSelectedListener? = null

    private val clickListener = OnClickListener {
        forceEnableIcon()

        selectedItem!!.setIcon(R.drawable.ic_tick)

        popup.setOnMenuItemClickListener { item ->
            selectedItem!!.setIcon(R.drawable.ic_transparent)
            selectedItem = item
            selectedItem!!.setIcon(R.drawable.ic_tick)

            setVisibleText(item.title.toString())
            mListener!!.onItemSelected(item)

            true
        }
        popup.show()
    }

    init {
        if (attrs != null) {
            val view = View.inflate(context, R.layout.spinner_layout, this)
            cardElevation = 0f
            radius = dipToPixels(22f)
            setCardBackgroundColor(
                ContextCompat.getColorStateList(
                    context,
                    R.color.surface_color_variant
                )!!
            )
        }

        popup.menuInflater
            .inflate(R.menu.menu_category_filter, popup.menu)

        selectedItem = popup.menu.getItem(0)
        setVisibleText(selectedItem!!.title)
        setOnClickListener(clickListener)
    }

    private fun dipToPixels(dipValue: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }

    private fun setVisibleText(title: CharSequence) {
        title_text.text = title
    }

    private fun forceEnableIcon() {
        val menuHelper: Any
        val argTypes: Array<Class<*>?>
        try {
            val fMenuHelper =
                PopupMenu::class.java.getDeclaredField("mPopup")
            fMenuHelper.isAccessible = true
            menuHelper = fMenuHelper[popup]!!
            argTypes = arrayOf(Boolean::class.java)
            menuHelper.javaClass.getDeclaredMethod("setForceShowIcon", *argTypes)
                .invoke(menuHelper, true)
        } catch (e: Exception) {

        }
    }

    fun setOnItemSelectedListener(listener: OnItemSelectedListener) {
        mListener = listener
    }

    fun setInitialItem(pos: Int) {
        mListener!!.onItemSelected(popup.menu.getItem(pos))
    }
}