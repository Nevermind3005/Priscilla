package eu.fitped.priscilla.model

enum class TaskType(val id: Long) {
    TEXT_ONLY(1L),
    TEXT_INPUT(2L),
    RADIO_INPUT(3L),
    CHECKBOX_INPUT(4L),
    TEXT_INSIDE_ONE(5L),
    CODE(9L);

    companion object {
        fun forId(id: Long) = entries.find{ it.id == id }
    }
}