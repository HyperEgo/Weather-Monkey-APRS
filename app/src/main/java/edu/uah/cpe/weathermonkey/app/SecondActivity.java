package fizzsoftware.weathermonkeyaprs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.Arrays;

public class SecondActivity extends Activity {
    private static final int MENU_ITEM1 = 0;
    private static final int MENU_ITEM2 = 1;
    private static final String STATE_ADD_IN_PROGRESS = "shelves.add.inprogress";
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;

    /* renamed from: mt */
    MyClientTask f14mt;
    ProgressBar progress;
    UserData userz;

    public static int getConnectivityStatus(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == 1) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == 0) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        if (conn == TYPE_WIFI) {
            return "Wifi enabled";
        }
        if (conn == TYPE_MOBILE) {
            return "Mobile data enabled";
        }
        if (conn == TYPE_NOT_CONNECTED) {
            return "Not connected to Internet";
        }
        return null;
    }

    public void singleAlert(String title, String message, Drawable icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setIcon(icon).setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    public void goMainActivity(UserData userB) {
        userB.setActFlag(true);
        Intent i = new Intent(getApplicationContext(), main.class);
        i.putExtra("userB", userB);
        setResult(-1, i);
        finish();
    }

    public String filterBrackets(String s) {
        if (s.charAt(0) != '[' || s.charAt(s.length() - 1) != ']') {
            return s;
        }
        return s.substring(s.indexOf("[") + 1, s.lastIndexOf("]"));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0194R.layout.activity_second);
        this.userz = (UserData) getIntent().getSerializableExtra("user");
        this.progress = (ProgressBar) findViewById(C0194R.C0196id.progBar);
        this.progress.setVisibility(4);
    }

    public void onConnect(View v) {
        Button btnConnect = (Button) findViewById(C0194R.C0196id.btnConnect);
        switch (v.getId()) {
            case C0194R.C0196id.btnBack:
                cancelTask();
                this.progress.setVisibility(4);
                goMainActivity(this.userz);
                return;
            case C0194R.C0196id.btnConnect:
                Toast.makeText(this, getConnectivityStatusString(this), 0).show();
                cancelTask();
                clearFields();
                this.f14mt = new MyClientTask();
                this.f14mt.setOutputString(this.userz.getOutput());
                this.f14mt.setServerName(this.userz.getServerName());
                this.f14mt.setServerPort(this.userz.getServerPort());
                this.f14mt.execute(new Void[0]);
                btnConnect.setEnabled(false);
                this.progress.setVisibility(0);
                return;
            case C0194R.C0196id.btnCancel:
                cancelTask();
                btnConnect.setEnabled(true);
                this.progress.setVisibility(4);
                return;
            default:
                return;
        }
    }

    private void cancelTask() {
        if (this.f14mt != null) {
            Log.d("LOG_TAG", "cancel result " + this.f14mt.cancel(false));
        }
    }

    public class MyClientTask extends AsyncTask<Void, String, Void> {

        /* renamed from: in */
        private BufferedReader f15in;
        private String input;
        private BufferedWriter out;
        private String output;
        private String serverName;
        private int serverPort;
        private Socket socket;

        public MyClientTask() {
        }

        public void setServerName(String addr) {
            this.serverName = addr;
        }

        public void setServerPort(int port) {
            this.serverPort = port;
        }

        public void setOutputString(String str) {
            this.output = str;
        }

        /* access modifiers changed from: package-private */
        public String getServerName() {
            return this.serverName;
        }

        /* access modifiers changed from: package-private */
        public int getServerPort() {
            return this.serverPort;
        }

        /* access modifiers changed from: package-private */
        public String getOutputString() {
            return this.output;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r5.socket.close();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Void... r6) {
            /*
                r5 = this;
                r4 = 0
                java.net.Socket r1 = new java.net.Socket     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.lang.String r2 = r5.getServerName()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                int r3 = r5.getServerPort()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.<init>(r2, r3)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r5.socket = r1     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.InputStreamReader r2 = new java.io.InputStreamReader     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.net.Socket r3 = r5.socket     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.InputStream r3 = r3.getInputStream()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r2.<init>(r3)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.<init>(r2)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r5.f15in = r1     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.BufferedWriter r1 = new java.io.BufferedWriter     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.OutputStreamWriter r2 = new java.io.OutputStreamWriter     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.net.Socket r3 = r5.socket     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.OutputStream r3 = r3.getOutputStream()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r2.<init>(r3)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.<init>(r2)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r5.out = r1     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.BufferedWriter r1 = r5.out     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.lang.String r2 = r5.getOutputString()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.write(r2)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.io.BufferedWriter r1 = r5.out     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.flush()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
            L_0x0042:
                java.io.BufferedReader r1 = r5.f15in     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                java.lang.String r1 = r1.readLine()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r5.input = r1     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                if (r1 == 0) goto L_0x0075
                java.util.concurrent.TimeUnit r1 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r2 = 555(0x22b, double:2.74E-321)
                r1.sleep(r2)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                boolean r1 = r5.isCancelled()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                if (r1 == 0) goto L_0x005a
            L_0x0059:
                return r4
            L_0x005a:
                r1 = 1
                java.lang.String[] r1 = new java.lang.String[r1]     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r2 = 0
                java.lang.String r3 = r5.input     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1[r2] = r3     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r5.publishProgress(r1)     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                goto L_0x0042
            L_0x0066:
                r0 = move-exception
                java.lang.Thread r1 = new java.lang.Thread
                fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$1 r2 = new fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$1
                r2.<init>()
                r1.<init>(r2)
                r0.printStackTrace()
                goto L_0x0059
            L_0x0075:
                java.net.Socket r1 = r5.socket     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                r1.close()     // Catch:{ UnknownHostException -> 0x0066, IOException -> 0x007b, InterruptedException -> 0x008a }
                goto L_0x0059
            L_0x007b:
                r0 = move-exception
                java.lang.Thread r1 = new java.lang.Thread
                fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$2 r2 = new fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$2
                r2.<init>()
                r1.<init>(r2)
                r0.printStackTrace()
                goto L_0x0059
            L_0x008a:
                r0 = move-exception
                java.lang.Thread r1 = new java.lang.Thread
                fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$3 r2 = new fizzsoftware.weathermonkeyaprs.SecondActivity$MyClientTask$3
                r2.<init>()
                r1.<init>(r2)
                r0.printStackTrace()
                goto L_0x0059
            */
            throw new UnsupportedOperationException("Method not decompiled: fizzsoftware.weathermonkeyaprs.SecondActivity.MyClientTask.doInBackground(java.lang.Void[]):java.lang.Void");
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            Toast.makeText(SecondActivity.this, "Restart thread", 0).show();
            Log.d("LOG_TAG", "End - close");
            SecondActivity.this.progress.setVisibility(4);
            super.onPostExecute(result);
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... progress) {
            SecondActivity.this.filterView(SecondActivity.this.filterBrackets(Arrays.toString(progress)));
            super.onProgressUpdate(progress);
        }

        /* access modifiers changed from: protected */
        public void onCancelled(Void aVoid) {
            SecondActivity.this.progress.setVisibility(4);
            Log.d("LOG_TAG", "OnCancelled fired");
        }
    }

    /* access modifiers changed from: package-private */
    public void clearFields() {
        ((TextView) findViewById(C0194R.C0196id.txtReport)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtTime)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtTemp)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtWGust)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtWSpeed)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtWDirection)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtBPressure)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtHumidity)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtRainHour)).setText("");
        ((TextView) findViewById(C0194R.C0196id.txtRainNight)).setText("");
    }

    /* access modifiers changed from: package-private */
    public void filterView(String s) {
        TextView txtReport = (TextView) findViewById(C0194R.C0196id.txtReport);
        TextView txtTime = (TextView) findViewById(C0194R.C0196id.txtTime);
        TextView txtTemp = (TextView) findViewById(C0194R.C0196id.txtTemp);
        TextView txtWGust = (TextView) findViewById(C0194R.C0196id.txtWGust);
        TextView txtWSpeed = (TextView) findViewById(C0194R.C0196id.txtWSpeed);
        TextView txtWDirection = (TextView) findViewById(C0194R.C0196id.txtWDirection);
        TextView txtBPressure = (TextView) findViewById(C0194R.C0196id.txtBPressure);
        TextView txtHumidity = (TextView) findViewById(C0194R.C0196id.txtHumidity);
        TextView txtRainHour = (TextView) findViewById(C0194R.C0196id.txtRainHour);
        TextView txtRainNight = (TextView) findViewById(C0194R.C0196id.txtRainNight);
        try {
            if (s.contains(":@") && s.contains(">")) {
                clearFields();
                txtReport.setText(s.substring(0, s.indexOf(">")));
                int j = s.indexOf("/");
                char let1 = s.charAt(j - 1);
                char let2 = s.charAt(j + 9);
                String u = s.substring(s.indexOf(":@") + 2, s.indexOf(":@") + 4);
                txtTime.setText(u + " " + let1 + "  |  " + s.substring(j + 1, j + 3) + " " + let2);
                txtWGust.setText(s.substring(s.indexOf("g") + 1, s.indexOf("g") + 4) + " mph");
                txtTemp.setText(s.substring(s.indexOf("t") + 1, s.indexOf("t") + 4) + " F");
                txtRainHour.setText(s.substring(s.indexOf("r") + 1, s.indexOf("r") + 4) + " 1/100 inch");
                txtRainNight.setText(s.substring(s.indexOf("p") + 1, s.indexOf("p") + 4) + " 1/100 inch");
                txtHumidity.setText(s.substring(s.indexOf("h") + 1, s.indexOf("h") + 3) + " %");
                txtBPressure.setText(s.substring(s.indexOf("b") + 1, s.indexOf("b") + 6) + " millibars / hPascal");
            }
            if (s.contains(":_") && s.contains(">")) {
                clearFields();
                txtReport.setText(s.substring(0, s.indexOf(">")));
                int j2 = s.indexOf(":_");
                txtTime.setText("Date " + s.substring(j2 + 2, j2 + 4) + "/" + s.substring(j2 + 4, j2 + 6) + " Time " + s.substring(j2 + 6, j2 + 8) + ":" + s.substring(j2 + 8, j2 + 10) + " (24hr)");
                txtWGust.setText(s.substring(s.indexOf("g") + 1, s.indexOf("g") + 4) + " mph");
                txtTemp.setText(s.substring(s.indexOf("t") + 1, s.indexOf("t") + 4) + " F");
                txtRainHour.setText(s.substring(s.indexOf("r") + 1, s.indexOf("r") + 4) + " 1/100 inch");
                txtRainNight.setText(s.substring(s.indexOf("p") + 1, s.indexOf("p") + 4) + " 1/100 inch");
                txtHumidity.setText(s.substring(s.indexOf("h") + 1, s.indexOf("h") + 3) + " %");
                txtBPressure.setText(s.substring(s.indexOf("b") + 1, s.indexOf("b") + 6) + " millibars / hPascal");
                txtWDirection.setText(s.substring(s.indexOf("c") + 1, s.indexOf("c") + 4) + " degrees");
                txtWSpeed.setText(s.substring(s.indexOf("s") + 1, s.indexOf("s") + 4) + " mph");
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Parsing oddity", 0).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0194R.C0197menu.menu_main, menu);
        menu.add(0, 0, 0, "Help");
        menu.add(0, 1, 0, "About");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                singleAlert("Help", getResources().getString(C0194R.string.help_alert1), ContextCompat.getDrawable(getBaseContext(), C0194R.C0195drawable.help_circle_su));
                break;
            case 1:
                singleAlert("About", getResources().getString(C0194R.string.about_alert1), ContextCompat.getDrawable(getBaseContext(), C0194R.C0195drawable.duck_inquiry_su));
                break;
            case C0194R.C0196id.action_settings:
                singleAlert("Settings", getResources().getString(C0194R.string.settings_alert1), ContextCompat.getDrawable(getBaseContext(), C0194R.C0195drawable.settings_green_su));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        MyClientTask task = this.f14mt;
        if (task != null && task.getStatus() != AsyncTask.Status.FINISHED) {
            task.cancel(true);
            savedInstanceState.putBoolean(STATE_ADD_IN_PROGRESS, true);
            this.f14mt = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(STATE_ADD_IN_PROGRESS)) {
            this.f14mt = (MyClientTask) new MyClientTask().execute(new Void[0]);
        }
    }
}
