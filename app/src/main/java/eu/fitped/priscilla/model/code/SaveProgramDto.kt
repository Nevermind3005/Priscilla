package eu.fitped.priscilla.model.code

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = SaveProgramDtoSerializer::class)
data class SaveProgramReqDto(
    @JsonProperty("activity_type")
    val activityType: String,
    @JsonProperty("files")
    val files: List<CodeFile>,
    @JsonProperty("task_id")
    val taskId: Long
)

class SaveProgramDtoSerializer : JsonSerializer<SaveProgramReqDto>() {
    override fun serialize(value: SaveProgramReqDto, gen: JsonGenerator, serializers: SerializerProvider) {
        val objectMapper = ObjectMapper()

        gen.writeStartObject()
        gen.writeStringField("activity_type", value.activityType)
        gen.writeNumberField("task_id", value.taskId)
        val filesJson = objectMapper.writeValueAsString(value.files)
        gen.writeStringField("files", filesJson)
        gen.writeEndObject()
    }
}