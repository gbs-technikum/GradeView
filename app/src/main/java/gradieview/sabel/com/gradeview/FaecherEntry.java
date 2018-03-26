package gradieview.sabel.com.gradeview;

import java.util.UUID;

/**
 * Created by Philipp Schweiger on 25.03.2018.
 */

public class FaecherEntry {

    private String id, fach;
    private int ausgewaehlt;
    public final static int TRUE = 1;
    public final static int FALSE = 0;



    public FaecherEntry() {
        super();
        this.id = UUID.randomUUID().toString();
    }

    public FaecherEntry(String fach) {
        this();
        this.fach = fach;
        this.ausgewaehlt = FALSE;
    }

    public int getAusgewaehlt() {
        return ausgewaehlt;
    }

    public void setAusgewaehlt(int ausgewaehlt) {
        this.ausgewaehlt = ausgewaehlt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFach() {
        return fach;
    }

    public void setFach(String fach) {
        this.fach = fach;
    }

    @Override
    public String toString() {
        return this.fach;
    }
}
