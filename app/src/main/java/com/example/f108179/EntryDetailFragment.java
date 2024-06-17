package com.example.f108179;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class EntryDetailFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_CONTENT = "content";
    private static final String ARG_DATE = "date";

    private String title;
    private String content;
    private String date;

    public static EntryDetailFragment newInstance(String title, String content, String date) {
        EntryDetailFragment fragment = new EntryDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
            date = getArguments().getString(ARG_DATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entry_detail, container, false);
        TextView titleTV = view.findViewById(R.id.titleTV);
        TextView contentTV = view.findViewById(R.id.contentTV);
        TextView dateTV = view.findViewById(R.id.dateTV);
        ImageButton btnClose = view.findViewById(R.id.btn_close);

        titleTV.setText(title);
        contentTV.setText(content);
        dateTV.setText(date);

        btnClose.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
            requireActivity().findViewById(R.id.fragment_container).setVisibility(View.GONE);
        });

        return view;
    }
}