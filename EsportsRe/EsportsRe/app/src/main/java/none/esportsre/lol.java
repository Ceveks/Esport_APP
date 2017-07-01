package none.esportsre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class lol extends AppCompatActivity {
    TextView tex;
    Button btn;
    EditText search;
    String searchedWord;
    Button goback;
    Button allBut;

    ImageButton ech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lol);
        search = (EditText) findViewById(R.id.Search);

        goback = (Button) findViewById(R.id.goBack);
        btn = (Button) findViewById(R.id.dataGetter);
        tex =  (TextView)findViewById(R.id.textHere);
        ech = (ImageButton)findViewById(R.id.astralis);
        tex.setMovementMethod(new ScrollingMovementMethod());
        allBut = (Button) findViewById(R.id.allMatches);

        allBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ech.setVisibility(View.INVISIBLE);
                tex.setVisibility(View.VISIBLE);
                allBut.setVisibility(View.INVISIBLE);
                getData();
            }
        });


        ech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedWord = "echofox";


                btn.setVisibility(View.INVISIBLE);
                search.setVisibility(View.INVISIBLE);
                goback.setVisibility(View.VISIBLE);
                ech.setVisibility(View.INVISIBLE);
                tex.setVisibility(View.VISIBLE);
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
                    tex.setVisibility(View.VISIBLE);
                    ech.setVisibility(View.INVISIBLE);
                    allBut.setVisibility(View.INVISIBLE);
                }
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText("");
                tex.setText("");
                btn.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                goback.setVisibility(View.INVISIBLE);
                tex.setVisibility(View.INVISIBLE);
                allBut.setVisibility(View.VISIBLE);


                ech.setVisibility(View.VISIBLE);
            }
        });
    }



    private void getData(){


        new Thread(new Runnable() {
            @Override
            public void run() {

                final StringBuilder builder = new StringBuilder();


                try {
                    Document doc = Jsoup.connect("www.lolesports.com/en_US/na-lcs/na_2017_summer/schedule/regular_season/4").get();

                    Elements ele = doc.select("div.ember-view");

                for(Element element :  ele.select("div.ember-view schedule-item hover-trigger regular_season")) {

                   //for (Element element : date.select("div.team-name")) {



                        if (element.select("div.team-name").text().toLowerCase().contains(searchedWord.toLowerCase())) {
                            builder.append("\n").append("\n").append(element.select("div.time").text()).
                                    append("     ").append(element.select("div.team-name").first().text()).
                                    append("  vs.  ").append(element.select("div.team-name").last().text()).
                                    append("    ");




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
                                tex.setText(builder.toString());
                            }
                        }
                    });


            }
        }).start();

    }
}

