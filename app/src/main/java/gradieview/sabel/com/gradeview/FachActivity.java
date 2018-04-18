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
//    private Button buttonSAplus, buttonKAplus, buttonMUEplus;
    private EditText editTextSA, editTextKA, editTextMUE;
    private FachDBHelper fachDBHelper;
    private ListView lv_SANoten, lv_KANoten, lv_MUENoten, lv_Schulaufgabe, lv_Kurzarbeit, lv_Muendlich;
    private ArrayAdapter<NotenEntry> arrayAdapterSA, arrayAdapterKA, arrayAdapterMUE;
    private FachActivityAdapter fachActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
        Intent intent = getIntent();
        fachname = intent.getStringExtra("fachname");
        setTitle(fachname);
        fachDBHelper = new FachDBHelper(getBaseContext());

//        buttonSAplus = findViewById(R.id.btnSAplus);
//        buttonKAplus = findViewById(R.id.btnKAplus);
//        buttonMUEplus = findViewById(R.id.btnMUEplus);

        editTextSA = findViewById(R.id.editTextSA);
        editTextKA = findViewById(R.id.editTextKA);
        editTextMUE= findViewById(R.id.editTextMUE);

        lv_SANoten = findViewById(R.id.lv_SANoten);
        lv_KANoten = findViewById(R.id.lv_KANoten);
        lv_MUENoten = findViewById(R.id.lv_MUENoten);

        lv_Schulaufgabe = findViewById(R.id.lv_Schulaufgabe);
        fachActivityAdapter = new FachActivityAdapter(this, "Schulaufgabe", editTextSA);
        lv_Schulaufgabe.setAdapter(fachActivityAdapter);
        fachActivityAdapter.notifyDataSetChanged();

        lv_Kurzarbeit = findViewById(R.id.lv_Kurzarbeit);
        fachActivityAdapter = new FachActivityAdapter(this, "Kurzarbeit", editTextKA);
        lv_Kurzarbeit.setAdapter(fachActivityAdapter);
        fachActivityAdapter.notifyDataSetChanged();

        lv_Muendlich = findViewById(R.id.lv_Muendlich);
        fachActivityAdapter = new FachActivityAdapter(this, "Mündlich", editTextMUE);
        lv_Muendlich.setAdapter(fachActivityAdapter);
        fachActivityAdapter.notifyDataSetChanged();





        arrayAdapterSA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        arrayAdapterKA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        arrayAdapterMUE = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        lv_SANoten.setAdapter(arrayAdapterSA);
        lv_KANoten.setAdapter(arrayAdapterKA);
        lv_MUENoten.setAdapter(arrayAdapterMUE);



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

        List<NotenEntry> listMUE = notenAusDatenbankLesenMUE();
        if (listKA != null) {
            for (NotenEntry notenEntry : listMUE) {
                arrayAdapterMUE.add(notenEntry);
            }
        }
        lv_MUENoten.setVisibility(View.VISIBLE);


//        buttonSAplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editTextSA.setVisibility(View.VISIBLE);
//                //eigabefeld öffnen
//                editTextSA.requestFocus();
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.showSoftInput(editTextSA, 0);
//            }
//        });

//        buttonKAplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editTextKA.setVisibility(View.VISIBLE);
//                editTextKA.requestFocus();
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.showSoftInput(editTextKA, 0);
//            }
//        });

//        buttonMUEplus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editTextMUE.setVisibility(View.VISIBLE);
//                editTextMUE.requestFocus();
//                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.showSoftInput(editTextMUE, 0);
//            }
//        });

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

        editTextMUE.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextMUE.getText().toString();
                    NotenEntry notenEntry = new NotenEntry(new Integer(note));
                    notenInDatenbankSchreibenMUE(notenEntry);
                    arrayAdapterMUE.add(notenEntry);
                    arrayAdapterMUE.notifyDataSetChanged();
                    lv_MUENoten.setVisibility(View.VISIBLE);
                    editTextMUE.setVisibility(View.GONE);
                    editTextMUE.setText("");
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

        lv_MUENoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotenEntry notenEntry = arrayAdapterMUE.getItem(position);
                arrayAdapterMUE.remove(notenEntry);
                arrayAdapterMUE.notifyDataSetChanged();
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
        List<NotenEntry> notenEntries = fachDBHelper.readAllFromFachSA(getFachname());
        return notenEntries;
    }

    private List<NotenEntry> notenAusDatenbankLesenKA() {
        fachDBHelper.leseRechtDatenbank();
        List<NotenEntry> notenEntries = fachDBHelper.readAllFromFachKA(getFachname());
        return notenEntries;
    }

    private List<NotenEntry> notenAusDatenbankLesenMUE() {
        fachDBHelper.leseRechtDatenbank();
        List<NotenEntry> notenEntries = fachDBHelper.readAllFromFachMUE(getFachname());
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

    private void notenInDatenbankSchreibenMUE(NotenEntry notenEntry) {
        fachDBHelper.schreibRechteDatenbank();
        fachDBHelper.insertNotenMUE(fachname, notenEntry);
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
