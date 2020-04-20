package com.example.Medici;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.os.Build;
import android.os.Bundle;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class MediciCamera extends Activity {

    //Initialises user interface elements
    private Button backButton;
    private ImageView mediciSwatch;
    private Button cameraButton;
    private FrameLayout mediciCameraFrameLayout;
    //InitialisesCamera API elements
    private Camera mediciCamera = null;
    private static SurfaceHolder mediciCameraHolder;
    private static CameraPreview mediciCameraPreview;
    private static OrientationEventListener orientationEventListener = null;
    private static boolean mediciCameraFocusMode;
    //Initialises variables
    public static final int MEDICI_IMAGE_TYPE = 1;
    private boolean isCameraInitialised;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_medici_camera);
        //Initialises XML elements for GUI layout
        mediciSwatch = (ImageView) findViewById(R.id.swatch);
        mediciCameraFrameLayout = (FrameLayout) findViewById(R.id.camera_preview);
        cameraButton = (Button) findViewById(R.id.captureButton);
        backButton = (Button) findViewById(R.id.backbutton);
        //Registers a callback to be invoked when the Camera Button is clicked
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Triggers an asynchronous image capture. The camera service will initiate a series of callbacks to the application as the image capture progresses.
                mediciCamera.takePicture(null, null, mediciPicture);
            }
        });
        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls openActivity() method
                openActivity();
            }
        });
    }

    private Camera.PictureCallback mediciPicture = new Camera.PictureCallback() {

        @Override
        //Called when image data is available after a picture is taken with the Camera Button
        public void onPictureTaken(byte[] data, Camera camera) {

            //Pass the Medici Picture file to be written to the device
            File mediciPicture = getOutputMediaFile(MEDICI_IMAGE_TYPE);

            //Provides error messages if the Medici Picture does not exist
            if (mediciPicture == null) {
                Log.d("Medici Camera ERROR",
                        "Error creating media file, check storage permissions: ");
                return;
            }
            Log.i("Medici Picture", mediciPicture.toString());
            try {

                //Writes bytes from the specified byte array
                final ImageView imageView = (ImageView) findViewById(R.id.imageView);
                FileOutputStream mediciFOS = new FileOutputStream(mediciPicture);
                mediciFOS.write(data);
                mediciFOS.close();
                BitmapFactory.Options scalingOptions = new BitmapFactory.Options();
                scalingOptions.inSampleSize = camera.getParameters().getPictureSize().width / imageView.getMeasuredWidth();
                final Bitmap mediciBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, scalingOptions);

            }
            //Provides error messages if the Medici Picture does not exist
            catch (FileNotFoundException e) {
                Log.d("Medici Camera ERROR", "Filepath not found: " + e.getMessage());
            }
            //Provides error messages if the Medici Picture does not exist
            catch (IOException e) {
                Log.d("Medici Camera ERROR", "Error accessing filepath: " + e.getMessage());
            }
            //Indicates to the system that this activity ought to be finished
            finish();
            //Calls openColourDetectorActivity() method
            openColourDetectorActivity(mediciPicture);
        }
    };

    private static File getOutputMediaFile(int type) {

        //Returns the absolute path to the directory on the external storage device where the Medici Paint Companion can place persistent files it creates
        File mediciStorageDirectory = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
                "Medici");
        //Determines if the Medici storage directory exists
        if (!mediciStorageDirectory.exists()) {
            if (!mediciStorageDirectory.mkdirs()) {
                Log.i("Medici", "failed to create directory");
                return null;
            }
        }
        //Formulates and parses dates to the captured image
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
        File mediciMediaFile;
        //Grants the image a uniquee name
        if (type == MEDICI_IMAGE_TYPE) {
            mediciMediaFile = new File(mediciStorageDirectory.getPath() + File.separator
                    + "Medici_IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        //Returns the mediciMediaFile to the onPictureTaken method
        return mediciMediaFile;
    }


    public void openColourDetectorActivity(File file) {
        //Explicit Intent to call the ColourDetector and navigate the user to the Colour Detector Screen
        Intent intent = new Intent(this, ColourDetector.class);
        //Pass the captured image via Intent to the ColourDetector
        intent.putExtra("FILE", file.getPath());
        startActivity(intent);
    }

    private static final String[] MEDICIPERMISSIONS = {
            //Request Camera permissions through Android dialog box
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_PERMISSIONS = 10;

    private static final int PERMISSIONS_COUNT = 1;

    @SuppressLint("NewApi")
    private boolean permissionsDenied(){
        //Grant permissions to Camera and Storage
        for(int i = 0; i < PERMISSIONS_COUNT; i++){
            if(checkSelfPermission(MEDICIPERMISSIONS[i])!=PackageManager.PERMISSION_GRANTED){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume(){
        //Called after OnRestoreInstanceState(Bundle), OnRestart(), or OnPause(), for your activity to start interacting with the user
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionsDenied()){
            //Notification channels were added in Android 8.0, anc attempting to call these on devices older than Android 8.0 would result in a crash. This checks that the device's SDK is newer than 8.0
            requestPermissions(MEDICIPERMISSIONS, REQUEST_PERMISSIONS);
            return;
        }

        if (!isCameraInitialised){
            //Intiialises camera and assigns its state to mediciCamera
            mediciCamera = Camera.open();
            //Returns the current settings for this Camera service.
            Camera.Parameters params = mediciCamera.getParameters();
            //Creates a camera preview class that extends SurfaceView and implements the SurfaceHolder interface. This class previews the live images from the camera.
            mediciCameraPreview = new CameraPreview(this, mediciCamera);
            //Associates the Medici Camera with its respective XML file for design layout
            mediciCameraFrameLayout = findViewById(R.id.camera_preview);
            //Adds the Medici Camera Preview to the layout
            mediciCameraFrameLayout.addView(mediciCameraPreview);
            orientationEventListener = new OrientationEventListener(this) {
                //Identify when the orientation of the device has changed.
                @Override
                public void onOrientationChanged(int orientation) {

                }
            };
            orientationEventListener.enable();
            //Interface definition for a callback to be invoked when the MediciCameraFrameLayout view has been initiated
            mediciCameraFrameLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (whichCamera){
                        if(mediciCameraFocusMode){
                            //Continuous auto focus mode intended for taking pictures. The camera continuously tries to focus.
                            mediciCameraSettings.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                        }else{
                            //Sets the focus mode to auto-focus mode
                            mediciCameraSettings.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                        }
                        try{
                            //Changes the settings for this Camera service.
                            mediciCamera.setParameters(mediciCameraSettings);
                        }catch (Exception e){

                        }
                        mediciCameraFocusMode = !mediciCameraFocusMode;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    protected void onPause(){
        //The system invokes a paused state with respect to the Medici Camera
        super.onPause();
        //Calls the releaseCamera() method
        releaseCamera();

    }

    private void releaseCamera(){
        if(mediciCamera != null){
            //Disconnects and releases the Camera object resources.
            mediciCameraFrameLayout.removeView(mediciCameraPreview);
            mediciCamera.release();
            orientationEventListener.disable();
            mediciCamera = null;
            whichCamera = !whichCamera;
        }
    }

    private static boolean whichCamera = true;

    private static Camera.Parameters mediciCameraSettings;

    private static int rotation;

    private static class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

        //Initialises the interface to display the surface
        private static SurfaceHolder mediciCameraHolder;
        //Initialises the camera preview
        private static Camera mediciCameraPreview;

        //Presents a live camera preview to the user.
        private CameraPreview(Context context, Camera camera){
            super(context);
            mediciCameraPreview = camera;
            mediciCameraHolder = getHolder();
            mediciCameraHolder.addCallback(this);
            mediciCameraHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        }

        public void surfaceCreated(SurfaceHolder holder){
            //Called immediately after the Surface is created to set the display orientation to portrait
            MediciCamera.mediciCameraHolder = holder;
            mediciCameraPreview.setDisplayOrientation(90);
            try {
                //Sets the Surface to be used for the live camera preview
                this.mediciCameraPreview.setPreviewDisplay(holder);
                //Starts the live camera preview
                mediciCameraPreview.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void surfaceDestroyed(SurfaceHolder holder){
            //Destroys the Surface
            if (this.mediciCameraPreview != null) {
                this.mediciCameraPreview.setPreviewCallback(null);
                this.mediciCameraPreview.stopPreview();
                this.mediciCameraPreview.release();
                this.mediciCameraPreview = null;
            }

        }
        //This is called immediately after any structural changes have been made to the surface
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        }
    }

    public void openActivity() {
        //Explicit Intent to call the HomeScreen activity and navigate the user back to the Home Screen
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }

}

