package com.omkar.hadpad.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omkar.hadpad.R


@Composable
fun ActionTopBar() {

    val statusBarPadding = WindowInsets.statusBars
        .asPaddingValues()
        .calculateTopPadding()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            //.statusBarsPadding()
            .padding(
                top = statusBarPadding * 0.85f,
                start = 16.dp,
                end = 16.dp,
                bottom = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Menu Icon
        IconButton(
            onClick = {},
            modifier = Modifier.size(42.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.menu_bars),
                contentDescription = null,
                tint = Color(0xFF1B1B3A),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title Section
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = "ActionNotes",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Stay organized, get things done.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Profile Button
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    Color(0xFFE8DFFF)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "A",
                color = Color(0xFF7C5CFF),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


