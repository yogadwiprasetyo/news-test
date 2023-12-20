package technical.test.yprsty.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import technical.test.yprsty.data.ArticleRepository
import technical.test.yprsty.data.paging.ArticleRemoteMediator
import technical.test.yprsty.data.source.locale.room.NewsDatabase
import technical.test.yprsty.data.source.remote.service.ApiService
import technical.test.yprsty.domain.repository.IArticleRepository
import technical.test.yprsty.domain.usecase.ArticleInteractor
import technical.test.yprsty.domain.usecase.ArticleUseCase
import technical.test.yprsty.presentation.articles.ArticlesViewModel
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<NewsDatabase>().articleDao() }
    factory { get<NewsDatabase>().remoteKeysDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            NewsDatabase::class.java,
            "NewsDatabase.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val dataLayerModule = module {
    factory {
        ArticleRemoteMediator(get(), get())
    }
    single<IArticleRepository> {
        ArticleRepository(get(), get())
    }
}

val domainLayerModule = module {
    factory<ArticleUseCase> {
        ArticleInteractor(get())
    }
}

val presentationLayerModule = module {
    viewModel { ArticlesViewModel(get()) }
}