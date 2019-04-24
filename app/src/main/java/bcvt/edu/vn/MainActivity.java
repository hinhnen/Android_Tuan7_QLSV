package bcvt.edu.vn;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

   ArrayList<Student> arrayList;
   StudentAdapter studentAdapter;
   DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(MainActivity.this);
        listView = (ListView)findViewById(R.id.list);
        arrayList = db.getAllStudent();
        studentAdapter = new StudentAdapter(MainActivity.this, R.layout.layout_row, arrayList);

        listView.setAdapter(studentAdapter);

        registerForContextMenu(listView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.mnu_add){
            final Student student = new Student();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Add student");
            builder.setCancelable(false);

            LayoutInflater inflater = getLayoutInflater().from(MainActivity.this);
            View view = inflater.inflate(R.layout.layout_input, null);

            final EditText edtName = (EditText)view.findViewById(R.id.edtName);
            final EditText edtAddress = (EditText)view.findViewById(R.id.edtAddress);
            final RadioGroup rbg = (RadioGroup) view.findViewById(R.id.rdgtGender);
            final ImageView img = (ImageView)view.findViewById(R.id.image);

            builder.setView(view);

            rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.rdbFeMale:
                            student.setGender(1);
                            img.setImageResource(R.drawable.girlion);
                            break;
                        case R.id.rdbMale:
                            student.setGender(0);
                            img.setImageResource(R.drawable.boyicon);
                            break;
                    }
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    student.setName(edtName.getText().toString());
                    student.setAddress(edtAddress.getText().toString());

                    db.insertStudent(student);
                    arrayList.add(student);
                    studentAdapter.notifyDataSetChanged();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.mnu_update:
                final Student student = arrayList.get(info.position);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Update student");
                builder.setCancelable(false);

                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.layout_input, null);

                final EditText edtName = (EditText)view.findViewById(R.id.edtName);
                final EditText edtAddress = (EditText)view.findViewById(R.id.edtAddress);
                final RadioGroup rbg = (RadioGroup) view.findViewById(R.id.rdgtGender);
                final RadioButton rdbMale = (RadioButton)view.findViewById(R.id.rdbMale);
                final RadioButton rdbFeMale = (RadioButton)view.findViewById(R.id.rdbFeMale);
                final ImageView img = (ImageView)view.findViewById(R.id.image);

                edtName.setText(student.getName());
                edtAddress.setText(student.getAddress());

                if(student.getGender()==1){
                    rdbFeMale.setChecked(true);
                    img.setImageResource(R.drawable.girlion);
                } else {
                    rdbMale.setChecked(true);
                    img.setImageResource(R.drawable.boyicon);
                }

                builder.setView(view);

                rbg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        switch (checkedId){
                            case R.id.rdbFeMale:
                                student.setGender(1);
                                img.setImageResource(R.drawable.girlion);
                                break;
                            case R.id.rdbMale:
                                student.setGender(0);
                                img.setImageResource(R.drawable.boyicon);
                                break;
                        }
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        student.setName(edtName.getText().toString());
                        student.setAddress(edtAddress.getText().toString());

                        db.updateStudent(student);
                        arrayList.set(info.position, student);
                        studentAdapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
            case R.id.mnu_delete:
                final Student student1 = arrayList.get(info.position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);

                builder1.setTitle("Delete student");
                builder1.setCancelable(false);
                builder1.setMessage("Are you sure delete this student?");

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteStudent(student1);
                        arrayList.remove(info.position);
                        studentAdapter.notifyDataSetChanged();
                    }
                });
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog1 = builder1.create();
                alertDialog1.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
