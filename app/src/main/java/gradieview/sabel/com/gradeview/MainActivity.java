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

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> ausgewaehlteFaecher;
    private String fach;
    private Intent intent;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    public MainActivity() {
        this.ausgewaehlteFaecher = new ArrayList<>();
        fach = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ausgewaehlteFaecher.add("Test");

        listView = findViewById(R.id.lv_faecher);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ausgewaehlteFaecher);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FachActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void faecherHinzufuegen() {
        for (String fach : ausgewaehlteFaecher) {
            arrayAdapter.add(fach);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("onActivityResult MainActivity");
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                ausgewaehlteFaecher = data.getStringArrayListExtra("listeFaecher");
                System.out.println(ausgewaehlteFaecher);
                faecherHinzufuegen();
            }
        }
    }

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


