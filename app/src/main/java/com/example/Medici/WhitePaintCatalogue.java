package com.example.Medici;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class WhitePaintCatalogue extends AppCompatActivity {

    //Initialises a hexadecimal colour code variable
    String hexCode = "#FFFFFF";

    //Initialises user interface elements
    private ImageView image;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_black_paint_catalogue);
        backButton = (Button) findViewById(R.id.backbutton);

        try {
            //Calls the paintCollector() function
            paintCollector();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the user back to the PaintColor activity screen
                finish();
            }
        });
    }
    public void paintCollector() throws IOException {

        //Reads data from the CSV Paint Data Repository in the form of a character-input stream
        InputStreamReader is = new InputStreamReader(getAssets().open("paint_data.csv"));

        //Reads text from the character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
        BufferedReader reader = new BufferedReader(is);

        //Creates a String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached
        reader.readLine();
        String thisLine;
        int count=0;

        //Reads the entire String and arranges it into an ArrayList
        List<String[]> lines = new ArrayList<String[]>();
        while ((thisLine = reader.readLine()) != null) {
            //Searches for instances where WhiteIdentifier exists in a row, and adds it to the csvLines while excluding everything else - thus only capturing white paints
            if (thisLine.contains("WhiteIdentifier")){
                //Uses semi-colon delimiter to recognise columns and rows of data
                lines.add(thisLine.split(";"));
            }
        }

        //Forces a substring to copy to a new underlying line array
        String[][] whitePaintsArrayCSV = new String[lines.size()][0];
        lines.toArray(whitePaintsArrayCSV);

        //Initialises XML elements for GUI layout
        TableLayout layout = (TableLayout) findViewById(R.id.cataloguetable);

        //Subjects the application to a control flow loop that runs for the length of the whitePaintsArrayCSV array
        for(int i=0;i< whitePaintsArrayCSV.length;i++)
        {
            //Creates a conversion for use of setting XML elements to dp (display pixels) instead of px (pixels) to handle different sized screens
            final float scale = this.getResources().getDisplayMetrics().density;
            int height_ = (int) (150 * scale + 0.5f);
            int width_ = (int) (150 * scale + 0.5f);

            //Programmatically creates a linear layout for each paint in the catalogue
            LinearLayout paintLinearLayout = new LinearLayout(this);
            paintLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height_));
            paintLinearLayout.setPadding(0,0,0,0);

            //Initialises a variable as the current element of third column of the whitePaintsArrayCSV array
            int red = Integer.parseInt(whitePaintsArrayCSV[i][2]);
            //Initialises a variable as the current element of fourth column of the whitePaintsArrayCSV array
            int green = Integer.parseInt(whitePaintsArrayCSV[i][3]);
            //Initialises a variable as the current element of fifth column of the whitePaintsArrayCSV array
            int blue = Integer.parseInt(whitePaintsArrayCSV[i][4]);

            //Truncates the red, green and blue values by only capturing the last 8 numbers and discards the numbers preceding this. Required for transforming RGB colours into hexadecimal colours
            red = (red) & 0xff;
            green = (green) & 0xff;
            blue = (blue) & 0xff;

            //Calls getHexFromRGB() method and passes the red, green and blue values for encoding into hexadecimal
            hexCode = getHexFromRGB(Color.rgb(red, green, blue));

            //Programmatically creates an image view for each paint in the catalogue
            ImageView image = new ImageView(this);
            image.setLayoutParams(new ViewGroup.LayoutParams(width_, height_));
            //Sets the background colour of the image view to the paint's hexadecimal colour code
            image.setBackgroundColor(Color.parseColor(hexCode));

            //Programmatically creates a text view for each paint in the catalogue
            TextView textViewName = new TextView(this);
            textViewName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //Sets the text in the text view to the paint's name, paint manufacturer and hexadecimal colour code
            textViewName.setText(whitePaintsArrayCSV[i][1] + " \n" + whitePaintsArrayCSV[i][5] + " \n" + hexCode);
            textViewName.setTextSize(16);

            //Programmatically sets an alternating shade of grey and black for each paint text view, to provide greater visibility to the user
            if (i % 2 == 0) {
                textViewName.setBackgroundColor(Color.parseColor("#111111"));
            } else {
                textViewName.setBackgroundColor(Color.parseColor("#060606"));
            }

            //Programmatically sets the colour of the text to the Medici Paint Companion's shade of gold to maintain design consistency while setting the size and location in the text view
            textViewName.setTextColor(Color.parseColor("#FBE5A1"));
            textViewName.setTextSize(16);
            textViewName.setGravity(Gravity.TOP);
            textViewName.setPadding(15,15,90,15);

            //Converts the current element in the whitePaintsArrayCSV array into integer, and intialises the redPass variable
            final int redPass = Integer.parseInt(whitePaintsArrayCSV[i][2]);
            //Converts the current element in the whitePaintsArrayCSV array into integer, and intialises the greenPass variable
            final int greenPass = Integer.parseInt(whitePaintsArrayCSV[i][3]);
            //Converts the current element in the whitePaintsArrayCSV array into integer, and intialises the bluePass variable
            final int bluePass = Integer.parseInt(whitePaintsArrayCSV[i][4]);

            //Registers a callback to be invoked when the paintLinearLayout is clicked
            paintLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Explicit Intent to call the PaintSummary activity and navigate the user to the Paint Summary screen of the user's chosen paint
                    Intent intent = new Intent(WhitePaintCatalogue.this, PaintSummary.class);
                    //Passes the paint's red, green and blue colour codes
                    intent.putExtra("red", redPass);
                    intent.putExtra("green", greenPass);
                    intent.putExtra("blue", bluePass);
                    startActivity(intent);
                }
            });

            //Assembles and adds the various programmatically created design elements to the layout
            layout.addView(paintLinearLayout);
            paintLinearLayout.addView(image);
            paintLinearLayout.addView(textViewName);
        }
    }


    public static String getHexFromRGB(int encodedRGBColorCode) {
        //Assembles the decoded hexadecimal colour code by first placing a leading number sign # and then the hexadecimal notation for red then green then blue - which comprises the hexadecimal colour code of the colour
        String decodedHexColourCode = String.format("#%02X%02X%02X", Color.red(encodedRGBColorCode), Color.green(encodedRGBColorCode), Color.blue(encodedRGBColorCode));
        return decodedHexColourCode;
    }

}