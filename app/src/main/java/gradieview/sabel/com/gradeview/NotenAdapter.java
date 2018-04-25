package gradieview.sabel.com.gradeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Leon Arnold on 25.04.2018.
 */

public class NotenAdapter extends BaseAdapter {

    private Context context;
    private List<NotenEntry> notenEntries;

    public NotenAdapter(Context context, List<NotenEntry> notenEntries) {
        this.context = context;
        this.notenEntries = notenEntries;
    }

    @Override
    public int getCount() {
        return notenEntries.size();
    }

    @Override
    public Object getItem(int i) {
        return notenEntries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       // View v = View.inflate(context, R.layout.)
        return null;
    }
}
