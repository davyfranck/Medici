package com.example.Medici;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ColourSchemeGenerator extends AppCompatActivity {

    //Initialises user interface elements
    private ImageView originalColourSwatch1;
    private ImageView originalColourSwatch2;
    private ImageView originalColourSwatch3;
    private ImageView complementaryColourSwatch;
    private ImageView triadicColourSwatch1;
    private ImageView triadicColourSwatch2;
    private ImageView tetradicColourSwatch1;
    private ImageView tetradicColourSwatch2;
    private ImageView tetradicColourSwatch3;
    private Button backButton;
    private Button backButton2;
    //Initialises hexadecimal colour code variables for the various colour schemes
    static String originalColourHexCode;
    static String complementaryHexCode;
    static String triadicHexCode1;
    static String triadicHexCode2;
    static String tetradicHexCode1;
    static String tetradicHexCode2;
    static String tetradicHexCode3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_colour_scheme_generator);

        //Initialises XML elements for GUI layout
        backButton = (Button) findViewById(R.id.backbutton);
        backButton2 = (Button) findViewById(R.id.backbutton2);
        originalColourSwatch1 = (ImageView) findViewById(R.id.colouroriginal);
        originalColourSwatch2 = (ImageView) findViewById(R.id.colouroriginal2);
        originalColourSwatch3 = (ImageView) findViewById(R.id.colouroriginal3);
        complementaryColourSwatch = (ImageView) findViewById(R.id.colourcompliment);
        triadicColourSwatch1 = (ImageView) findViewById(R.id.colourtriadic1);
        triadicColourSwatch2 = (ImageView) findViewById(R.id.colourtriadic2);
        tetradicColourSwatch1 = (ImageView) findViewById(R.id.colourtetradic1);
        tetradicColourSwatch2 = (ImageView) findViewById(R.id.colourtetradic2);
        tetradicColourSwatch3 = (ImageView) findViewById(R.id.colourtetradic3);

        Intent intent = getIntent();

        //Receives the hexadecimal colour code from the PaintSummary activity
        originalColourHexCode = intent.getExtras().getString("ColourCode");

        //Receives the paint name from the PaintSummary activity
        String matchingPaintName = intent.getExtras().getString("PaintName");

        //Receives the paint manufacturer from the PaintSummary activity
        String matchingPaintManufacturer = intent.getExtras().getString("PaintManufacturer");
        TextView paintTitleTextView = (TextView)findViewById(R.id.painttitle);

        //Transforms the title of the paint on the GUI to the paint's manufacturer and paint's name to provide feedback to the user
        paintTitleTextView.setText(String.valueOf(matchingPaintManufacturer) + "\n" + String.valueOf(matchingPaintName));

        //Transforms the Medici Swatch drawable on the GUI to the decoded hexadecimal colour code to provide feedback to the user
        GradientDrawable gradientDrawable_originalColour1 = (GradientDrawable) originalColourSwatch1.getBackground().mutate();
        gradientDrawable_originalColour1.setColor(Color.parseColor(originalColourHexCode));

        //Transforms the Medici Swatch drawable on the GUI to the decoded hexadecimal colour code to provide feedback to the user
        GradientDrawable gradientDrawable_originalColour2 = (GradientDrawable) originalColourSwatch2.getBackground().mutate();
        gradientDrawable_originalColour2.setColor(Color.parseColor(originalColourHexCode));

        //Transforms the Medici Swatch drawable on the GUI to the decoded hexadecimal colour code to provide feedback to the user
        GradientDrawable gradientDrawable_originalColour3 = (GradientDrawable) originalColourSwatch3.getBackground().mutate();
        gradientDrawable_originalColour3.setColor(Color.parseColor(originalColourHexCode));

        int red = Integer.valueOf(originalColourHexCode.substring(1, 3), 16);
        int green = Integer.valueOf(originalColourHexCode.substring(3, 5), 16);
        int blue = Integer.valueOf(originalColourHexCode.substring(5, 7), 16);

        //Truncates the red, green and blue values by only capturing the last 8 numbers and discards the numbers preceding this. Required for transforming RGB colours into hexadecimal colours
        red = (red) & 0xff;
        green = (green) & 0xff;
        blue = (blue) & 0xff;

        //Uses the bitwise lef shift operator to shift the colours back to their original place - the purpose is to accomplish interpolation between colours to prime them for manipulation to HSV
        int rgbColourCode = ((red)<<16)|((green)<<8)|((blue));

        //Invokes the method getComplementaryColour() and passes the encoded RGB colour code
        getComplementaryColour(rgbColourCode);

        //Transforms the complementary colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_complimentaryColour = (GradientDrawable) complementaryColourSwatch.getBackground().mutate();
        gradientDrawable_complimentaryColour.setColor(Color.parseColor(complementaryHexCode));

        //Invokes the method getTriadicColours() and passes the encoded RGB colour code
        getTriadicColours(rgbColourCode);

        //Transforms the triadic colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_triadicColour1 = (GradientDrawable) triadicColourSwatch1.getBackground().mutate();
        gradientDrawable_triadicColour1.setColor(Color.parseColor(triadicHexCode1));

        //Transforms the triadic colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_triadicColour2 = (GradientDrawable) triadicColourSwatch2.getBackground().mutate();
        gradientDrawable_triadicColour2.setColor(Color.parseColor(triadicHexCode2));

        //Invokes the method getTetradicColours() and passes the encoded RGB colour code
        getTetradicColours(rgbColourCode);

        //Transforms the tetradic colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_tetradicColour1 = (GradientDrawable) tetradicColourSwatch1.getBackground().mutate();
        gradientDrawable_tetradicColour1.setColor(Color.parseColor(tetradicHexCode1));

        //Transforms the tetradic colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_tetradicColour2 = (GradientDrawable) tetradicColourSwatch2.getBackground().mutate();
        gradientDrawable_tetradicColour2.setColor(Color.parseColor(tetradicHexCode2));

        //Transforms the tetradic colour swatch drawable on the GUI to the decoded HSV colour code to provide feedback to the user
        GradientDrawable gradientDrawable_tetradicColour3 = (GradientDrawable) tetradicColourSwatch3.getBackground().mutate();
        gradientDrawable_tetradicColour3.setColor(Color.parseColor(tetradicHexCode3));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the user back to the PaintSummary activity screen
                finish();
            }
        });
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the user back to the PaintSummary activity screen
                finish();
            }
        });
        originalColourSwatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(originalColourHexCode);
            }
        });
        originalColourSwatch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(originalColourHexCode);
            }
        });
        originalColourSwatch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(originalColourHexCode);
            }
        });
        complementaryColourSwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(complementaryHexCode);
            }
        });
        triadicColourSwatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(triadicHexCode1);
            }
        });
        triadicColourSwatch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(triadicHexCode2);
            }
        });
        tetradicColourSwatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(tetradicHexCode1);
            }
        });
        tetradicColourSwatch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(tetradicHexCode2);
            }
        });
        tetradicColourSwatch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2(tetradicHexCode3);
            }
        });
    }

    public void openActivity2(String hexCode) {

        //Decodes the hexadecimal colour code into the RGB colour space and provides separate, red, green and blue values
        int complementaryColorRGB = Integer.parseInt(hexCode.substring(1, 7), 16);
        int r = (complementaryColorRGB >> 16) & 0xFF;
        int g = (complementaryColorRGB >> 8) & 0xFF;
        int b = (complementaryColorRGB >> 0) & 0xFF;

        //Explicit Intent to call the PaintSummary activity and navigate the user to the Paint Summary
        Intent i = new Intent(this, PaintSummary.class);
        //Passes the separate red, green and blue values of the colour swatch
        i.putExtra("red", r);
        i.putExtra("green", g);
        i.putExtra("blue", b);
        startActivity(i);

    }

    public static int getComplementaryColour(int colourToDecode){
        //Initialises a float array to store float data type values relating to the colour in HSV colour space; [0] = hue, [1] = saturation and [2] = value.
        float[] complementaryHSVColourCode1 = new float[3];

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToDecode), Color.green(colourToDecode), Color.blue(colourToDecode), complementaryHSVColourCode1);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 180 degrees to achieve the colour's complement (or direct opposite) while retaining its saturation and value ratings
        complementaryHSVColourCode1[0] = (complementaryHSVColourCode1[0] + 180) % 360;

        //Converts HSV components from the complementaryHSVColourCode1[] to an ARGB colour
        int complementaryRGB1 = Color.HSVToColor(complementaryHSVColourCode1);

        //Extracts the red, green and blue properties of the ARGB colour
        int red = Color.red(complementaryRGB1);
        int green = Color.green(complementaryRGB1);
        int blue = Color.blue(complementaryRGB1);

        //Invokes the method getHexStringForRGB() and passes the encoded complementaryRGB1 colour code for decoding into hexadecimal
        complementaryHexCode = getHexStringForRGB(Color.rgb(red, green, blue));
        return Color.HSVToColor(complementaryHSVColourCode1);
    }

    public static int getTriadicColours(int colourToInvert){
        //Initialises float arrays to store float data type values relating to the colours in HSV colour space; [0] = hue, [1] = saturation and [2] = value.
        float[] triadicHSVColourCode1 = new float[3];
        float[] triadicHSVColourCode2 = new float[3];

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToInvert), Color.green(colourToInvert), Color.blue(colourToInvert), triadicHSVColourCode1);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 120 degrees to achieve the colour's first triadic colour while retaining its saturation and value ratings
        triadicHSVColourCode1[0] = (triadicHSVColourCode1[0] + 120) % 360;

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToInvert), Color.green(colourToInvert), Color.blue(colourToInvert), triadicHSVColourCode2);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 240 degrees to achieve the colour's second triadic colour while retaining its saturation and value ratings
        triadicHSVColourCode2[0] = (triadicHSVColourCode2[0] + 240) % 360;

        //Converts HSV components from the triadicHSVColourCode1[] to ARGB colours
        int triadicRGB1 = Color.HSVToColor(triadicHSVColourCode1);
        int triadicRGB2 = Color.HSVToColor(triadicHSVColourCode2);

        //Extracts the red, green and blue properties of the triadicRGB1 ARGB colour
        int red = Color.red(triadicRGB1);
        int green = Color.green(triadicRGB1);
        int blue = Color.blue(triadicRGB1);

        //Invokes the method getHexStringForRGB() and passes the encoded triadicRGB1 colour code for decoding into hexadecimal
        triadicHexCode1 = getHexStringForRGB(Color.rgb(red, green, blue));

        //Extracts the red, green and blue properties of the triadicRGB2 ARGB colour
        red = Color.red(triadicRGB2);
        green = Color.green(triadicRGB2);
        blue = Color.blue(triadicRGB2);

        //Invokes the method getHexStringForRGB() and passes the encoded triadicRGB2 colour code for decoding into hexadecimal
        triadicHexCode2 = getHexStringForRGB(Color.rgb(red, green, blue));

        return Color.HSVToColor(triadicHSVColourCode1);
    }

    public static int getTetradicColours(int colourToInvert){
        //Initialises float arrays to store float data type values relating to the colours in HSV colour space; [0] = hue, [1] = saturation and [2] = value.
        float[] tetradicHSVColourCode1 = new float[3];
        float[] tetradicHSVColourCode2 = new float[3];
        float[] tetradicHSVColourCode3 = new float[3];

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToInvert), Color.green(colourToInvert), Color.blue(colourToInvert), tetradicHSVColourCode1);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 90 degrees to achieve the colour's first tetradic colour while retaining its saturation and value ratings
        tetradicHSVColourCode1[0] = (tetradicHSVColourCode1[0] + 90) % 360;

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToInvert), Color.green(colourToInvert), Color.blue(colourToInvert), tetradicHSVColourCode2);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 90 degrees to achieve the colour's second tetradic colour while retaining its saturation and value ratings
        tetradicHSVColourCode2[0] = (tetradicHSVColourCode2[0] + 180) % 360;

        //Converts RGB components (red, green and blue properties from the colourToDecode variable) into HSV colour space
        Color.RGBToHSV(Color.red(colourToInvert), Color.green(colourToInvert), Color.blue(colourToInvert), tetradicHSVColourCode3);

        //Modifies the hue value (complementaryHSVColourCode1[0]) by 90 degrees to achieve the colour's third tetradic colour while retaining its saturation and value ratings
        tetradicHSVColourCode3[0] = (tetradicHSVColourCode3[0] + 270) % 360;

        //Converts HSV components from the tetradicHSVColourCode1[] to ARGB colours
        int tetradicRGB1 = Color.HSVToColor(tetradicHSVColourCode1);
        int tetradicRGB2 = Color.HSVToColor(tetradicHSVColourCode2);
        int tetradicRGB3 = Color.HSVToColor(tetradicHSVColourCode3);

        //Extracts the red, green and blue properties of the tetradicRGB1 ARGB colour
        int red = Color.red(tetradicRGB1);
        int green = Color.green(tetradicRGB1);
        int blue = Color.blue(tetradicRGB1);

        //Invokes the method getHexStringForRGB() and passes the encoded tetradicRGB1 colour code for decoding into hexadecimal
        tetradicHexCode1 = getHexStringForRGB(Color.rgb(red, green, blue));

        //Extracts the red, green and blue properties of the tetradicRGB2 ARGB colour
        red = Color.red(tetradicRGB2);
        green = Color.green(tetradicRGB2);
        blue = Color.blue(tetradicRGB2);

        //Invokes the method getHexStringForRGB() and passes the encoded tetradicRGB2 colour code for decoding into hexadecimal
        tetradicHexCode2 = getHexStringForRGB(Color.rgb(red, green, blue));

        //Extracts the red, green and blue properties of the tetradicRGB3 ARGB colour
        red = Color.red(tetradicRGB3);
        green = Color.green(tetradicRGB3);
        blue = Color.blue(tetradicRGB3);

        //Invokes the method getHexStringForRGB() and passes the encoded tetradicRGB3 colour code for decoding into hexadecimal
        tetradicHexCode3 = getHexStringForRGB(Color.rgb(red, green, blue));

        return Color.HSVToColor(tetradicHSVColourCode1);
    }

    public static String getHexStringForRGB(int encodedRGBColorCode) {
        //Assembles the decoded hexadecimal colour code by first placing a leading number sign # and then the hexadecimal notation for red then green then blue - which comprises the hexadecimal colour code of the colour
        String decodedHexColourCode = String.format("#%02X%02X%02X", Color.red(encodedRGBColorCode), Color.green(encodedRGBColorCode), Color.blue(encodedRGBColorCode));
        return decodedHexColourCode;

    }

}
