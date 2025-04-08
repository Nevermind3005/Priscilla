package eu.fitped.priscilla.repository

import eu.fitped.priscilla.model.TaskDto

interface ITaskRepository {
    fun getTasks() : List<TaskDto>
    fun getTaskById(id: String) : TaskDto?
    fun setTasks(tasks: List<TaskDto>)
}