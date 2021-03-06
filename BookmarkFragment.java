package com.example.princesaoud.dictionnaryapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class BookmarkFragment extends Fragment {

    FragmentListener listener;
    String value = "Bookmark somewhere here";
    ListView listView;
    ListViewAdapterBmk adapter;
    DBHelper dbHelper;
    public BookmarkFragment() {
        // Required empty public constructor
    }

    public static BookmarkFragment getNewInstance(DBHelper dbHelper){
        BookmarkFragment fragment = new BookmarkFragment();
        fragment.dbHelper = dbHelper;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmark, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        listView = (ListView) view.findViewById(R.id.bookmarkList);
        adapter = new ListViewAdapterBmk(getContext(), dbHelper.getAllWordFromBookmark());
        adapter.setOnItemClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                if(listener!=null)
                    listener.onItemClik(String.valueOf(adapter.getItem(position)));
            }
        });


        listView.setAdapter(adapter);

        adapter.setOnItemButtonClick(new ListItemListener() {
            @Override
            public void onItemClick(int position) {
                assert listener!= null;
                    adapter.removeItem(position);
                    adapter.notifyDataSetChanged();
            }
        });



    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void setOnClickListener(FragmentListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_clear, menu);
    }
}
