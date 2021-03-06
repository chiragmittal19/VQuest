package com.csy.vquest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.sql.Timestamp;
import java.util.Map;


public class QuestionFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int qno = 2;

    private String category = "";
    private String question = "";
    private int anonymity = 0;

    private Timestamp timestamp = null;
    private int views = 0;
    private String username = "chirag";
    private int edited = 0;
    private int r_no = -1;
    private int visibility = 1;

    private Button btn1;
    private CheckBox checkBox;
    private EditText editText;
    private Spinner spinner;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getActivity().setTitle("New Question");

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        checkBox = (CheckBox) view.findViewById(R.id.checkBox1);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    anonymity = 1;
                else
                    anonymity = 0;
            }
        });

        btn1 = (Button) view.findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                question = editText.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference rootRef = database.getReference();
                DatabaseReference questionRef = rootRef.child("question");
                DatabaseReference newQuestionRef = questionRef.child(String.valueOf(qno));
                newQuestionRef.child("category").setValue(category);
                newQuestionRef.child("qanonymity").setValue(anonymity);
                newQuestionRef.child("qedited").setValue(edited);
                newQuestionRef.child("qstring").setValue(question);
                newQuestionRef.child("r_no").setValue(r_no);
                newQuestionRef.child("time").setValue(ServerValue.TIMESTAMP);
                newQuestionRef.child("username").setValue(username);
                newQuestionRef.child("views").setValue(views);
                newQuestionRef.child("visibility").setValue(visibility);

                Toast.makeText(getActivity(),category+", "+question+", "+anonymity,Toast.LENGTH_LONG).show();
            }
        });

        editText = (EditText) view.findViewById(R.id.editText3);

        spinner = (Spinner) view.findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = parent.getItemAtPosition(0).toString();
    }

}
