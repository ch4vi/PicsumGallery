package com.ch4vi.picsumgallery.domain

import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.model.UserState

object DomainConstants {

    val picture by lazy {
        Picture(
            id = 1,
            author = "foo",
            width = 3,
            height = 3,
            url = "foo",
            downloadUrl = "bar"
        )
    }

    val secondPicture by lazy {
        Picture(
            id = 2,
            author = "foobar",
            width = 2,
            height = 1,
            url = "bar",
            downloadUrl = "foo"
        )
    }

    val thirdPicture by lazy {
        Picture(
            id = 3,
            author = "foobar",
            width = 1,
            height = 2,
            url = "bar",
            downloadUrl = "foo"
        )
    }

    val pictures by lazy {
        listOf(picture, secondPicture, thirdPicture)
    }

    val authors by lazy {
        listOf("foo", "bar", "baz")
    }

    val userState by lazy {
        UserState()
    }

    val otherUserState by lazy {
        UserState(imageOrder = ImageOrder.Height(OrderType.Ascending))
    }
}
