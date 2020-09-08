package in.droidparkz.droidband;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Pairingmessage extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairingmessage);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                Intent intent = new Intent(Pairingmessage.this,Userinfo.class);
                Pairingmessage.this.startActivity(intent);
                Pairingmessage.this.finish();

            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
