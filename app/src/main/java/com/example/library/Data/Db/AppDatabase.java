package com.example.library.Data.Db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.library.Data.Dao.RecetteDao;
import com.example.library.Data.Dao.UserDao;
import com.example.library.Data.model.Recette;
import com.example.library.Data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Recette.class}, version = 9, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    // ...

    public abstract RecetteDao recetteDao();


    private static volatile AppDatabase instance;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "library_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "library_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}