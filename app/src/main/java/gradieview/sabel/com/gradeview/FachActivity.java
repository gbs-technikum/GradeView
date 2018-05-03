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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import java.util.List;


public class FachActivity extends AppCompatActivity {

    private static String fachname;
    //    private Button buttonSAplus, buttonKAplus, buttonMUEplus;
    private EditText editTextSA, editTextKA, editTextMUE;
    private FachDBHelper fachDBHelper;
    private ListView lv_Schulaufgabe, lv_Kurzarbeit, lv_Muendlich;
    private GridView gv_SANoten, gv_KANoten, gv_MUENoten;
    private ArrayAdapter<NotenEntry> arrayAdapterSA, arrayAdapterKA, arrayAdapterMUE;
    private FachActivityAdapter fachActivityAdapter;
    private NotenAdapter notenAdapterSA, notenAdapterKA, notenAdapterMUE;

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
        editTextMUE = findViewById(R.id.editTextMUE);

        gv_SANoten = findViewById(R.id.gv_SANoten);
        gv_KANoten = findViewById(R.id.gv_KANoten);
        gv_MUENoten = findViewById(R.id.gv_MUENoten);

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


        List<NotenEntry> listSA = notenAusDatenbankLesen();
        if (notenAdapterSA == null && listSA != null) {
            notenAdapterSA = new NotenAdapter(this, listSA);

        }
        gv_SANoten.setVisibility(View.VISIBLE);

        List<NotenEntry> listKA = notenAusDatenbankLesenKA();
        if (notenAdapterKA == null && listSA != null) {
            notenAdapterKA = new NotenAdapter(this, listKA);
        }
        gv_KANoten.setVisibility(View.VISIBLE);

        List<NotenEntry> listMUE = notenAusDatenbankLesenMUE();
        if (notenAdapterMUE == null && listSA != null) {
            notenAdapterMUE = new NotenAdapter(this, listMUE);
        }
        gv_MUENoten.setVisibility(View.VISIBLE);

        if (listSA != null && notenAdapterSA != null) {
            notenAdapterSA = new NotenAdapter(FachActivity.this, listSA);
        }
        if (listKA != null && notenAdapterKA != null) {
            notenAdapterKA = new NotenAdapter(FachActivity.this, listKA);
        }
        if (listMUE != null && notenAdapterMUE != null) {
            notenAdapterMUE = new NotenAdapter(FachActivity.this, listMUE);
        }
//        arrayAdapterSA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        arrayAdapterKA = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
//        arrayAdapterMUE = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);


        gv_SANoten.setAdapter(notenAdapterSA);
        gv_KANoten.setAdapter(notenAdapterKA);
        gv_MUENoten.setAdapter(notenAdapterMUE);


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

        editTextSA.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    editTextSA.setVisibility(View.GONE);
                }
            }
        });

        editTextKA.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    editTextKA.setVisibility(View.GONE);
                }
            }
        });

        editTextMUE.setOnFocusChangeListener(new View.OnFocusChangeListener()

        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    editTextMUE.setVisibility(View.GONE);
                }
            }
        });

        editTextSA.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextSA.getText().toString();
                    if (note != null && !(note.equals(""))) {
                        NotenEntry notenEntry = new NotenEntry(new Integer(note));
                        notenInDatenbankSchreiben(notenEntry);
                        notenAdapterSA = new NotenAdapter(FachActivity.this, notenAusDatenbankLesen());
//                        arrayAdapterSA.add(notenEntry);
//                        arrayAdapterSA.notifyDataSetChanged();
                        notenAdapterSA.notifyDataSetChanged();
                        gv_SANoten.setAdapter(notenAdapterSA);
                        gv_SANoten.setVisibility(View.VISIBLE);
                        editTextSA.setVisibility(View.GONE);
                        editTextSA.setText("");
                        //eingabefeld schließen
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                        return true;
                    }
                }
                return false;
            }
        });

        editTextKA.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextKA.getText().toString();
                    if (note != null && !(note.equals(""))) {
                        NotenEntry notenEntry = new NotenEntry(new Integer(note));
                        notenInDatenbankSchreibenKA(notenEntry);
                        notenAdapterKA = new NotenAdapter(FachActivity.this, notenAusDatenbankLesenKA());
//                        arrayAdapterKA.add(notenEntry);
//                        arrayAdapterKA.notifyDataSetChanged();
                        notenAdapterKA.notifyDataSetChanged();
                        gv_KANoten.setAdapter(notenAdapterKA);
                        gv_KANoten.setVisibility(View.VISIBLE);
                        editTextKA.setVisibility(View.GONE);
                        editTextKA.setText("");
                        //eingabefeld schließen
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                        return true;
                    }
                }
                return false;
            }
        });

        editTextMUE.setOnKeyListener(new View.OnKeyListener()

        {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextMUE.getText().toString();
                    if (note != null && !(note.equals(""))) {
                        NotenEntry notenEntry = new NotenEntry(new Integer(note));
                        notenInDatenbankSchreibenMUE(notenEntry);
                        notenAdapterMUE = new NotenAdapter(FachActivity.this, notenAusDatenbankLesenMUE());
//                        arrayAdapterMUE.add(notenEntry);
//                        arrayAdapterMUE.notifyDataSetChanged();
                        notenAdapterMUE.notifyDataSetChanged();
                        gv_MUENoten.setAdapter(notenAdapterMUE);
                        gv_MUENoten.setVisibility(View.VISIBLE);
                        editTextMUE.setVisibility(View.GONE);
                        editTextMUE.setText("");
                        //eingabefeld schließen
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                        return true;
                    }
                }
                return false;
            }
        });

        // Noten löschen
        gv_SANoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                NotenEntry notenEntry = arrayAdapterSA.getItem(position);
                NotenEntry notenEntry = (NotenEntry) notenAdapterSA.getItem(position);
                notenAdapterSA.removeItem(notenEntry);
                notenAdapterSA.notifyDataSetChanged();
//                arrayAdapterSA.remove(notenEntry);
//                arrayAdapterSA.notifyDataSetChanged();
                if (position >= 0) {
                    fachDBHelper.getWritableDatabase();
                    return fachDBHelper.deleteNote(fachname, notenEntry);
                }
                return false;
            }
        });

        gv_KANoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotenEntry notenEntry =(NotenEntry)notenAdapterKA.getItem(position);
                notenAdapterKA.removeItem(notenEntry);
                notenAdapterKA.notifyDataSetChanged();
                if (position >= 0) {
                    fachDBHelper.getWritableDatabase();
                    return fachDBHelper.deleteNote(fachname, notenEntry);
                }
                return false;
            }
        });

        gv_MUENoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()

        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                NotenEntry notenEntry = (NotenEntry) notenAdapterMUE.getItem(position);
                notenAdapterMUE.removeItem(notenEntry);
                notenAdapterMUE.notifyDataSetChanged();
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.fertig:
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    // Menü hinzufügen in Leiste oben rechts
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menufaecher, menu);
//        return true;
//    }

    public String getFachname() {
        return fachname;
    }


}
