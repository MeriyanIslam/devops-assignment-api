package com.meriyan.devops.assignment.repository;

import com.meriyan.devops.assignment.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
