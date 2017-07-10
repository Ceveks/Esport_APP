package none.esportsre;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class csgoEvents extends AppCompatActivity {

    //Global variabler
    String getteam1, getteam2, getteam3, getteam4;

    ListView ongoing;
    ArrayList<String> onList=new ArrayList<>();
    ArrayAdapter<String> onAdapter;
    Button sorter;
    ListView upcoming;
    ArrayList<String> upList =new ArrayList<>();
    ArrayAdapter<String> upAdapter;
    StateVO sel;
    TextView noOnFound;

    //Holder links til hver event fra upcomingEvents
    String[] upComingBracketHolder = new String[100];

    //Holder link til nuværende event
    String onGoingBracketHolder;



//android.R.layout.simple_list_item_1
    TextView onFoundorNot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Laver en back button på siden med parent: csgo
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_csgo_events);

        //Henter data fra lokal fil om de nuværende "Favorite teams"
        favteams();

        noOnFound = (TextView) findViewById(R.id.noOngoingFound);
        sorter = (Button) findViewById(R.id.sortForFav);

        //Ikke i brug
        sel = new StateVO();

        sorter.setEnabled(false);



        //Ikke i brug
        sorter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onList.clear();
                upList.clear();
                onGoingEventsWithFavoriteTeams();

                upcomingEventsWithFavoriteTeams();

            }
        });

        //Drop-down menu med favorite teams (Har ikke nogen funktion lige pt.)
        final String[] select_qualification = { "teams",
                getteam1, getteam2, getteam3, getteam4};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayList<StateVO> listVOs = new ArrayList<>();
        for(int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        MyAdapter myAdapter = new MyAdapter(csgoEvents.this, 0,
                listVOs);
        spinner.setAdapter(myAdapter);



        //Set-up til de to lists ongoing og upcoming

        onAdapter = new ArrayAdapter<>(csgoEvents.this,
                R.layout.list, onList);
        upAdapter = new ArrayAdapter<>(csgoEvents.this, R.layout.list
                , upList);

        onFoundorNot = (TextView) findViewById(R.id.ongoingEventsTextView);

        ongoing = (ListView) findViewById(R.id.ongoingEvents);

        ongoing.setAdapter(onAdapter);

        upcoming = (ListView) findViewById(R.id.upcomingEvents);

        upcoming.setAdapter(upAdapter);

        onGoingEvents();
        upcomingEvents();




        //Hvis man klikker på et item i upcoming listen

        upcoming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                upComingEventsBracketsOrGroups(upComingBracketHolder[position]);

            }
        });
        //Hvis man klikker på et item i ongoing listen

        ongoing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                onGoingEventsBracketAndGroups(onGoingBracketHolder);

            }
        });

    }





    //Hvis et item i ongoing listen er klikket
    private void onGoingEventsBracketAndGroups(final String linkToPage) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder allTeams = new StringBuilder();
                Document doc = null;
                try {
                    //Connect til det pågældendes event's link
                    doc = Jsoup.connect(linkToPage).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Hent navnet på eventet til titlen i dialog boksen
                final Element nameOfEvent = doc.select("div.eventname").first();



                //  Elements groups = doc.select("div.group standard-box");


                Elements teamsAttending = doc.select("div.teams-attending.grid");
                for(Element teams : teamsAttending.select("div.team-name")){

                    //Se hvis holdnavnet har et punktum, hvis det er tilfældet kør removeDot

                    if(teams.select("div.text").text().contains(".")){
                        allTeams.append(removeDot(teams.select("div.text").text())).append("\n");
                    }else {
                        allTeams.append(teams.select("div.text").text()).append("\n");
                    }



                }

                //Start en AlertDialog (Pop-up vindue)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        final SpannableString s = new SpannableString(linkToPage);
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgoEvents.this);
                        mBuilder.setMessage("All teams:  \n \n ---------------------------------------------------- \n" +
                                ""+ allTeams.toString()+ "\n ----------------------------------------------------\n" +s)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        AlertDialog alert = mBuilder.create();
                        alert.setTitle(nameOfEvent.text());
                        alert.show();

                        //Lav link i dialog boksen
                        Linkify.addLinks((TextView) alert.findViewById(android.R.id.message), Linkify.ALL);



                    }
                });


            }
        }).start();

    }

    //Hvis et item upcoming listen er klikket
    private void upComingEventsBracketsOrGroups(final String linkToPage) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder allTeams = new StringBuilder();
                Document doc = null;
                try {
                    doc = Jsoup.connect(linkToPage).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Hent navnet på eventet til titlen i dialog boksen
                final Element nameOfEvent = doc.select("div.eventname").first();



                //  Elements groups = doc.select("div.group standard-box");


                Elements teamsAttending = doc.select("div.teams-attending.grid");
               for(Element teams : teamsAttending.select("div.team-name")){


                   if(teams.select("div.text").text().contains(".")){
                       allTeams.append(removeDot(teams.select("div.text").text())).append("\n");
                   }else {
                       allTeams.append(teams.select("div.text").text()).append("\n");
                   }


               }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final SpannableString s = new SpannableString(linkToPage);


                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(csgoEvents.this);
                        mBuilder.setMessage("All teams:  \n \n ---------------------------------------------------- \n" +
                                ""+ allTeams.toString()+ "\n ---------------------------------------------------- \n More details: "+s)
                                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                        AlertDialog alert = mBuilder.create();
                        alert.setTitle(nameOfEvent.text());
                        alert.show();
                        Linkify.addLinks((TextView) alert.findViewById(android.R.id.message), Linkify.ALL);



                    }
                });


            }
        }).start();

    }





    //Knappen er ikke i brug så denne bliver ikke kørt
    private void onGoingEventsWithFavoriteTeams() {



        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();
                final StringBuilder builder3 = new StringBuilder();


                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/events").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }




                Elements ele;
                ele = doc.select("div.ongoing-events-holder");



                for (Element big : ele.select("div.big-event-info")) {
                    for (Element table : big.select("table.table")) {
                        for (Element row : table.select("tr")) {
                            Elements tds = row.select("td");

                            builder2.append(":").append(tds.get(0).text() + ":" + tds.get(1).text());

                        }
                    }

                    for (Element teams : big.select("div.top-team-logos")) {
                        for (Element img : teams.select("img")) {

                            builder3.append(img.attr("alt")).append("-");
                        }
                    }



                    String[] b3String = builder3.toString().split("-");




                    String[] b2String = builder2.toString().split(":");

                    builder2.delete(0, builder2.length());


                    if(builder3.toString().toLowerCase().contains(getteam1) ||
                            builder3.toString().toLowerCase().contains(getteam2)||
                            builder3.toString().toLowerCase().contains(getteam3) ||
                            builder3.toString().toLowerCase().contains(getteam4)) {
                        builder.append(big.select("div.big-event-name").text()).append("\n")
                                .append("Prize pool:  ")
                                .append(b2String[2]).append("\n").
                                append("Location:  ").
                                append(big.select("span.big-event-location").text()).append("\n").
                                append("Date:  ").
                                append(b2String[1]).append("\n").append("Top teams:  ");
                        //.append(builder3.toString())
                        for (int x = 0; x < b3String.length; x++) {
                            builder.append(b3String[x]);
                            if (x < b3String.length - 1) {
                                builder.append(" - ");

                            }
                        }
                        if (builder3.toString().isEmpty()) {

                            builder.append("To be announced");
                        }

                        builder.append("#");

                        builder3.delete(0, builder3.length());
                    }

                }





                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(builder.length() == 0){

                            Toast.makeText(csgoEvents.this, "No ongoing events found", Toast.LENGTH_SHORT).show();

                        }else {
                            String[] teams = builder.toString().split("#");


                            for (int y = 0; y < teams.length; y++) {
                                onList.add(teams[y]);
                                onAdapter.notifyDataSetChanged();

                            }


                        }

                    }
                });


            }
        }).start();

    }
    //Knappen er ikke i brug så denne bliver ikke kørt
    private void upcomingEventsWithFavoriteTeams() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();
                final StringBuilder builder3 = new StringBuilder();


                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/events").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Elements ele;
                ele = doc.select("div.events-holder");
                for (Element big : ele.select("div.big-event-info")) {
                    for (Element table : big.select("table.table")) {
                        for (Element row : table.select("tr")) {
                            Elements tds = row.select("td");

                            builder2.append(":").append(tds.get(0).text() + ":" + tds.get(1).text());

                        }
                    }

                    for (Element teams : big.select("div.top-team-logos")) {
                        for (Element img : teams.select("img")) {

                            builder3.append(img.attr("alt")).append("-");
                        }
                    }


                    String[] b3String = builder3.toString().split("-");




                    String[] b2String = builder2.toString().split(":");

                    builder2.delete(0, builder2.length());
                    if(builder3.toString().toLowerCase().contains(getteam1) ||
                            builder3.toString().toLowerCase().contains(getteam2)||
                            builder3.toString().toLowerCase().contains(getteam3) ||
                            builder3.toString().toLowerCase().contains(getteam4)) {


                        builder.append(big.select("div.big-event-name").text()).append("\n")
                                .append("Prize pool:  ")
                                .append(b2String[2]).append("\n").
                                append("Location:  ").
                                append(big.select("span.big-event-location").text()).append("\n").
                                append("Date:  ").
                                append(b2String[1]).append("\n").append("Top teams:  ");
                        for (int x = 0; x < b3String.length; x++) {
                            builder.append(b3String[x]);
                            if (x < b3String.length - 1) {
                                builder.append(" - ");

                            }
                        }
                        if (builder3.toString().isEmpty()) {

                            builder.append("To be announced");
                        }

                        builder.append("#");

                        builder3.delete(0, builder3.length());
                    }

                }





                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(builder.length() == 0){

                            Toast.makeText(csgoEvents.this, "No upcoming events found", Toast.LENGTH_SHORT).show();

                        }else {
                            String[] teams = builder.toString().split("#");


                            for (int y = 0; y < teams.length; y++) {
                                upList.add(teams[y]);
                                upAdapter.notifyDataSetChanged();

                            }


                        }

                    }
                });


            }
        }).start();

    }




    //Data til listen ongoing events
    private void onGoingEvents() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();
                final StringBuilder builder3 = new StringBuilder();


                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/events").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //Sætter det første url i denne div til det nuværende event's link
                Elements ele;
                Element newUrl;
                ele = doc.select("div.ongoing-events-holder");
                newUrl = ele.select("a").first();
               onGoingBracketHolder =  newUrl.absUrl("href");


                for (Element big : ele.select("div.big-event-info")) {

                    //Henter data fra en table med denne metode
                    for (Element table : big.select("table.table")) {
                        for (Element row : table.select("tr")) {
                            Elements tds = row.select("td");

                            builder2.append(":").append(tds.get(0).text() + ":" + tds.get(1).text());

                        }
                    }
                    //Henter hold navne til "Top teams"
                    for (Element teams : big.select("div.top-team-logos")) {
                        for (Element img : teams.select("img")) {

                            builder3.append(img.attr("alt")).append("-");
                        }
                    }

                    //Sætter holdnavnene ind i et array
                    String[] b3String = builder3.toString().split("-");



                    //Sætter data fra tabelen ind i et array
                    String[] b2String = builder2.toString().split(":");

                    //Gør plads til næste event

                    builder2.delete(0, builder2.length());

                    //Bygger event string
                    builder.append(big.select("div.big-event-name").text()).append("\n")
                            .append("Prize pool:  ")
                            .append(b2String[2]).append("\n").
                            append("Location:  ").
                            append(big.select("span.big-event-location").text()).append("\n").
                            append("Date:  ").
                            append(b2String[1]).append("\n").append("Top teams:  ");

                    //Sætter holdnavne ind, kun sæt et " - " ind hvis der er flere hold.
                    //Undgår der ikke står e.g. NiP - Virtuspro - North -
                    for(int x=0; x<b3String.length; x++) {
                        builder.append(b3String[x]);
                        if (x<b3String.length-1) {
                            builder.append(" - ");

                        }
                    }
                    //Hvis der er ikke er nogen Top teams
                    if (builder3.toString().isEmpty()){

                        builder.append("To be announced");
                    }

                    builder.append("#");

                    //Gør plads til næste event
                    builder3.delete(0, builder3.length());

                }




                //Kør GUI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(builder.length() == 0){

                            Toast.makeText(csgoEvents.this, "No ongoing events found", Toast.LENGTH_SHORT).show();

                        }else {
                            String[] teams = builder.toString().split("#");


                            for (int y = 0; y < teams.length; y++) {
                                onList.add(teams[y]);
                                onAdapter.notifyDataSetChanged();

                            }


                        }

                    }
                });


            }
        }).start();

    }


    //Data i upcoming events listen  (Det samme om onGoingEvents)
    private void upcomingEvents() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                final StringBuilder builder2 = new StringBuilder();
                final StringBuilder builder3 = new StringBuilder();


                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/events").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements events = doc.select("div.events-month");


                int y = 0;
                for (Element finder : events.select("div.big-events")){
                    for(Element links : finder.select("a")){
                        upComingBracketHolder[y] = links.absUrl("href");
                        y++;
                    }


                }


                Elements ele;
                ele = doc.select("div.events-holder");
                    for (Element big : ele.select("div.big-event-info")) {
                        for (Element table : big.select("table.table")) {
                            for (Element row : table.select("tr")) {
                                Elements tds = row.select("td");

                                builder2.append(":").append(tds.get(0).text() + ":" + tds.get(1).text());

                            }
                        }

                        for (Element teams : big.select("div.top-team-logos")) {
                            for (Element img : teams.select("img")) {

                                builder3.append(img.attr("alt")).append("-");
                            }
                        }


                        String[] b3String = builder3.toString().split("-");




                        String[] b2String = builder2.toString().split(":");

                        builder2.delete(0, builder2.length());


                        builder.append(big.select("div.big-event-name").text()).append("\n")
                                .append("Prize pool:  ")
                                .append(b2String[2]).append("\n").
                                append("Location:  ").
                                append(big.select("span.big-event-location").text()).append("\n").
                                append("Date:  ").
                                append(b2String[1]).append("\n").append("Top teams:  ");

                        for(int x=0; x<b3String.length; x++) {
                            builder.append(b3String[x]);
                            if (x<b3String.length-1) {
                                builder.append(" - ");

                            }
                        }
                        if (builder3.toString().isEmpty()){

                            builder.append("To be announced");
                        }

                                builder.append("#");

                        builder3.delete(0, builder3.length());

                    }




                //Kør GUI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Hvis string er tom
                        if(builder.length() == 0){

                            Toast.makeText(csgoEvents.this, "No upcoming events found", Toast.LENGTH_SHORT).show();

                        }else {
                            String[] teams = builder.toString().split("#");


                            for (int y = 0; y < teams.length; y++) {
                                upList.add(teams[y]);
                                upAdapter.notifyDataSetChanged();

                            }


                        }

                    }
                });


            }
        }).start();

    }
    //Hent favorit hold
    public void favteams(){
        SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);
        getteam1 = teams.getString("team1", "");
        getteam2 = teams.getString("team2", "");
        getteam3 = teams.getString("team3", "");
        getteam4 = teams.getString("team4", "");

    }
    //Fjern punktum fra holdnavn
    public String removeDot(String teamNameWithDot){

        String teamNameWithoutDot= teamNameWithDot.replace(".", "");

    return teamNameWithoutDot;}

}

