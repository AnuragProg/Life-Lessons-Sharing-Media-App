package com.android.personallifelessons.di

import androidx.work.WorkManager
import com.android.personallifelessons.components.NetworkInterceptor
import com.android.personallifelessons.data.api.CategoryApi
import com.android.personallifelessons.data.api.CommentApi
import com.android.personallifelessons.data.api.PLLApi
import com.android.personallifelessons.data.api.UserApi
import com.android.personallifelessons.data.components.Constants
import com.android.personallifelessons.data.repository.CategoryRepositoryImpl
import com.android.personallifelessons.data.repository.CommentRepositoryImpl
import com.android.personallifelessons.data.repository.PllRepositoryImpl
import com.android.personallifelessons.data.repository.UserRepositoryImpl
import com.android.personallifelessons.domain.datastore.UserDatastore
import com.android.personallifelessons.domain.repository.CategoryRepository
import com.android.personallifelessons.domain.repository.CommentRepository
import com.android.personallifelessons.domain.repository.PllRepository
import com.android.personallifelessons.domain.repository.UserRepository
import com.android.personallifelessons.domain.room.PllDatabase
import com.android.personallifelessons.domain.worker.LikeDislikeWorker
import com.android.personallifelessons.presenter.category.CategoryViewModel
import com.android.personallifelessons.presenter.comments.CommentViewModel
import com.android.personallifelessons.presenter.dashboard.DashboardViewModel
import com.android.personallifelessons.presenter.auth.LoginViewModel
import com.android.personallifelessons.presenter.auth.SignUpViewModel
import com.android.personallifelessons.presenter.postAndUpdate.PostAndUpdatePllViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val workerModule = module{
    single { WorkManager.getInstance(get()) }
//    worker { LikeDislikeWorker(androidContext(), get()) }
    worker{ LikeDislikeWorker(androidContext(), get())}
}

val appModule = module {
    single{ UserDatastore(androidContext()) }
    single{ PllDatabase.getInstance(androidContext()).likedDislikedDao() }
}

val repositoryModule = module{
    single<CategoryRepository>{ CategoryRepositoryImpl(get(), get())}
    single<CommentRepository>{ CommentRepositoryImpl(get(), get()) }
    single<PllRepository>{ PllRepositoryImpl(get(), get()) }
    single<UserRepository>{ UserRepositoryImpl(get(), get()) }
}

val networkModule = module{

    single(named("client1")){
        OkHttpClient.Builder()
            .addNetworkInterceptor(NetworkInterceptor(androidContext()))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    single{
        Retrofit.Builder()
            .client(get(named("client1")))
    }
    single{
        get<Retrofit.Builder>()
            .baseUrl(Constants.USERBASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApi::class.java)
    }
    single{
        get<Retrofit.Builder>()
            .baseUrl(Constants.COMMENTBASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommentApi::class.java)
    }
    single{
        get<Retrofit.Builder>()
            .baseUrl(Constants.PLLBASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PLLApi::class.java)
    }
    single{
        get<Retrofit.Builder>()
            .baseUrl(Constants.CATEGORYBASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoryApi::class.java)
    }
}

val viewModelModule = module{
    viewModel{ DashboardViewModel(get(), get(), get()) }
    viewModel{ CategoryViewModel(get()) }
    viewModel { params -> CommentViewModel(get(), get(), get(), params[0]) }
    viewModel{ params -> PostAndUpdatePllViewModel(get(), get(), params[0])}
    viewModel{ LoginViewModel(get()) }
    viewModel{ SignUpViewModel(get()) }
}
