package com.example.impulseme.ui.form

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.impulseme.model.form.Opcion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MultipleAnswerQuestionForm(
    question: String,
    options: List<String>,
    selectedOptions: List<String>,
    onOptionSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Pregunta con estilo
        Text(
            text = question,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Opciones con Checkboxes animados
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = option in selectedOptions,
                    onCheckedChange = { onOptionSelected(option) }
                )
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SingleChoiceQuestion(
    question: String,
    options: List<Opcion>?,
    onOptionSelected: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Pregunta con estilo claro
        Text(
            text  = question,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 12.dp)
        )
         // Opciones con RadioButtons
        options?.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var selected by remember { mutableStateOf(false) }
                ChoiceCard(
                    text = option.label,
                    selected = selected,
                    onClick = {
                        selected = !selected
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(200)
                            onOptionSelected(option.valor)
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun QuestionPreview(){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        SingleChoiceQuestion(
            question = "¿Cuál es tu color fav  orito?",
            options = listOf(
                Opcion("1", "Rojo"),
                Opcion("2", "Verde"),
                Opcion("3", "Azul")
            ),
            onOptionSelected = {}
        )
    }
}

@Composable
fun ChoiceCard(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )
            RadioButton(
                selected = selected,
                onClick = { onClick() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}