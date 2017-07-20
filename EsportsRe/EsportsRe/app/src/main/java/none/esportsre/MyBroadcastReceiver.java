package none.esportsre;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MyBroadcastReceiver extends BroadcastReceiver {
    final StringBuilder matchBuilder = new StringBuilder();
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences teams = context.getSharedPreferences("favteams", Context.MODE_PRIVATE);
        try {
            String getteam1 = teams.getString("team1", "");
            String getteam2 = teams.getString("team2", "");
            String getteam3 = teams.getString("team3", "");
            String getteam4 = teams.getString("team4", "");

            getData(context, getteam1, getteam2, getteam3, getteam4);
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!matchBuilder.toString().isEmpty()) {
            String[] splitter = matchBuilder.toString().split("#");

            if(isItTimeToSendANotification(splitter[1].split(":")[0], splitter[1].substring(splitter[1].indexOf(":")+1, 5).trim())){

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


                final StringBuilder newMatchBuilder = new StringBuilder();
                for (String pet : splitter) {

                    if (!pet.contains("weeks") || !pet.contains("week")
                            || !pet.contains("days") || !pet.contains("day")
                            ) {

                        newMatchBuilder.append(pet.split("||")[0]).append("#");
                    }

                }
                String[] newSplitter = newMatchBuilder.toString().split("#");
                String Setter;
                if (!splitter[1].isEmpty()) {
                    Setter = splitter[1].split("-")[0] + " || " + splitter[1].split("-")[1];
                } else {
                    Setter = "No match";
                }
                Intent intent1 = new Intent(context, main.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context).
                        setSmallIcon(R.drawable.csgo).
                        setContentIntent(pendingIntent).
                        setContentText(Setter).
                        setContentTitle("Upcoming match").

                        setAutoCancel(true);
                notificationManager.notify(0, builder.build());
            }

        }
    }



    private void getData(final Context con, final String getteam1, final String getteam2, final String getteam3, final String getteam4) {

        new Thread(new Runnable() {
            public void run() {


                try {




                    Document doc = Jsoup.connect("https://www.hltv.org/matches").get();


                    Elements ele;

                    ele = doc.select("div.upcoming-matches");
                    for (Element date : ele.select("div.match-day")) {
                        for (Element element : date.select("table.table")) {


                            if (element.select("div.team").text().toLowerCase().trim().contains(getteam1.toLowerCase().trim())
                                    || element.select("div.team").text().toLowerCase().trim().contains(getteam2.toLowerCase().trim())
                                    || element.select("div.team").text().toLowerCase().trim().contains(getteam3.toLowerCase().trim())
                                    || element.select("div.team").text().toLowerCase().trim().contains(getteam4.toLowerCase().trim()))


                            {
                                String currentDate = parseDateToddMMyyyy(date.select("span.standard-headline").text());

                                String daysLeft = getDifference(date.select("span.standard-headline").text(), element.select("div.time").text());

                                matchBuilder.append("#").append(element.select("div.time").text()).
                                        append("  -  ").append(element.select("div.team").first().text()).
                                        append("  vs.  ").append(element.select("div.team").last().text());
                                int leng = element.select("div.team").first().text().length() + element.select("div.team").last().text().length();

                                if (leng > 32) {


                                    matchBuilder.append("   - ").append("   date:   ").
                                            append(currentDate).append(" || ").append(daysLeft).append("\n");

                                } else {

                                    matchBuilder.append("   -                                               ").append("   date:   ").
                                            append(currentDate).append(" || ").append(daysLeft).append("\n");
                                }

                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();

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

    public Boolean isItTimeToSendANotification(String hou, String min) {

        int hours = Integer.parseInt(hou.trim());
        int minutes = Integer.parseInt(min.trim());

        DateTime startDate = DateTime.now();
        DateTime endDate = new DateTime(startDate.getYear(),startDate.getMonthOfYear(), startDate.getDayOfMonth(),  hours, minutes);

        Period period = new Period(startDate, endDate);


        if (period.getHours() < 1) return true;
        else return false;
    }



}







