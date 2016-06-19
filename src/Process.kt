import org.opencv.core.Rect
import java.util.*

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/18/2016
 */

class FaceDataProcesser() {
    fun run() {
        var timer: Timer = Timer()
        timer.schedule(FaceDataProcesserTask(), 0, 10000)
    }
}

class FaceDataProcesserTask() : TimerTask() {
    private val faceDetector: FaceDetector = FaceDetector()
    private var reference: Pair<Rect?, Int>? = null
    private var averageY: Int = 0

    override fun run() {
        var totalY: Int = 0

        for (i in 0..10) {
            var newTotalY: Int = totalY

            while (totalY == newTotalY) {
                reference = faceDetector.run()

                if (reference!!.first != null) {
                    newTotalY += reference!!.first!!.y + reference!!.second
                } else {
                    println("faces = null")
                }
            }

            totalY = newTotalY
        }

        println(totalY / 10)
    }
}