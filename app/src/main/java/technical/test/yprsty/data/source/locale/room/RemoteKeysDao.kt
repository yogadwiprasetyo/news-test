package technical.test.yprsty.data.source.locale.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import technical.test.yprsty.data.source.locale.entity.LocaleRemoteKeys

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<LocaleRemoteKeys>)

    @Query("SELECT * FROM remote_keys_paging WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): LocaleRemoteKeys?

    @Query("DELETE FROM remote_keys_paging")
    suspend fun deleteRemoteKeys()
}