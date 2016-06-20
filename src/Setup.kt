import org.opencv.core.Rect

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/20/2016
 */

class Setup() {
    var goodPostureCalibration: Int = 0

    fun calibratePosture(faceDetector: FaceDetector) {
        var facePos: Pair<Rect?, Int>? = faceDetector.run()

        while (facePos?.first == null) facePos = faceDetector.run()

        goodPostureCalibration = facePos!!.first!!.y + facePos.second
        println("Posture calibrated to {$goodPostureCalibration}")
    }
}