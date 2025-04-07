package com.example.impulseme.viewModel

import androidx.lifecycle.ViewModel
import com.example.impulseme.repository.FormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FormViewModel @Inject constructor(
    private val formRepository: FormRepository
): ViewModel() {

    fun getProfileQuestionary() {
        return formRepository.getProfileQuestionary()
    }
}