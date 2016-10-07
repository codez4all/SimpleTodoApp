package com.example.sheetal.todoapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class EditDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "text";
    private static  final String ARG_DUEDATE = "dueDate";
    private static final String ARG_POSITION = "position";

    EditText etEditItem;
    DatePicker datePicker;
    Button btnSave;


    // TODO: Rename and change types of parameters
    private String mText;
    private int mPosition;
    private String mDueDate;

    private OnFragmentInteractionListener mListener;

    public EditDialogFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static EditDialogFragment newInstance(String text, int position, String dueDate) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        args.putString(ARG_DUEDATE, dueDate.toString());
        args.putInt(ARG_POSITION, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mText = getArguments().getString(ARG_TEXT);
            mPosition = getArguments().getInt(ARG_POSITION);

           // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            mDueDate =  getArguments().getString(ARG_DUEDATE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEditItem = (EditText) view.findViewById(R.id.etEditItem);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        btnSave = (Button) view.findViewById(R.id.btnSave);

        //set default text as text to edit
        etEditItem.setText(mText);
        etEditItem.setSelection(mText.length());
        etEditItem.requestFocus();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newText = etEditItem.getText().toString();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                int year = datePicker.getYear();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

                String newDate=  dateFormat.format(calendar.getTime());

                onSave(newText,mPosition, newDate.toString());
                dismiss();
            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onSave(String newText, int position, String newDate) {
        if (mListener != null) {
            mListener.onFragmentInteraction(newText, position, newDate);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String newText, int position, String dueDate);
    }
}
