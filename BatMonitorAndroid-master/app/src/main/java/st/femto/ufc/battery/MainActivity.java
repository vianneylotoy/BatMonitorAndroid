package st.femto.ufc.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mBatteryLevelText;
    private ProgressBar mBatteryLevelProgress;
    private BroadcastReceiver mReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBatteryLevelText = (TextView) findViewById(R.id.textView);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);
        mReceiver = new BatteryBroadcastReceiver();
    }
    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }
    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent batteryStatus = context.registerReceiver(null, ifilter);


            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            mBatteryLevelText.setText(getString(R.string.battery_level) + " " + level);
            mBatteryLevelProgress.setProgress(level);

            // Are we charging / charged?
            int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;

            // How are we charging?
            int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
            boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

            int  temperature= intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0);
            int  voltage= intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,0);

            Log.i("Broadcast", "level : "+level);
            Log.i("Broadcast", "status : "+status);
            Log.i("Broadcast", "est en charge : "+isCharging);
            Log.i("Broadcast", "chargePlugg : "+chargePlug);
            Log.i("Broadcast", "charge par usb : "+usbCharge);
            Log.i("Broadcast", "charge ac : "+acCharge);
            Log.i("Broadcast", "Temperature : "+temperature);
            Log.i("Broadcast", "voltage : "+voltage);

        }
    }




        /*public static Context getContext() throws NamingException
        {
            Properties jndiProps = new Properties();
            jndiProps.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProps.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            jndiProps.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
            jndiProps.put("jboss.naming.client.ejb.context", true);
            Context ctx = new InitialContext(jndiProps);
            return ctx;
        }

        if (os.equals(LINUX)) {
			linux.battery.BatteryLvls batlvls = new linux.battery.BatteryLvls();
			System.out.println(batlvls.getBatteryLvl());

			//add batterylvls to the list untill your batterylvl decreases then add one batterylvl, then stops
			int i = batlvls.getBatteryLvl();
			while(i == batlvls.getBatteryLvl()) {
				batlvls.addBatteryLvl();
				Thread.sleep(10000);
			}
		*/ /*
        batlvls.addBatteryLvl();
        System.out.println("Battery lvls liste : " + batlvls.getBatteryLvls().toString());

        Context ctx = Main.getContext();
        NamingEnumeration<Binding> e = ctx.listBindings("");
        System.out.println("batmonitor");
        while(e.hasMore())
        {
            Binding xx = e.nextElement();
            System.out.println("jndi " + xx.getName());
        }
        java.util.Date date = new java.util.Date();
        System.out.println("la d: "+date.toString());
        CommunicationRemote remote=(CommunicationRemote)ctx.lookup("BatMonitorEAR/BatMonitorEJB/Communication!connexion.CommunicationRemote");
        remote.createLevel(batlvls.getBatteryLvl(),date, 1);
        System.out.println("Envoi reussi!");
    }


    */

}
