package org.skvortsov.controller;

import lombok.extern.slf4j.Slf4j;
import org.skvortsov.model.TodoData;
import org.skvortsov.model.TodoItem;
import org.skvortsov.service.TodoItemService;
import org.skvortsov.util.AttributeNames;
import org.skvortsov.util.Mappings;
import org.skvortsov.util.ViewNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Slf4j
@Controller
public class TodoItemController {

    private final TodoItemService todoItemService;

    @Autowired
    public TodoItemController(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @ModelAttribute
    public TodoData todoData() {
        return todoItemService.getData();
    }

    // http://localhost:8080/todo-list/items
    @GetMapping(Mappings.ITEMS)
    public String items() {
        return ViewNames.ITEM_LIST;
    }

    @GetMapping(Mappings.ADD_ITEM)
    public String addEditItem(@RequestParam(required = false, defaultValue = "-1") int id, Model model) {
        log.info("Editing id = {}", id);
        TodoItem item = todoItemService.getItem(id);
        if (item == null) {
            item = new TodoItem("", "", LocalDate.now());
        }
        model.addAttribute(AttributeNames.TODO_ITEM, item);
        return ViewNames.ADD_ITEM;
    }

    @GetMapping(Mappings.VIEW_ITEM)
    public String viewItem(@RequestParam int id, Model model) {
        TodoItem item = todoItemService.getItem(id);
        log.info("todoItem = {}", item);
        model.addAttribute(AttributeNames.TODO_ITEM, item);
        return ViewNames.VIEW_ITEM;
    }

    @PostMapping(Mappings.ADD_ITEM)
    public String processItem(@ModelAttribute(AttributeNames.TODO_ITEM) TodoItem item) {
        log.info("todoItem from form = {}", item);
        if (item.getId() == 0) {
            todoItemService.addItem(item);
        } else {
            todoItemService.updateItem(item);
        }
        return "redirect:/" + Mappings.ITEMS;
    }

    @GetMapping(Mappings.DELETE_ITEM)
    public String deleteItem(@RequestParam int id) {
        log.info("Deleting item with id = {}", id);
        todoItemService.removeItem(id);
        return "redirect:/" + Mappings.ITEMS;
    }
}
