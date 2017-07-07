package none.esportsre;

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
import android.widget.Toast;

import java.util.ArrayList;
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
    Button save;
    CheckBox flip;

    ArrayList<String> selected = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        flip = (CheckBox) findViewById(R.id.flipsid3);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected.size()==4){


                    String[] newArray = selected.toArray(new String[selected.size()]);
                    SharedPreferences teams = getSharedPreferences("favteams", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = teams.edit();
                    editor.putString("team1", newArray[0]);
                    editor.putString("team2", newArray[1]);
                    editor.putString("team3", newArray[2]);
                    editor.putString("team4", newArray[3]);
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
                if(selected.size()==3) {
                    Toast.makeText(getApplicationContext(), "Please select 1 more team", Toast.LENGTH_SHORT).show();

                }
                if(selected.size()==2) {
                    Toast.makeText(getApplicationContext(), "Please select 2 more teams", Toast.LENGTH_SHORT).show();

                }
                if(selected.size()==1) {
                    Toast.makeText(getApplicationContext(), "Please select 3 more teams", Toast.LENGTH_SHORT).show();

                }

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

}
