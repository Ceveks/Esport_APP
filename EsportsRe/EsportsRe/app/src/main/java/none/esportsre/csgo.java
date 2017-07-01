package none.esportsre;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
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
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class csgo extends AppCompatActivity {

    Button btn;
    EditText search;
    String searchedWord;
    Button goback;
    Button allBut;
    Button clearBut;
    Button saved;



    TextView tex;

    ListView savedLister;
    ArrayList<String> savedItems=new ArrayList<>();
    ArrayAdapter<String> savedAdapter;

    ListView lister;
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;

    ImageButton ast;
    ImageButton nort;
    ImageButton hero;
    ImageButton cloud;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo);

        nort = (ImageButton) findViewById(R.id.north);
        hero = (ImageButton) findViewById(R.id.heroic);
        cloud = (ImageButton) findViewById(R.id.cloud9);
        tex = (TextView) findViewById(R.id.textHere);
        clearBut =(Button)findViewById(R.id.clearMatches);
        clearBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(savedAdapter.isEmpty()){

                    Toast.makeText(getApplicationContext(), "No matches found", Toast.LENGTH_SHORT).show();
                }else {


                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgo.this);
                    mBuilder.setMessage("Are you sure you want to clear saved matches?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            search.setText("");
                            tex.setText("");
                            savedLister.setVisibility(View.INVISIBLE);
                            clearBut.setVisibility(View.INVISIBLE);
                            savedItems.clear();
                            saved.setVisibility(View.VISIBLE);
                            listItems.clear();
                            btn.setVisibility(View.VISIBLE);
                            search.setVisibility(View.VISIBLE);
                            goback.setVisibility(View.INVISIBLE);
                            lister.setVisibility(View.INVISIBLE);
                            allBut.setVisibility(View.VISIBLE);
                            ast.setVisibility(View.VISIBLE);
                            cloud.setVisibility(View.VISIBLE);
                            hero.setVisibility(View.VISIBLE);
                            nort.setVisibility(View.VISIBLE);

                            Toast.makeText(getApplicationContext(), "Matches cleared", Toast.LENGTH_SHORT).show();

                            clearMatches();


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


        search = (EditText) findViewById(R.id.Search);

        adapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listItems);
        savedAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1, savedItems);

        savedLister = (ListView) findViewById(R.id.savedList);
        savedLister.setAdapter(savedAdapter);
        savedLister.setVisibility(View.INVISIBLE);
        clearBut.setVisibility(View.INVISIBLE);

        saved = (Button) findViewById(R.id.savedMatches);

        savedLister.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, final int position, long id)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgo.this);
                mBuilder.setMessage("Do you want to be notified?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String notify[] = savedAdapter.getItem(position).split(":");

                        Toast.makeText(getApplicationContext(), notify[2] , Toast.LENGTH_SHORT).show();

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

        saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saved.setVisibility(View.INVISIBLE);
                clearBut.setVisibility(View.VISIBLE);
                savedLister.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                lister.setVisibility(View.INVISIBLE);

                cloud.setVisibility(View.INVISIBLE);
                hero.setVisibility(View.INVISIBLE);
                nort.setVisibility(View.INVISIBLE);

                allBut.setVisibility(View.INVISIBLE);


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


                            while ((message=bfr.readLine())!=null) {
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


                                   for(int a = 1; a <= str.length-1; a++){

                                        strB[a] = getDifference(parseDateToyyyyMMdd(str[a].split(":")[2]), str[a].substring(0, 5));

                                   }

                                for (int z = 0; z < str.length; z++) {
                                        savedItems.add(str[z]+" || "+strB[z]);
                                        savedAdapter.notifyDataSetChanged();
                                    }

                                savedItems.remove(0);


                            }
                        });


                    }
                }).start();

            }
        });

        lister = (ListView) findViewById(R.id.ListerID);

        lister.setAdapter(adapter);
        lister.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View  view, final int position, final long id)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgo.this);
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


                            while ((message=bfr.readLine())!=null) {
                                crossChecker.append(message);

                            }


                        } catch (IOException e) {

                            e.printStackTrace();
                        }

                        String[] str = crossChecker.toString().split("#");



                        for (int z = 0; z < str.length; z++) {


                           if (str[z].regionMatches(1,adapter.getItem(position),2, 20)){
                               CheckIfMatchSavedAlready = 1;
                               break;
                           }
                        }

                        if (CheckIfMatchSavedAlready==1){



                            Toast.makeText(getApplicationContext(), "Match is already saved", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                            saveMatch(adapter.getItem(position));

                            listItems.remove(position);
                            adapter.notifyDataSetChanged();
                        }
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


        lister.setVisibility(View.INVISIBLE);
        goback = (Button) findViewById(R.id.goBack);
        btn = (Button) findViewById(R.id.dataGetter);
        ast = (ImageButton)findViewById(R.id.astralis);

        allBut = (Button) findViewById(R.id.allMatches);
        allBut.setEnabled(false);
        allBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn.setVisibility(View.INVISIBLE);
                lister.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                allBut.setVisibility(View.INVISIBLE);

                getData();
            }
        });


        ast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedWord = "astralis";

                lister.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                cloud.setVisibility(View.INVISIBLE);
                hero.setVisibility(View.INVISIBLE);
                nort.setVisibility(View.INVISIBLE);

                allBut.setVisibility(View.INVISIBLE);
                getData();
            }
        });

        nort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedWord = "north";

                lister.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                cloud.setVisibility(View.INVISIBLE);
                hero.setVisibility(View.INVISIBLE);
                nort.setVisibility(View.INVISIBLE);

                allBut.setVisibility(View.INVISIBLE);
                getData();
            }
        });
        hero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedWord = "heroic";

                lister.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                cloud.setVisibility(View.INVISIBLE);
                hero.setVisibility(View.INVISIBLE);
                nort.setVisibility(View.INVISIBLE);

                allBut.setVisibility(View.INVISIBLE);
                getData();
            }
        });
        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedWord = "cloud9";

                lister.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ast.setVisibility(View.INVISIBLE);
                cloud.setVisibility(View.INVISIBLE);
                hero.setVisibility(View.INVISIBLE);
                nort.setVisibility(View.INVISIBLE);

                allBut.setVisibility(View.INVISIBLE);
                getData();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchedWord = search.getText().toString();

                if (searchedWord.length() < 2) {

                    Toast toast = Toast.makeText(getApplicationContext(), "Input at least 2 characters", Toast.LENGTH_LONG);
                    toast.show();

                } else {
                    getData();
                    btn.setVisibility(View.INVISIBLE);
                    search.setVisibility(View.INVISIBLE);
                    goback.setVisibility(View.VISIBLE);
                    search.setText("");
                    lister.setVisibility(View.VISIBLE);
                    cloud.setVisibility(View.INVISIBLE);
                    hero.setVisibility(View.INVISIBLE);
                    nort.setVisibility(View.INVISIBLE);
                    ast.setVisibility(View.INVISIBLE);
                    allBut.setVisibility(View.INVISIBLE);
                }
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
                tex.setText("");
                savedLister.setVisibility(View.INVISIBLE);
                savedItems.clear();
                saved.setVisibility(View.VISIBLE);
                listItems.clear();
                btn.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                goback.setVisibility(View.INVISIBLE);
                lister.setVisibility(View.INVISIBLE);
                allBut.setVisibility(View.VISIBLE);
                ast.setVisibility(View.VISIBLE);
                clearBut.setVisibility(View.INVISIBLE);
                cloud.setVisibility(View.VISIBLE);
                hero.setVisibility(View.VISIBLE);
                nort.setVisibility(View.VISIBLE);
            }
        });
    }



    private void getData(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();


                try {

                    Document doc = Jsoup.connect("https://www.hltv.org/matches").get();
                    Elements ele = doc.select("div.upcoming-matches");
                    for(Element date :  ele.select("div.match-day") ) {
                        for (Element element : date.select("table.table")) {
                            if (element.select("div.team").text().toLowerCase().contains(searchedWord.toLowerCase())) {
                                String currentDate = parseDateToddMMyyyy(date.select("span.standard-headline").text());

                                String daysLeft = getDifference(date.select("span.standard-headline").text(), element.select("div.time").text());

                                builder.append("#").append(element.select("div.time").text()).
                                        append("  -  ").append(element.select("div.team").first().text()).
                                        append("  vs.  ").append(element.select("div.team").last().text());

                                if (element.select("div.team").last().text().length() > 10 || element.select("div.team").last().text().length() >10) {


                                    builder.append(" - ").append("   date:   ").
                                            append(currentDate).append(" || ").append(daysLeft).append("\n");

                                }else{

                                    builder.append("    -                    ").append("   date:   ").
                                            append(currentDate).append(" || ").append(daysLeft).append("\n");
                                }
                            }
                        }
                    }

                }catch (IOException e){
                    Toast exepToast = Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG);
                    exepToast.show();

                }

                runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(builder.length() == 0){

                                tex.setText("No matches found");

                            }else {
                                String[] teams = builder.toString().split("\n");

                               for (int y = 0; y < teams.length; y++) {
                                    listItems.add(teams[y]);
                                    adapter.notifyDataSetChanged();
                               }

                            }
                        }
                    });


            }
        }).start();

    }

    public void saveMatch(String matchData){
        StringBuilder strB = new StringBuilder();
        String Mess = matchData;
        String NewString = Mess.substring(Mess.indexOf("#") + 1, Mess.indexOf("||"));
        strB.append("#").append(NewString);


        String file_name = "matchesSaved";



            try{
                FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_APPEND);
                fileOutputStream.write(strB.toString().getBytes());

                fileOutputStream.close();

            }catch (IOException e){

                e.printStackTrace();
            }
    }

    public void clearMatches(){

        deleteFile("matchesSaved");

    }

    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd. MMM - yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date curDate = null;
        String str = null;

        try {
            curDate = inputFormat.parse(time);
            str = outputFormat.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public String parseDateToyyyyMMdd(String time) {
        String inputPattern = "dd. MMM - yyyy";
        String outputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date curDate = null;
        String str = null;

        try {
            curDate = inputFormat.parse(time);
            str = outputFormat.format(curDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    public String getDifference(String Date, String Clock){

        String[] newDate = Date.split("-");

        String[] newClock = Clock.split(":");
        int ho = Integer.parseInt(newClock[0]);
        int mi = Integer.parseInt(newClock[1]);
        int ye = Integer.parseInt(newDate[0]);
        int da = Integer.parseInt(newDate[1]);
        int mo = Integer.parseInt(newDate[2]);
        DateTime startDate = DateTime.now();
        DateTime endDate = new DateTime(ye, da, mo, ho, mi);

        Period period = new Period(startDate, endDate);



        PeriodFormatter formatter = new PeriodFormatterBuilder()

                .appendDays().appendSuffix(" day ", " d ")
                .appendHours().appendSuffix(" hour ", " h ")
                .toFormatter();


    return formatter.print(period);
    }
}



