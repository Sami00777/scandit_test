package com.xanroid.mybubblescandit.scan.bubble

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract.Data
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.xanroid.mybubblescandit.R
import com.xanroid.mybubblescandit.scan.data.BubbleDataNew

class Bubble(context: Context, data: BubbleDataNew, private val code: String) {

    private val dataList = listOf(
        DataExample("123456780", 4.5F),
        DataExample("123456781", 3F),
        DataExample("123456782", 2.5F),
        DataExample("123456783", 1F),
        DataExample("123456784", 5F),
        DataExample("123456785", 3.5F),
    )



    @SuppressLint("SetTextI18n")
    val root1: View = (View.inflate(context, R.layout.bubble_new, null) as CardView).apply {

        layoutParams = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        val txtRate = findViewById<TextView>(R.id.txtRate)
        val txtNUmber = findViewById<TextView>(R.id.txtNumber)
        val rateBar = findViewById<RatingBar>(R.id.ratingBar)
        rateBar.isEnabled = false
        var rate = 5.0F
        for (i in dataList){
            if (data.number == i.barcode){
                rate = i.rate
            }
        }

        txtRate.text = "Rate: $rate"
        rateBar.rating = rate

        txtNUmber.text = "No: " + data.number

    }
}

data class DataExample(
    val barcode: String,
    val rate: Float
)