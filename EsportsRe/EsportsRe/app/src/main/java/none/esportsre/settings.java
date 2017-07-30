package none.esportsre;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;

public class settings extends AppCompatActivity {

    CheckBox fnatic;
    CheckBox north;
    CheckBox faze;
    CheckBox astralis;
    CheckBox navi;
    CheckBox sk;
    CheckBox gambit;
    CheckBox mouse;
    CheckBox liquid;
    CheckBox nip;
    CheckBox cloud;
    CheckBox virtus;
    CheckBox g2;
    CheckBox heroic;
    CheckBox flip;
    CheckBox imm;

    Button save;

    TextView team1Text, team2Text, team3Text, team4Text;
    ImageView team1Img, team2Img, team3Img, team4Img;

    String team1, team2, team3, team4;

    ArrayList<String> selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favteams();

        fnatic = (CheckBox) findViewById(R.id.fnatic);
        north = (CheckBox) findViewById(R.id.northCheck);
        faze = (CheckBox) findViewById(R.id.Faze);
        astralis = (CheckBox) findViewById(R.id.astral);
        navi = (CheckBox) findViewById(R.id.navi);
        sk = (CheckBox) findViewById(R.id.sk);
        gambit = (CheckBox) findViewById(R.id.gambit);
        mouse = (CheckBox) findViewById(R.id.mousesports);
        liquid = (CheckBox) findViewById(R.id.liquid);
        nip = (CheckBox) findViewById(R.id.nip);
        cloud = (CheckBox) findViewById(R.id.Cloud9);
        virtus = (CheckBox) findViewById(R.id.virtuspro);
        g2 = (CheckBox) findViewById(R.id.g2);
        heroic = (CheckBox) findViewById(R.id.heroicCheck);
        save = (Button) findViewById(R.id.saveButt);
        imm = (CheckBox) findViewById(R.id.immortals);
        flip = (CheckBox) findViewById(R.id.flipsid3);
        team1Text =(TextView) findViewById(R.id.team1Text);
        team2Text =(TextView) findViewById(R.id.team2Text);
        team3Text =(TextView) findViewById(R.id.team3Text);
        team4Text =(TextView) findViewById(R.id.team4Text);
        team1Img =(ImageView) findViewById(R.id.team1Img);
        team2Img =(ImageView) findViewById(R.id.team2Img);
        team3Img =(ImageView) findViewById(R.id.team3Img);
        team4Img =(ImageView) findViewById(R.id.team4Img);

        team1Text.setText(team1);
        team2Text.setText(team2);
        team3Text.setText(team3);
        team4Text.setText(team4);



        String mDrawableName1 = team1;
        if (team1.isEmpty()) {
            mDrawableName1 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName1, "drawable", getPackageName());
            team1Img.setBackgroundResource(resID);


        } else {
            int resID = getResources().getIdentifier(mDrawableName1, "drawable", getPackageName());
            team1Img.setBackgroundResource(resID);
        }
        String mDrawableName2 = team2;
        if (team2.isEmpty()) {
            mDrawableName2 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName2, "drawable", getPackageName());
            team2Img.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName2, "drawable", getPackageName());


            team2Img.setBackgroundResource(resID);
        }
        String mDrawableName3 = team3;
        if (team3.isEmpty()) {
            mDrawableName3 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName3, "drawable", getPackageName());
            team3Img.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName3, "drawable", getPackageName());

            team3Img.setBackgroundResource(resID);
        }
        String mDrawableName4 = team4;
        if (team4.isEmpty()) {
            mDrawableName4 = "questionmark";
            int resID = getResources().getIdentifier(mDrawableName4, "drawable", getPackageName());
            team4Img.setBackgroundResource(resID);
        } else {
            int resID = getResources().getIdentifier(mDrawableName4, "drawable", getPackageName());

            team4Img.setBackgroundResource(resID);
        }



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected.size()>0 && selected.size()<5){



                    String[] newArray = selected.toArray(new String[selected.size()]);
                    SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = teams.edit();


                    for(int x=0; x<selected.size(); x++){
                        editor.putString("team"+x, newArray[x]);
                    }
                    /*
                    editor.putString("team1", newArray[0]);
                    editor.putString("team2", newArray[1]);
                    editor.putString("team3", newArray[2]);
                    editor.putString("team4", newArray[3]);
                    */


                    editor.apply();

                    selected.clear();
                    Toast.makeText(getApplicationContext(), "Your favorite teams are saved!", Toast.LENGTH_SHORT).show();

                    if(fnatic.isChecked()){
                        fnatic.toggle();
                    }
                    if(astralis.isChecked()){
                        astralis.toggle();
                    }
                    if(sk.isChecked()){
                        sk.toggle();
                    }
                    if(cloud.isChecked()){
                        cloud.toggle();
                    }
                    if(north.isChecked()){
                        north.toggle();
                    }
                    if(imm.isChecked()){
                        imm.toggle();
                    }
                    if(virtus.isChecked()){
                        virtus.toggle();
                    }
                    if(nip.isChecked()){
                        nip.toggle();
                    }
                    if(g2.isChecked()){
                        g2.toggle();
                    }
                    if(faze.isChecked()){
                        faze.toggle();
                    }
                    if(navi.isChecked()){
                         navi.toggle();
                    }
                    if(liquid.isChecked()){
                        liquid.toggle();
                    }
                    if(mouse.isChecked()){
                        mouse.toggle();
                    }
                    if(gambit.isChecked()){
                        gambit.toggle();
                    }
                    if(heroic.isChecked()){
                        heroic.toggle();
                    }
                    if(flip.isChecked()){
                        flip.toggle();
                    }





                } if(selected.size()>4) {
                    Toast.makeText(getApplicationContext(), "Please only select 4 teams", Toast.LENGTH_SHORT).show();

                }
                /*
                if(selected.size()==3) {
                    Toast.makeText(getApplicationContext(), "Please select 1 more team", Toast.LENGTH_SHORT).show();

                }
                if(selected.size()==2) {
                    Toast.makeText(getApplicationContext(), "Please select 2 more teams", Toast.LENGTH_SHORT).show();

                }

                if(selected.size()<1) {
                    Toast.makeText(getApplicationContext(), "Please select 3 more teams", Toast.LENGTH_SHORT).show();

                }
*/

            }
        });


    }
    public void selectItem(View view){
        boolean checked = ((CheckBox) view).isChecked();
            switch(view.getId()){
                case R.id.fnatic:

                    if(checked) {
                        selected.add("fnatic");
                    }
                    else{
                        selected.remove("fnatic");
                    }
                    break;

                case R.id.immortals:

                    if(checked) {
                        selected.add("immortals");
                    }
                    else{
                        selected.remove("immortals");
                    }
                    break;
                case R.id.northCheck:

                    if(checked) {
                        selected.add("north");
                    }
                    else{
                        selected.remove("north");
                    }
                    break;
                case R.id.Faze:

                    if(checked) {
                        selected.add("faze");
                    }
                    else{
                        selected.remove("faze");
                    }
                    break;
                case R.id.astral:

                    if(checked) {
                        selected.add("astralis");
                    }
                    else{
                        selected.remove("astralis");
                    }
                    break;
                case R.id.navi:

                    if(checked) {
                        selected.add("natus");
                    }
                    else{
                        selected.remove("natus");
                    }
                    break;
                case R.id.sk:

                    if(checked) {
                        selected.add("sk");
                    }
                    else{
                        selected.remove("sk");
                    }
                    break;
                case R.id.gambit:

                    if(checked) {
                        selected.add("gambit");
                    }
                    else{
                        selected.remove("gambit");
                    }
                    break;
                case R.id.mousesports:

                    if(checked) {
                        selected.add("mousesports");
                    }
                    else{
                        selected.remove("mousesports");
                    }
                    break;
                case R.id.liquid:

                    if(checked) {
                        selected.add("liquid");
                    }
                    else{
                        selected.remove("liquid");
                    }
                    break;
                case R.id.nip:

                    if(checked) {
                        selected.add("nip");
                    }
                    else{
                        selected.remove("nip");
                    }
                    break;
                case R.id.Cloud9:

                    if(checked) {
                        selected.add("cloud9");
                    }
                    else{
                        selected.remove("cloud9");
                    }
                    break;
                case R.id.virtuspro:

                    if(checked) {
                        selected.add("virtus");
                    }
                    else{
                        selected.remove("virtus");
                    }
                    break;
                case R.id.g2:

                    if(checked) {
                        selected.add("g2");
                    }
                    else{
                        selected.remove("g2");
                    }
                    break;
                case R.id.heroicCheck:

                    if(checked) {
                        selected.add("heroic");
                    }
                    else{
                        selected.remove("heroic");
                    }
                    break;

                case R.id.flipsid3:

                    if(checked) {
                        selected.add("flipsid3");
                    }
                    else{
                        selected.remove("flipsid3");
                    }
                    break;



            }

    }

    public void favteams(){
        SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);
        team1 = teams.getString("team0", "");
        team2 = teams.getString("team1", "");
        team3 = teams.getString("team2", "");
        team4 = teams.getString("team3", "");

    }



}
