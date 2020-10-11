package com.example.products;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int Add_car_request_code=1;
    private static final int edit_car_request_code=1;
    public  static final String car_key="car_key";
    private static final int permission_request_code=5;
    RecyclerView rv;
    private FloatingActionButton fab;
    private  Toolbar tb;
    private carRecyclerVeiwAdapter adapter;
    private DatabaseAccess db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permission_request_code);
        }
        tb=findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        rv=findViewById(R.id.main_rc);
        fab=findViewById(R.id.Main_fab);
        db=DatabaseAccess.getInstance(this);
        db.open();
        ArrayList<car> cars=db.getAllCars();
        db.close();
        adapter=new carRecyclerVeiwAdapter(cars, new onRecycleViewClickListener() {
            @Override
            public void OnItemClick(int carID) {
                Intent intent =new Intent(getBaseContext(),veiwCarActivity.class);
                intent.putExtra(car_key,carID);
                startActivityForResult(intent,edit_car_request_code);
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm=new GridLayoutManager(this,2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(),veiwCarActivity.class);
                startActivityForResult(intent,Add_car_request_code);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.main_menu,menu);
        SearchView searchView= (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                db.open();
                ArrayList<car> cars=db.getCars(query);
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                db.open();
                ArrayList<car> cars=db.getCars(newText);
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                db.open();
                ArrayList<car> cars=db.getAllCars();
                db.close();
                adapter.setCars(cars);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
       return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public class x implements onRecycleViewClickListener{

        @Override
        public void OnItemClick(int carID) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Add_car_request_code){
            db.open();
            ArrayList<car> cars=db.getAllCars();
            db.close();
            adapter.setCars(cars);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case permission_request_code:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                }else{

                }
        }
    }
}
