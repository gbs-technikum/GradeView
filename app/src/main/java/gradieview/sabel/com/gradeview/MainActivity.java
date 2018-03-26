package gradieview.sabel.com.gradeview;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> ausgewaehlteFaecher;
    private String fach;
    private Intent intent;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private FachDBHelper fachDBHelper;
    private FaecherListAdapter faecherListAdapter;


    public MainActivity() {
        this.ausgewaehlteFaecher = new ArrayList<>();
        fach = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Todo Darf nur aufgerufen werden wenn man aus FaecherAuswahl.class kommt
        faecherAusDBlesenUndInLVhinzufuegen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fachDBHelper != null) {
            fachDBHelper.close();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fachDBHelper = new FachDBHelper(getBaseContext());

        // ListView
        listView = findViewById(R.id.lv_faecher);

        faecherAusDBlesenUndInLVhinzufuegen();
//        // String Array der ausgewählten Fächer
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ausgewaehlteFaecher);

        // Array zur ListView hinzufügen
        //       listView.setAdapter(arrayAdapter);


        // Beim Clicken eines Items der ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ausgewaehltesFach = listView.getItemAtPosition(position).toString();
                Intent intent = new Intent(MainActivity.this, FachActivity.class);
                intent.putExtra("fachname", ausgewaehltesFach);
                MainActivity.this.startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String eintrag = (String) faecherListAdapter.getItem(position);
                loescheFachAusListe(eintrag);
                return true;
            }
        });

    }



    private void loescheFachAusListe(final String eintrag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Wollen Sie das Fach wirklich löschen?");
        builder.setTitle("Warnung");
        AlertDialog dialog = builder.create();
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                faecherListAdapter.deletItem(eintrag);
                faecherListAdapter.notifyDataSetChanged();

                //arrayAdapter.remove(eintrag);
                //arrayAdapter.notifyDataSetChanged();
                fachDBHelper.getWritableDatabase();

                fachDBHelper.deleteFach(eintrag);
                //dialog.show();
            }
        });
        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void faecherAusDBlesenUndInLVhinzufuegen() {
        fachDBHelper.leseRechtDatenbank();
//        Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
        List<String> itemIds = fachDBHelper.readFaecherInUse();
//        while (cursor.moveToNext()) {
//            String s = cursor.getString(cursor.getColumnIndex("name"));
//            itemIds.add(s);
//        }
//        cursor.close();

        ausgewaehlteFaecher = (ArrayList<String>) itemIds;
        //todo liste mit noten hinzufuegen, anstatt null
        faecherListAdapter = new FaecherListAdapter(this, ausgewaehlteFaecher);
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ausgewaehlteFaecher);
//        listView.setAdapter(arrayAdapter);
        listView.setAdapter(faecherListAdapter);
        faecherListAdapter.deletItem("android_metadata");
        faecherListAdapter.deletItem("Faecherliste");
        faecherListAdapter.notifyDataSetChanged();
//        arrayAdapter.remove("android_metadata");
//        arrayAdapter.remove("Test");

//        arrayAdapter.notifyDataSetChanged();


    }


    // Ausgewählte Fächer von FaecherAuswahl.java hinzufügen
//    private void faecherHinzufuegen() {
//        for (String fach : ausgewaehlteFaecher) {
//            arrayAdapter.add(fach);
//        }
//    }

    // Menü hinzufügen (rechts oben in Leiste)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Menü unterpunkt "Fächer hinzufügen" Listener
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fachHinzufuegen:
                intent = new Intent(this, FaecherAuswahl.class);
                intent.putStringArrayListExtra("ausgewaehlteFaecher", ausgewaehlteFaecher);
                setResult(RESULT_OK, intent);
                startActivityForResult(intent, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Array Liste von FaecherAuswahl.java mit dem Inhalt der ausgewählten Fächer
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("onActivityResult MainActivity");
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                ausgewaehlteFaecher = data.getStringArrayListExtra("listeFaecher");
//                System.out.println(ausgewaehlteFaecher);
//                faecherHinzufuegen();
//            }
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MainActivity that = (MainActivity) o;

        if (ausgewaehlteFaecher != null ? !ausgewaehlteFaecher.equals(that.ausgewaehlteFaecher) : that.ausgewaehlteFaecher != null)
            return false;
        if (fach != null ? !fach.equals(that.fach) : that.fach != null) return false;
        if (intent != null ? !intent.equals(that.intent) : that.intent != null) return false;
        if (listView != null ? !listView.equals(that.listView) : that.listView != null)
            return false;
        return arrayAdapter != null ? arrayAdapter.equals(that.arrayAdapter) : that.arrayAdapter == null;
    }

    @Override
    public int hashCode() {
        int result = ausgewaehlteFaecher != null ? ausgewaehlteFaecher.hashCode() : 0;
        result = 31 * result + (fach != null ? fach.hashCode() : 0);
        result = 31 * result + (intent != null ? intent.hashCode() : 0);
        result = 31 * result + (listView != null ? listView.hashCode() : 0);
        result = 31 * result + (arrayAdapter != null ? arrayAdapter.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MainActivity{" +
                "ausgewaehlteFaecher=" + ausgewaehlteFaecher +
                ", fach='" + fach + '\'' +
                ", intent=" + intent +
                ", listView=" + listView +
                ", arrayAdapter=" + arrayAdapter +
                '}';
    }
}


