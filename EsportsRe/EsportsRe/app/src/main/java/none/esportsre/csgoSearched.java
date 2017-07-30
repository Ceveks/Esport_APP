package none.esportsre;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;



public class csgoSearched extends AppCompatActivity {


    String firstTeam;
    String secondteam;
    Button recent;

    String searchedWord;
    String getteam1;
    String getteam2;
    String getteam3;
    String getteam4;

    TextView tex;
    Boolean matchFound;
    ListView lister;
    ArrayList<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favteams();
        tex = (TextView) findViewById(R.id.tex);
        recent = (Button) findViewById(R.id.recentResults);
        Intent intent = getIntent();

       boolean all = intent.getBooleanExtra("all", false);
         searchedWord =  intent.getStringExtra("teamName");

        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recentMatches = new Intent(csgoSearched.this, csgoResults.class);
                recentMatches.putExtra("team", searchedWord);
                startActivity(recentMatches);

            }
        });



        adapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list, listItems);

        lister = (ListView) findViewById(R.id.ListerID);

        lister.setAdapter(adapter);
        lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

                if (adapter.getItem(position).startsWith("LIVE")) {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgoSearched.this);
                    mBuilder.setMessage("Do you want to continue to the match page?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent goToLiveMatch = new Intent(csgoSearched.this, liveMatch.class);
                            goToLiveMatch.putExtra("matchup", adapter.getItem(position));
                            startActivity(goToLiveMatch);

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alert = mBuilder.create();
                    alert.setTitle("LIVE MATCH");
                    alert.show();

                } else {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgoSearched.this);
                    mBuilder.setMessage("Do you want to be notified about this match?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int CheckIfMatchSavedAlready = 0;

                            final StringBuilder crossChecker = new StringBuilder();


                            FileInputStream fis;

                            try {
                                fis = openFileInput("matchesSaved");
                                InputStreamReader isr = new InputStreamReader(fis);
                                BufferedReader bfr = new BufferedReader(isr);
                                String message;


                                while ((message = bfr.readLine()) != null) {
                                    crossChecker.append(message);
                                }

                            } catch (IOException e) {

                                e.printStackTrace();
                            }

                            String[] str = crossChecker.toString().split("#");


                            for (int z = 0; z < str.length; z++) {

                                if (str[z].regionMatches(2, adapter.getItem(position), 2, 30)) {
                                    CheckIfMatchSavedAlready = 1;
                                    break;
                                }
                            }

                            if (CheckIfMatchSavedAlready == 1) {

                                Toast.makeText(getApplicationContext(), "Match is already saved", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                saveMatch(adapter.getItem(position));

                                listItems.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }).setNeutralButton("Head-to-head", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            firstTeam = adapter.getItem(position).substring(adapter.getItem(position)
                                    .indexOf("-",2)+1,  adapter.getItem(position).indexOf("vs.")).trim();
                           secondteam = adapter.getItem(position).substring(adapter.getItem(position).indexOf("vs.")+1,
                                   adapter.getItem(position).indexOf("-",10 )).trim();

                            Intent startHeadToHead = new Intent(csgoSearched.this, headtohead.class);
                            startHeadToHead.putExtra("firstTeam",firstTeam);
                           startHeadToHead.putExtra("firstTeam",secondteam);
                            startActivity(startHeadToHead);
                        }

                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alert = mBuilder.create();
                    alert.setTitle("Reminder");
                    alert.show();

                }
            }
        });

        if (all){
            searchedWord= "asdadmadamsdmdajasdasdad";
                    getData();

        }else {
            getData();
        }
    }



    private void getData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final String tolowercaseAndTrimmed = searchedWord.toLowerCase().trim();

                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/matches").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                Elements ele;
                switch (tolowercaseAndTrimmed) {
                    case "sk":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/matches?team=6137").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "navi":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/matches?team=4608").get();
                            searchedWord = "natus vincere";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "north":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/matches?team=7533").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "fnatic":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/matches?team=4991").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "asdadmadamsdmdajasdasdad":

                        ele = doc.select("div.upcoming-matches");
                        for (Element date : ele.select("div.match-day")) {
                            for (Element element : date.select("table.table")) {

                                if (element.select("div.team").text().toLowerCase().trim().contains(getteam1.toLowerCase().trim())
                                        || element.select("div.team").text().toLowerCase().trim().contains(getteam2.toLowerCase().trim())
                                        || element.select("div.team").text().toLowerCase().trim().contains(getteam3.toLowerCase().trim())
                                        || element.select("div.team").text().toLowerCase().trim().contains(getteam4.toLowerCase().trim())) {
                                    String currentDate = parseDateToddMMyyyy(date.select("span.standard-headline").text());

                                    String daysLeft = getDifference(date.select("span.standard-headline").text(), element.select("div.time").text());

                                    builder.append("#").append(element.select("div.time").text()).
                                            append("  -  ").append(element.select("div.team").first().text()).
                                            append("  vs.  ").append(element.select("div.team").last().text());
                                    int leng = element.select("div.team").first().text().length() + element.select("div.team").last().text().length();

                                    if (leng > 32) {


                                        builder.append("   - ").append("   date:   ").
                                                append(currentDate).append(" || ").append(daysLeft).append("\n");

                                    } else {

                                        builder.append("   -                                               ").append("   date:   ").
                                                append(currentDate).append(" || ").append(daysLeft).append("\n");
                                    }

                                }
                            }
                        }
                        break;
                }
                int k = 1;
                ele = doc.select("div.upcoming-matches");
                for (Element date : ele.select("div.match-day")) {
                    for (Element element : date.select("table.table")) {

                        if (element.select("div.team").text().toLowerCase().contains(searchedWord.toLowerCase())) {
                            String currentDate = parseDateToddMMyyyy(date.select("span.standard-headline").text());

                            String daysLeft = getDifference(date.select("span.standard-headline").text(), element.select("div.time").text());

                            int leng = element.select("div.team").first().text().length() + element.select("div.team").last().text().length();

                            builder.
                                    //append("#").
                                            append(element.select("div.time").text()).
                                    append("  -  ").append(element.select("div.team").first().text()).
                                    append("  vs.  ").append(element.select("div.team").last().text());

                            k++;

                            if (leng > 32) {


                                builder.append("   - ").append("   date:   ").
                                        append(currentDate).append(" || ").append(daysLeft).append("\n");

                            } else {

                                builder.append("   -                                               ").append("   date:   ").
                                        append(currentDate).append(" || ").append(daysLeft).append("\n");
                            }
                        }
                    }
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Resources res = getResources();
                        String[] teamNamesForRecentResults = res.getStringArray(R.array.teamsForAutofill);
                        String[] teamsForRecentResultsToLowerCase = new String[teamNamesForRecentResults.length];
                        for (int x = 0; x < teamNamesForRecentResults.length; x++) {

                            teamsForRecentResultsToLowerCase[x] = teamNamesForRecentResults[x].toLowerCase();
                        }
                        if (Arrays.asList(teamsForRecentResultsToLowerCase).contains(searchedWord.toLowerCase())) {
                            recent.setEnabled(true);
                        } else {
                            recent.setEnabled(false);
                        }

                        if (searchedWord == "asdadmadamsdmdajasdasdad") {
                            recent.setEnabled(false);


                        }

                        if (builder.length() == 0) {

                            matchFound = false;


                        } else {
                            String[] teams = builder.toString().split("\n");

                            for (int y = 0; y < teams.length; y++) {
                                listItems.add(teams[y] + "\n");
                                adapter.notifyDataSetChanged();
                            }
                            matchFound = true;


                        }
                        liveMatch(searchedWord);

                    }
                });


            }
        }).start();

    }

    public void saveMatch(String matchData) {
        StringBuilder strB = new StringBuilder();
        String NewString = matchData.substring(0, matchData.indexOf("||"));
        strB.append("#").append(NewString);

        String file_name = "matches_saved";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_APPEND);
            fileOutputStream.write(strB.toString().getBytes());

            fileOutputStream.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void clearMatches() {

        deleteFile("matchesSaved");

    }

    public String parseDateToddMMyyyy(String time) {
        String parserIdentifier = "eu";
        return Dateparser(time, parserIdentifier);
    }

    public String parseDateToyyyyMMdd(String time) {
        String parserIdentifier = "na";
        return Dateparser(time, parserIdentifier);
    }

    public String Dateparser(String time, String parserIdentifier) {
        String inputPattern = null, outputPattern = null;
        switch (parserIdentifier) {
            case "eu":
                inputPattern = "yyyy-MM-dd";
                outputPattern = "dd. MMM - yyyy";
                break;
            case "na":
                inputPattern = "dd. MMM - yyyy";
                outputPattern = "yyyy-MM-dd";
                break;
            default:
                break;
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date curDate;
        String str = null;

        try {
            curDate = inputFormat.parse(time);
            str = outputFormat.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private void liveMatch(final String teamName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder liveBuilder = new StringBuilder();
                Document doc = null;
                try {
                    //Connect til det pågældendes event's link
                    doc = Jsoup.connect("https://www.hltv.org/matches").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements live = doc.select("div.live-matches");

                for (Element matches : live.select("div.live-match")) {

                    if (matches.select("span.team-name").text().toLowerCase().contains(teamName.trim().toLowerCase())) {
                        liveBuilder.append("LIVE MATCH >>").append(matches.select("span.team-name")
                                .first().text()).append(" - ").append(matches.select("span.team-name").last().text());

                        break;
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!liveBuilder.toString().isEmpty()) {

                            adapter.insert(liveBuilder.toString(), 0);
                            adapter.notifyDataSetChanged();
                            recent.setEnabled(true);
                        }
                        if (!matchFound && liveBuilder.toString().isEmpty()) {
                            tex.setText("No matches found for: " + searchedWord);


                        }

                    }
                });


            }
        }).start();

    }

    public String getDifference(String Date, String Clock) {

        String[] newDate = Date.split("-");
        String[] newClock = Clock.split(":");

        int years = Integer.parseInt(newDate[0]);
        int months = Integer.parseInt(newDate[2]);
        int days = Integer.parseInt(newDate[1]);
        int hours = Integer.parseInt(newClock[0]);
        int minutes = Integer.parseInt(newClock[1]);

        DateTime startDate = DateTime.now();
        DateTime endDate = new DateTime(years, days, months, hours, minutes);

        Period period = new Period(startDate, endDate);


        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendWeeks().appendSuffix(" week ", " weeks ")
                .appendDays().appendSuffix(" day ", " days ")
                .appendHours().appendSuffix(" hour ", " hours ")
                .toFormatter();

        PeriodFormatter formatterMinutes = new PeriodFormatterBuilder()
                .appendMinutes().appendSuffix(" minute ", " minutes ")
                .toFormatter();


        if (period.getMinutes() > 0 || period.getHours() > 0 || period.getWeeks() > 0) {
            if (period.getMinutes() > 0 && period.getHours() <= 0 && period.getWeeks() <= 0) {
                return formatterMinutes.print(period);

            } else {
                return formatter.print(period);
            }
        } else {

            return "Match already played";
        }


    }
    public void favteams(){
        SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);
        getteam1 = teams.getString("team0", "");
        getteam2 = teams.getString("team1", "");
        getteam3 = teams.getString("team2", "");
        getteam4 = teams.getString("team3", "");

    }

}