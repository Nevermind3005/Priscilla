package eu.fitped.priscilla.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContent(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
    @JsonProperty("answer_list")
    val answerList: List<AnswerDto>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentText(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentDraggable(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("help")
    val help: String,
    @JsonProperty("code")
    val code: String,
    @JsonProperty("codes")
    val codes: List<String>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentDND(
    @JsonProperty("content")
    val content: String,
    @JsonProperty("fakes")
    val fakes: List<String>,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentCodeFile(
    @JsonProperty("rContent")
    val rContent: String,
    @JsonProperty("aContent")
    val aContent: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TaskContentCode(
    @JsonProperty("title")
    val title: String,
    @JsonProperty("assignment")
    val assignment: String,
    @JsonProperty("files")
    val files: List<TaskContentCodeFile>,
    @JsonProperty("testCases")
    val testCases: String,
    @JsonProperty("filesToKeep")
    val filesToKeep: List<String>,
    @JsonProperty("configFiles")
    val configFiles: List<String>,
    @JsonProperty("help")
    val help: String
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class GlobalsCodeTask(
    @JsonProperty("files")
    val files: Files
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Files(
    @JsonProperty("files")
    val files: List<String>,

    @JsonProperty("filesToKeep")
    val filesToKeep: List<String>,

    @JsonProperty("configFiles")
    val configFiles: List<String>
)