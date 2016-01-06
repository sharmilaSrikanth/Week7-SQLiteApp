package com.shrisharmi.sqliteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseClass myDB;
    EditText editname ;
    EditText txtid;
    Button btnadd;
    Button btnview;
    Button btnupdate;
    Button btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseClass(this);
        editname = (EditText)findViewById(R.id.editText);
        btnadd = (Button) findViewById(R.id.button);
        btnview = (Button)findViewById(R.id.button2);
        btnupdate = (Button)findViewById(R.id.button3);
        btndelete = (Button)findViewById(R.id.button4);
        txtid = (EditText)findViewById(R.id.editText2);
        add();
        showData();
        update();
        delete();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void delete()
    {
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDB.deleteData(txtid.getText().toString());
                if(deletedRows > 0 )
                {

                        Toast.makeText(MainActivity.this, "Data was deleted", Toast.LENGTH_LONG).show();
                    }
                    else
                    Toast.makeText(MainActivity.this, "Data was not deleted", Toast.LENGTH_LONG).show();

                }


        });
    }
    public void update()
    {
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdated = myDB.updateData(txtid.getText().toString(), editname.getText().toString());
                if (isUpdated==true)
                {
                    Toast.makeText(MainActivity.this, "Data was updated", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Data was not updated", Toast.LENGTH_LONG).show();

                }


        });
    }
    public void add()
    {
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.addData(editname.getText().toString());
                if(isInserted == true) {
                    Toast.makeText(MainActivity.this, "Data was inserted", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(MainActivity.this, "Data was not inserted", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void showData()
    {
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getData();
                if(res.getCount()== 0)
                {
                    viewMessage("Error","No data was found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Id :"+ res.getString(0)+"\n");
                    buffer.append("Name :"+ res.getString(1)+"\n\n");

                }

                viewMessage("Data", buffer.toString());
                }

        });
    }

    public void viewMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
