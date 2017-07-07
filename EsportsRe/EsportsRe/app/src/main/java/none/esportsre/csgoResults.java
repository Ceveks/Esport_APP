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

    //GLOBAL VARIABLER

    ListView recentLister;
    ArrayList<String> recentItems=new ArrayList<>();
    ArrayAdapter<String> recentAdapter;
    TextView changeColor;
    Button goback;
    TextView tester;
    String searchedWord;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Laver en back button på denne side
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
        //Henter det holdnavn der er blevet søgt fra når man trykker på "Recent result" på csgo siden.
        Intent intent = getIntent();
        searchedWord = intent.getStringExtra("team");
        recentAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.recent_matches_colors, recentItems);
        recentLister.setAdapter(recentAdapter);
        //Bruger dette holdnavn som parameter  i getData
        getData(searchedWord);

    }


    private void getData(final String teamName) {
        //Kører to threads for at kunne opdatere GUI samtidig.
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                //Sætter hjemmesidenavnet der skal bruges, hvis det er et ukendt holdnavn, tager bare main siden.

                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.hltv.org/results").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String toLowerCase = teamName.toLowerCase();

                //Se om det søgte navn matcher nogen af disse:

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
                //Vælger den <div> med class navnet: results-all
                ele = doc.select("div.results-all");



               //Kig igennem "results-all" efter divs med class navnet: results-sublist


                    for (Element date : ele.select("div.results-sublist")) {
                        
                        //Inde i hver div.results-sublist kig efter en div med class navnet: result
                        for (Element element : date.select("div.result")) {

                                //Tag længden af scoren
                                int leng = element.select("td.result-score").text().length();


                            /*
                            String test = element.select("div.team").first().text() + "   " +
                                    element.select("td.result-score").text() + "   " +
                                    element.select("div.team").last().text();
                                    */


                            //Laver to strings første og anden resultat, sub bliver lavet ved hjælp af substring
                            //e.g. 16 - 4, så tager den fra første index til indekset af "-", og bruger .trim() for at fjerne whitespace
                            String firstResultInString = element.select("td.result-score").text()
                                    .substring(0,element.select("td.result-score").text().indexOf("-")).trim();
                            String secondResultInString = element.select("td.result-score").text()
                                    .substring(element.select("td.result-score").text().indexOf("-")+1, leng).trim();

                            //Parser begge strings til ints
                            int firstResult = Integer.parseInt(firstResultInString);
                            int secondResult = Integer.parseInt(secondResultInString);


                            //Laver en bool for at se om det er et win
                            boolean lOrW = false;

                            //Tjek om første resultat er større end andet, og om holdnavnet matcher det første holdnavn.
                            if (firstResult > secondResult && element.select("div.team").first().text().toLowerCase().contains(toLowerCase)) {
                                /*
                                for(int x=0; x<=alignRight-test.length(); x++) {
                                    builder.append(" ");
                                }
                                */

                                builder.append("W  -  ");
                                lOrW =true;

                            }
                            //Tjek om første andet er større end første, og om holdnavnet matcher det sidste hold navn.
                            if (firstResult < secondResult && element.select("div.team").last().text().toLowerCase().contains(toLowerCase)) {

                                    builder.append("W  -  ");
                                lOrW=true;
                                }
                            //Hvis ikke så bliver der skrevet et L
                                if (lOrW==false){

                                    builder.append("L   -  ");
                                }



                            //Bygger den string der skal sættes ind i listen
                                builder.append(date.select("div.standard-headline").text()).append(element.select("div.team").first().text()).append("   ").
                                        append(element.select("td.result-score").text()).append("   ").
                                        append(element.select("div.team").last().text()).append("  -  ").append(element.select("td.event").text());



                           /* int bLeng = leng +
                                    element.select("div.team").first().text().length() +
                                    element.select("div.team").last().text().length();
*/

                           //Sætter et linjeskift ind
                                builder.append("\n");

                        }
                    }
                //Kører GUI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Laver et array med den string fra før og splitter den ved linjeskift
                            String[] teams = builder.toString().split("\n");
                        
                      //  tester.setText(.toString());
                            
                            //Sætter data ind i GUI'en
                            for (int y = 0; y < teams.length; y++) {

                                        recentItems.add(teams[y]);

                                        recentAdapter.notifyDataSetChanged();



                            }

                    }
                });


            }
        }).start();

    }

}