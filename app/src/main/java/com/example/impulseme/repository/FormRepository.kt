package com.example.impulseme.repository

import android.util.Log
import com.example.impulseme.model.form.Pregunta
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class FormRepository @Inject constructor() {
    val db = Firebase.firestore

    fun getProfileQuestionary(){
        db.collection("preguntas")
            .orderBy("order")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.w("Preguntas", "Preguntas: $document")
                }
                val preguntas = result.map { it.toObject(Pregunta::class.java) }

            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents.", exception)
            }
    }
}