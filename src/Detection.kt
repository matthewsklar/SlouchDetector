import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Rect
import org.opencv.highgui.VideoCapture
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/18/2016
 */

class FaceDetector {
    private val faceCascadeName: String = "util/haarcascade_frontalface_default.xml"
    private val mouthCascadeName: String = "util/Mouth.xml"

    private val vid: VideoCapture = VideoCapture(0)

    private var cascadesLoaded: Boolean = false

    private var faceCascade: CascadeClassifier = CascadeClassifier()
    private var mouthCascade: CascadeClassifier = CascadeClassifier()

    init {
        cascadesLoaded = loadCascades()
    }

    /**
     * Check if prerequisites to detect face are met
     *
     * @return the list of rects for the faces position or null if none are found
     */
    fun run(): Pair<Rect?, Int>? {
        var frame: Mat = Mat()

        if (cascadesLoaded) {
            if (vid.isOpened) {
                vid.read(frame)

                if (!frame.empty()) return detectFace(frame)
            }
        }

        return null
    }

    private fun detectFace(frame: Mat): Pair<Rect?, Int> {
        var greyFrame: Mat = Mat()
        var faces: MatOfRect = MatOfRect()
        var mouth: MatOfRect = MatOfRect()
        var startValue: Int = 0

        var lowestMouth: Rect? = null

        Imgproc.cvtColor(frame, greyFrame, Imgproc.COLOR_BGR2GRAY)
        Imgproc.equalizeHist(greyFrame, greyFrame)

        faceCascade.detectMultiScale(greyFrame, faces)

        faces.toList().forEach {
            mouthCascade.detectMultiScale(greyFrame.submat(it), mouth)
            startValue = it.y
        }

        mouth.toList().forEach {
            if (lowestMouth == null) lowestMouth = it
            else if (it.y < lowestMouth!!.y) lowestMouth = it
        }

        return Pair(lowestMouth, startValue)
    }

    /**
     * Load the CascadeClassifiers with the files
     *
     * @return if all the files successfully loaded
     */
    fun loadCascades(): Boolean {
        if (!faceCascade.load(faceCascadeName)) {
            println("Failed to load faceCascade with {$faceCascadeName}")
            return false
        }
        if (!mouthCascade.load(mouthCascadeName)) {
            println("Failed to load eyeCascade with {$mouthCascadeName}")
            return false
        }

        return true
    }
}
