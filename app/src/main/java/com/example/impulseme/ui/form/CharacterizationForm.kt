package com.example.impulseme.ui.form

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.impulseme.model.form.Opcion
import com.example.impulseme.viewModel.FormViewModel

@Composable
fun CharacterizationForm(
    onBack: () -> Unit,
    onNext: () -> Unit,
    formViewModel: FormViewModel = hiltViewModel()
) {
    formViewModel.getProfileQuestionary()
    TabLayout()
}


@Composable
@Preview
fun TabLayout() {
    val tabs = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
    )

    var tabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier.fillMaxWidth(),
            contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                )
            }
        }
        when (tabIndex) {
            0 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu nombre?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            1 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu apellido?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            2 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu edad?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            3 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu país?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            4 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu ciudad?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            5 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu ocupación?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            6 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu nivel de educación?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            7 -> {
                SingleChoiceQuestion(
                    question = "¿Cuál es tu estado civil?",
                    options = listOf(
                        Opcion("1", "Opción 1"),
                        Opcion("2", "Opción 2"),
                        Opcion("3", "Opción 3"),
                    ),
                    onOptionSelected = {
                        tabIndex++
                    }
                )
            }
            else -> {
                // Aquí puedes manejar el caso en que todas las pestañas han sido completadas
                Text(text = "Formulario completado")
            }
        }
    }
}