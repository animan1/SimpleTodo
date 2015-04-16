package com.codepath.simpletodo;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "ToDo")
public class ToDo extends Model {
  @Column(name = "text")
  public String text;

  public ToDo(String text) {
    super();
    this.text = text;
  }

  public ToDo() {
    this("");
  }

  @Override
  public String toString() {
    return text;
  }
}
