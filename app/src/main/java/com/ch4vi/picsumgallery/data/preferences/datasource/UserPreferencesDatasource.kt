package com.ch4vi.picsumgallery.data.preferences.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesMapper.toImageOrder
import com.ch4vi.picsumgallery.data.preferences.datasource.UserPreferencesMapper.toPreferences
import com.ch4vi.picsumgallery.domain.model.Failure
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.UserState

class UserPreferencesDatasource(
    private val preferences: SharedPreferences
) {
    private object Key {
        const val author = "Author"
        const val imageOrder = "ImageOrder"
        const val orderType = "OrderType"
    }

    fun storeUserState(userState: UserState) {
        preferences.edit {
            putString(Key.author, userState.authorFilter)
            userState.imageOrder.toPreferences().let {
                putInt(Key.imageOrder, it.first)
                putInt(Key.orderType, it.second)
            }
        }
    }

    fun getUserState(): UserState {
        return try {
            val author = preferences.getString(Key.author, "") ?: ""
            val default = ImageOrder.None(OrderType.Ascending).toPreferences()
            val imageOrder = (
                preferences.getInt(Key.imageOrder, default.first) to
                    preferences.getInt(Key.orderType, default.second)
                ).toImageOrder()
            UserState(author, imageOrder)
        } catch (f: Failure.MapperFailure) {
            UserState()
        }
    }
}
