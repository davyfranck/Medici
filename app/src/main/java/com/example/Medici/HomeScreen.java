package com.example.Medici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class HomeScreen extends AppCompatActivity {

    //Initialises user interface elements
    private Button cameraButton;
    private Button backButton;
    private ImageView paintCatalogueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.activity_home_screen);

        //Initialising XML elements for GUI layout
        cameraButton = (Button) findViewById(R.id.camerabutton);
        backButton = (Button) findViewById(R.id.backbutton);
        paintCatalogueButton = (ImageView) findViewById(R.id.paint_catalogue_banner);

        //Registers a callback to be invoked when the Camera Button is clicked
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });

        //Registers a callback to be invoked when the Back Button is clicked
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        //Registers a callback to be invoked when the Paint Catalogue Banner Button is clicked
        paintCatalogueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    public void openActivity() {
        //Explicit Intent to call the MediciCamera activity and navigate the user to the Medici Camera
        Intent intent = new Intent(this, MediciCamera.class);
        startActivity(intent);
    }

    public void openActivity2() {
        //Explicit Intent to call the LaunchScreen activity and navigate the user to the Launch Screen
        Intent intent = new Intent(this, LaunchScreen.class);
        startActivity(intent);
    }

    public void openActivity3() {
        //Explicit Intent to call the PaintColour activity and navigate the user to the Paint Catalogue Colour Category screen
        Intent intent = new Intent(this, PaintColour.class);
        startActivity(intent);
    }
}