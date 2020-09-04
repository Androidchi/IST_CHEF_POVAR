package ist.uz.istchef.model

data class EventModel<T>(
    val event: Int,
    var data: T
)