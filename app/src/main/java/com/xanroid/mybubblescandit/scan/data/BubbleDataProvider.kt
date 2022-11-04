package com.xanroid.mybubblescandit.scan.data

import java.util.WeakHashMap


object BubbleDataProvider {

    fun getData(barcode: String): BubbleDataNew {
        return BubbleDataNew("rate", barcode)
    }
}