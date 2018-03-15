package com.example.ankit_pc.bakingappudacity;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Widget_Config extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget__config);
        setResult(RESULT_CANCELED);
        final Button nutella = (Button) findViewById(R.id.nutellaButton);
        final Button brownies = (Button) findViewById(R.id.browniesButton);
        final Button yellow = (Button) findViewById(R.id.yellowButton);
        final Button cheese = (Button) findViewById(R.id.cheeseButton);
        nutella.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAppWidget(nutella.getText().toString());
            }
        });


        yellow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAppWidget(yellow.getText().toString());
            }
        });


        brownies.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAppWidget(brownies.getText().toString());
            }
        });


        cheese.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAppWidget(cheese.getText().toString());
            }
        });


    }

    /*private void handleSetupWidget() {
        showAppWidget();

    }*/

    int appWidgetId;
    private void showAppWidget(String recipe) {
        appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

        //Retrieve the App Widget ID from the Intent that launched the Activity//

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            appWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

            //If the intent doesn’t have a widget ID, then call finish()//

            if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
                finish();
            }

            //TO DO, Perform the configuration and get an instance of the AppWidgetManager//
            SharedPreferences sharedPreferences = getSharedPreferences("MyPref",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("recipe_name",recipe);
            editor.commit();

//Create the return intent//

            Intent resultValue = new Intent();

//Pass the original appWidgetId//

            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

//Set the results from the configuration Activity//

            setResult(RESULT_OK, resultValue);

//Finish the Activity//

            finish();
        }


    }
}
