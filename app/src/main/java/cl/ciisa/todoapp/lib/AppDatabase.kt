package cl.ciisa.todoapp.lib

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cl.ciisa.todoapp.dao.TaskDAO
import cl.ciisa.todoapp.dao.UserDAO
import cl.ciisa.todoapp.models.TaskEntity
import cl.ciisa.todoapp.models.UserEntity
import cl.ciisa.todoapp.utils.Converters

@Database(entities = [UserEntity::class, TaskEntity::class],version = 2)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun taskDao(): TaskDAO

    companion object{
        const val DATABASE_NAME = "todo-app"
    }
}