package com.example.Medici;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PaintSummary extends AppCompatActivity {

    //Initialises heexadecimal colour code variables and received paint information
    String hexColourCode = "#FFFFFF";
    String passedPaintName;
    String passedPaintManufacturer;
    //Initialises user interface elements
    private ImageView paintSwatch;
    private Button backButton;
    private Button forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_paint_summary);

        try {
            //Calls the paintMatcher() function
            paintMatcher();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Initialises XML elements for GUI layout
        backButton = (Button) findViewById(R.id.backbutton);
        forwardButton = (Button) findViewById(R.id.forwardbutton);
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the user back to the MediciCamera activity screen
                finish();
            }
        });
    }

    public void openActivity() {
        //Explicit Intent to call the ColourSchemeGenerator activity and navigate the user to the Colour Scheme Generator
        Intent i = new Intent(this, ColourSchemeGenerator.class);
        //Passes the paint's hexadecimal colour code, name and manufacturer
        i.putExtra("ColourCode", hexColourCode);
        i.putExtra("PaintName", passedPaintName);
        i.putExtra("PaintManufacturer", passedPaintManufacturer);
        startActivity(i);
    }

    public void paintMatcher() throws IOException {

        //Initialises XML elements for GUI layout
        paintSwatch = (ImageView) findViewById(R.id.paintswatch);
        TextView paintTitleTextView = (TextView)findViewById(R.id.painttitle);
        TextView paintInformationTextView = (TextView)findViewById(R.id.paintinfo);
        TextView hexInfo = (TextView)findViewById(R.id.hexinfo);

        //Retrieves the separate red, green and blue values from the ColourDetector activity
        int scannedRed = getIntent().getIntExtra("red", 0);
        int scannedGreen = getIntent().getIntExtra("green", 0);
        int scannedBlue = getIntent().getIntExtra("blue", 0);

        //Reads data from the CSV Paint Data Repository in the form of a character-input stream
        InputStreamReader is = new InputStreamReader(getAssets().open("paint_data.csv"));

        //Reads text from the character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines.
        BufferedReader reader = new BufferedReader(is);

        //Creates a String containing the contents of the line, not including any line-termination characters, or null if the end of the stream has been reached
        reader.readLine();
        String csvLine;
        int count=0;

        //Reads the entire String and arranges it into an ArrayList
        List<String[]> csvLines = new ArrayList<String[]>();
        while ((csvLine = reader.readLine()) != null) {
            //Uses semi-colon delimiter to recognise columns and rows of data
            csvLines.add(csvLine.split(";"));
        }

        //Forces a substring to copy to a new underlying line array
        String[][] arrayCSV = new String[csvLines.size()][0];
        csvLines.toArray(arrayCSV);

        //Initialises the euclidianDistanceArray array to store double-precision 64 bits of information for a float type, and sets its length to the length of the arrayCSV
        double[] euclidianDistanceArray = new double[arrayCSV.length];
        //Initialises the matchedPaintID array to store information for integer types, and sets its length to the length of the arrayCSV
        String [] matchedPaintName = new String [arrayCSV.length];
        //Initialises the matchedPaintInfo array to store character strings (paint information), and sets its length to the length of the arrayCSV
        String [] matchedPaintInfo = new String [arrayCSV.length];
        //Initialises the matchedPaintManufacturer array to store character strings (paint manufacturer names), and sets its length to the length of the arrayCSV
        String [] matchedPaintManufacturer = new String [arrayCSV.length];
        //Initialises the matchedPaintRed array to store information for integer types (paint red properties), and sets its length to the length of the arrayCSV
        int [] matchedPaintRed = new int[arrayCSV.length];
        //Initialises the matchedPaintGreen array to store information for integer types (paint green properties), and sets its length to the length of the arrayCSV
        int [] matchedPaintGreen = new int[arrayCSV.length];
        //Initialises the matchedPaintBlue array to store information for integer types (paint blue properties), and sets its length to the length of the arrayCSV
        int [] matchedPaintBlue = new int[arrayCSV.length];

        //Subjects the application to a control flow loop that runs for the length of the arrayCSV array
        int i = 0;
        while (i < arrayCSV.length) {
            //Initialises the paintIDCSV array to store the contents of the current element in the second column of the arrayCSV array (paint name in the CSV Paint Data Repository)
            String paintName = arrayCSV[i][1];
            //Initialises the paintIDCSV array to store the contents of the current element in the third column of the arrayCSV array (paint red property in the CSV Paint Data Repository)
            String redCSV = arrayCSV[i][2];
            //Initialises the paintIDCSV array to store the contents of the current element in the fourth column of the arrayCSV array (paint green property in the CSV Paint Data Repository)
            String greenCSV = arrayCSV[i][3];
            //Initialises the paintIDCSV array to store the contents of the current element in the fifth column of the arrayCSV array (paint blue property in the CSV Paint Data Repository)
            String blueCSV = arrayCSV[i][4];
            //Initialises the paintIDCSV array to store the contents of the current element in the sixth column of the arrayCSV array (paint manufacturer name in the CSV Paint Data Repository)
            String paintManufacturer = arrayCSV[i][5];
            //Initialises the paintIDCSV array to store the contents of the current element in the seventh column of the arrayCSV array (paint information in the CSV Paint Data Repository)
            String paintInformation = arrayCSV[i][6];
            //Converts the current element in the redCSV array into integer, and intialises the paintRed variable
            int paintRed = Integer.parseInt(redCSV);
            //Converts the current element in the greenCSV array into integer, and intialises the greenRed variable
            int paintGreen = Integer.parseInt(greenCSV);
            //Converts the current element in the blueCSV array into integer, and intialises the blueRed variable
            int paintBlue = Integer.parseInt(blueCSV);
            //Creates a mean using the red property of the scanned colour and the red property of the paint in the current row of the array - used for weighting in the euclidian distance function
            int redMean = (scannedRed + paintRed) / 1;
            //Initialises a new red value called euclidianRed which is equal to the scanned red minus the current paint's red property within the arrayCSV
            int euclidianRed = scannedRed - paintRed;
            //Initialises a new green value called euclidianGreen which is equal to the scanned green minus the current paint's green property within the arrayCSV
            int euclidianGreen = scannedGreen - paintGreen;
            //Initialises a new blue value called euclidianBlue which is equal to the scanned blue minus the current paint's blue property within the arrayCSV
            int euclidianBlue = scannedBlue - paintBlue;
            //Sets the current element of the matchedPaintRed array to the recently parsed red integer
            matchedPaintRed[i] = paintRed;
            //Sets the current element of the matchedPaintGreen array to the recently parsed green integer
            matchedPaintGreen[i] = paintGreen;
            //Sets the current element of the matchedPaintBlue array to the recently parsed blue integer
            matchedPaintBlue[i] = paintBlue;
            //Sets the current element of the matchedPaintName array to the paint name
            matchedPaintName[i] = paintName;
            //Sets the current element of the matchedPaintManufacturer array to the paint manufacturer
            matchedPaintManufacturer[i] = paintManufacturer;
            //Sets the current element of the matchedPaintInfo array to the paint information
            matchedPaintInfo[i] = paintInformation;
            //Subjects the: redMean; euclidianRed; euclidianGreen; and euclidianBlue variables to the euclidian distance algorithm which approximates a closeness value between the scanned colour and current paint in the arrayCSV array. These closeness values are stored in the euclidianDistanceArray array as float types
            euclidianDistanceArray[i] = Math.sqrt((((512+redMean)*euclidianRed*euclidianRed)>>8) + 4*euclidianGreen*euclidianGreen + (((768-redMean)*euclidianBlue*euclidianBlue)>>8));
            i++;
        }

        //Initialises a variable to handle the closeness value of the euclidian distance algorithm
        double matchingPaint = euclidianDistanceArray[0];
        //Initialises a variable as the current element of the matchedPaintRed array
        int matchingPaintRed = matchedPaintRed[0];
        //Initialises a variable as the current element of the matchedPaintGreen array
        int matchingPaintGreen = matchedPaintGreen[0];
        //Initialises a variable as the current element of the matchedPaintBlue array
        int matchingPaintBlue = matchedPaintBlue[0];
        //Initialises a variable as the current element of the matchingPaintName array
        String matchingPaintName = matchedPaintName[0];
        //Initialises a variable as the current element of the matchingPaintInfo array
        String matchingPaintInfo = matchedPaintInfo[0];
        //Initialises a variable as the current element of the matchingPaintManufacturer array
        String matchingPaintManufacturer = matchedPaintManufacturer[0];

        //Subjects the application to a control flow loop that runs for the length of the euclidianDistanceArray array
        for (int x=0; x < euclidianDistanceArray.length; x++) {
            //Cross references the current element in the euclidianDistanceArray (closeness value) with the matchingPaint variable, and determines if the value is smaller
            if (euclidianDistanceArray[x] < matchingPaint) {
                //Sets the values to those of the closest matching paint to the scanned colour
                matchingPaintRed = matchedPaintRed[x];
                matchingPaintGreen = matchedPaintGreen[x];
                matchingPaintBlue = matchedPaintBlue[x];
                matchingPaint = euclidianDistanceArray[x];
                matchingPaintName = matchedPaintName[x];
                matchingPaintInfo = matchedPaintInfo[x];
                matchingPaintManufacturer = matchedPaintManufacturer[x];

            }
        }
        //Truncates the red, green and blue values by only capturing the last 8 numbers and discards the numbers preceding this. Required for transforming RGB colours into hexadecimal colours
        matchingPaintRed = (matchingPaintRed) & 0xff;
        matchingPaintGreen = (matchingPaintGreen) & 0xff;
        matchingPaintBlue = (matchingPaintBlue) & 0xff;

        //Calls getHexFromRGB() method and passes the red, green and blue values for encoding into hexadecimal
        hexColourCode = getHexFromRGB(Color.rgb(matchingPaintRed, matchingPaintGreen, matchingPaintBlue));

        //Transforms the title of the paint on the GUI to the paint's manufacturer and paint's name to provide feedback to the user
        paintTitleTextView.setText(String.valueOf(matchingPaintManufacturer) + "\n" + String.valueOf(matchingPaintName));

        //Transforms the Medici Swatch drawable on the GUI to the decoded hexadecimal colour code to provide feedback to the user
        paintSwatch.setBackgroundColor(Color.parseColor(hexColourCode));

        //Changes the text next to the Medici Swatch to the decoded hexadecimal colour code to provide feedback to the user
        hexInfo.setText(String.valueOf(hexColourCode));
        paintInformationTextView.setText(String.valueOf(matchingPaintInfo));

        //Updates the global variables passedPaintName and passedPaintManufacturer for passing to other activities through intent
        passedPaintName = matchingPaintName;
        passedPaintManufacturer = matchingPaintManufacturer;

    }

    public static String getHexFromRGB(int encodedRGBColorCode) {
        //Assembles the decoded hexadecimal colour code by first placing a leading number sign # and then the hexadecimal notation for red then green then blue - which comprises the hexadecimal colour code of the colour
        String decodedHexColourCode = String.format("#%02X%02X%02X", Color.red(encodedRGBColorCode), Color.green(encodedRGBColorCode), Color.blue(encodedRGBColorCode));
        return decodedHexColourCode;
    }

}
