package com.trainerview.app.presentation.update_group

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class UpdateGroupType : Parcelable {

    @Parcelize
    data object CreateGroup : UpdateGroupType()

    @Parcelize
    class EditGroup(val groupId: Long, val groupName: String) : UpdateGroupType()
}