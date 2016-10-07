package com.example.sheetal.todoapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements EditDialogFragment.OnFragmentInteractionListener{

    Button btnAddItem;
    ListView lvItems;
    EditText etEditText;
    ArrayList<ListItem> listArray;
    ListItemAdapter customAdapter;
    private final int REQUEST_CODE = 200;
    TodoDatabaseHelper dbHelper;

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        etEditText = (EditText) findViewById(R.id.etEditText);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);

        dbHelper = TodoDatabaseHelper.getInstance(getApplicationContext());
        listArray = new ArrayList<ListItem>();
        customAdapter = new ListItemAdapter(getApplicationContext(),listArray);

        lvItems.setAdapter(customAdapter);

        populateArrayItems();


        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodoItem();

            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                listArray.remove(position);
                customAdapter.notifyDataSetChanged();

                //update database
                dbHelper.deleteListItem(position);

                return true;
            }
        });

        //Edit Item
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ListItem tempItem = (ListItem) lvItems.getItemAtPosition(position);

                //call dialog fragment to edit the item
                showEditDialog(tempItem.text.toString(), position, tempItem.dueDate);

                //update databse
                dbHelper.updateListItem(tempItem, position);
            }
        });

    }

    public void  showEditDialog(String oldText, int position, String dueDate)
    {
        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment editDialogFragment = EditDialogFragment.newInstance(oldText, position, dueDate);
        editDialogFragment.show(fm,"fragment_edit_dialog");
    }


    public void populateArrayItems()
    {
        listArray.addAll(dbHelper.getAllListItems());
        customAdapter.notifyDataSetChanged();
    }

    public void addTodoItem()
    {
        ListItem tempItem = new ListItem(etEditText.getText().toString(), dateFormat.format(cal.getTime())); // initially pass duedate as Today's Date
        listArray.add(tempItem);
        customAdapter.notifyDataSetChanged();
        etEditText.setText("");

        // update database
        dbHelper.addListItem(tempItem, (lvItems.getLastVisiblePosition()+1));

        Log.d("DEBUG","Add Item: "+ tempItem.text + "," + tempItem.dueDate);
    }

    @Override
    public void onFragmentInteraction(String newText, int position, String newDueDate) {
        ListItem item= (ListItem) lvItems.getItemAtPosition(position);
        item.text = newText;
        item.dueDate = newDueDate;
        customAdapter.notifyDataSetChanged();

        //update db
        dbHelper.updateListItem(item,position);

    }
}
