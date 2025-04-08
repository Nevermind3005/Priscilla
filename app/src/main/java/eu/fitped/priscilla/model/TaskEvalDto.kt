package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = TaskEvalDtoSerializer::class)
data class TaskEvalDto(
    @JsonProperty("activity_type")
    val activityType: String,
    @JsonProperty("answer_list")
    val answerList: List<String>,
    @JsonProperty("task_id")
    val taskId: Long,
    @JsonProperty("task_type_id")
    val taskTypeId: Long,
    @JsonProperty("time_length")
    val timeLength: Long,
)

class TaskEvalDtoSerializer : JsonSerializer<TaskEvalDto>() {
    override fun serialize(value: TaskEvalDto, gen: JsonGenerator, serializers: SerializerProvider) {
        gen.writeStartObject()
        gen.writeNumberField("task_id", value.taskId)
        gen.writeNumberField("task_type_id", value.taskTypeId)
        gen.writeNumberField("time_length", value.timeLength)
        gen.writeStringField("activity_type", value.activityType)
        val escapedList = value.answerList.map { it.replace("\"", "\\\"") }
        gen.writeStringField("answer_list", escapedList.joinToString(prefix = "[\"", postfix = "\"]", separator = "\",\""))
        gen.writeEndObject()
    }
}