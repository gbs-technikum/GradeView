package gradieview.sabel.com.gradeview;

        import android.content.Context;

        import java.util.List;

/**
 * Created by Leon Arnold on 23.03.2018.
 */

public class FachPlusNote  {

    private FachDBHelper fachDBHelper;
    private List<String> fachname;
    private List<NotenEntry> durchschnittsnote;

    public FachPlusNote(Context context, String fachname) {
        fachDBHelper = new FachDBHelper(context);
        fachDBHelper.readDatabase();
        this.fachname = fachDBHelper.readFaecher();
        durchschnittsnote = fachDBHelper.readAll(fachname);
    }

    public List<String> getFachname() {
        return fachname;
    }

    public List<NotenEntry> getDurchschnittsnote() {
        return durchschnittsnote;
    }
}
