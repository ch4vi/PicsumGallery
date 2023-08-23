package com.ch4vi.picsumgallery.domain.usecase

import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.domain.model.Picture
import com.ch4vi.picsumgallery.domain.model.UserState
import com.ch4vi.picsumgallery.domain.repository.PicturesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetPicturesGallery @Inject constructor(
    private val repository: PicturesRepository
) {

    operator fun invoke(clear: Boolean, page: Int, userState: UserState): Flow<List<Picture>> {
        return repository.getPictures(clear, page, userState.authorFilter.ifEmpty { null })
            .transform { pictures ->
                if (pictures == null) {
                    emit(emptyList())
                } else {
                    val sortedList = when (userState.imageOrder) {
                        is ImageOrder.None -> pictures
                        is ImageOrder.Height -> {
                            when (userState.imageOrder.orderType) {
                                OrderType.Ascending -> pictures.sortedBy { it.height }
                                OrderType.Descending -> pictures.sortedByDescending { it.height }
                            }
                        }

                        is ImageOrder.Width -> {
                            when (userState.imageOrder.orderType) {
                                OrderType.Ascending -> pictures.sortedBy { it.width }
                                OrderType.Descending -> pictures.sortedByDescending { it.width }
                            }
                        }
                    }
                    emit(sortedList)
                }
            }
    }
}
