package com.example.princesaoud.dictionnaryapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class DictionaryFragment extends Fragment {

    private static final String TAG = "DictionaryFragment";
    String value = "Dictionary somewhere here";
    ArrayAdapter<String> adapter;
    ListView dictionaryList;
    ArrayList<String> mSource = new ArrayList<>();
    private FragmentListener listener;
    public  DictionaryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionnary, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dictionaryList = view.findViewById(R.id.dictionaryList);
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,mSource);
        dictionaryList.setAdapter(adapter);
        dictionaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.onItemClik(mSource.get(position));
            }
        });

    }

    public void resetDataSource(ArrayList<String> source){
        mSource = source;
        adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, mSource);
        dictionaryList.setAdapter(adapter);
    }

    public void filterValue(String text){
        int size = adapter.getCount();
        for(int i =0; i < size; i++){
            if(adapter.getItem(i).startsWith(text)){
                dictionaryList.setSelection(i);
                break;
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    public void setOnFragmentListener(FragmentListener listener){
        this.listener = listener;
    }

}
