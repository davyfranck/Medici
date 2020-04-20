package com.example.Medici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class PaintColour extends AppCompatActivity {

    //Initialising user interface elements
    private LinearLayout redPaintButton;
    private LinearLayout orangePaintButton;
    private LinearLayout yellowPaintButton;
    private LinearLayout greenPaintButton;
    private LinearLayout bluePaintButton;
    private LinearLayout purplePaintButton;
    private LinearLayout pinkPaintButton;
    private LinearLayout brownPaintButton;
    private LinearLayout blackPaintButton;
    private LinearLayout whitePaintButton;
    private LinearLayout goldPaintButton;
    private LinearLayout silverPaintButton;
    private LinearLayout greyPaintButton;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_paint_colour);
        //Initialising XML elements for GUI layout
        redPaintButton = (LinearLayout) findViewById(R.id.red);
        orangePaintButton = (LinearLayout) findViewById(R.id.orange);
        yellowPaintButton = (LinearLayout) findViewById(R.id.yellow);
        greenPaintButton = (LinearLayout) findViewById(R.id.green);
        bluePaintButton = (LinearLayout) findViewById(R.id.blue);
        purplePaintButton = (LinearLayout) findViewById(R.id.purple);
        pinkPaintButton = (LinearLayout) findViewById(R.id.pink);
        brownPaintButton = (LinearLayout) findViewById(R.id.brown);
        blackPaintButton = (LinearLayout) findViewById(R.id.black);
        whitePaintButton = (LinearLayout) findViewById(R.id.white);
        goldPaintButton = (LinearLayout) findViewById(R.id.gold);
        silverPaintButton = (LinearLayout) findViewById(R.id.silver);
        greyPaintButton = (LinearLayout) findViewById(R.id.grey);

        backButton = (Button) findViewById(R.id.backbutton);

        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes the user back to the HomeScreen activity screen
                finish();
            }
        });

        //Registers a callback to be invoked when the Red Paint Button is clicked
        redPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the RedPaintCatalogue activity and navigate the user to the dynamically rendered Red Paint Catalogue
                Intent intent = new Intent(PaintColour.this, RedPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Orange Paint Button is clicked
        orangePaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the OrangePaintCatalogue activity and navigate the user to the dynamically rendered Orange Paint Catalogue
                Intent intent = new Intent(PaintColour.this, OrangePaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Yellow Paint Button is clicked
        yellowPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the YellowPaintCatalogue activity and navigate the user to the dynamically rendered Yellow Paint Catalogue
                Intent intent = new Intent(PaintColour.this, YellowPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Green Paint Button is clicked
        greenPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the GreenPaintCatalogue activity and navigate the user to the dynamically rendered Green Paint Catalogue
                Intent intent = new Intent(PaintColour.this, GreenPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Blue Paint Button is clicked
        bluePaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the BluePaintCatalogue activity and navigate the user to the dynamically rendered Blue Paint Catalogue
                Intent intent = new Intent(PaintColour.this, BluePaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Purple Paint Button is clicked
        purplePaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the PurplePaintCatalogue activity and navigate the user to the dynamically rendered Purple Paint Catalogue
                Intent intent = new Intent(PaintColour.this, PurplePaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Pink Paint Button is clicked
        pinkPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the PinkPaintCatalogue activity and navigate the user to the dynamically rendered Pink Paint Catalogue
                Intent intent = new Intent(PaintColour.this, PinkPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Brown Paint Button is clicked
        brownPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the BrownPaintCatalogue activity and navigate the user to the dynamically rendered Brown Paint Catalogue
                Intent intent = new Intent(PaintColour.this, BrownPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Black Paint Button is clicked
        blackPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the BlackPaintCatalogue activity and navigate the user to the dynamically rendered Black Paint Catalogue
                Intent intent = new Intent(PaintColour.this, BlackPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the White Paint Button is clicked
        whitePaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the WhitePaintCatalogue activity and navigate the user to the dynamically rendered White Paint Catalogue
                Intent intent = new Intent(PaintColour.this, WhitePaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Gold Paint Button is clicked
        goldPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the GoldPaintCatalogue activity and navigate the user to the dynamically rendered Gold Paint Catalogue
                Intent intent = new Intent(PaintColour.this, GoldPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Silver Paint Button is clicked
        silverPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the SilverPaintCatalogue activity and navigate the user to the dynamically rendered Silver Paint Catalogue
                Intent intent = new Intent(PaintColour.this, SilverPaintCatalogue.class);
                startActivity(intent);
            }
        });

        //Registers a callback to be invoked when the Grey Paint Button is clicked
        greyPaintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Explicit Intent to call the RedPaintCatalogue activity and navigate the user to the dynamically rendered Red Paint Catalogue
                Intent intent = new Intent(PaintColour.this, GreyPaintCatalogue.class);
                startActivity(intent);
            }
        });


    }
}
