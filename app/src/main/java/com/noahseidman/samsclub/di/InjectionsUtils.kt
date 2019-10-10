package com.noahseidman.samsclub.di

import android.content.Context

class InjectionsUtils {

    companion object {
        lateinit var components: BaseComponents

        fun init(context: Context) {
            components = DaggerComponents.builder()
                .context(context).build()
        }

        fun setTestComponents(testComponents: BaseComponents) {
            components = testComponents
        }
    }
}