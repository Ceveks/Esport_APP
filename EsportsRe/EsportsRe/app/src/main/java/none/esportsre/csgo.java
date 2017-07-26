package none.esportsre;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;





public class csgo extends AppCompatActivity {

    Button btn;
    Button allBut;
    Button saved;


    String searchedWord;
    String getteam1;
    String getteam2;
    String getteam3;
    String getteam4;

    TextView tex;
    Button events;
    ImageButton ast;
    ImageButton nort;
    ImageButton hero;
    ImageButton cloud;
    AutoCompleteTextView teamsAuto;
    String[] teamAutoArray;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_csgo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favteams();

        events = (Button) findViewById(R.id.settings);

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsActive = new Intent(csgo.this, settings.class);
                startActivity(settingsActive);
            }
        });
        saved = (Button)findViewById(R.id.savedMatches);




        teamsAuto = (AutoCompleteTextView) findViewById(R.id.Search);
        teamAutoArray = getResources().getStringArray(R.array.teamsForAutofill);

        final ArrayAdapter<String> teamAdapter = new ArrayAdapter<String>
                (this, R.layout.recent_matches_colors, teamAutoArray);

        teamsAuto.setAdapter(teamAdapter);

        ast = (ImageButton) findViewById(R.id.team1);
        cloud = (ImageButton) findViewById(R.id.team2);
        nort = (ImageButton) findViewById(R.id.team3);
        hero = (ImageButton) findViewById(R.id.team4);

        String mDrawableName1 = getteam1;
        if (getteam1.isEmpty()) {
            mDrawableName1 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName1, "drawable", getPackageName());
            ast.setBackgroundResource(resID);


        } else {
            int resID = getResources().getIdentifier(mDrawableName1, "drawable", getPackageName());
            ast.setBackgroundResource(resID);
        }
        String mDrawableName2 = getteam2;
        if (getteam2.isEmpty()) {
            mDrawableName2 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName2, "drawable", getPackageName());
            nort.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName2, "drawable", getPackageName());


            nort.setBackgroundResource(resID);
        }
        String mDrawableName3 = getteam3;
        if (getteam3.isEmpty()) {
            mDrawableName3 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName3, "drawable", getPackageName());
            hero.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName3, "drawable", getPackageName());

            hero.setBackgroundResource(resID);
        }
        String mDrawableName4 = getteam4;
        if (getteam4.isEmpty()) {
            mDrawableName4 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName4, "drawable", getPackageName());
            cloud.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName4, "drawable", getPackageName());

            cloud.setBackgroundResource(resID);
        }


        tex = (TextView) findViewById(R.id.textHere);


        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startSaved = new Intent(csgo.this, saved_matches.class);
                startActivity(startSaved);

             }
        });

        btn = (Button) findViewById(R.id.dataGetter);
        allBut = (Button) findViewById(R.id.allMatches);

        allBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startSearcher = new Intent(csgo.this, csgoSearched.class);
                startSearcher.putExtra("all", true);
                startActivity(startSearcher);
                searchedWord="asdadmadamsdmdajasdasdad";
            }
        });


        ast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getteam1.isEmpty()) {
                    searchedWord = "astralis";
                } else {
                    searchedWord = getteam1;
                }
                search();
            }
        });

        nort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getteam1.isEmpty()) {
                    searchedWord = "north";
                } else {
                    searchedWord = getteam2;
                }


                search();
            }
        });
        hero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getteam1.isEmpty()) {
                    searchedWord = "heroic";
                } else {

                    searchedWord = getteam3;
                }
                search();
            }
        });
        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getteam1.isEmpty()) {
                    searchedWord = "cloud9";
                } else {
                    searchedWord = getteam4;
                }

                search();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchedWord = teamsAuto.getText().toString();

                if (searchedWord.length() < 2) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Input at least 2 characters", Toast.LENGTH_LONG);
                    toast.show();

                } else {
                    search();
                }
            }
        });

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras != null) {


        }
    }

    public void favteams(){
        SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);
        getteam1 = teams.getString("team1", "");
        getteam2 = teams.getString("team2", "");
        getteam3 = teams.getString("team3", "");
        getteam4 = teams.getString("team4", "");

    }

    public void search(){

        Intent startSearcher = new Intent(csgo.this, csgoSearched.class);
        startSearcher.putExtra("teamName", searchedWord);
        startActivity(startSearcher);
    }

}