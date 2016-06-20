import org.opencv.core.Core

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/18/2016
 */

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME) //opencv_java2413

    val faceDetector: FaceDetector = FaceDetector()
    faceDetector.loadCascades()

    val setup: Setup = Setup()
    setup.calibratePosture(faceDetector)

    val faceDataProcessor: FaceDataProcessor = FaceDataProcessor(faceDetector, setup)
    faceDataProcessor.run()
}