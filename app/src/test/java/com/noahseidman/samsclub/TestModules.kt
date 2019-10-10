package com.noahseidman.samsclub

import android.content.Context
import com.noahseidman.samsclub.di.BaseComponents
import com.noahseidman.samsclub.services.RetrofitInstance
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestModules {
    @Provides
    @Singleton
    fun provideRetrofit(context: Context): RetrofitInstance {
        val retrofit = RetrofitInstance(context)
        retrofit.addMock()
        return retrofit
    }
}

@Singleton
@Component(modules = arrayOf(TestModules::class))
interface TestComponents: BaseComponents {
    @Component.Builder
    interface Builder {
        fun build(): TestComponents
        @BindsInstance
        fun context(context: Context): Builder
    }
}