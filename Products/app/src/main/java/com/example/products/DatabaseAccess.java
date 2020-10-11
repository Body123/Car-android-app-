package com.example.products;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private  static  DatabaseAccess instance;
    private DatabaseAccess (Context context){
        this.openHelper=new MyDatabase(context);
    }
    public static DatabaseAccess getInstance(Context context){
        if(instance==null){
            instance=new DatabaseAccess(context);
        }
        return instance;
    }
    public void open(){
        this.database=this.openHelper.getWritableDatabase();
    }
    public void close(){
        if(this.database!=null){
            this.database.close();
        }

    }
    public boolean insertCar(car car){
        ContentValues values =new ContentValues();
        values.put(MyDatabase.modelColumn,car.getModel());
        values.put(MyDatabase.descriptionColumn,car.getDescription());
        values.put(MyDatabase.imageColumn,car.getImage());
        values.put(MyDatabase.colorColumn,car.getColor());
        values.put(MyDatabase.destanceColumn,car.getDistancePerLetter());

        long result=database.insert(MyDatabase.tblName,null,values);
        return result!=-1;
    }
    public boolean updatetCar(car car){
        ContentValues values =new ContentValues();
        values.put(MyDatabase.modelColumn,car.getModel());
        values.put(MyDatabase.descriptionColumn,car.getDescription());
        values.put(MyDatabase.imageColumn,car.getImage());
        values.put(MyDatabase.colorColumn,car.getColor());
        values.put(MyDatabase.destanceColumn,car.getDistancePerLetter());
        String args[]={String.valueOf(car.getId())};
        int result=database.update(MyDatabase.tblName,values,"id=?",args);
        return result>0;
    }
    public long getCarCount(){
        return DatabaseUtils.queryNumEntries(database,MyDatabase.tblName);
    }
    public boolean deleteCar(car car){
        String args[]={String.valueOf(car.getId())};
        int result=database.delete(MyDatabase.tblName,"id=?",args);
        return result>0;
    }
    public ArrayList<car> getAllCars(){
        ArrayList<car> cars=new ArrayList<>();
        Cursor curser=database.rawQuery("Select * FROM "+MyDatabase.tblName,null);
        if(curser!=null&&curser.moveToFirst()){
            do {
                int id=curser.getInt(curser.getColumnIndex(MyDatabase.idColumn));
                String model=curser.getString(curser.getColumnIndex(MyDatabase.modelColumn));
                String color=curser.getString(curser.getColumnIndex(MyDatabase.colorColumn));
                double dpl=curser.getDouble(curser.getColumnIndex(MyDatabase.destanceColumn));
                String image=curser.getString(curser.getColumnIndex(MyDatabase.imageColumn));
                String description=curser.getString(curser.getColumnIndex(MyDatabase.descriptionColumn));
                car c=new car(id,model,color,dpl,image,description);
                cars.add(c);
            }while (curser.moveToNext());
            curser.close();
        }
        return cars;
    }


    public ArrayList<car> getCars(String search){
        ArrayList<car> cars=new ArrayList<>();
        Cursor curser=database.rawQuery("Select * FROM "+MyDatabase.tblName +" WHERE " +MyDatabase.modelColumn+
                " like ?",new String[]{search+"%"});
        if(curser!=null&&curser.moveToFirst()){
            do {
                int id=curser.getInt(curser.getColumnIndex(MyDatabase.idColumn));
                String model=curser.getString(curser.getColumnIndex(MyDatabase.modelColumn));
                String color=curser.getString(curser.getColumnIndex(MyDatabase.colorColumn));
                double dpl=curser.getDouble(curser.getColumnIndex(MyDatabase.destanceColumn));
                String image=curser.getString(curser.getColumnIndex(MyDatabase.imageColumn));
                String description=curser.getString(curser.getColumnIndex(MyDatabase.descriptionColumn));
                car c=new car(id,model,color,dpl,image,description);
                cars.add(c);
            }while (curser.moveToNext());
            curser.close();
        }
        return cars;
    }

    public car getcar(int carID){
        Cursor curser=database.rawQuery("Select * FROM "+MyDatabase.tblName +" WHERE " +MyDatabase.idColumn+
                " =?",new String[]{String.valueOf(carID)});
        if(curser!=null&&curser.moveToFirst()){

                int id=curser.getInt(curser.getColumnIndex(MyDatabase.idColumn));
                String model=curser.getString(curser.getColumnIndex(MyDatabase.modelColumn));
                String color=curser.getString(curser.getColumnIndex(MyDatabase.colorColumn));
                double dpl=curser.getDouble(curser.getColumnIndex(MyDatabase.destanceColumn));
                String image=curser.getString(curser.getColumnIndex(MyDatabase.imageColumn));
                String description=curser.getString(curser.getColumnIndex(MyDatabase.descriptionColumn));
                car c=new car(id,model,color,dpl,image,description);
                return c;
            }
            curser.close();

        return null;
    }


}
