package com.trainerview.app.presentation.update_group

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class UpdateGroupNavParams : Parcelable {

    @Parcelize
    data object CreateGroup : UpdateGroupNavParams()

    @Parcelize
    class EditGroup(val groupId: Long, val groupName: String) : UpdateGroupNavParams()
}