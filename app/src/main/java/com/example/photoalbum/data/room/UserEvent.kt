package com.example.photoalbum.data.room


sealed interface UserEvent{
    object SaveUser: UserEvent
    data class SetFirstName(val firstName:String):UserEvent
    data class SetLastName(val lastName:String):UserEvent
    data class SetEmail(val email:String):UserEvent
    data class SetPassword(val password:String):UserEvent
    data class DeleteUser(val user: User):UserEvent
    data class ChangeEnableGeolocation(val isGeolocationEnabled: Boolean):UserEvent
    object ShowDialog: UserEvent
    object HideDialog: UserEvent
}