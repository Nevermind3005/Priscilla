package eu.fitped.priscilla.model.vpl

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import eu.fitped.priscilla.model.code.CodeFile

@JsonIgnoreProperties(ignoreUnknown = true)
data class VPLRunTestResDto(
    @JsonProperty("adminticket")
    val adminTicket: String,
    @JsonProperty("monitorticket")
    val monitorTicket: String,
    @JsonProperty("executionticket")
    val executionTicket: String
)

@JsonSerialize(using = VPLRunTestReqDtoSerializer::class)
data class VPLRunTestReqDto(
    @JsonProperty("exe_type")
    val executionType: Int,
    @JsonProperty("submitted_files")
    val submittedFiles: List<CodeFile>,
    @JsonProperty("task_id")
    val taskId: Long
)

class VPLRunTestReqDtoSerializer : JsonSerializer<VPLRunTestReqDto>() {
    override fun serialize(value: VPLRunTestReqDto, gen: JsonGenerator, serializers: SerializerProvider) {
        val objectMapper = ObjectMapper()

        gen.writeStartObject()
        gen.writeNumberField("exe_type", value.executionType)
        gen.writeNumberField("task_id", value.taskId)
        val filesJson = objectMapper.writeValueAsString(value.submittedFiles)
        gen.writeStringField("submitted_files", filesJson)

        gen.writeEndObject()
    }
}