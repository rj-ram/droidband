package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Setgoal extends AppCompatActivity implements View.OnClickListener {

    TextView t1,backtext,reset,help;

    ImageView back;

    Button submit;

    String step,distance,cycling,calorie;

    Spinner spinner1,spinner2,spinner3,spinner4;

    public static final String MY_PREFS_SETGOAL = "MyPrefsSetGoal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView)findViewById(R.id.setgoalheadtext);
        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");
        t1.setTypeface(custom_font);

        addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();
        addItemsOnSpinner4();

        reset = (TextView) findViewById(R.id.setgoalresettext);
        reset.setOnClickListener(this);

        help = (TextView) findViewById(R.id.setgoalhelptext);
        help.setOnClickListener(this);

        backtext = (TextView) findViewById(R.id.setgoalbacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.setgoalbackicon);
        back.setOnClickListener(this);

        submit = (Button) findViewById(R.id.setgoalsubmit);
        submit.setOnClickListener(this);

    }

    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.setgoalwalkspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Target Steps");
        list3.add("300");
        list3.add("600");
        list3.add("1000");
        list3.add("1500");
        list3.add("2000");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.setgoalrunspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Target Distance in KM");
        list3.add("1");
        list3.add("3");
        list3.add("5");
        list3.add("7");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner3() {

        spinner3 = (Spinner) findViewById(R.id.setgoalcyclingspinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Target Cycling in KM");
        list3.add("1");
        list3.add("3");
        list3.add("5");
        list3.add("7");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);
    }

    public void addItemsOnSpinner4() {

        spinner4 = (Spinner) findViewById(R.id.setgoalcaloriespinner);
        List<String> list3 = new ArrayList<String>();
        list3.add("Target Calories");
        list3.add("5");
        list3.add("10");
        list3.add("20");
        list3.add("40");
        list3.add("50");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter);
    }

    public void setDetails()
    {
        step = String.valueOf(spinner1.getSelectedItem());
        distance = String.valueOf(spinner2.getSelectedItem());
        cycling = String.valueOf(spinner3.getSelectedItem());
        calorie = String.valueOf(spinner4.getSelectedItem());

        if(step == null || step.equals("Target Steps"))
        {
            Toast.makeText(Setgoal.this, "Select Target Steps", Toast.LENGTH_LONG).show();
        }

        else if(distance == null || distance.equals("Target Distance in KM"))
        {
            Toast.makeText(Setgoal.this, "Select Target Distance", Toast.LENGTH_LONG).show();
        }

        else if(cycling == null || cycling.equals("Target Cycling in KM"))
        {
            Toast.makeText(Setgoal.this, "Select Target Cycling", Toast.LENGTH_LONG).show();
        }

        else if(calorie == null || calorie.equals("Target Calories"))
        {
            Toast.makeText(Setgoal.this, "Select Target Calories", Toast.LENGTH_LONG).show();
        }

        else
        {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_SETGOAL, MODE_PRIVATE).edit();
            editor.putString("targetstep", step);
            editor.putString("targetdistance", distance);
            editor.putString("targetcycling", cycling);
            editor.putString("targetcalorie", calorie);
            editor.apply();
        }
    }

    @Override
    public void onClick(View view) {

        if (view == back || view == backtext)
        {
            onBackPressed();
        }

        if (view == reset)
        {
            spinner1.setSelection(0);
            spinner2.setSelection(0);
            spinner3.setSelection(0);
            spinner4.setSelection(0);
        }

        if (view == submit)
        {
            setDetails();
            Intent intent = new Intent(Setgoal.this,Homewindow.class);
            startActivity(intent);
        }

        if (view == help)
        {
            Intent intent = new Intent(Setgoal.this, Helpguide.class);
            startActivity(intent);
        }

    }
}
