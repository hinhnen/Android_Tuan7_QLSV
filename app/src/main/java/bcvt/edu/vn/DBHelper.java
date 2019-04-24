package bcvt.edu.vn;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBHelper {
    private static String DATABASE_NAME = "DataQLSV.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase db = null;

    Context context;

    public DBHelper(Context context){
        this.context = context;
        processSQLite();
    }

    private void processSQLite(){
        File dbFile = context.getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()){
            try{
                copyDatabaseFromAsset();
                Toast.makeText(context,"coppy success !", Toast.LENGTH_SHORT).show();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void copyDatabaseFromAsset(){
        try{
            InputStream dataInputStream = context.getAssets().open(DATABASE_NAME);

            String outputStream = getPathDataSystem();
            File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if(!file.exists()){
                file.mkdir();
            }

            OutputStream dataOutPutStream = new FileOutputStream(outputStream);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dataInputStream.read(buffer)) > 0){
                dataOutPutStream.write(buffer, 0, length);
            }
            dataOutPutStream.flush();
            dataOutPutStream.close();
            dataInputStream.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private String getPathDataSystem(){
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public ArrayList<Student> getAllStudent(){
        ArrayList<Student> arrayList = new ArrayList<>();
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        String sql = "SELECT * FROM student ";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);;
            String address = cursor.getString(2);
            int gender = cursor.getInt(3);

            arrayList.add(new Student(id, name, address, gender));
        }
        return arrayList;
    }

    public void insertStudent(Student student){
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        contentValues.put("gender", student.getGender());

        if(db.insert("student", null, contentValues)>0){
            Toast.makeText(context, "success !", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
    }



    public void updateStudent(Student student){
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        contentValues.put("gender", student.getGender());

        if(db.update("student", contentValues, "id = " + student.getId(), null) > 0){
            Toast.makeText(context, "success !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStudent(Student student){
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null);

        if(db.delete("student", "id="+student.getId(), null)>0){
            Toast.makeText(context, "success !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Fail", Toast.LENGTH_SHORT).show();
        }
    }
}
