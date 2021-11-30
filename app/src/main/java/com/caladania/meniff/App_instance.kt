package com.caladania.meniff

import android.app.Application


class App_tochange : Application() {

    companion object {
        lateinit var instance_tochange_app: App_tochange
    }


    override fun onCreate() {
        super.onCreate()
        instance_tochange_app = this
    }
}

