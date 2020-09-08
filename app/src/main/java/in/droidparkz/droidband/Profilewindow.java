package in.droidparkz.droidband;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import static in.droidparkz.droidband.Deviceinfo.MY_PREFS_BANDINFO;
import static in.droidparkz.droidband.PairingselectionAdapter.PairingselectionViewHolder.MY_PREFS_NAME;
import static in.droidparkz.droidband.Userinfo.MY_PREFS_USERINFO;

public class Profilewindow extends AppCompatActivity implements View.OnClickListener {

    TextView t1,devicename,email,backtext;

    String bandname,currentmail,imgurl;

    Uri personPhoto;

    RelativeLayout device,personal,connected,logout;

    ImageView profileimage,back;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilewindow);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        t1 = (TextView) findViewById(R.id.profileheadtext);

        Typeface custom_font = Typeface.createFromAsset(getApplicationContext().getAssets(),  "font/forte.ttf");

        t1.setTypeface(custom_font);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {

            currentmail = account.getEmail();
            personPhoto = account.getPhotoUrl();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        bandname = prefs.getString("bandname", null);

        profileimage = (ImageView) findViewById(R.id.profileuserimage);
        Picasso.with(this).load(personPhoto).fit().centerInside().into(profileimage);

        backtext = (TextView) findViewById(R.id.profilebacktext);
        backtext.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.profilebackicon);
        back.setOnClickListener(this);

        email = (TextView) findViewById(R.id.profileuseremail);
        email.setText(currentmail);

        devicename = (TextView) findViewById(R.id.profiledevicename);
        devicename.setText(bandname);

        device = (RelativeLayout) findViewById(R.id.profiledevice);
        device.setOnClickListener(this);

        personal = (RelativeLayout) findViewById(R.id.profilepersonal);
        personal.setOnClickListener(this);

        connected = (RelativeLayout) findViewById(R.id.profileconnected);
        connected.setOnClickListener(this);

        logout = (RelativeLayout) findViewById(R.id.profilesignout);
        logout.setOnClickListener(this);

    }

    public void clearPreferences()
    {
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.remove("bandname");
        editor.remove("bandmacid");
        editor.apply();

        SharedPreferences.Editor editor1 = getSharedPreferences(MY_PREFS_BANDINFO, MODE_PRIVATE).edit();
        editor1.remove("bandname");
        editor1.remove("bandmacid");
        editor1.remove("bluetooth");
        editor1.remove("battery");
        editor1.remove("firmware");
        editor1.remove("bandimage");
        editor1.remove("newfirmware");
        editor1.remove("updatelink");
        editor1.remove("brandimage");
        editor1.apply();

        SharedPreferences.Editor editor2 = getSharedPreferences(MY_PREFS_USERINFO, MODE_PRIVATE).edit();
        editor2.remove("username");
        editor2.remove("useremail");
        editor2.remove("usercontact");
        editor2.remove("userheight");
        editor2.remove("userweight");
        editor2.apply();

        signOut();

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Profilewindow.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profilewindow.this, Loginwindow.class));
                        finish();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        if (view == device)
        {
            Intent intent = new Intent(Profilewindow.this,Deviceinfo.class);
            startActivity(intent);
        }

        if (view == personal)
        {
            Intent intent = new Intent(Profilewindow.this,Personalinfo.class);
            startActivity(intent);
        }

        if (view == connected)
        {
            Intent intent = new Intent(Profilewindow.this,Connectedapps.class);
            startActivity(intent);
        }

        if (view == logout)
        {
            clearPreferences();
        }

        if (view == back || view == backtext)
        {
            Intent intent = new Intent(Profilewindow.this,Homewindow.class);
            startActivity(intent);
        }

    }
}
