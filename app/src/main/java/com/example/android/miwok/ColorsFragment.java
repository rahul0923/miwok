package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        // Pause playback immediately
                        releaseMediaPlayer();
                    } else if (focusChange == AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };
    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_list, container, false);
        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int bgColor = R.color.category_colors;

        ArrayList<Word> colorList = new ArrayList<>();

        colorList.add(new Word("Black", "Kaala",  R.drawable.color_red, R.raw.one));
        colorList.add(new Word("White", "Safaaid",  R.drawable.color_mustard_yellow, R.raw.one));
        colorList.add(new Word("Red", "Laal",  R.drawable.color_dusty_yellow, R.raw.one));
        colorList.add(new Word("Yellow", "Peela",  R.drawable.color_green, R.raw.one));
        colorList.add(new Word("Orange", "Naarangi",  R.drawable.color_brown, R.raw.one));
        colorList.add(new Word("Green", "Haara",  R.drawable.color_gray, R.raw.one));
        colorList.add(new Word("Blue", "Neela",  R.drawable.color_black, R.raw.one));
        colorList.add(new Word("Pink", "Gulabi",  R.drawable.color_white, R.raw.one));
        //colorList.add(new Word("Brown", "Bhuura",  R.drawable.number_one));
        //colorList.add(new Word("Violet", "Baingani",  R.drawable.number_one));

        WordsAdapter wordsAdapter = new WordsAdapter(getActivity(), colorList, R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(wordsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Word word = (Word)adapterView.getItemAtPosition(i);
                int res = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getmAudioID());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(myCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        this.releaseMediaPlayer();
    }

    private  void releaseMediaPlayer() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();
            this.mediaPlayer = null;
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    private MediaPlayer.OnCompletionListener myCompletionListener = new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

}
