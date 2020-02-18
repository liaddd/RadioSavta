package com.liad.radiosavta.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liad.radiosavta.models.Program
import com.liad.radiosavta.models.ProgramTimes
import com.liad.radiosavta.models.User

@TypeConverters(Converters::class)
@Database(entities = [Program::class, ProgramTimes::class, User::class], version = 1 , exportSchema = false)
abstract class RadioSavtaDatabase : RoomDatabase() {

    abstract fun dao() : RadioSavtaDao

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
