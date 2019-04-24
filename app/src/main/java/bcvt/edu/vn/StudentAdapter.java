package bcvt.edu.vn;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {
    Context context;
    int layout;
    ArrayList<Student> arrList;

    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Student> objects){
        super(context, resource, objects);
        this.context=context;
        this.layout=resource;
        this.arrList=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout, null);

        ImageView img = (ImageView)convertView.findViewById(R.id.image);
        TextView txt1 = (TextView)convertView.findViewById(R.id.txtName);
        TextView txt2 = (TextView)convertView.findViewById(R.id.txtAddress);

        if(arrList.get(position).getGender()==1){
            img.setImageResource(R.drawable.girlion);
        } else {
            img.setImageResource(R.drawable.boyicon);
        }

        txt1.setText(arrList.get(position).getName());
        txt2.setText(arrList.get(position).getAddress());

        return convertView;
    }
}
