package com.ch4vi.picsumgallery.presentation.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ch4vi.picsumgallery.R
import com.ch4vi.picsumgallery.domain.model.ImageOrder
import com.ch4vi.picsumgallery.domain.model.OrderType
import com.ch4vi.picsumgallery.presentation.util.MultiPreview
import com.ch4vi.picsumgallery.ui.theme.PicsumGalleryTheme

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    imageOrder: ImageOrder = ImageOrder.None(OrderType.Ascending),
    onOrderChange: (ImageOrder) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SortRadioButton(
                label = stringResource(id = R.string.order_none),
                isSelected = imageOrder is ImageOrder.None,
                onSelected = { onOrderChange(ImageOrder.None(imageOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortRadioButton(
                label = stringResource(id = R.string.order_width),
                isSelected = imageOrder is ImageOrder.Width,
                onSelected = { onOrderChange(ImageOrder.Width(imageOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortRadioButton(
                label = stringResource(id = R.string.order_height),
                isSelected = imageOrder is ImageOrder.Height,
                onSelected = { onOrderChange(ImageOrder.Height(imageOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SortRadioButton(
                label = stringResource(id = R.string.order_ascending),
                isSelected = imageOrder.orderType is OrderType.Ascending,
                onSelected = { onOrderChange(imageOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortRadioButton(
                label = stringResource(id = R.string.order_descending),
                isSelected = imageOrder.orderType is OrderType.Descending,
                onSelected = { onOrderChange(imageOrder.copy(OrderType.Descending)) }
            )
        }
    }
}

@MultiPreview
@Composable
fun SortSectionPreview() {
    PicsumGalleryTheme {
        SortSection {}
    }
}
