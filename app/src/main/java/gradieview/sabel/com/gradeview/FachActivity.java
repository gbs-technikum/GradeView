package gradieview.sabel.com.gradeview;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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


import java.util.ArrayList;
import java.util.List;


public class FachActivity extends AppCompatActivity {

    private static String fachname;
    private Button buttonSAplus;
    private EditText editTextSA;
    private FachDBHelper fachDBHelper;
    private ListView lv_SANoten;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
        Intent intent = getIntent();
        fachname = intent.getStringExtra("fachname");

        buttonSAplus = findViewById(R.id.btnSAplus);

        editTextSA = findViewById(R.id.editTextSA);

        lv_SANoten = findViewById(R.id.lv_SANoten);

        fachDBHelper = new FachDBHelper(getBaseContext());

        if (notenAusDatenbankLesen()) {
            lv_SANoten.setVisibility(View.VISIBLE);
        }


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

        editTextSA.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editTextSA.getText().toString();
                    notenInDatenbankSchreiben(new Integer(note));
                    notenAusDatenbankLesen();
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

//        lv_SANoten.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                arrayAdapter.remove(arrayAdapter.getItem(position));
//
//                arrayAdapter.notifyDataSetChanged();
//            }
//        });


        // Noten löschen
        // Todo Tabelle löschen und neu schreiben
        lv_SANoten.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                arrayAdapter.remove(arrayAdapter.getItem(position));
                arrayAdapter.notifyDataSetChanged();
                SQLiteDatabase db = fachDBHelper.getWritableDatabase();
                if (position > 0) {
//                    fachDBHelper.onUpgrade(db,1,2);
                    db.delete(fachname, "_ID" + "=" + position, null);
//                System.out.println(FachContract.FachEntry._ID + "=" + (position + 1));
                    notenAusDatenbankLesen();
                    return true;
                }
                return false;
            }
        });

    }

    private boolean notenAusDatenbankLesen() {
        SQLiteDatabase db = fachDBHelper.getReadableDatabase();
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.COLUMN_NAME_TITLE_1,
                FachContract.FachEntry.COLUMN_NAME_TITLE_2,
                FachContract.FachEntry.COLUMN_NAME_TITLE_3
        };

        Cursor cursor = db.query(fachname, projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            List<String> notenSA = new ArrayList<>();
            notenSA.add("Schulaufgabe");
            while (cursor.moveToNext()) {
                notenSA.add(cursor.getString(cursor.getColumnIndex(FachContract.FachEntry.COLUMN_NAME_TITLE_1)));
                System.out.println(cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID)));
            }
            arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notenSA);
            lv_SANoten.setAdapter(arrayAdapter);

        } else {
            return false;
        }
        return true;
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

    // Noten in Datenbank schreiben
    private void notenInDatenbankSchreiben(int note) {
        SQLiteDatabase db = fachDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FachContract.FachEntry.COLUMN_NAME_TITLE_1, note);
        long newRowId = db.insert(fachname, null, values);
        System.out.println(newRowId);
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
