package com.omkar.hadpad.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
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
import com.omkar.hadpad.data.model.Task

@Composable
fun TaskCard(
    task: Task,
    onCheckedChange: () -> Unit
) {

    val priorityColor = when (task.priority) {
        1 -> Color(0xFF4CD787)
        2 -> Color(0xFFFFB84D)
        else -> Color(0xFFFF6B81)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.08f),
                shape = RoundedCornerShape(24.dp)
            )
    ) {

        // Colored Side Tag
        Box(
            modifier = Modifier
                .padding(
                    start = 10.dp, top = 18.dp, bottom = 18.dp
                )
                .width(5.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(priorityColor)
                .height(90.dp)
        )

        Row(
            modifier = Modifier.padding(18.dp)
        ) {

            Checkbox(
                checked = task.isCompleted, onCheckedChange = {
                    onCheckedChange()
                })

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.rounded_calendar_month_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text(
                        text = "Today",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.End
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.rounded_more_vert_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(priorityColor.copy(alpha = 0.12f))
                        .padding(
                            horizontal = 10.dp, vertical = 5.dp
                        )
                ) {

                    Text(
                        text = when (task.priority) {
                            1 -> "Low"
                            2 -> "Medium"
                            else -> "High"
                        }, style = MaterialTheme.typography.bodySmall, color = priorityColor
                    )
                }
            }
        }
    }
}