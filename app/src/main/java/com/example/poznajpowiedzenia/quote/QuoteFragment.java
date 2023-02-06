package com.example.poznajpowiedzenia.quote;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.speech.tts.TextToSpeech;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.poznajpowiedzenia.R;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuoteFragment extends Fragment {

    Button btnFontSizeUp;
    Button btnFontSizeDown;
    Button btnSpeak;
    TextToSpeech textToSpeech;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "quote";
    private static final String ARG_PARAM2 = "meaning";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public QuoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param quote Parameter 1.
     * @param meaning Parameter 2.
     * @return A new instance of fragment QuoteFragment.
     */
    public static final QuoteFragment newInstance(String quote, String meaning) {
        QuoteFragment fragment = new QuoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, quote);
        args.putString(ARG_PARAM2, meaning);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(new Locale("pl"));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View quoteView = inflater.inflate(R.layout.fragment_quote, container, false);

        btnFontSizeDown = quoteView.findViewById(R.id.sizeDown);
        btnFontSizeUp = quoteView.findViewById(R.id.sizeUp);
        btnSpeak = quoteView.findViewById(R.id.speakBtn);

        TextView quoteText = quoteView.findViewById(R.id.quoteText);
        quoteText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
        TextView meaningText = quoteView.findViewById(R.id.meaning);
        meaningText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
        CardView cardView = quoteView.findViewById(R.id.cardview);

        String quote = getArguments().getString("quote");
        String meaning = getArguments().getString("meaning");
        quoteText.setText(quote);
        meaningText.setText(meaning);

        btnFontSizeUp.setOnClickListener( view -> {
            if(quoteText.getTextSize() <= 80 && meaningText.getTextSize() <= 80) {
                quoteText.setTextSize(TypedValue.COMPLEX_UNIT_PX, quoteText.getTextSize() + 5);
                meaningText.setTextSize(TypedValue.COMPLEX_UNIT_PX, meaningText.getTextSize() + 5);
            }
        });

        btnFontSizeDown.setOnClickListener( view -> {
            if(quoteText.getTextSize() >= 15 && meaningText.getTextSize() >= 15) {
                quoteText.setTextSize(TypedValue.COMPLEX_UNIT_PX, quoteText.getTextSize() - 5);
                meaningText.setTextSize(TypedValue.COMPLEX_UNIT_PX, meaningText.getTextSize() - 5);
            }
        });

        btnSpeak.setOnClickListener( view -> {
            textToSpeech.speak(quote, TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak(" co oznacza, że ", TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak(meaning, TextToSpeech.QUEUE_ADD, null);
        });


        return quoteView;
    }

}