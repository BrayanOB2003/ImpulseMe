package com.example.impulseme.ui.list

import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.impulseme.model.EnumPriorityTask
import com.example.impulseme.model.TaskInfo

@Composable
fun CardList(cardItems: List<TaskInfo>, onDelete: (id: Int) -> Unit) {
    LazyColumn (
        modifier = Modifier.fillMaxSize()
    ) {
        items(cardItems) { item ->
            CardItem(
                title = item.title,
                description = item.description,
                dateStart = item.dateStart,
                dateEnd = item.dateEnd,
                timeStart = item.timeStart,
                timeEnd = item.timeEnd,
                priority = item.priority,
                isAllDay = item.isAllDay,
                onDelete = { onDelete(item.id) }
            )
        }
    }
}


@Composable
fun CardItem(
    title: String,
    description: String,
    dateStart: String,
    dateEnd: String,
    timeStart: String,
    timeEnd: String,
    isAllDay: Boolean,
    priority: EnumPriorityTask,
    onDelete: () -> Unit
) {
    val priorityColor = EnumPriorityTask.fromValueToColor(priority.value)

    fun getDateTimeString(): String {
        return if (isAllDay)
            "Todo el día"
        else if(dateStart == dateEnd)
            "$dateStart $timeStart - $timeEnd"
        else
            "$dateStart $timeStart - $dateEnd $timeEnd"
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { /* Acción si se desea abrir detalles */ },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador de prioridad
            Box(
                modifier = Modifier
                    .size(8.dp, 48.dp)
                    .background(priorityColor, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = getDateTimeString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp) // Superficie táctil mejorada
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea $title",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


