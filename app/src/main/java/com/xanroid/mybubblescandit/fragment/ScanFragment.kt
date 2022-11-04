package com.xanroid.mybubblescandit.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProvider
import com.scandit.datacapture.barcode.tracking.data.TrackedBarcode
import com.scandit.datacapture.barcode.tracking.ui.overlay.BarcodeTrackingAdvancedOverlay
import com.scandit.datacapture.core.ui.DataCaptureView
import com.xanroid.mybubblescandit.CameraPermission
import com.xanroid.mybubblescandit.R
import com.xanroid.mybubblescandit.scan.bubble.Bubble
import com.xanroid.mybubblescandit.scan.data.BubbleDataNew

class ScanFragment : CameraPermission(), ScanViewModel.ScanViewModelListener {

    private lateinit var viewModel: ScanViewModel
    private lateinit var dataCaptureView: DataCaptureView
    private lateinit var bubblesOverlay: BarcodeTrackingAdvancedOverlay

    private var bubbles: MutableMap<Int, Bubble> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ScanViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layout: View = inflater.inflate(R.layout.fragment_scan, container, false)

        dataCaptureView = DataCaptureView.newInstance(requireContext(), viewModel.dataCaptureContext)
        bubblesOverlay = BarcodeTrackingAdvancedOverlay.newInstance(viewModel.barcodeTracking, dataCaptureView)
        bubblesOverlay.listener = viewModel

        (layout.findViewById<View>(R.id.root) as ViewGroup).addView(
            dataCaptureView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataCaptureView.removeOverlay(bubblesOverlay)
        bubblesOverlay.listener = null
    }

    override fun onResume() {
        super.onResume()
        requestCameraPermission()
    }

    override fun onCameraPermissionGranted() {
        resumeFrameSource()
    }

    private fun resumeFrameSource() {
        viewModel.setListener(this)
        viewModel.startFrameSource()
        viewModel.resumeScanning()
    }

    override fun onPause() {
        pauseFrameSource()
        super.onPause()
    }

    private fun pauseFrameSource() {
        viewModel.setListener(null)
        viewModel.pauseScanning()
        viewModel.stopFrameSource()
    }

    override fun getOrCreateViewForBubbleData(
        barcode: TrackedBarcode,
        bubbleData: BubbleDataNew
    ): View {
        val identifier = barcode.identifier
        var bubble = bubbles[identifier]
        if (bubble == null) {
            val code = barcode.barcode.data ?: ""
            bubble = Bubble(requireContext(), bubbleData, code)
            bubbles[identifier] = bubble
        }
        return bubble.root1
    }

    override fun removeBubbleView(identifier: Int) {
        bubbles.remove(identifier)
    }

}