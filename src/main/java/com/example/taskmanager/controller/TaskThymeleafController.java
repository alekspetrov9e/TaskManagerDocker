package com.example.taskmanager.controller;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class  TaskThymeleafController {

    @Autowired
    private TaskRepository taskRepository;

    // View all tasks
    @GetMapping("/view")
    public String viewTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "tasks"; // Thymeleaf template: tasks.html
    }

    // Create a new task
    @PostMapping("/create")
    public String createTaskFromForm(Task task) {
        taskRepository.save(task);
        return "redirect:/tasks/view";
    }

    // Show the Edit Task form
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            model.addAttribute("task", task.get());
            return "edit-task"; // A separate Thymeleaf template for editing
        } else {
            model.addAttribute("error", "Task not found");
            return "error"; // Or redirect to an error page
        }
    }

    // Edit/Update an existing task
    @PostMapping("/edit/{id}")
    public String editTask(@PathVariable Long id, @ModelAttribute Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setCompleted(updatedTask.isCompleted());
            taskRepository.save(task);
        }
        return "redirect:/tasks/view"; // Redirect to the task list after updating
    }

    // Toggle task completion
    @PostMapping("/toggle-completed/{id}")
    public String toggleTaskCompletion(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        taskOptional.ifPresent(task -> {
            task.setCompleted(!task.isCompleted());
            taskRepository.save(task);
        });
        return "redirect:/tasks/view";
    }

    // Delete a task
    @PostMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        }
        return "redirect:/tasks/view";
    }
}
