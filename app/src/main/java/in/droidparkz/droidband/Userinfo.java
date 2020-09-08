package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.security.PublicKey;

public class Userinfo extends AppCompatActivity implements View.OnClickListener {

    EditText username,useremail,usercontact,userheight,userweight;

    String name,email,contact,height,weight;

    public static final String MY_PREFS_USERINFO = "MyPrefsUserInfo";

    GoogleSignInClient mGoogleSignInClient;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {

            email = account.getEmail();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        username = (EditText) findViewById(R.id.userinfoname);
        username.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        usercontact = (EditText) findViewById(R.id.userinfocontact);
        userheight = (EditText) findViewById(R.id.userinfoheight);
        userweight = (EditText) findViewById(R.id.userinfoweight);

        submit = (Button) findViewById(R.id.userinfobutton);
        submit.setOnClickListener(this);

    }

    public void getInput()
    {
        name = username.getText().toString();
        contact = usercontact.getText().toString();
        height = userheight.getText().toString();
        weight = userweight.getText().toString();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_USERINFO, MODE_PRIVATE).edit();
        editor.putString("username", name);
        editor.putString("useremail", email );
        editor.putString("usercontact",contact);
        editor.putString("userheight",height);
        editor.putString("userweight",weight);
        editor.apply();
    }

    @Override
    public void onClick(View view) {

        if (view == submit)
        {
            getInput();
            Intent intent = new Intent(Userinfo.this,Setgoal.class);
            startActivity(intent);
        }

    }
}
