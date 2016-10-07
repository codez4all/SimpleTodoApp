package com.example.sheetal.todoapp;

import android.support.v7.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

 //   EditText etEditItem;
 //   Button btnSave;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_item);
//
//        etEditItem = (EditText) findViewById(R.id.etEditItem);
//        btnSave = (Button) findViewById(R.id.btnSave);
//
//        String text = getIntent().getStringExtra("Text");
//        final int position =  getIntent().getIntExtra("Position",0);
//
//        //Set the text to edit
//        etEditItem.setText(text);
//        etEditItem.setSelection(etEditItem.getText().length());
//        etEditItem.setClickable(true);
//        etEditItem.setFocusable(true);
//        etEditItem.requestFocus();
//
//        //On Save
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String newText = etEditItem.getText().toString();
//
//                Intent data = new Intent();
//                data.putExtra("NewText",newText);
//                data.putExtra("Position", position);
//                setResult(RESULT_OK,data);
//                finish();
//            }
//        });
//
//    }

}
