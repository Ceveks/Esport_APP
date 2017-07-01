package none.esportsre; /**
 * Created by Marius on 27-06-2017.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Triggered", Toast.LENGTH_SHORT).show();
    }
}
