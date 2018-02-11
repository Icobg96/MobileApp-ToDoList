package com.example.ico.todolist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class AddTask extends Fragment {
ArrayList<String> items=new ArrayList<String>();
MyAdapter adapter;
ListView addFragmentListView;
EditText addFragmentEditText;
Button addFragmentButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_task, container, false);
        DBHelper dbHelper=new DBHelper(view.getContext());
        addFragmentListView=(ListView) view.findViewById(R.id.addFragmentListView);
        addFragmentEditText=(EditText) view.findViewById(R.id.addFragmentEditText);
        addFragmentButton=(Button) view.findViewById(R.id.addFragmentButton);
        items.removeAll(items);
        items=dbHelper.getAllTask();

        // adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,items);
        adapter=new MyAdapter(view.getContext(),items,R.layout.adapter_task_layout);
        addFragmentListView.setAdapter(adapter);
        addFragmentButton.setOnClickListener(onClick);
        return view;
    }

    View.OnClickListener onClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DBHelper dbHelper=new DBHelper(getView().getContext());
            String task =addFragmentEditText.getText().toString();
            if(!(task.length()>0)){
                Toast.makeText(getView().getContext(), "Something is wrong",
                        Toast.LENGTH_LONG).show();
                return;
            }
            Task t=new Task(task);
            dbHelper.addTask(t,getView().getContext());
            items.removeAll(items);
            items=dbHelper.getAllTask();
            adapter=new MyAdapter(getView().getContext(),items,R.layout.adapter_task_layout);
            addFragmentListView.setAdapter(adapter);
        }
    };


}
