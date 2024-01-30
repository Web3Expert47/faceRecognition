package com.ivanskodje.service;

import java.util.Arrays;
import java.util.Comparator;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class GlassesOverlay {

    String _haarcascade_path;

    // public GlassesOverlay() {

    // }

    static {
        System.out.printf("java.library.path: %s%n", System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Point[] getEyePoints(String imagePath) {

        String haarcascade_frontalface_default = this._haarcascade_path + "haarcascade_frontalface_default.xml";
        String haarcascade_eye = this._haarcascade_path + "haarcascade_eye.xml";
        // Load the Haar cascades files for face and eye detection
        CascadeClassifier faceCascade = new CascadeClassifier(haarcascade_frontalface_default);
        CascadeClassifier eyeCascade = new CascadeClassifier(haarcascade_eye);

        // Read the image
        Mat image = Imgcodecs.imread(imagePath);

        // Convert to grayscale for detection
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(grayImage, faces);

        Point[] res = new Point[2];
        for (Rect face : faces.toArray()) {
            // Define ROI for the face
            Mat faceROI = grayImage.submat(face);

            // Detect eyes within face ROI
            MatOfRect eyes = new MatOfRect();
            eyeCascade.detectMultiScale(faceROI, eyes);

            // Sort eyes by x-coordinate so left is first
            Rect[] eyesArray = eyes.toArray();
            Arrays.sort(eyesArray, Comparator.comparingInt(r -> r.x));

            // Assume the first two detected eyes are the actual eyes
            if (eyesArray.length >= 2) {
                Point eyeLeftCenter = new Point(face.x + eyesArray[0].x + eyesArray[0].width / 2,
                        face.y + eyesArray[0].y + eyesArray[0].height / 2);
                Point eyeRightCenter = new Point(face.x + eyesArray[1].x + eyesArray[1].width / 2,
                        face.y + eyesArray[1].y + eyesArray[1].height / 2);
                res[0] = eyeLeftCenter;
                res[1] = eyeRightCenter;

                // Draw circles on the centers of the eyes
                Imgproc.circle(image, eyeLeftCenter, 5, new Scalar(255, 0, 0), -1);
                Imgproc.circle(image, eyeRightCenter, 5, new Scalar(255, 0, 0), -1);
            }
        }

        // Save or display the output image with detected eyes
        Imgcodecs.imwrite("output_with_eyes_detected.jpg", image);
        return res;
    }

    public void addGlassesToFace(String faceImagePath, String glassesImagePath, Point eyeLeft, Point eyeRight) {
        _haarcascade_path = "D:\\workspace\\java\\face_recognition\\javafx-spring-boot\\src\\main\\resources\\opencv\\sources\\data\\haarcascades\\";
        System.out.println("construct loading: =====================>" + _haarcascade_path);

        // Load the images
        Mat faceImage = Imgcodecs.imread(faceImagePath);
        Mat glassesImage = Imgcodecs.imread(glassesImagePath, Imgcodecs.IMREAD_UNCHANGED);

        // Calculate the position to place the glasses based on eye coordinates.
        Point glassesPosition = calculateGlassesPosition(eyeLeft, eyeRight);

        // Resize glassesImage if necessary
        Mat resizedGlassesImage = resizeGlassesImage(glassesImage, eyeLeft, eyeRight);

        // Overlay the glasses on the face image
        overlayImages(faceImage, resizedGlassesImage, glassesPosition);

        // Save or display the result
        Imgcodecs.imwrite("face_with_glasses.jpg", faceImage);
    }

    public void addGlassesToFace(Mat frame, Mat glassesImage, String faceCascadePath, String eyesCascadePath) {
        // Load the cascade classifiers for face and eyes detection
        CascadeClassifier faceCascade = new CascadeClassifier(faceCascadePath);
        CascadeClassifier eyesCascade = new CascadeClassifier(eyesCascadePath);

        // Detect faces
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(frame, faces);

        for (Rect face : faces.toArray()) {
            // Region of interest for the eyes detection within the face
            Mat faceROI = frame.submat(face);

            // Detect eyes
            MatOfRect eyes = new MatOfRect();
            eyesCascade.detectMultiScale(faceROI, eyes);

            // Assuming that we found two eyes
            if (eyes.toArray().length == 2) {
                Rect eye1 = eyes.toArray()[0];
                Rect eye2 = eyes.toArray()[1];

                // Coordinates for the left and right eye
                Point eyeLeft = eye1.tl().x < eye2.tl().x ? eye1.tl() : eye2.tl();
                Point eyeRight = eye1.br().x > eye2.br().x ? eye1.br() : eye2.br();

                // Now use these points to position and scale your glasses image
                // ... (code to overlay glasses based on eyeLeft and eyeRight)
            }
        }
    }

    private Point calculateGlassesPosition(Point eyeLeft, Point eyeRight) {
        // Calculate the position based on the eye coordinates
        // This is simplified; actual calculation should consider the size of the
        // glasses and the distance between the eyes
        return new Point((eyeLeft.x + eyeRight.x) / 2, (eyeLeft.y + eyeRight.y) / 2);
    }

    private Mat resizeGlassesImage(Mat glassesImage, Point eyeLeft, Point eyeRight) {
        // Here you would add your logic to resize the glasses image according to the
        // distance between eyes
        // For simplification in this example, we assume it's already the correct size
        return glassesImage;
    }

    private void overlayImages(Mat faceImage, Mat glassesImage, Point position) {
        // This function needs to blend the glasses image onto the face image at the
        // given position
        // Handling transparency and blending is needed but omitted for simplicity
    }
}