package gradieview.sabel.com.gradeview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

public class FachDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Noten.db";
    private SQLiteDatabase db;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " +
                    FachContract.FachEntry.TABLE_FAECHERLISTE
                    + " (" +
                    FachContract.FachEntry._ID + " TEXT PRIMARY KEY, " +
                    FachContract.FachEntry.FACH + " COLLATE NOCASE, " +
                    FachContract.FachEntry.AUSGEWAEHLT + " INTEGER, " +
                    " UNIQUE (" + FachContract.FachEntry.FACH + ")) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FachContract.FachEntry.TABLE_FAECHERLISTE;

    public FachDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void schreibRechteDatenbank() {
        this.db = super.getWritableDatabase();
    }

    public void leseRechtDatenbank() {
        this.db = super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
        FaecherEntry faecherEntry = new FaecherEntry("Mathe");
        db.execSQL("INSERT INTO " + FachContract.FachEntry.TABLE_FAECHERLISTE + " VALUES ('" + faecherEntry.getId() + "', '" + faecherEntry.getFach() + "'," + faecherEntry.getAusgewaehlt() + ")");
        faecherEntry = new FaecherEntry("Deutsch");
        db.execSQL("INSERT INTO " + FachContract.FachEntry.TABLE_FAECHERLISTE + " VALUES ('" + faecherEntry.getId() + "', '" + faecherEntry.getFach() + "'," + faecherEntry.getAusgewaehlt() + ")");
        faecherEntry = new FaecherEntry("Englisch");
        db.execSQL("INSERT INTO " + FachContract.FachEntry.TABLE_FAECHERLISTE + " VALUES ('" + faecherEntry.getId() + "', '" + faecherEntry.getFach() + "'," + faecherEntry.getAusgewaehlt() + ")");
        faecherEntry = new FaecherEntry("Programmieren");
        db.execSQL("INSERT INTO " + FachContract.FachEntry.TABLE_FAECHERLISTE + " VALUES ('" + faecherEntry.getId() + "', '" + faecherEntry.getFach() + "'," + faecherEntry.getAusgewaehlt() + ")");
        faecherEntry = new FaecherEntry("Datenbanken");
        db.execSQL("INSERT INTO " + FachContract.FachEntry.TABLE_FAECHERLISTE + " VALUES ('" + faecherEntry.getId() + "', '" + faecherEntry.getFach() + "'," + faecherEntry.getAusgewaehlt() + ")");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void createTable(SQLiteDatabase db, String tabellenname) {
        if (tabellenname != null && tabellenname.length()> 0) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " +
                    tabellenname
                    + " (" +
                    FachContract.FachEntry._ID + " TEXT PRIMARY KEY, " +
                    FachContract.FachEntry.SCHULAUFGABE + " INTEGER, " +
                    FachContract.FachEntry.KURZARBEIT + " INTEGER, " +
                    FachContract.FachEntry.MÜNDLICH + " INTEGER)");
        }
    }

    public void insertNoten(String fachname, NotenEntry notenEntry) {
        ContentValues values = new ContentValues();
        values.put(FachContract.FachEntry._ID, notenEntry.getId());
        values.put(FachContract.FachEntry.SCHULAUFGABE, notenEntry.getNote());
        this.db.insert(fachname, null, values);
    }
    public void insertNotenKA(String fachname, NotenEntry notenEntry) {
        ContentValues values = new ContentValues();
        values.put(FachContract.FachEntry._ID, notenEntry.getId());
        values.put(FachContract.FachEntry.KURZARBEIT, notenEntry.getNote());
        this.db.insert(fachname,null, values);
    }

    public List<NotenEntry> readAllFromFach(String fachname) {
        List<NotenEntry> list = null;
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.SCHULAUFGABE,
                FachContract.FachEntry.KURZARBEIT,
                FachContract.FachEntry.MÜNDLICH
        };
        if (!fachname.equals("Faecherliste")) {
            Cursor cursor = db.query(fachname, projection, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int note = cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.SCHULAUFGABE));
                    String id = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID));
                    if (note != 0){
                    NotenEntry notenEntry = new NotenEntry(note);
                    notenEntry.setId(id);
                    list.add(notenEntry);}
                }
            }
            if(cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public List<NotenEntry> readAllFromFachKA(String fachname) {
        List<NotenEntry> list = null;
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.SCHULAUFGABE,
                FachContract.FachEntry.KURZARBEIT,
                FachContract.FachEntry.MÜNDLICH
        };
        if (!fachname.equals("Faecherliste")) {
            Cursor cursor = db.query(fachname, projection, null, null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int note = cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.KURZARBEIT));
                    String id = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID));
                    if (note != 0){
                    NotenEntry notenEntry = new NotenEntry(note);
                    notenEntry.setId(id);
                    list.add(notenEntry);}
                }
            }
            if(cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public boolean deleteNote(String fachname, NotenEntry notenEntry) {
        if (fachname != null && notenEntry != null) {
            String entryId = notenEntry.getId();
            db.delete(fachname, "_ID" + " = '" + entryId + "' ", null);
            return true;
        }
        return false;
    }

    public boolean deleteFach(String fachname) {
        if (fachname != null) {
            db.execSQL("DROP TABLE IF EXISTS " + fachname);
            return true;
        }
        return false;
    }

    public List<String> readFaecherInUse() {
        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table';", null);
            List<String> itemIds = new ArrayList<>();

            while (cursor.moveToNext()) {
                String s = cursor.getString(cursor.getColumnIndex("name"));
                itemIds.add(s);
            }
            if(cursor != null) {
                cursor.close();
            }
            return itemIds;
        }
        return null;
    }

    public List<FaecherEntry> readFaecherlisteNotInUse() {
        List<FaecherEntry> list = null;
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.FACH,
                FachContract.FachEntry.AUSGEWAEHLT
        };

        Cursor cursor = db.query(FachContract.FachEntry.TABLE_FAECHERLISTE, projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.AUSGEWAEHLT)) == FaecherEntry.FALSE) {
                    String id = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID));
                    String fach = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry.FACH));
                    int ausgewaehlt = cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.AUSGEWAEHLT));
                    FaecherEntry faecherEntry = new FaecherEntry(fach);
                    faecherEntry.setId(id);
                    faecherEntry.setAusgewaehlt(ausgewaehlt);
                    list.add(faecherEntry);
                }
            }
        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }

    public List<FaecherEntry> readFaecherlisteInUse() {
        List<FaecherEntry> list = null;
        String[] projection = {
                FachContract.FachEntry._ID,
                FachContract.FachEntry.FACH,
                FachContract.FachEntry.AUSGEWAEHLT
        };

        Cursor cursor = db.query(FachContract.FachEntry.TABLE_FAECHERLISTE, projection, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.AUSGEWAEHLT)) == FaecherEntry.TRUE) {
                    String id = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry._ID));
                    String fach = cursor.getString(cursor.getColumnIndex(FachContract.FachEntry.FACH));
                    int ausgewaehlt = cursor.getInt(cursor.getColumnIndex(FachContract.FachEntry.AUSGEWAEHLT));
                    FaecherEntry faecherEntry = new FaecherEntry(fach);
                    faecherEntry.setId(id);
                    faecherEntry.setAusgewaehlt(ausgewaehlt);
                    list.add(faecherEntry);
                }
            }
        }
        if(cursor != null) {
            cursor.close();
        }
        return list;
    }

    public boolean insertFachToFaecherliste(FaecherEntry faecherEntry) {
        ContentValues values = new ContentValues();
        String id = faecherEntry.getId();
        String fach = faecherEntry.getFach();
        values.put(FachContract.FachEntry._ID, id);
        values.put(FachContract.FachEntry.FACH, fach);
        values.put(String.valueOf(FachContract.FachEntry.AUSGEWAEHLT), faecherEntry.getAusgewaehlt());
        try {
            this.db.insertOrThrow(FachContract.FachEntry.TABLE_FAECHERLISTE, null, values);
        }catch (Exception e){

            return false;
        }
        return true;
    }

    public void updateFaecherlisteEintragAusgewaehlt(FaecherEntry faecherEntry){
        ContentValues values = new ContentValues();
        String id = faecherEntry.getId();
        String[]whereArgs = {id};
        int ausgewaehlt = faecherEntry.getAusgewaehlt();
        values.put(FachContract.FachEntry.AUSGEWAEHLT, ausgewaehlt);
        db.update(FachContract.FachEntry.TABLE_FAECHERLISTE, values, FachContract.FachEntry._ID +"=?", whereArgs);
    }
}
