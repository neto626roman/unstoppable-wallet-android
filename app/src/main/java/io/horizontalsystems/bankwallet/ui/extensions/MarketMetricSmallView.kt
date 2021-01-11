package io.horizontalsystems.bankwallet.ui.extensions

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import io.horizontalsystems.bankwallet.R
import io.horizontalsystems.bankwallet.core.App
import kotlinx.android.synthetic.main.view_market_metric_small.view.*
import java.math.BigDecimal

class MarketMetricSmallView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflate(context, R.layout.view_market_metric_small, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.MarketMetricSmallView)
        try {
            setTitle(ta.getString(R.styleable.MarketMetricSmallView_title))
            setValue(ta.getString(R.styleable.MarketMetricSmallView_value))
        } finally {
            ta.recycle()
        }
    }

    fun setValue(v: String?) {
        value.text = v
    }

    fun setTitle(v: String?) {
        title.text = v
    }

    fun setDiff(v: BigDecimal?) {
        diffCircle.animateVertical(v?.toFloat())
        if (v == null) return

        val sign = if (v >= BigDecimal.ZERO) "+" else "-"

        diffPercentage.text = App.numberFormatter.format(v.abs(), 0, 2, sign, "%")
    }

    fun setMetricData(data: MetricData?) {
        setValue(data?.value)
        setDiff(data?.diff)
    }
}
