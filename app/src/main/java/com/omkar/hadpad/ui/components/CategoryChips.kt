package com.omkar.hadpad.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.omkar.hadpad.R

@Composable
fun CategoryChips(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
){

    val categories = listOf(
        "All",
        "Pending",
        "Completed"
    )

    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(categories) { category ->



            val selected = category == selectedCategory

            val icon = when (category) {
                "All" -> R.drawable.rounded_grid_view_24
                "Pending" -> R.drawable.rounded_schedule_24
                else -> R.drawable.rounded_check_circle_24
            }



            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(18.dp))
                    .clickable {
                        onCategorySelected(category)
                    }
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surface
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected)
                            Color.Transparent
                        else
                            MaterialTheme.colorScheme.outline.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(
                        horizontal = 14.dp,
                        vertical = 10.dp
                    )
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {

                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = if (selected)
                            Color.White
                        else
                            MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = category,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (selected)
                            Color.White
                        else
                            MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}