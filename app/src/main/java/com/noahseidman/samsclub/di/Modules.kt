package com.noahseidman.samsclub.di

import android.content.Context
import com.noahseidman.samsclub.services.RetrofitInstance
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Modules {

    @Provides
    @Singleton
    fun provideRetrofit(context: Context): RetrofitInstance {
        return RetrofitInstance(context);
    }
}

@Singleton
@Component(modules = [Modules::class])
interface Components : BaseComponents {
    @Component.Builder
    interface Builder {
        fun build(): Components
        @BindsInstance
        fun context(context: Context): Builder
    }
}
