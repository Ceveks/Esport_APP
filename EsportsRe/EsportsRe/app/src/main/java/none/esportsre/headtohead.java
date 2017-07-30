package none.esportsre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class headtohead extends AppCompatActivity {
TextView firstTeam, secondTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headtohead);
        firstTeam = (TextView) findViewById(R.id.team1H2H);
        secondTeam = (TextView) findViewById(R.id.team2H2H);
        Intent getIntent = getIntent();

       String team1 = getIntent.getStringExtra("firstTeam");
        String team2 = getIntent.getStringExtra("secondTeam");
        firstTeam.setText(team1);
        secondTeam.setText(team2);
    }
}
