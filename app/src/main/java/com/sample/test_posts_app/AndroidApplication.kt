package com.sample.test_posts_app

import android.app.Application
import com.sample.test_posts_app.di.AppComponent
import com.sample.test_posts_app.di.AppComponentProvider
import com.sample.test_posts_app.di.DaggerAppComponent

class AndroidApplication : Application(), AppComponentProvider {

    override val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}
