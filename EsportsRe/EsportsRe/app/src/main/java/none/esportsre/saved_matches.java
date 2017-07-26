package none.esportsre;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class saved_matches extends AppCompatActivity {

    Button clearBut;
    Button saved;

    ListView savedLister;
    ArrayList<String> savedItems=new ArrayList<>();
    ArrayAdapter<String> savedAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_matches);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clearBut = (Button) findViewById(R.id.clearMatches);

        savedAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list, savedItems);

        savedLister = (ListView) findViewById(R.id.savedList);
        savedLister.setAdapter(savedAdapter);

        saved = (Button) findViewById(R.id.savedMatches);

        savedLister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(saved_matches.this);
                mBuilder.setMessage("Do you want to be notified?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String notify[] = savedAdapter.getItem(position).split(":");

                        Toast.makeText(getApplicationContext(), notify[2], Toast.LENGTH_SHORT).show();

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
        });

        new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder strBuild = new StringBuilder();
                FileInputStream fis;

                try {
                    fis = openFileInput("matchesSaved");
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader bfr = new BufferedReader(isr);
                    String message;


                    while ((message = bfr.readLine()) != null) {
                        strBuild.append(message);

                    }


                } catch (IOException e) {

                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String[] str = strBuild.toString().split("#");
                        String[] strB = new String[str.length];


                        for (int a = 1; a <= str.length - 1; a++) {

                            strB[a] = getDifference(parseDateToyyyyMMdd(str[a].split(":")[2]).trim(), str[a].substring(0, 5).trim());


                        }

                        for (int z = 0; z < str.length; z++) {
                            savedItems.add(str[z] + " || " + strB[z]);
                            savedAdapter.notifyDataSetChanged();
                        }

                        savedItems.remove(0);

                    }
                });


            }
        }).start();

        clearBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (savedAdapter.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "No matches found", Toast.LENGTH_SHORT).show();

                } else {

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(saved_matches.this);
                    mBuilder.setMessage("Are you sure you want to clear saved matches?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    Toast.makeText(getApplicationContext(), "Matches cleared", Toast.LENGTH_SHORT).show();
                                    clearMatches();

                                    Intent goBack = new Intent(saved_matches.this, csgo.class);
                                    startActivity(goBack);
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    AlertDialog alert = mBuilder.create();
                    alert.setTitle("Clear matches");
                    alert.show();

                }
            }
        });
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
                .appendWeeks().appendSuffix(" week ", " weeks " )
                .appendDays().appendSuffix(" day ", " days ")
                .appendHours().appendSuffix(" hour ", " hours ")
                .toFormatter();

        PeriodFormatter formatterMinutes = new PeriodFormatterBuilder()
                .appendMinutes().appendSuffix(" minute ", " minutes ")
                .toFormatter();



        if (period.getMinutes() > 0 || period.getHours() > 0 || period.getWeeks() > 0) {
            if(period.getMinutes()>0 && period.getHours()<=0 && period.getWeeks() <= 0){
                return formatterMinutes.print(period);

            }else {
                return formatter.print(period);
            }
        }
        else{

            return "Match already played";
        }


    }
    public void clearMatches(){

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

    public String Dateparser(String time, String parserIdentifier)
    {
        String inputPattern = null, outputPattern = null;
        switch (parserIdentifier)
        {
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
}






