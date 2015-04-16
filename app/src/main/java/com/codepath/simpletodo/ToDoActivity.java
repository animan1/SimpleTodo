package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

public class ToDoActivity extends Activity {

  private final int EDIT_TEXT_REQUEST_CODE = 1;
  private final String POSITION = "position";
  private ArrayList<ToDo> items;
  private ArrayAdapter<ToDo> itemsAdapter;
  private ListView lvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    lvItems = (ListView) findViewById(R.id.lvItems);
    readItems();
    itemsAdapter = new ArrayAdapter<ToDo>(this, android.R.layout.simple_list_item_1, items);
    lvItems.setAdapter(itemsAdapter);
    setupListViewListener();
  }

  private void setupListViewListener() {
    lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
      @Override
      public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ToDo item = items.remove(position);
        item.delete();
        itemsAdapter.notifyDataSetChanged();
        return true;
      }
    });
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(ToDoActivity.this, EditItemActivity.class);
        i.putExtra(EditItemActivity.ITEM, items.get(position).text);
        i.putExtra(POSITION, position);
        startActivityForResult(i, EDIT_TEXT_REQUEST_CODE);
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
    ToDo item = new ToDo(itemText);
    item.save();
    itemsAdapter.add(item);
    etNewItem.setText("");
  }

  private void readItems() {
    List<ToDo> dbItems = new Select().from(ToDo.class).execute();
    items = new ArrayList<>(dbItems);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && requestCode == EDIT_TEXT_REQUEST_CODE) {
      String itemText = data.getStringExtra(EditItemActivity.ITEM).trim();
      int position = data.getIntExtra(POSITION, -1);
      if ((itemText == null) || itemText.isEmpty() || (position == -1) || (position >= items.size())) {
        return;
      }

      ToDo item = items.get(position);
      item.text = itemText;
      item.save();
      itemsAdapter.notifyDataSetChanged();
    } else {
      super.onActivityResult(requestCode, resultCode, data);
    }
  }
}
