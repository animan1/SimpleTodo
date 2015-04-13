package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {

  public static final String ITEM = "item";
  private EditText itemEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);
    String item = getIntent().getStringExtra(ITEM);
    itemEditText = (EditText) findViewById(R.id.editItemText);
    itemEditText.setText(item);
    itemEditText.setSelection(item.length());
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_edit_item, menu);
    return true;
  }

  public void onSaveItem(View v) {
    Intent data = getIntent();
    data.putExtra(EditItemActivity.ITEM, itemEditText.getText().toString());
    setResult(RESULT_OK, data);
    this.finish();
  }
}
