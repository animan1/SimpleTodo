package com.codepath.simpletodo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ToDoActivity extends Activity {

  private ArrayList<String> items;
  private ArrayAdapter<String> itemsAdapter;
  ListView lvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        items.remove(position);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
        return true;
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  public void onAddItem(View v) {
    EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
    String itemText = etNewItem.getText().toString();
    itemsAdapter.add(itemText);
    etNewItem.setText("");
    writeItems();
  }

  private void readItems() {
    File todoFile = getTodoFile();
    try {
      items = new ArrayList<>(FileUtils.readLines(todoFile));
    } catch (IOException e) {
      items = new ArrayList<>();
    }
  }

  private File getTodoFile() {
    File filesDir = getFilesDir();
    return new File(filesDir, "todo.txt");
  }

  private void writeItems() {
    File todoFile = getTodoFile();
    try {
      FileUtils.writeLines(todoFile, items);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
