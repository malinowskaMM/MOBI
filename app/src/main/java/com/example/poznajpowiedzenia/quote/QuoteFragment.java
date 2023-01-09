package com.example.poznajpowiedzenia.quote;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.poznajpowiedzenia.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuoteFragment extends Fragment {

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View quoteView = inflater.inflate(R.layout.fragment_quote, container, false);

        TextView quoteText = quoteView.findViewById(R.id.quoteText);
        TextView meaningText = quoteView.findViewById(R.id.meaning);
        CardView cardView = quoteView.findViewById(R.id.cardview);

        String quote = getArguments().getString("quote");
        String meaning = getArguments().getString("meaning");

        quoteText.setText(quote);
        meaningText.setText("-" + meaning);

        return quoteView;
    }

}