package com.example.products;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class veiwCarActivity extends AppCompatActivity {
    private  static  final int pick_image_req_code=1;
    public   static  final int add_car_result_code=2;
    public   static  final int edit_car_result_code=3;
    private Toolbar toolbar;
    private TextInputEditText txtModel,txtColor,txtDPL,txtDescription;
    private ImageView iv;
    private  DatabaseAccess db;
    private  Uri imageURI;
    private int carID=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veiw_car);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        iv=findViewById(R.id.iv);
        txtModel=findViewById(R.id.details);
        txtColor=findViewById(R.id.color);
        txtDPL=findViewById(R.id.dpl);
        txtDescription=findViewById(R.id.description);
        db=DatabaseAccess.getInstance(this);
        Intent intent=getIntent();
        carID=intent.getIntExtra(MainActivity.car_key,-1);
        if(carID==-1){
            //addingnewcar
            enableFields();
            clearFields();
        }else{
            //editing
            disableFields();
            db.open();
           car c= db.getcar(carID);
           db.close();
           if(c!=null){
               fillCarToField(c);
           }
        }
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(in,pick_image_req_code);
            }
        });
    }
    public void fillCarToField(car c){
        if(c.getImage()!=null&&!c.getImage().equals(""))
            iv.setImageURI(Uri.parse(c.getImage()));
        txtModel.setText(c.getModel());
        txtColor.setText(c.getColor());
        txtDPL.setText(c.getDistancePerLetter()+"");
        txtDescription.setText(c.getDescription());


    }
    public void disableFields(){
        iv.setEnabled(false);
        txtModel.setEnabled(false);
        txtColor.setEnabled(false);
        txtDPL.setEnabled(false);
        txtDescription.setEnabled(false);
    }
    public void enableFields(){
        iv.setEnabled(true);
        txtModel.setEnabled(true);
        txtColor.setEnabled(true);
        txtDPL.setEnabled(true);
        txtDescription.setEnabled(true);
    }
    public void clearFields(){
        iv.setImageURI(null);
        txtModel.setText("");
        txtColor.setText("");
        txtDPL.setText("");
        txtDescription.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menue,menu);
        MenuItem save=menu.findItem(R.id.details_menu_save);
        MenuItem edit=menu.findItem(R.id.details_menu_edit);
        MenuItem delete=menu.findItem(R.id.details_menu_delete);


        if(carID==-1){
            //addingnewcar
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        }else{
            //editing
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String model,color,description,image="";
        Double dpl;
        switch (item.getItemId()){
            case R.id.details_menu_save :
                model=txtModel.getText().toString();
                color=txtColor.getText().toString();
                description=txtDescription.getText().toString();
                if(imageURI!=null)
                    image=imageURI.toString();
                dpl=Double.parseDouble(txtDPL.getText().toString());
                car c=new car(carID,model,color,dpl,image,description);
                db.open();
                boolean checker;
                if(carID==-1){
                    checker=db.insertCar(c);
                    if(checker) {
                        Toast.makeText(this, "Car Added Successfully", Toast.LENGTH_SHORT).show();
                        setResult(add_car_result_code, null);
                        finish();
                    }
                }else{
                    checker=db.updatetCar(c);
                    if(checker){
                        Toast.makeText(this, "Car Modified Successfully", Toast.LENGTH_SHORT).show();
                        setResult(edit_car_result_code,null);
                        finish();

                    }

                }
                db.close();

                return true;
            case R.id.details_menu_edit :
                enableFields();
                MenuItem save=toolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit=toolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete=toolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);

                return true;
            case R.id.details_menu_delete :
                db.open();
                c=new car(carID,null,null,0,null,null);
                checker=db.deleteCar(c);
                db.close();
                if(checker){
                    Toast.makeText(this, "Car deleted Successfully", Toast.LENGTH_SHORT).show();
                    setResult(edit_car_result_code,null);
                    finish();
                }
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==pick_image_req_code&&resultCode==RESULT_OK){
            if(data!=null){
                imageURI=data.getData();
                iv.setImageURI(imageURI);
            }
        }
    }
}
