import org.opencv.core.Core

/**
 * @author Matthew Sklar
 * @version 0.1.0
 * @since 6/18/2016
 */

fun main(args: Array<String>) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME) //opencv_java2413

    var faceDataProcessor: FaceDataProcesser = FaceDataProcesser()
    faceDataProcessor.run()
}