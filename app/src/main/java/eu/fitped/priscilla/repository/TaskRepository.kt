package eu.fitped.priscilla.repository

import eu.fitped.priscilla.model.TaskDto
import javax.inject.Inject

class TaskRepository @Inject constructor() : ITaskRepository {

    private var tasksData = mutableListOf<TaskDto>()

    override fun getTasks(): List<TaskDto> {
        return tasksData
    }

    override fun getTaskById(id: String): TaskDto? {
        return tasksData.find { it.taskId == id.toLong() }
    }

    override fun setTasks(tasks: List<TaskDto>) {
        tasksData = tasks.toMutableList()
    }

}