package none.esportsre;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class csgoResults extends AppCompatActivity {

    ListView recentLister;
    ArrayList<String> recentItems=new ArrayList<>();
    ArrayAdapter<String> recentAdapter;
    TextView changeColor;
    Button goback;
    TextView tester;
    String searchedWord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csgo_results);
        changeColor = (TextView) findViewById(R.id.changeColorsOfListViewItems);
        recentLister = (ListView) findViewById(R.id.resultsList);

        goback = (Button) findViewById(R.id.goBackToCsgo);
        tester = (TextView) findViewById(R.id.tester); 
        
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent csgo = new Intent(csgoResults.this, csgo.class);
                csgo.putExtra("savedSearchedWord",searchedWord);
                startActivity(csgo);
            }
        });

        Intent intent = getIntent();
        searchedWord = intent.getStringExtra("team");
        recentAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.recent_matches_colors, recentItems);
        recentLister.setAdapter(recentAdapter);

        getData(searchedWord);

    }


    private void getData(final String teamName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();


                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/results").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String toLowerCase = teamName.toLowerCase();

                switch (toLowerCase) {
                    case "sk":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=6137").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "navi":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=4608").get();
                            toLowerCase = "natus vincere";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "north":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=7533").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "astralis":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=6665").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "cloud9":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=5752").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "flipsid3":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=5988").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "virtus":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=5378").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "nip":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=4411").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "natus vincere":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=4608").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "g2":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=5995").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "faze":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=6667").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "liquid":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=5973").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "mousesports":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=4494").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "gambit":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=6651").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "heroic":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=7175").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "fnatic":

                        try {
                            doc = Jsoup.connect("https://www.hltv.org/results?team=4991").get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                }
                Elements ele;
                ele = doc.select("div.results-all");



                int alignRight = 50;
                

                    for (Element date : ele.select("div.results-sublist")) {
                        

                        for (Element element : date.select("div.result")) {
                                int leng = element.select("td.result-score").text().length();
                            String test = element.select("div.team").first().text() + "   " +
                                    element.select("td.result-score").text() + "   " +
                                    element.select("div.team").last().text();



                            String firstResultInString = element.select("td.result-score").text()
                                    .substring(0,element.select("td.result-score").text().indexOf("-")).trim();
                            String secondResultInString = element.select("td.result-score").text()
                                    .substring(element.select("td.result-score").text().indexOf("-")+1, leng).trim();

                            int firstResult = Integer.parseInt(firstResultInString);
                            int secondResult = Integer.parseInt(secondResultInString);
                            boolean lOrW = false;
                            if (firstResult > secondResult && element.select("div.team").first().text().toLowerCase().contains(toLowerCase)) {
                                /*
                                for(int x=0; x<=alignRight-test.length(); x++) {
                                    builder.append(" ");
                                }
                                */

                                builder.append("W  -  ");
                                lOrW =true;

                            }
                            if (firstResult < secondResult && element.select("div.team").last().text().toLowerCase().contains(toLowerCase)) {

                                    builder.append("W  -  ");
                                lOrW=true;
                                }

                                if (lOrW==false){

                                    builder.append("L   -  ");
                                }




                                builder.append(date.select("div.standard-headline").text()).append(element.select("div.team").first().text()).append("   ").
                                        append(element.select("td.result-score").text()).append("   ").
                                        append(element.select("div.team").last().text()).append("  -  ").append(element.select("td.event").text());


                            int bLeng = leng +
                                    element.select("div.team").first().text().length() +
                                    element.select("div.team").last().text().length();


                                builder.append("\n");

                        }
                    }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                            String[] teams = builder.toString().split("\n");
                        
                      //  tester.setText(.toString());
                            

                            for (int y = 0; y < teams.length; y++) {
                                        /*
                                        if(teams[y].endsWith("W")){


                                        }
*/      
                                        recentItems.add(teams[y]);

                                        recentAdapter.notifyDataSetChanged();



                            }

                    }
                });


            }
        }).start();

    }

}