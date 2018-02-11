package com.example.ico.todolist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class TaskMain extends Fragment {
  ListView taskFragmentListView;
  ArrayList<String> items=new ArrayList<String>();
  MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_main, container, false);
        DBHelper dbHelper=new DBHelper(view.getContext());
        taskFragmentListView=(ListView) view.findViewById(R.id.taskFragmentListView);
        items.removeAll(items);
        items=dbHelper.getAllTask();
        adapter=new MyAdapter(view.getContext(),items,R.layout.adapter_task_layout);
        taskFragmentListView.setAdapter(adapter);
        return view;
    }


}
