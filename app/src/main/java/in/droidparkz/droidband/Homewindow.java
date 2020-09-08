package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;

import static in.droidparkz.droidband.PairingselectionAdapter.PairingselectionViewHolder.MY_PREFS_NAME;
import static in.droidparkz.droidband.Setgoal.MY_PREFS_SETGOAL;

public class Homewindow extends AppCompatActivity implements View.OnClickListener {

    TextView t1,date,tardis,curdis;

    String day,month;

    int currentdistance, progresspercentage;

    String targetdistance;

    ImageView add,profile,refresh,running;

    RelativeLayout heart,cycling,steps,reminder,sleep,calorie;

    SharedPreferences prefs,prefs1;

    ProgressBar progress;

    public static final String MY_PREFS_SETCURRENT = "MyPrefsSetCurrent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homewindow);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        t1 = (TextView) findViewById(R.id.homewindowheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        prefs = getSharedPreferences(MY_PREFS_SETGOAL, MODE_PRIVATE);
        targetdistance = (prefs.getString("targetdistance", null)+" KM");

        prefs1 = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE);

        add = (ImageView) findViewById(R.id.homewindowadd);
        add.setOnClickListener(this);

        refresh = (ImageView) findViewById(R.id.homewindowrefresh);
        refresh.setOnClickListener(this);

        profile = (ImageView) findViewById(R.id.homewindowprofile);
        profile.setOnClickListener(this);

        heart = (RelativeLayout) findViewById(R.id.homewindowheartbeat);
        heart.setOnClickListener(this);

        cycling = (RelativeLayout) findViewById(R.id.homewindowcycling);
        cycling.setOnClickListener(this);

        steps = (RelativeLayout) findViewById(R.id.homewindowsteps);
        steps.setOnClickListener(this);

        reminder = (RelativeLayout) findViewById(R.id.homewindowreminder);
        reminder.setOnClickListener(this);

        sleep = (RelativeLayout) findViewById(R.id.homewindowsleep);
        sleep.setOnClickListener(this);

        calorie = (RelativeLayout) findViewById(R.id.homewindowcalories);
        calorie.setOnClickListener(this);

        date = (TextView) findViewById(R.id.homewindowdate);

        tardis = (TextView) findViewById(R.id.homewindowtargetdistance);
        tardis.setText(targetdistance);

        curdis = (TextView) findViewById(R.id.homewindowcurrentdistance);
        progress = (ProgressBar) findViewById(R.id.homewindowprogressbar);

        running = (ImageView) findViewById(R.id.homerunning1);

        if (prefs1.getString("currentdistance",null )== null)
        {
            getDistance();
        }
        else
        {
            curdis.setText(prefs1.getString("currentdistance",null)+ " KM");
            progresspercentage = Integer.valueOf(prefs1.getString("distancepercentage",null));
            progress.setProgress(progresspercentage);

            if (progresspercentage >= 50)
            {
                running.setImageResource(R.drawable.running1);
            }
            else
            {
                running.setImageResource(R.drawable.running2);
            }
        }
        getDate();
    }

    public void getDate()
    {

        Calendar c = Calendar.getInstance();
        int dt = c.get(Calendar.DAY_OF_MONTH);
        int mt = c.get(Calendar.MONTH);
        int yr = c.get(Calendar.YEAR);

        switch (mt)
        {
            case 0:
                month = "JAN";
                day = dt +" " + month;
                break;

            case 1:
                month = "FEB";
                day = dt +" " + month;
                break;

            case 2:
                month = "MAR";
                day = dt + month;
                break;

            case 3:
                month = "APR";
                day = dt +" " + month;
                break;

            case 4:
                month = "MAY";
                day = dt +" " + month;
                break;

            case 5:
                month = "JUN";
                day = dt +" " + month;
                break;

            case 6:
                month = "JUL";
                day = dt +" " + month;
                break;

            case 7:
                month = "AUG";
                day = dt +" " + month;
                break;

            case 8:
                month = "SEP";
                day = dt +" " + month;
                break;

            case 9:
                month = "OCT";
                day = dt +" " + month;
                break;

            case 10:
                month = "NOV";
                day = dt +" " + month;
                break;

            case 11:
                month = "DEC";
                day = dt +" " + month;
                break;
        }

        date.setText(day);

    }

    public void getDistance()
    {
        int min = 0;
        int max = Integer.valueOf(prefs.getString("targetdistance",null));
        currentdistance = new Random().nextInt((max - min) + 1) + min;
        curdis.setText(String.valueOf(currentdistance)+" KM");
        percentage(currentdistance,max);

    }

    public void percentage(int min,int max)
    {
        double per = 100.0 * min / max;
        int percentage = (int) per;
        progress.setProgress(percentage);
        if (percentage >= 50)
        {
            running.setImageResource(R.drawable.running1);
        }
        else
        {
            running.setImageResource(R.drawable.running2);
        }
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_SETCURRENT, MODE_PRIVATE).edit();
        editor.putString("currentdistance", String.valueOf(currentdistance));
        editor.putString("distancepercentage",String.valueOf(percentage));
        editor.apply();
    }


    @Override
    public void onClick(View view) {

        if (view == add)
        {
            Intent intent = new Intent(Homewindow.this,Setgoal.class);
            startActivity(intent);
        }

        if (view == refresh)
        {
           getDistance();
        }

        if (view == profile)
        {
            Intent intent = new Intent(Homewindow.this, Profilewindow.class);
            startActivity(intent);
        }

        if (view == heart)
        {
            Intent intent = new Intent(Homewindow.this, Heartrate.class);
            startActivity(intent);
        }

        if (view == cycling)
        {
            Intent intent = new Intent(Homewindow.this, Cycling.class);
            startActivity(intent);
        }

        if (view == steps)
        {
            Intent intent = new Intent(Homewindow.this, Steps.class);
            startActivity(intent);
        }

        if (view == reminder)
        {
            Intent intent = new Intent(Homewindow.this, Reminder.class);
            startActivity(intent);
        }

        if (view == sleep)
        {
            Intent intent = new Intent(Homewindow.this, Sleepwindow.class);
            startActivity(intent);
        }

        if (view == calorie)
        {
            Intent intent = new Intent(Homewindow.this, Calorie.class);
            startActivity(intent);
        }
    }
}
