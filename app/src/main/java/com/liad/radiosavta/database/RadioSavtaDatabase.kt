package com.liad.radiosavta.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.liad.radiosavta.models.PresentedUser

@Database(entities = [PresentedUser::class], version = 1)
abstract class RadioSavtaDatabase : RoomDatabase() {

    abstract val dao: RadioSavtaDao


    companion object {
        @Volatile
        private var instance: RadioSavtaDatabase? = null

        fun getDatabase(context: Context): RadioSavtaDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                return Room.databaseBuilder(
                    context.applicationContext,
                    RadioSavtaDatabase::class.java,
                    "radio_savta.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}
