package com.example.Medici;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class ColourDetector extends AppCompatActivity {

    //Initialises RGB colour code variables
    public int red = 0;
    public int green = 0;
    public int blue = 0;
    //Initialises hexadecimal colour code variable
    String hexColourCode = "#FFFFFF";
    //Initialises user interface elements
    private ImageView mediciSwatch;
    private ImageView mediciCapturedImage;
    private TextView hexText;
    private TextView redText;
    private TextView greenText;
    private TextView blueText;
    private Button backButton;
    private Button forwardbutton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_colour_detector);
        //Initialises XML elements for GUI layout
        mediciCapturedImage = (ImageView) findViewById(R.id.image);
        mediciSwatch = (ImageView) findViewById(R.id.swatch);
        hexText = (TextView) findViewById(R.id.hex);
        redText = (TextView) findViewById(R.id.redRGB);
        greenText = (TextView) findViewById(R.id.greenRGB);
        blueText = (TextView) findViewById(R.id.blueRGB);
        backButton = (Button) findViewById(R.id.backbutton);
        forwardbutton = (Button) findViewById(R.id.forwardbutton);
        //Retrieves data from the MediciCamera activity, specifically the captured mediciCapturedImage
        File mediciImage = new File(getIntent().getStringExtra("FILE"));
        //Decodes data into bitmap
        Bitmap mediciBitmap = BitmapFactory.decodeFile(mediciImage.getAbsolutePath());
        if (mediciBitmap.getWidth() > mediciBitmap.getHeight()) {
            //Rotates captured mediciCapturedImage to portrait, as it is displayed in landscape mode by default
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            //Returns a bitmap from subset of the source bitmap, transformed by the optional matrix. It is initialized with the same density and color space as the original bitmap.
            mediciBitmap = Bitmap.createBitmap(mediciBitmap , 0, 0, mediciBitmap.getWidth(), mediciBitmap.getHeight(), matrix, true);
            mediciCapturedImage.setImageBitmap(mediciBitmap);
        } else {
            mediciCapturedImage.setImageBitmap(mediciBitmap);
        }

        //Registers a callback to be invoked when the previously captured (and now displayed) mediciCapturedImage is touched
        mediciCapturedImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                // TODO Auto-generated method stub

                //Captures the X and Y coordinates of the image where the user touched
                final int motionEventX = (int) motionEvent.getX();
                final int motionEventY = (int) motionEvent.getY();

                //Builds a drawing cache before inserting the image, otherwise it will provide a blank bitmap
                mediciCapturedImage.setDrawingCacheEnabled(true);
                Bitmap mediciCameraImageBitmap = Bitmap.createBitmap(mediciCapturedImage.getDrawingCache());
                mediciCapturedImage.setDrawingCacheEnabled(false);

                try {
                    //Points to the location of the image based on the X and Y coordinates
                    int pixelCoordinate = mediciCameraImageBitmap.getPixel(motionEventX,motionEventY);

                    //Identifies the red, green and blue values from the X and Y coordinate
                    red = Color.red(pixelCoordinate);
                    green = Color.green(pixelCoordinate);
                    blue = Color.blue(pixelCoordinate);

                    //Truncates the red, green and blue values by only capturing the last 8 numbers and discards the numbers preceding this. Required for transforming RGB colours into hexadecimal colours
                    red = (red) & 0xff;
                    green = (green) & 0xff;
                    blue = (blue) & 0xff;

                    //Calls getHexFromRGB() method and passes the red, green and blue values for encoding into hexadecimal
                    hexColourCode = getHexFromRGB(Color.rgb(red, green, blue));

                    //Invokes the method changeColour() and passes the encoded hexadecimal colour code
                    changeColour(hexColourCode, red, green, blue);
                    return false;

                }catch (Exception ignore){
                }

                //Allows the system to reclaim memory for the bitmap - specifically if the Colour Detector Activity is accessed again with a new image
                mediciCameraImageBitmap.recycle();
                return true;
            }
        });

        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        //Registers a callback to be invoked when the Forward Button is clicked
        forwardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity2();
            }
        });

        //Registers a callback to be invoked when the Medici Swatch is clicked
        mediciSwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity2();
            }
        });

    }

    public void changeColour(String updatedSwatchColour, int red, int green, int blue){
        //Transforms the Medici Swatch drawable on the GUI to the decoded hexadecimal colour code to provide feedback to the user
        GradientDrawable gradientDrawable = (GradientDrawable) mediciSwatch.getBackground().mutate();
        gradientDrawable.setColor(Color.parseColor(updatedSwatchColour));
        //Changes the text next to the Medici Swatch to the decoded hexadecimal colour code to provide feedback to the user
        hexText.setText(updatedSwatchColour);
        //Changes the text next to the Medici Swatch to the encoded red, green and blue colour properties to provide feedback to the user
        redText.setText(""+red);
        greenText.setText(""+green);
        blueText.setText(""+blue);
    }

    public static String getHexFromRGB(int encodedRGBColorCode) {
        //Assembles the decoded hexadecimal colour code by first placing a leading number sign # and then the hexadecimal notation for red then green then blue - which comprises the hexadecimal colour code of the colour
        String decodedHexColourCode = String.format("#%02X%02X%02X", Color.red(encodedRGBColorCode), Color.green(encodedRGBColorCode), Color.blue(encodedRGBColorCode));
        return decodedHexColourCode;
    }

    public void openActivity() {
        //Explicit Intent to call the MediciCamera activity and navigate the user to the Medici Camera
        Intent intent = new Intent(this, MediciCamera.class);
        startActivity(intent);
    }

    public void openActivity2() {
        //Explicit Intent to call the PaintSummary activity and navigate the user to the Paint Summary
        Intent i = new Intent(this, PaintSummary.class);
        //Passes the separate red, green and blue values of the identified colour
        i.putExtra("red", red);
        i.putExtra("green", green);
        i.putExtra("blue", blue);
        startActivity(i);

    }

}
