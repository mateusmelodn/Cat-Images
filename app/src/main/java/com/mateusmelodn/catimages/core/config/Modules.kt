package com.mateusmelodn.catimages.core.config

import com.mateusmelodn.catimages.core.api.ImgurApi
import com.mateusmelodn.catimages.core.repo.CatImagesRepository
import com.mateusmelodn.catimages.core.repo.CatImagesRepositoryImp
import com.mateusmelodn.catimages.ui.MainViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * A Koin module encapsulating app-based dependencies.
 */
val appModule = module {

    single {
        androidContext().contentResolver
    }

    single<CatImagesRepository> {
        CatImagesRepositoryImp(get())
    }
}

/**
 * A Koin module encapsulating network-based dependencies.
 */
val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.imgur.com")
            .client(get())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    single {
        get<Retrofit>().create(ImgurApi::class.java)
    }

    single {
        Moshi.Builder()
            .build()
    }

}

/**
 * A Koin module encapsulating viewmodel dependencies.
 */
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}