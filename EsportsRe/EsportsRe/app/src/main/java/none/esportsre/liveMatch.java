package none.esportsre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class liveMatch extends AppCompatActivity {
    TextView tex;

    String MyString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_live_match);

        //Få data fra nyt intent
        Intent getMatchup = getIntent();
        String matchup = getMatchup.getStringExtra("matchup");

        tex = (TextView) findViewById(R.id.testView);

        getLiveMatchData(matchup.split("-")[1]);



    }

    private void getLiveMatchData(final String team) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder linkBuild = new StringBuilder();
                final StringBuilder strBuild = new StringBuilder();
                Document doc = null;
                try {
                    //Connect til det pågældendes event's link
                    doc = Jsoup.connect("https://www.hltv.org/matches").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Elements live = doc.select("div.live-matches");

                for (Element finder : live.select("div.live-match")) {

                    if (finder.select("span.team-name").text().toLowerCase().contains(team.toLowerCase())) {

                        Element link = finder.select("a").first();

                        linkBuild.append(link.absUrl("href"));
                    }
                }


                Document newDoc = null;

                try {
                    //Connect til det pågældendes event's link
                    newDoc = Jsoup.connect(linkBuild.toString()).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Element element = newDoc.select("div.match-page").first();

                strBuild.append(element.select("div.lineups").text());



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!strBuild.toString().isEmpty()) {
                            tex.setText(strBuild.toString());
                        }
                        else {
                            tex.setText("Not found");
                        }

                    }
                });


            }
        }).start();

    }




    private void findMatchup() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                StringBuilder matchupBuilder = new StringBuilder();
                Document doc = null;
                try {
                    //Connect til det pågældendes event's link
                    doc = Jsoup.connect(MyString).get();
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


