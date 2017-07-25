package none.esportsre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class main extends AppCompatActivity {

    Button csgoButton;
    Button settingsButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        csgoButton = (Button) findViewById(R.id.csgoButt);

        settingsButt = (Button) findViewById(R.id.settingsButton);


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
                Intent csgoActive = new Intent(main.this, csgo.class);
                startActivity(csgoActive);
            }
        });
        settingsButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsActive = new Intent(main.this, settings.class);
                startActivity(settingsActive);
            }
        });

    }

}
