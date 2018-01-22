package cn.wangliang.foodsafe.data;

import android.content.Context;

import cn.wangliang.foodsafe.data.database.dao.DaoMaster;

public class MyOpenHelper extends DaoMaster.OpenHelper{

    public MyOpenHelper(Context context, String name){
        super(context,name);
    }
}