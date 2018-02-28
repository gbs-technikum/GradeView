package gradieview.sabel.com.gradeview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class FachActivity extends AppCompatActivity {

    private static String fachname;
    private LinearLayout linearLayout;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
        Intent intent = getIntent();
        fachname = intent.getStringExtra("fachname");

        linearLayout = findViewById(R.id.linearLayout);
        button = findViewById(R.id.btnSAplus);
        button.setOnClickListener(onClick());
        editText = findViewById(R.id.editText);

        TextView textView = new TextView(this);
        textView.setText("new text");


    }

    private View.OnClickListener onClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.addView(createNewTextView(editText.getText().toString()));
            }
        };
    }

    private TextView createNewTextView(String text){
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText(text);
        return textView;
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
