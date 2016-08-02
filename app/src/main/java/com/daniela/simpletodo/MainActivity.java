package com.daniela.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Main activity responsible for presenting the items list
 */
public class MainActivity extends AppCompatActivity {

    private PersistenceStore persistenceStore = new PersistenceStore();

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView todoItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todoItemsList = (ListView)findViewById(R.id.todo_items_list);
        // Get items from internal storage
        items = persistenceStore.readItems(this);

        // Setup adapter
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        todoItemsList.setAdapter(itemsAdapter);

        // Setup listener
        setupListViewListener();
    }

    private void setupListViewListener() {

        // Adding long click to delete item
        todoItemsList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long id) {
                        items.remove(index);
                        itemsAdapter.notifyDataSetChanged();

                        // Remove item in internal storage
                        persistenceStore.writeItems(MainActivity.this, items);

                        return true;
                    }
                }
        );
    }

    //region Actions
    public void onAddNewItem(View view) {
        EditText newItemEditText = (EditText)findViewById(R.id.new_item_edit_text);
        String newItemText = newItemEditText.getText().toString();
        items.add(newItemText);
        itemsAdapter.notifyDataSetChanged();

        // Add item to internal storage
        persistenceStore.writeItems(this, items);

        // Put edit text back to empty text
        newItemEditText.setText("");
    }
    //endregion
}
