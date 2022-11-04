package com.xanroid.mybubblescandit.models

import com.scandit.datacapture.barcode.data.Symbology
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTracking
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTracking.Companion.forDataCaptureContext
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTrackingSettings
import com.scandit.datacapture.core.capture.DataCaptureContext
import com.scandit.datacapture.barcode.tracking.capture.BarcodeTrackingScenario.A
import com.scandit.datacapture.core.source.Camera
import com.scandit.datacapture.core.source.VideoResolution
import com.scandit.datacapture.core.source.Camera.Companion.getDefaultCamera
import com.scandit.datacapture.core.capture.DataCaptureContext.Companion.forLicenseKey



object DataCaptureManager {

    private const val SCANDIT_LICENSE_KEY = "AZ2iZgyeLmkGCmYswh1QrzEMQWGoPfhnxU14LPxmLSDKc4Xud2Wg8KhAl9VuQgp9VkHTCkhRK0FRVleeL0EIGJ5rcCjyayWeXyVbL7wwrVYSSThj+nMWHrxvq4dKQ+eSVTclxLZDds/YNbhO+zl7Uc1iTj85SGDrrWx3/Qh3ZT39TCGuJyj+vJ5Qj1R2TIVPHXbb70txSJQ4f+jZgkbFwyVZ/wR7EpRGdm095d07fT81WIhtzHUkyMNsQw8xd+SchGIHk4dbThuURi1lJWvhFrUjiQdWUiQGcWDcc91arCcuR3M5Y3/4VKcs3EopdxrA0XBNy09G2HRiSzlEnmSLmjd5R9KHaNTPWhLI5bJoJVeLZPn44lSZ97pqfiD5Q0asmEVkIM1Q6k3vfzMPcUPnysZjdnYTU5v1yGwRzfJ/Hh4BenFHPHOdQAJJK3YoQ3ZXbkU1opJBs5KLWBUWbl+KhxZcW0jEb2o8GEVBp+o1DYILcRGUlki4hQ5P9J7sXCdR/kQBVWptlVuyIKVA0Vx29mRGvIVACM07ZFmalvV313wgWlDQIwVjYcu38T8xaKnmqBbar5bbpth8oHb0Qo2fga0ZBBRnKxukhNtvGfavnn9R9nzceZWvqFU0pYY1TZn7X1SuCwqoMSOfvDo3d3a/vbtjsq1V1NHd05n9L1wp101+xa1TI6XTfOS24GbVMeA+jMYMr4KbfB9rkxfnceYijP+lYWrUclakBx12n5AzRoY0Qs5wsrY+6A5antwndSpI3OI94lGEJsp7gw5S60jd26W+I2JMK/q0IOLEWehfH0zBcrQEQwrcjIT0n5Lzrpfl7ayrrqRVttj/OJLt3QU7bu4LutFgSadOKAu04OqxMTN6yGAmV3AxpfdKnoqAcLSnl0K21XZIoihBoIhnTooFW8CI2Bqzf0c+tv2tOSnyN49Cdb65dIdlgnZ+09njabtgRvE6mjuk6Lx6FBa04tU8nCoaYj1cp75P/U0X2yRLbEOPsz7Yf24q7yoJ+i4oE3PUu7hDk6Sw+i3xiZPfMRheZ6rUp0OvrG1iZGqCMag6iAvt/oJwQmsiJNDohDEBGiYcftk+yMi0Lx6ptVYU7tdnwoySZK8YHWj7fZW+TcdwLsgXilQQfa1Bk2ZOJYcuKfHVvw1mV69Bv3CBkWimDW24A5I32gnQFh5yyhtmOkLnw67ch7FWMQFM2UY4DhMtZtZe5zW7CeXYwrBaxJZQN96YY0QBUUzR9oM="

    val barcodeTracking : BarcodeTracking
    val dataCaptureContext: DataCaptureContext
    val camera: Camera

    init {

        val barcodeTrackingSettings = BarcodeTrackingSettings.forScenario(A).apply {
            enableSymbology(Symbology.CODE128, true)
            enableSymbology(Symbology.CODE39, true)
            enableSymbology(Symbology.QR, true)
            enableSymbology(Symbology.EAN8, true)
            enableSymbology(Symbology.UPCE, true)
            enableSymbology(Symbology.EAN13_UPCA, true)
        }

        val cameraSettings = BarcodeTracking.createRecommendedCameraSettings().apply {
            preferredResolution = VideoResolution.UHD4K
        }
        camera = getDefaultCamera(cameraSettings) ?: throw java.lang.IllegalStateException(
            "Sample depends on a camera, which failed to initialize."
        )
        dataCaptureContext = forLicenseKey(SCANDIT_LICENSE_KEY).apply {
            setFrameSource(camera)
        }
        barcodeTracking = forDataCaptureContext(dataCaptureContext, barcodeTrackingSettings)

    }

}

