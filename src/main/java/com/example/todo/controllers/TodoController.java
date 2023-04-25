package com.example.todo.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.model.Todo;
import com.example.todo.services.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	@Autowired
	private TodoService todoService;

	@GetMapping
	public List<Todo> getAllTodos() {
		return todoService.getAllTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
		Optional<Todo> todo = todoService.getTodoById(id);
		return todo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
		Todo createdTodo = todoService.createTodo(todo);
		return ResponseEntity.created(URI.create("/api/todos/" + createdTodo.getId())).body(createdTodo);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTodoById(@PathVariable Long id) {
		todoService.deleteTodoById(id);
		return ResponseEntity.noContent().build();
	}
}
