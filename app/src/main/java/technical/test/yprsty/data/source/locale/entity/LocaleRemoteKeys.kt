package technical.test.yprsty.data.source.locale.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_paging")
data class LocaleRemoteKeys (
    @PrimaryKey
    val id: String,
    val prevKey: Int?,
    val nextKey: Int?,
)