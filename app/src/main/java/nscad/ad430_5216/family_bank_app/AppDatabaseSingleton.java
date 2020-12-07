package nscad.ad430_5216.family_bank_app;

import android.content.Context;

import androidx.room.Room;

// Connects to AppDatabase
// May need to look into migrations later, if app db develops after beta release.
public class AppDatabaseSingleton {

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context) {

        if(db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "FamilyBankDB")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return db;
    }
}