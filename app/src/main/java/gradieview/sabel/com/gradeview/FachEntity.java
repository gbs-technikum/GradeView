package gradieview.sabel.com.gradeview;

import android.arch.persistence.room.*;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */

@Entity(tableName = "")

public class FachEntity {
    @PrimaryKey
    private int uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.

}
