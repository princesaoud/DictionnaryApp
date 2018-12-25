package com.example.princesaoud.dictionnaryapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";

    String value = "";
    private TextView tv_showTextOnTop, tv_showText_Translated;
    private ImageView imgV_sound, imgV_bookmark;
    private DBHelper mHelper;
    private int mDicType;

    public DetailsFragment() {
        // Required empty public constructor
        Log.d(TAG, "DetailsFragment: called");
    }

    public static DetailsFragment getInstance(String value, DBHelper mHelper, int mDicType) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.value = value;
        fragment.mHelper = mHelper;
        fragment.mDicType = mDicType;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView: called");
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_showTextOnTop = (TextView) view.findViewById(R.id.tvWordToShow_details);
        tv_showText_Translated = (TextView) view.findViewById(R.id.tv_word_translated);
        imgV_sound = (ImageView) view.findViewById(R.id.imgV_volumeUp);
        imgV_bookmark = (ImageView) view.findViewById(R.id.imgV_bookmark_checker);

        final Word word = mHelper.getWord(mDicType, value);
        tv_showTextOnTop.setText(word.getKey());
        tv_showText_Translated.setText(word.getValue());

        Word bookmarkWord = mHelper.getWordFromBookmark(value);
        int isMark = bookmarkWord == null ? 0 : 1;
        imgV_bookmark.setTag(isMark);

        int icon = bookmarkWord == null ? R.drawable.ic_bookmark : R.drawable.ic_bookmark_black_24dp;
        imgV_bookmark.setImageResource(icon);

        imgV_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) imgV_bookmark.getTag();
                if (i == 0) {
                    imgV_bookmark.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    imgV_bookmark.setTag(1);
                    mHelper.addBookmark(word);
                } else if (i == 1) {
                    imgV_bookmark.setImageResource(R.drawable.ic_bookmark);
                    imgV_bookmark.setTag(0);
                    mHelper.removeBookmark(word);
                }
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


}
