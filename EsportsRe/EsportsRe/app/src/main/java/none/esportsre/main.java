package none.esportsre;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class main extends AppCompatActivity {

    Button csgoButton;
    Button settingsButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);
        csgoButton = (Button) findViewById(R.id.Cs_button);
        csgoButton.setEnabled(false);
        SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);


        if(teams.contains("team1")){
           csgoButton.setEnabled(true);
        }else {
            Toast.makeText(this, "Go to settings and choose four teams", Toast.LENGTH_LONG).show();
        }



        settingsButt = (Button) findViewById(R.id.Event_button);


        /*
        lolButt = (Button) findViewById(R.id.lolButton);
        lolButt.setEnabled(false);

        lolButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lolActive = new Intent(main.this, lol.class);

                startActivity(lolActive);
            }
        });
*/
        csgoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,17);
                calendar.set(Calendar.MINUTE,50);
                Intent intent = new Intent(getApplicationContext(),MyBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR,pendingIntent);


                Intent csgoActive = new Intent(main.this, csgo.class);
                startActivity(csgoActive);
            }
        });
        settingsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent eventsIntent = new Intent(main.this, csgoEvents.class);

                startActivity(eventsIntent);
            }
        });

    }
}
