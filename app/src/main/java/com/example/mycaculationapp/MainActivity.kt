package com.example.mycaculationapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private var currentNumber = StringBuilder()
    private var firstOperand = 0
    private var operator: String? = null
    private var isNewCalculation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Khởi tạo TextView
        textView = findViewById(R.id.textView)

        // Khởi tạo các nút số
        val numberButtons = listOf(
            R.id.button5, R.id.button6, R.id.button7, R.id.button9,
            R.id.button10, R.id.button12, R.id.button13, R.id.button15,
            R.id.button16, R.id.button18
        )

        // Gán sự kiện cho các nút số
        numberButtons.forEach { id ->
            findViewById<Button>(id).setOnClickListener {
                appendNumber((it as Button).text.toString())
            }
        }

        // Gán sự kiện cho các nút toán tử
        findViewById<Button>(R.id.button4).setOnClickListener { setOperator("/") } // Chia
        findViewById<Button>(R.id.button11).setOnClickListener { setOperator("×") } // Nhân
        findViewById<Button>(R.id.button14).setOnClickListener { setOperator("-") } // Trừ
        findViewById<Button>(R.id.button17).setOnClickListener { setOperator("+") } // Cộng

        // Nút CE (xóa toàn bộ)
        findViewById<Button>(R.id.button1).setOnClickListener {
            clearEverything()
        }

        // Nút C (xóa số hiện tại)
        findViewById<Button>(R.id.button2).setOnClickListener {
            clearCurrent()
        }

        // Nút BS (xóa ký tự cuối)
        findViewById<Button>(R.id.button3).setOnClickListener {
            backspace()
        }

        // Nút +/- (đổi dấu)
        findViewById<Button>(R.id.button8).setOnClickListener {
            toggleSign()
        }

        // Nút = (tính kết quả)
        findViewById<Button>(R.id.button20).setOnClickListener {
            calculate()
        }

        // Nút . (bỏ qua vì xử lý số nguyên)
        findViewById<Button>(R.id.button19).isEnabled = false
    }

    private fun appendNumber(number: String) {
        if (isNewCalculation) {
            currentNumber.clear()
            isNewCalculation = false
        }
        currentNumber.append(number)
        updateDisplay()
    }

    private fun setOperator(op: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toString().toInt()
            operator = op
            currentNumber.clear()
            isNewCalculation = false
        }
    }

    private fun calculate() {
        if (operator != null && currentNumber.isNotEmpty()) {
            val secondOperand = currentNumber.toString().toInt()
            val result = when (operator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "×" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0) firstOperand / secondOperand else {
                    textView.text = "Error"
                    return
                }
                else -> 0
            }
            textView.text = result.toString()
            currentNumber.clear()
            currentNumber.append(result)
            operator = null
            isNewCalculation = true
        }
    }

    private fun clearEverything() {
        currentNumber.clear()
        firstOperand = 0
        operator = null
        isNewCalculation = true
        textView.text = "0"
    }

    private fun clearCurrent() {
        currentNumber.clear()
        updateDisplay()
    }

    private fun backspace() {
        if (currentNumber.isNotEmpty()) {
            currentNumber.deleteCharAt(currentNumber.length - 1)
            updateDisplay()
        }
    }

    private fun toggleSign() {
        if (currentNumber.isNotEmpty()) {
            if (currentNumber.startsWith("-")) {
                currentNumber.deleteCharAt(0)
            } else {
                currentNumber.insert(0, "-")
            }
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        textView.text = if (currentNumber.isEmpty()) "0" else currentNumber.toString()
    }
}