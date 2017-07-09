package none.esportsre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class liveMatch extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_live_match);

        //Få data fra nyt intent
        Intent getMatchup = getIntent();
        String matchup = getMatchup.getStringExtra("matchup");


        getLiveMatchData(matchup.split("-")[1]);
    }

    public String getLiveMatchData(final String team) {


        StringBuilder linkBuild = new StringBuilder();
        Document doc = null;
        try {
            //Connect til det pågældendes event's link
            doc = Jsoup.connect("https://www.hltv.org/matches").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements live = doc.select("div.live-matches");

        for (Element finder : live.select("div.live-match")) {

            Element link = finder.select("a").first();

            linkBuild.append(link.absUrl("href"));

        }

    return linkBuild.toString();}

    private void upComingEventsBracketsOrGroups(final String linkToPage) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuilder matchupBuilder = new StringBuilder();
                Document doc = null;
                try {
                    //Connect til det pågældendes event's link
                    doc = Jsoup.connect(linkToPage).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }




                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {



                    }
                });


            }
        }).start();

    }
}


