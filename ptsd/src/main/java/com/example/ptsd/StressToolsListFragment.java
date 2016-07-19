package com.example.ptsd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;

import java.util.Vector;

public class StressToolsListFragment extends ListFragment {
//need to implement list adapter
    //number of stress tools our application currently provides
    //should be changed when new tools are added

    public int stress_tool_count = 2;
    Vector<String> tool_list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter = new Array;
        populateStressToolsList();
    }

    public void populateStressToolsList(){
        //add names of all tools to the vector
        tool_list.add("Breathing");
        tool_list.add("Muscle Relaxation");

        for (int i = 0; i < stress_tool_count; i++) {
            //initialize fragments here and add them to the list
            Bundle args = new Bundle();
           // args.putString(tool_list.get("tool"), tool_list.get("1"));
            Fragment fragment = new StressToolsFragment();
            fragment.setArguments(args);

        }
    }
}

