package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class FachActivity extends AppCompatActivity {

    private static String fachname;
    private Button button;
    private EditText editText;
    private TextView txtViewSa;
    private ArrayList<String> notenSa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
        Intent intent = getIntent();
        fachname = intent.getStringExtra("fachname");

        button = findViewById(R.id.btnSAplus);

        editText = findViewById(R.id.editText);

        txtViewSa = findViewById(R.id.txtViewSa);

        notenSa = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setVisibility(View.VISIBLE);
                //eigabefeld öffnen
                editText.requestFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editText, 0);
            }
        });

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_ENTER) {
                    String note = editText.getText().toString();
                    //set text Array
                    notenSa.add(note);
                    String temp = "";
                    for (String s : notenSa) {
                       temp += " "+s;
                    }
                    txtViewSa.setText(temp);
                    txtViewSa.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.GONE);
                    editText.setText("");
                    //eingabefeld schließen
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInputFromWindow(view.getWindowToken(), 0, 0);
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.fertig:
                //speichern();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getFachname() {
        return fachname;
    }


}
