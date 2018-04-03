package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class FachActivity extends AppCompatActivity {

    private static String fachname;
    private Button buttonSAplus, buttonKAplus, buttonMUEplus;
    private EditText editTextSA, editTextKA;
    private FachDBHelper fachDBHelper;
    private ListView lv_SANoten, lv_KANoten;
    private ArrayAdapter<NotenEntry> arrayAdapterSA, arrayAdapterKA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
        Intent intent = getIntent();
        fachname = intent.getStringExtra("fachname");
        setTitle(fachname);

        buttonSAplus = findViewById(R.id.btnSAplus);
        buttonKAplus = findViewById(R.id.btnKAplus);
        buttonMUEplus = findViewById(R.id.btnMUEplus);

        editTextSA = findViewById(R.id.editTextSA);
        editTextKA = findViewById(R.id.editTextKA);

        lv_SANoten = findViewById(R.id.lv_SANoten);
        lv_KANoten = findViewById(R.id.lv_KANoten);

        arrayAdapterSA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        arrayAdapterKA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        lv_SANoten.setAdapter(arrayAdapterSA);
        lv_KANoten.setAdapter(arrayAdapterKA);

        fachDBHelper = new FachDBHelper(getBaseContext());


        List<NotenEntry> listSA = notenAusDatenbankLesen();
        if (listSA != null) {
            for (NotenEntry notenEntry : listSA) {
                arrayAdapterSA.add(notenEntry);
            }
        }
        lv_SANoten.setVisibility(View.VISIBLE);

        List<NotenEntry> listKA = notenAusDatenbankLesenKA();
        if (listKA != null) {
            for (NotenEntry notenEntry : listKA) {
                arrayAdapterKA.add(notenEntry);
            }
        }
        lv_KANoten.setVisibility(View.VISIBLE);


        buttonSAplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextSA.setVisibility(View.VISIBLE);
                //eigabefeld öffnen
                editTextSA.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editTextSA, 0);
            }
        });

        buttonKAplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextKA.setVisibility(View.VISIBLE);
                editTextKA.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editTextKA, 0);
            }
        });

        editTextSA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextSA.getText().toString();
                    NotenEntry notenEntry = new NotenEntry(new Integer(note));
                    notenInDatenbankSchreiben(notenEntry);
                    arrayAdapterSA.add(notenEntry);
                    arrayAdapterSA.notifyDataSetChanged();
                    lv_SANoten.setVisibility(View.VISIBLE);
                    editTextSA.setVisibility(View.GONE);
                    editTextSA.setText("");
                    //eingabefeld schließen
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                    return true;
                }
                return false;
            }
        });

        editTextKA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextKA.getText().toString();
                    NotenEntry notenEntry = new NotenEntry(new Integer(note));
                    notenInDatenbankSchreibenKA(notenEntry);
                    arrayAdapterKA.add(notenEntry);
                    arrayAdapterKA.notifyDataSetChanged();
                    lv_KANoten.setVisibility(View.VISIBLE);
                    editTextKA.setVisibility(View.GONE);
                    editTextKA.setText("");
                    //eingabefeld schließen
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                    return true;
                }
                return false;
            }
        });


        // Noten löschen
        lv_SANoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotenEntry notenEntry = arrayAdapterSA.getItem(position);
                arrayAdapterSA.remove(notenEntry);
                arrayAdapterSA.notifyDataSetChanged();
                if (position >= 0) {
                    fachDBHelper.getWritableDatabase();
                    return fachDBHelper.deleteNote(fachname, notenEntry);
                }
                return false;
            }
        });

        lv_KANoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotenEntry notenEntry = arrayAdapterKA.getItem(position);
                arrayAdapterKA.remove(notenEntry);
                arrayAdapterKA.notifyDataSetChanged();
                if (position >= 0) {
                    fachDBHelper.getWritableDatabase();
                    return fachDBHelper.deleteNote(fachname, notenEntry);
                }
                return false;
            }
        });

    }

    // Noten aus Datenbank lesen
    private List<NotenEntry> notenAusDatenbankLesen() {
        fachDBHelper.leseRechtDatenbank();
        List<NotenEntry> notenEntries = fachDBHelper.readAllFromFach(getFachname());
        return notenEntries;
    }

    private List<NotenEntry> notenAusDatenbankLesenKA() {
        fachDBHelper.leseRechtDatenbank();
        List<NotenEntry> notenEntries = fachDBHelper.readAllFromFachKA(getFachname());
        return notenEntries;
    }

    // Noten in Datenbank schreiben
    private void notenInDatenbankSchreiben(NotenEntry notenEntry) {
        fachDBHelper.schreibRechteDatenbank();
        fachDBHelper.insertNoten(fachname, notenEntry);
    }

    private void notenInDatenbankSchreibenKA(NotenEntry notenEntry) {
        fachDBHelper.schreibRechteDatenbank();
        fachDBHelper.insertNotenKA(fachname, notenEntry);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fertig:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Menü hinzufügen in Leiste oben rechts
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufaecher, menu);
        return true;
    }

    public String getFachname() {
        return fachname;
    }


}
