package technical.test.yprsty

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import technical.test.yprsty.di.dataLayerModule
import technical.test.yprsty.di.databaseModule
import technical.test.yprsty.di.domainLayerModule
import technical.test.yprsty.di.networkModule
import technical.test.yprsty.di.presentationLayerModule

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NewsApplication)
            loadKoinModules(
                listOf(
                    databaseModule,
                    networkModule,
                    dataLayerModule,
                    domainLayerModule,
                    presentationLayerModule
                )
            )
        }
    }
}