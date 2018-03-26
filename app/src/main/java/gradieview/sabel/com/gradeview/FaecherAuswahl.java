package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class FaecherAuswahl extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<FaecherEntry> arrayAdapter;
    private ArrayList<FaecherEntry> faecher;
    private ArrayList<String> ausgewaehlteFaecher, vonMainActivity;
    private Intent intent;
    private FachDBHelper fachDBHelper;
    private EditText et_fachHinzufuegen;

    public FaecherAuswahl() {
        this.faecher = new ArrayList<>();
        this.ausgewaehlteFaecher = new ArrayList<>();
        this.vonMainActivity = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher_auswahl);
        et_fachHinzufuegen = findViewById(R.id.et_fachHinzufuegen);

        //Datenbank erstellen
        fachDBHelper = new FachDBHelper(getBaseContext());

        // Intent holen
        intent = getIntent();

        // Array Liste von den Fächern holen, damit diese nicht mehr in der Liste zum hinzufügen der Fächer erscheinen
        vonMainActivity = intent.getStringArrayListExtra("ausgewaehlteFaecher");
        // List View wird hinzugefügt
        listView = findViewById(R.id.lv_faecher);
//        faecher.add("Mathe");


        //Todo Alle Fächer noch hinzufügen
        // Methode zum Fächer hinzufügen
        faecherHinzufuegen();
        // Fächer werden verglichen, damit nicht alle hinzugefügt werden
        // faecherVergleich(vonMainActivity, faecher);

        // ausgewählte Facher werden in Array übergeben und aus Liste gelöscht
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FaecherEntry faecherEntry = faecher.get(position);
                arrayAdapter.remove(faecherEntry);
                arrayAdapter.notifyDataSetChanged();
                faecherEntry.setAusgewaehlt(FaecherEntry.TRUE);
                fachDBHelper.schreibRechteDatenbank();
                fachDBHelper.updateFaecherlisteEingragAusgewaehlt(faecherEntry);
                ausgewaehlteFaecher.add(faecherEntry.getFach());
            }
        });

        et_fachHinzufuegen.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    String fach = et_fachHinzufuegen.getText().toString();
                    if (fach != null && fach.length() > 0) {
                        FaecherEntry faecherEntry = new FaecherEntry(fach);
                        fachDBHelper.getWritableDatabase();
                        if (!(fachDBHelper.insertFachToFaecherliste(faecherEntry))) {

                            Context context = getApplicationContext();
                            CharSequence text = "Fach schon vorhanden";
                            int duration = Toast.LENGTH_LONG;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                            et_fachHinzufuegen.setText("");
                            et_fachHinzufuegen.setFocusable(true);
                            et_fachHinzufuegen.setFocusableInTouchMode(true);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    et_fachHinzufuegen.requestFocus();

                                    et_fachHinzufuegen.setSelection(0, et_fachHinzufuegen.getText().length());
                                }
                            }, 200);


                        } else {

                            et_fachHinzufuegen.setText("");
                            arrayAdapter.add(faecherEntry);
                            arrayAdapter.notifyDataSetChanged();

                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.toggleSoftInputFromWindow(v.getWindowToken(), 0, 0);
                            return true;
                        }
                    }
                }
                return false;
            }
        });

    }

    //todo nach Datenbankanschluss löschen
    // Array mit ausgewählten Fächern an MainActivity.java übergeben und FaecherAuswahl.java wird geschlossen -> zurück zu MainActivity.java
    public void speichern() {
        this.intent = new Intent();
        intent.putStringArrayListExtra("listeFaecher", ausgewaehlteFaecher);
        setResult(RESULT_OK, intent);
        // todo finisch nach button Fertig Menüpunkt
        finish();
    }

    // Menü hinzufügen in Leiste oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufaecher, menu);
        return true;
    }

    // Menüpunkt Fertig Listener, Methode speichern()
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fertig:
                speichern();
                faecherAlsTabelleInDatenbankSchreiben();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void faecherAlsTabelleInDatenbankSchreiben() {
        SQLiteDatabase sqLiteDatabase = fachDBHelper.getWritableDatabase();
        for (String s : ausgewaehlteFaecher) {
            fachDBHelper.createTable(sqLiteDatabase, s);
        }
    }


    //Todo alle Fächer hinzufügen
    // Fächer werden hinzugefügt
    public void faecherHinzufuegen() {
        fachDBHelper.leseRechtDatenbank();

        faecher = (ArrayList) fachDBHelper.readFaecherlisteNotInUse();
        // String Array der Fächer
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, faecher);
        // Array zur ListView hinzufügen
        listView.setAdapter(arrayAdapter);

    }

    // Todo nach Datenbankanschluss löschen
    // Fächer vergleich damit nicht wieder alle Fächer hinzugefügt werden
    public void faecherVergleich(ArrayList<String> vonMainActivity, ArrayList<String> vonFaecherAuswahl) {
        System.out.println(vonMainActivity);
        System.out.println(vonFaecherAuswahl);
        if (vonMainActivity != null || vonFaecherAuswahl != null) {
            for (int i = 0; i < vonMainActivity.size(); i++) {
                System.out.println("äußereSchleife");
                for (int j = 0; j < vonFaecherAuswahl.size(); j++) {
                    System.out.println("InnereSchleife");
                    if (vonMainActivity.get(i).equals(vonFaecherAuswahl.get(j))) {
                        faecher.remove(j);
                    }
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FaecherAuswahl that = (FaecherAuswahl) o;

        if (listView != null ? !listView.equals(that.listView) : that.listView != null)
            return false;
        if (arrayAdapter != null ? !arrayAdapter.equals(that.arrayAdapter) : that.arrayAdapter != null)
            return false;
        if (faecher != null ? !faecher.equals(that.faecher) : that.faecher != null) return false;
        if (ausgewaehlteFaecher != null ? !ausgewaehlteFaecher.equals(that.ausgewaehlteFaecher) : that.ausgewaehlteFaecher != null)
            return false;
        if (vonMainActivity != null ? !vonMainActivity.equals(that.vonMainActivity) : that.vonMainActivity != null)
            return false;
        return intent != null ? intent.equals(that.intent) : that.intent == null;
    }

    @Override
    public int hashCode() {
        int result = listView != null ? listView.hashCode() : 0;
        result = 31 * result + (arrayAdapter != null ? arrayAdapter.hashCode() : 0);
        result = 31 * result + (faecher != null ? faecher.hashCode() : 0);
        result = 31 * result + (ausgewaehlteFaecher != null ? ausgewaehlteFaecher.hashCode() : 0);
        result = 31 * result + (vonMainActivity != null ? vonMainActivity.hashCode() : 0);
        result = 31 * result + (intent != null ? intent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FaecherAuswahl{" +
                "listView=" + listView +
                ", arrayAdapter=" + arrayAdapter +
                ", faecher=" + faecher +
                ", ausgewaehlteFaecher=" + ausgewaehlteFaecher +
                ", vonMainActivity=" + vonMainActivity +
                ", intent=" + intent +
                '}';
    }
}
