package gradieview.sabel.com.gradeview;

import android.arch.persistence.room.*;

import java.util.List;

/**
 * Created by Philipp Schweiger on 21.02.2018.
 */
@Dao
public interface FachDAO {

        @Query("SELECT * FROM fachentity")
        List<FachEntity> getAll();

        @Insert
        void insertAll(FachEntity... fachEntities);

        @Update
        void updateUsers(FachEntity... fachEntities);

        @Delete
        void delete(FachEntity fachEntity);

}
