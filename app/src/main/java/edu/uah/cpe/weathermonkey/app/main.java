package fizzsoftware.weathermonkeyaprs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class main extends Activity {
    public static final int APRS_PORT = 14580;
    public static final String APRS_SERVER1 = "rotate.aprs.net";
    public static final String APRS_SERVER2 = "rotate.aprs2.net";
    public static final double HSV_DLAT = 34.73d;
    public static final double HSV_DLONG = -86.58d;
    public static final String HSV_NAME = "Huntsville, AL";
    private static final int MENU_ITEM1 = 0;
    private static final int MENU_ITEM2 = 1;
    public static final int RADIUS1 = 700;
    public static final int RADIUS2 = 500;
    public static final int RADIUS3 = 300;
    public static final int RADIUS4 = 1000;
    public static final int UAH_PORT = 20154;
    public static final String UAH_SERVER = "blackhawk.ece.uah.edu";
    private Locale locz;
    /* access modifiers changed from: private */
    public Spinner spin;
    private UserData user;

    public void singleAlert(String title, String message, Drawable icon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setIcon(icon).setMessage(message).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    public void initializeGUI() {
        ((RadioButton) ((RadioGroup) findViewById(C0194R.C0196id.rdbGp2)).getChildAt(0)).setChecked(true);
        ((RadioButton) ((RadioGroup) findViewById(C0194R.C0196id.rdbGp1)).getChildAt(0)).setChecked(true);
        setLoc(new Locale(HSV_NAME, 34.73d, -86.58d, false));
        ImageView txtStart = (ImageView) findViewById(C0194R.C0196id.txtStart);
        txtStart.setFocusable(true);
        txtStart.setFocusableInTouchMode(true);
        txtStart.requestFocus();
    }

    public void setLoc(Locale local) {
        this.locz = local;
    }

    public boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public static void hideSoftKeyboard(Activity act) {
        ((InputMethodManager) act.getSystemService("input_method")).hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {
        final TextView txtLocale = (TextView) findViewById(C0194R.C0196id.txtLocale);
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    txtLocale.setFocusable(true);
                    txtLocale.setFocusableInTouchMode(true);
                    txtLocale.requestFocus();
                    main.hideSoftKeyboard(main.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setupUI(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    public void addKeyListener() {
        final TextView txtLocale = (TextView) findViewById(C0194R.C0196id.txtLocale);
        ((EditText) findViewById(C0194R.C0196id.txtCallsign)).setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != 0 || keyCode != 66) {
                    return false;
                }
                txtLocale.setFocusable(true);
                txtLocale.setFocusableInTouchMode(true);
                txtLocale.requestFocus();
                main.hideSoftKeyboard(main.this);
                return true;
            }
        });
    }

    public void addItemsToSpinner() {
        List<Locale> list = new ArrayList<>();
        list.add(new Locale(HSV_NAME, 34.73d, -86.58d, false));
        list.add(new Locale("Albany, NY", 42.65d, -73.75d, false));
        list.add(new Locale("Birmingham, AL", 33.52d, -86.81d, false));
        list.add(new Locale("Bismarck, ND", 46.81d, -100.77d, false));
        list.add(new Locale("Chicago, IL", 41.83d, -87.68d, false));
        list.add(new Locale("Dallas, TX", 32.77d, -96.79d, false));
        list.add(new Locale("Las Vegas, CA", 36.12d, -115.17d, false));
        list.add(new Locale("Los Angles, CA", 34.05d, -118.25d, false));
        list.add(new Locale("Mobile, AL", 30.69d, -88.04d, false));
        list.add(new Locale("Nashville, TN", 36.16d, -86.78d, false));
        list.add(new Locale("Seattle, WA", 47.6d, -122.33d, false));
        list.add(new Locale("=>> Custom Location <<=", 0.0d, 0.0d, true));
        ArrayAdapter<Locale> dataAdapter = new ArrayAdapter<>(this, C0194R.layout.spinner_layout, list);
        dataAdapter.setDropDownViewResource(C0194R.layout.spinner_drop_down);
        this.spin.setAdapter(dataAdapter);
    }

    public void getServerSelection(View view) {
        switch (((RadioGroup) findViewById(C0194R.C0196id.rdbGp1)).getCheckedRadioButtonId()) {
            case C0194R.C0196id.rdb1:
                this.user.setServerName(APRS_SERVER1);
                this.user.setServerPort(APRS_PORT);
                return;
            case C0194R.C0196id.rdb2:
                this.user.setServerName(APRS_SERVER2);
                this.user.setServerPort(APRS_PORT);
                return;
            case C0194R.C0196id.rdb3:
                this.user.setServerName(UAH_SERVER);
                this.user.setServerPort(UAH_PORT);
                this.user.setServerFlag(true);
                return;
            default:
                Toast.makeText(this, "Bad server selection", 0).show();
                return;
        }
    }

    public void getRadiusSelection(View view) {
        switch (((RadioGroup) findViewById(C0194R.C0196id.rdbGp2)).getCheckedRadioButtonId()) {
            case C0194R.C0196id.rdb4:
                this.user.setRadioId(RADIUS4);
                return;
            case C0194R.C0196id.rdb5:
                this.user.setRadioId(RADIUS1);
                return;
            case C0194R.C0196id.rdb6:
                this.user.setRadioId(RADIUS2);
                return;
            case C0194R.C0196id.rdb7:
                this.user.setRadioId(RADIUS3);
                return;
            default:
                Toast.makeText(this, "Bad radius selection", 0).show();
                return;
        }
    }

    public void onClick(View view) {
        EditText txtCallsign = (EditText) findViewById(C0194R.C0196id.txtCallsign);
        if (isEmpty(txtCallsign)) {
            txtCallsign.setError("Empty Call");
            txtCallsign.setHint("Empty Callsign");
            txtCallsign.setHintTextColor(getResources().getColor(C0194R.color.button_material_dark));
            return;
        }
        txtCallsign.setError((CharSequence) null);
        this.user.setCallSign(txtCallsign.getText().toString());
        getServerSelection(view);
        getRadiusSelection(view);
        this.user.setOutput("user " + this.user.getCallSign() + " pass -1 vers testsoftware 1.0_05 filter r/" + this.locz.getLatit() + "/" + this.locz.getLongit() + "/" + Integer.toString(this.user.getRadioId()) + "\n");
        Intent i = new Intent(getApplicationContext(), SecondActivity.class);
        i.putExtra("user", this.user);
        startActivityForResult(i, 1);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0194R.layout.activity_main);
        initializeGUI();
        this.user = new UserData();
        this.spin = (Spinner) findViewById(C0194R.C0196id.spinLocale);
        setupUI((ScrollView) findViewById(C0194R.C0196id.parent));
        addKeyListener();
        addItemsToSpinner();
        this.spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Locale loct = (Locale) adapterView.getItemAtPosition(i);
                if (loct.getKey()) {
                    View promptsView = LayoutInflater.from(main.this.getApplicationContext()).inflate(C0194R.layout.alert_map, (ViewGroup) null);
                    AlertDialog.Builder alertDia = new AlertDialog.Builder(main.this);
                    alertDia.setTitle(main.this.getResources().getString(C0194R.string.title_map));
                    alertDia.setIcon(C0194R.C0195drawable.chimp_stare_48);
                    alertDia.setView(promptsView);
                    alertDia.setCancelable(false);
                    final EditText inLat = (EditText) promptsView.findViewById(C0194R.C0196id.txtLat);
                    final EditText inLong = (EditText) promptsView.findViewById(C0194R.C0196id.txtLong);
                    ImageView img = (ImageView) promptsView.findViewById(C0194R.C0196id.imgMap);
                    img.setFocusable(true);
                    img.setFocusableInTouchMode(true);
                    img.requestFocus();
                    img.setOnTouchListener(new View.OnTouchListener() {
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            int x = (int) motionEvent.getX();
                            LLfromXY util = new LLfromXY();
                            util.setMapValues(420.0d, 760.0d, 50.0d, 24.0d, -126.0d, -66.0d);
                            double userLat = util.getLatitude((int) motionEvent.getY());
                            double userLon = util.getLongitude(x);
                            inLat.setText(Double.toString(userLat));
                            inLong.setText(Double.toString(userLon));
                            return false;
                        }
                    });
                    alertDia.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            main.this.setLoc(new Locale(main.HSV_NAME, 34.73d, -86.58d, false));
                            main.this.spin.setSelection(0);
                        }
                    });
                    alertDia.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Locale dummy = new Locale();
                            if (main.this.isEmpty(inLat)) {
                                dummy.setLatit(34.73d);
                            } else {
                                dummy.setLatit(Double.parseDouble(inLat.getText().toString()));
                            }
                            if (main.this.isEmpty(inLong)) {
                                dummy.setLongit(-86.58d);
                            } else {
                                dummy.setLongit(Double.parseDouble(inLong.getText().toString()));
                            }
                            main.this.setLoc(dummy);
                        }
                    });
                    alertDia.create().show();
                    return;
                }
                main.this.setLoc(loct);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == -1) {
            this.user = (UserData) data.getSerializableExtra("userB");
            ((EditText) findViewById(C0194R.C0196id.txtCallsign)).setText(this.user.getCallSign());
        }
    }
}
