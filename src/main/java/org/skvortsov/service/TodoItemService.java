package org.skvortsov.service;

import org.skvortsov.model.TodoData;
import org.skvortsov.model.TodoItem;

public interface TodoItemService {

    void addItem(TodoItem item);

    void removeItem(int id);

    TodoItem getItem(int id);

    void updateItem(TodoItem item);

    TodoData getData();
}
