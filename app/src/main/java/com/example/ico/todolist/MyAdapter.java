package com.example.ico.todolist;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Ico on 25.3.2017 г..
 */

public class MyAdapter extends BaseAdapter implements ListAdapter {
    private Context context;
    private ArrayList<String> items;
    int id;
    Dialog customDialog;
    DBHelper dbHelper;

    public MyAdapter(Context context, ArrayList<String> items, int id) {
        this.context = context;
        this.items = items;
        this.id=id;

    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup parent) {

       if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           view= inflater.inflate(id, null);
        }
           if(id==R.layout.adapter_update_delete_layout) {
               TextView tv = (TextView) view.findViewById(R.id.adapterUpdateDeleteTextView);

               tv.setText(items.get(i));
               ImageView updateImageView = (ImageView) view.findViewById(R.id.adapterEditImageView);
               ImageView deleteImageView = (ImageView) view.findViewById(R.id.adapterDeleteImageView);

               View.OnClickListener onClick=new View.OnClickListener() {
                   @Override

                   public void onClick(View v) {
                       dbHelper=new DBHelper(context);
                       if(v.getId() == R.id.adapterDeleteImageView){
                           dbHelper.deleteTask(items.get(i));
                           items.remove(i);
                           notifyDataSetChanged();

                       }else{

                                   dbHelper=new DBHelper(context);
                                   customDialog = new Dialog(context);
                                   customDialog.setContentView(R.layout.custom_dialog_layout);
                                   customDialog.setTitle("Update item");
                                   customDialog.setCanceledOnTouchOutside(false);

                                   Button okButton = (Button) customDialog.findViewById(R.id.customOkButton);
                                   Button cancelButton = (Button)
                                           customDialog.findViewById(R.id.customCancelButton);

                                   View.OnClickListener onCustomClick = new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           if(view.getId() == R.id.customOkButton){
                                               EditText et = (EditText)
                                                       customDialog.findViewById(R.id.customEditText);

                                               String item = et.getText().toString();
                                               if(!(item.length()>0)){
                                                   Toast.makeText(context, "Something is wrong",
                                                           Toast.LENGTH_LONG).show();
                                                   return;
                                               }
                                               dbHelper.updateTask(items.get(i),item,context);
                                               items.removeAll(items);
                                               items=dbHelper.getAllTask();

                                               notifyDataSetChanged();
                                           }

                                           customDialog.hide();
                                       }
                                   };

                                   okButton.setOnClickListener(onCustomClick);
                                   cancelButton.setOnClickListener(onCustomClick);

                                   customDialog.show();


                       }

                   }
               };
               updateImageView.setOnClickListener(onClick);
               deleteImageView.setOnClickListener(onClick);
           }else{
               TextView tv = (TextView) view.findViewById(R.id.adapterTaskTextView);
               tv.setText(items.get(i));
           }
            return view;

    }


}
