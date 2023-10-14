package br.com.erikmorbach.roller.task;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	private ITaskRepository repository;

	@PostMapping("/")
	public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
		var userId = request.getAttribute("userId");
		taskModel.setUserId((UUID) userId);

		var task = repository.save(taskModel);

		return ResponseEntity.status(HttpStatus.OK).body(task);
	}

	@GetMapping("/")
	public ResponseEntity<List<TaskModel>> list(HttpServletRequest request) {
		var userId = request.getAttribute("userId");
		var taskList = this.repository.findByUserId((UUID) userId);
		return ResponseEntity.status(HttpStatus.OK).body(taskList);
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
		taskModel.setId(id);
		var originalTask = this.repository.findById(id).orElse(null);

		if(originalTask == null)
			return ResponseEntity.badRequest().body("Tarefa não encontrada");

		var userId = request.getAttribute("userId");
		if (!originalTask.getUserId().equals(userId))
			return ResponseEntity.badRequest().body("Sem permissão");

		taskModel.setUserId((UUID) userId);

		taskModel.setNullProp(originalTask);

		taskModel = this.repository.save(taskModel);
		return ResponseEntity.ok().body(taskModel);
	}
}
