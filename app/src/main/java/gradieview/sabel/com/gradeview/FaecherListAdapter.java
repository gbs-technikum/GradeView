package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Leon Arnold on 23.03.2018.
 */

public class FaecherListAdapter extends BaseAdapter {

    private Context context;
    private List<String> faecher;


    public FaecherListAdapter(Context context, List<String> faecher) {
        this.context = context;
        this.faecher = faecher;


    }


    public void deletItem(String i) {
        faecher.remove(i);
    }

    @Override
    public int getCount() {
        return faecher.size();
    }

    @Override
    public Object getItem(int i) {
        return faecher.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.textview_noten, null);
        TextView tvFach = v.findViewById(R.id.tv_fach);
        TextView tvNote = v.findViewById(R.id.tv_durchschnittsnote);
        String fach = String.valueOf(getItem(i));
        FachDBHelper fachDBHelper = new FachDBHelper(context);
        fachDBHelper.leseRechtDatenbank();
        List<NotenEntry> notenEntries = fachDBHelper.readAll(fach);

        double noten = 0;
        if (notenEntries != null && notenEntries.size() > 0) {
            for (int j = 0; j < notenEntries.size(); j++) {
                noten += notenEntries.get(j).getNote();
            }
            noten /= notenEntries.size();
            String durchschnittsnote = String.valueOf(noten);
            if (durchschnittsnote.length() > 3) {
                durchschnittsnote = durchschnittsnote.substring(0, 4);
            }
            tvNote.setText(durchschnittsnote);
        }

        if (faecher != null && faecher.size() > 0) {
            tvFach.setText(faecher.get(i));
        }


        return v;
    }
}
