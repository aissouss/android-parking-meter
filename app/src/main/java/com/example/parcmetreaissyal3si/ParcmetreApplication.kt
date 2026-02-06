package com.example.parcmetreaissyal3si

import android.app.Application
import com.example.parcmetreaissyal3si.data.AppDatabase
import com.example.parcmetreaissyal3si.repository.ParcmetreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParcmetreApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { ParcmetreRepository(database.configDao(), database.sessionDao()) }

    override fun onCreate() {
        super.onCreate()
        // Insert default config if not exists
        CoroutineScope(Dispatchers.IO).launch {
            val existing = database.configDao().getConfigDirect()
            if (existing == null) {
                database.configDao().insertConfig(
                    com.example.parcmetreaissyal3si.data.Config()
                )
            }
        }
    }
}
