import org.opencv.core.Rect
import java.util.*

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/18/2016
 */

class FaceDataProcessor(faceDetector: FaceDetector, setup: Setup) {
    private var faceDetector: FaceDetector = faceDetector
    private var setup: Setup = setup

    fun run() {
        val timer: Timer = Timer()
        timer.schedule(FaceDataProcessorTask(faceDetector, setup), 0, 10000)
    }
}

class FaceDataProcessorTask(faceDetector: FaceDetector, setup: Setup) : TimerTask() {
    private val faceDetector: FaceDetector = faceDetector
    private val setup: Setup = setup

    private var reference: Pair<Rect?, Int>? = null
    private var averageY: Int = 0
    private var slouchDifference: Int = 30 // Guess


    override fun run() {
        val postureBorder: Int

        var totalY: Int = 0

        val sampleSize: Int = 50

        for (i in 0..sampleSize) {
            var newTotalY: Int = totalY

            while (totalY == newTotalY) {
                reference = faceDetector.run()

                if (reference!!.first != null) {
                    newTotalY += reference!!.first!!.y + reference!!.second
                }
            }

            totalY = newTotalY
        }

        averageY = totalY / sampleSize
        postureBorder = setup.goodPostureCalibration + slouchDifference
        println("{$averageY} " + if (averageY < postureBorder) "< {$postureBorder}: Good" else "> {$postureBorder}: Slouching")
    }
}