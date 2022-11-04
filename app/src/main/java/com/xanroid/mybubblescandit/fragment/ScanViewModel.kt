package com.xanroid.mybubblescandit.fragment

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModel
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTracking
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTrackingListener
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTrackingSession
import com.scandit.datacapture.barcode.tracking.data.TrackedBarcode
import com.scandit.datacapture.barcode.tracking.ui.overlay.BarcodeTrackingAdvancedOverlay
import com.scandit.datacapture.barcode.tracking.ui.overlay.BarcodeTrackingAdvancedOverlayListener
import com.scandit.datacapture.core.capture.DataCaptureContext
import com.scandit.datacapture.core.common.geometry.Anchor
import com.scandit.datacapture.core.common.geometry.FloatWithUnit
import com.scandit.datacapture.core.common.geometry.MeasureUnit
import com.scandit.datacapture.core.common.geometry.PointWithUnit
import com.scandit.datacapture.core.data.FrameData
import com.scandit.datacapture.core.source.FrameSourceState
import com.xanroid.mybubblescandit.models.DataCaptureManager
import com.xanroid.mybubblescandit.scan.data.BubbleDataNew
import com.xanroid.mybubblescandit.scan.data.BubbleDataProvider
import java.util.concurrent.atomic.AtomicBoolean

class ScanViewModel: ViewModel(),  BarcodeTrackingListener, BarcodeTrackingAdvancedOverlayListener{

    private val camera = DataCaptureManager.camera
    val barcodeTracking = DataCaptureManager.barcodeTracking
    private val handler = Handler(Looper.getMainLooper())
    private var listener: ScanViewModelListener? = null
    val dataCaptureContext: DataCaptureContext
        get() = DataCaptureManager.dataCaptureContext


    init {
        DataCaptureManager.barcodeTracking.addListener(this)
    }

    fun setListener(listener: ScanViewModelListener?){
        this.listener = listener
    }

    fun resumeScanning(){
        DataCaptureManager.barcodeTracking.isEnabled = true
    }

    fun pauseScanning() {
        DataCaptureManager.barcodeTracking.isEnabled = false
    }

    fun startFrameSource() {
        camera.switchToDesiredState(FrameSourceState.ON)
    }

    fun stopFrameSource() {
        camera.switchToDesiredState(FrameSourceState.OFF)
    }


    override fun onSessionUpdated(
        mode: BarcodeTracking,
        session: BarcodeTrackingSession,
        data: FrameData
    ) {
        for (i in session.removedTrackedBarcodes) {
            handler.post { listener?.removeBubbleView(i)}
        }
    }

    override fun onCleared() {
        super.onCleared()
        barcodeTracking.removeListener(this)
    }


    override fun anchorForTrackedBarcode(
        overlay: BarcodeTrackingAdvancedOverlay,
        trackedBarcode: TrackedBarcode
    ): Anchor {
        return Anchor.TOP_CENTER
    }

    override fun offsetForTrackedBarcode(
        overlay: BarcodeTrackingAdvancedOverlay,
        trackedBarcode: TrackedBarcode,
        view: View
    ): PointWithUnit {
        return PointWithUnit(
            FloatWithUnit(0f, MeasureUnit.FRACTION),
            FloatWithUnit(-0.5f, MeasureUnit.FRACTION)
        )
    }

    override fun viewForTrackedBarcode(
        overlay: BarcodeTrackingAdvancedOverlay,
        trackedBarcode: TrackedBarcode
    ): View? {
        return listener?.getOrCreateViewForBubbleData(
            trackedBarcode,
        BubbleDataProvider.getData(trackedBarcode.barcode.data!!)
        )
    }

    interface ScanViewModelListener {
        fun getOrCreateViewForBubbleData(barcode: TrackedBarcode, bubbleData: BubbleDataNew): View
        fun removeBubbleView(identifier: Int)
    }

}