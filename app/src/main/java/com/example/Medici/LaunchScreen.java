package com.example.Medici;
import androidx.fragment.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LaunchScreen extends FragmentActivity {

    //Initialising user interface elements
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Sets the activity content to an explicit view and associates the activity with its respective XML file for design layout
        setContentView(R.layout.launch_activity);
        //Initialising XML elements for GUI layout
        startButton = (Button) findViewById(R.id.startButton);

        //Registers a callback to be invoked when the Start Button is clicked
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calls openActivity() method
                openActivity();
            }
        });
    }

    public void openActivity() {
        //Explicit Intent to call the HomeScreen activity and navigate the user to the Home Screen
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
