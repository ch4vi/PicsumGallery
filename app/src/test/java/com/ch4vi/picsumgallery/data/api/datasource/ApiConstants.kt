package com.ch4vi.picsumgallery.data.api.datasource

import com.ch4vi.picsumgallery.data.api.model.PictureDTO

object ApiConstants {
    val picturePageSuccessResponse by lazy {
        """
        [
            {
                "id": 0,
                "author": "author 1",
                "width": 5000,
                "height": 2333,
                "url": "https://url.one",
                "download_url": "https://download_url.one"
            },
            {
                "id": 1,
                "author": "author 2",
                "width": 4000,
                "height": 1333,
                "url": "https://url.two",
                "download_url": "https://download_url.two"
            },
            {
                "id": 2,
                "author": "author 3",
                "width": 3000,
                "height": 3333,
                "url": "https://url.three",
                "download_url": "https://download_url.three"
            }
        ]
        """
    }

    val picturePageErrorResponse by lazy {
        """
        [
            {
                "id": null,
                "author": "author 1",
                "width": 5000,
                "height": 2333,
                "url": "https://url.one",
                "download_url": "https://download_url.one"
            }
        ]
        """
    }

    val pictureDTO by lazy {
        PictureDTO(
            id = 1,
            author = "foo",
            width = 1234,
            height = 4321,
            url = "foo",
            downloadUrl = "bar"
        )
    }

    val otherPictureDTO by lazy {
        PictureDTO(
            id = 2,
            author = "foobar",
            width = 4321,
            height = 1234,
            url = "bar",
            downloadUrl = "foo"
        )
    }
}
