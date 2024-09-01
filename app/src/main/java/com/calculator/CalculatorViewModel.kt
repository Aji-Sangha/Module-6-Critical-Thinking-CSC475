package com.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _displayText = MutableLiveData<String>("")
    val displayText: LiveData<String> get() = _displayText

    fun onButtonClick(buttonText: String) {
        _displayText.value = (_displayText.value ?: "") + buttonText
    }

    fun clear() {
        _displayText.value = ""
    }

    fun onBackspace() {
        _displayText.value = (_displayText.value ?: "").dropLast(1)
    }

    fun calculateResult() {
        try {
            // Basic evaluation logic; for more complex expressions, use a library
            val result = evaluateExpression(_displayText.value ?: "")
            _displayText.value = result.toString()
        } catch (e: Exception) {
            _displayText.value = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Handle simple expressions; consider using a library for more complex parsing
        return expression.toDoubleOrNull() ?: 0.0
    }
}
