package technical.test.yprsty.data.source.locale.room

import androidx.room.Database
import androidx.room.RoomDatabase
import technical.test.yprsty.data.source.locale.entity.LocaleArticle
import technical.test.yprsty.data.source.locale.entity.LocaleRemoteKeys

@Database(
    entities = [LocaleArticle::class, LocaleRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}