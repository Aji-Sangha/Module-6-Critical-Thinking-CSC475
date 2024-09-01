package com.calculator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.calculator.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    private lateinit var binding: FragmentCalculatorBinding
    private var expression = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        setupKeypad()
        setupThemeSwitchButton()

        return binding.root
    }

    private fun setupKeypad() {
        binding.apply {
            button0.setOnClickListener { appendToExpression("0") }
            button1.setOnClickListener { appendToExpression("1") }
            button2.setOnClickListener { appendToExpression("2") }
            button3.setOnClickListener { appendToExpression("3") }
            button4.setOnClickListener { appendToExpression("4") }
            button5.setOnClickListener { appendToExpression("5") }
            button6.setOnClickListener { appendToExpression("6") }
            button7.setOnClickListener { appendToExpression("7") }
            button8.setOnClickListener { appendToExpression("8") }
            button9.setOnClickListener { appendToExpression("9") }
            buttonPlus.setOnClickListener { appendToExpression(" + ") }
            buttonMinus.setOnClickListener { appendToExpression(" - ") }
            buttonMultiply.setOnClickListener { appendToExpression(" * ") }
            buttonDivide.setOnClickListener { appendToExpression(" / ") }
            buttonClear.setOnClickListener { clear() }
            buttonBackspace.setOnClickListener { backspace() }
            buttonEquals.setOnClickListener { calculateResult() }
        }
    }

    private fun setupThemeSwitchButton() {
        binding.switchThemeButton.setOnClickListener {
            val currentMode = AppCompatDelegate.getDefaultNightMode()
            if (currentMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }

    private fun appendToExpression(value: String) {
        expression += value
        binding.displayText.text = expression
    }

    private fun clear() {
        expression = ""
        binding.displayText.text = expression
    }

    private fun backspace() {
        if (expression.isNotEmpty()) {
            expression = expression.substring(0, expression.length - 1)
            binding.displayText.text = expression
        }
    }

    private fun calculateResult() {
        try {
            val result = evaluateExpression(expression)
            expression += " = $result"
            binding.displayText.text = expression
        } catch (e: Exception) {
            binding.displayText.text = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val sanitizedExpression = expression.replace(",", ".")
        val parts = sanitizedExpression.split(" ").filter { it.isNotEmpty() }
        if (parts.size < 3) return parts[0].toDouble()

        var result = parts[0].toDouble()
        var i = 1
        while (i < parts.size) {
            val operator = parts[i]
            val nextNumber = parts[i + 1].toDouble()
            result = when (operator) {
                "+" -> result + nextNumber
                "-" -> result - nextNumber
                "*" -> result * nextNumber
                "/" -> result / nextNumber
                else -> result
            }
            i += 2
        }
        return result
    }
}
