package cn.wangliang.foodsafe.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import cn.wangliang.foodsafe.data.database.dao.DaoMaster;
import cn.wangliang.foodsafe.data.database.dao.DaoSession;



public class DatabaseManager {

    private static DatabaseManager instance;
    private final SQLiteDatabase database;
    private DaoMaster daoMaster;
    private DaoSession daoSession;


    private DatabaseManager(Context context) {
        MyOpenHelper helper = new MyOpenHelper(context, "daichuqu.db");
        database = helper.getWritableDatabase();
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
    }

    public static void initialize(Context context) {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager(context);
                }
            }
        }
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
