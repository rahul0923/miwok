package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rkesavan on 4/5/2017.
 */

public class WordsAdapter extends ArrayAdapter {

    private  int mBgColor;

    public WordsAdapter(@NonNull Context context, @NonNull ArrayList<Word> words, int bgColor) {
        super(context, 0, words);
        mBgColor = bgColor;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_words_view, parent, false);
        }

        Word word = (Word) getItem(position);
        ViewGroup viewGoup = (ViewGroup) listItemView.findViewById(R.id.textViews);
        TextView defView = (TextView) listItemView.findViewById(R.id.defaultView);
        defView.setText(word.getOrigWord());

        TextView transView = (TextView) listItemView.findViewById(R.id.translationView);
        transView.setText(word.getTranslatedWord());

        ImageView imgView = (ImageView) listItemView.findViewById(R.id.img);
        if (word.hasImage()) {
            imgView.setImageResource(word.getmImgID());
            imgView.setVisibility(ImageView.VISIBLE);
        } else {
            imgView.setVisibility(ImageView.GONE);
        }
        viewGoup.setBackgroundColor(ContextCompat.getColor(this.getContext(), mBgColor));

        return listItemView;
    }
}
