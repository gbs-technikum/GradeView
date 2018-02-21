package gradieview.sabel.com.gradeview;

import android.content.Intent;
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

public class FaecherAuswahl extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> faecher, ausgewaehlteFaecher, vonMainActivity;
    private Intent intent;

    public FaecherAuswahl() {
        this.faecher = new ArrayList<>();
        this.ausgewaehlteFaecher = new ArrayList<>();
        this.vonMainActivity = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faecher_auswahl);

        // Intent holen
        intent = getIntent();

        // Array Liste von den Fächern holen, damit diese nicht mehr in der Liste zum hinzufügen der Fächer erscheinen
        vonMainActivity = intent.getStringArrayListExtra("ausgewaehlteFaecher");

        //Todo Alle Fächer noch hinzufügen
        // Methode zum Fächer hinzufügen
        faecherHinzufuegen();
        // Fächer werden verglichen, damit nicht alle hinzugefügt werden
        faecherVergleich(vonMainActivity, faecher);

        // List View wird hinzugefügt
        listView = findViewById(R.id.lv_faecher);
//        faecher.add("Mathe");
        // String Array der Fächer
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, faecher);
        // Array zur ListView hinzufügen
        listView.setAdapter(arrayAdapter);

        // ausgewählte Facher werden in Array übergeben und aus Liste gelöscht
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String fach = faecher.get(position);
                arrayAdapter.remove(fach);
                ausgewaehlteFaecher.add(fach);
            }
        });
    }

    // Array mit ausgewählten Fächern an MainActivity.java übergeben und FaecherAuswahl.java wird geschlossen -> zurück zu MainActivity.java
    public void speichern() {
        this.intent = new Intent();
        intent.putStringArrayListExtra("listeFaecher", ausgewaehlteFaecher);
        setResult(RESULT_OK, intent);
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Todo alle Fächer hinzufügen
    // Fächer werden hinzugefügt
    public void faecherHinzufuegen() {
        faecher.add("Mathe");
        faecher.add("Deutsch");
    }

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
