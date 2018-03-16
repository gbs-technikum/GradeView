package gradieview.sabel.com.gradeview;

import java.util.UUID;

/**
 * Created by Philipp Schweiger on 16.03.2018.
 */

public class NotenEntry {

    private String id;
    private int note;

    public NotenEntry() {
        super();
        this.id =UUID.randomUUID().toString();
    }

    public NotenEntry(int note) {
        this();
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
