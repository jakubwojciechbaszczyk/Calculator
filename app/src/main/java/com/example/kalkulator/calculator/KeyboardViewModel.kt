package com.example.kalkulator.calculator

import android.view.View
import androidx.lifecycle.*
import com.example.kalkulator.R
import kotlin.math.pow

class KeyboardViewModel: ViewModel() {
    val currentChar: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    private val repository = Repository
    val list: MutableList<String> = repository.list

    fun charConcat(view: View): Char =
        when (view.id) {
            R.id.one -> '1'
            R.id.two -> '2'
            R.id.three -> '3'
            R.id.four -> '4'
            R.id.five -> '5'
            R.id.six -> '6'
            R.id.seven -> '7'
            R.id.eight -> '8'
            R.id.nine -> '9'
            R.id.zero -> '0'
            R.id.dot -> '.'
            R.id.num -> '^'
            R.id.divide -> '/'
            R.id.multiply -> '*'
            R.id.minus -> '-'
            R.id.plus -> '+'
            R.id.bin -> 'b'
            R.id.okt -> 'o'
            R.id.double_okt -> 'h'
            else -> ' '
        }

    fun toBin(str: String): String {
        val intValue = str.toFloat().toInt()
        // Konwersja Float na reprezentację binarną za pomocą toBits()
        val binaryString = Integer.toBinaryString(intValue)

        // Dodanie zer wiodących, aby uzyskać pełny ciąg 32 bitów
        val paddedBinaryString = binaryString.padStart(14, '0')

        return paddedBinaryString
    }

    fun toOkt(str: String): String {
        val binary = toBin(str)
        val decimal = binary.toInt(2)

        // Konwersja liczby dziesiętnej na liczbę oktalną
        val octal = Integer.toOctalString(decimal)

        return octal
    }

    fun toHex(str: String): String {
        val binary = toBin(str)
        val decimal = binary.toInt(2)

        // Konwersja liczby dziesiętnej na liczbę oktalną
        val hex = Integer.toHexString(decimal)

        return hex
    }


//    fun computing2(): String {
//        val computingValue = currentChar.value ?: "0.0"
//
//            val tokens = tokenizeMathExpression(computingValue)
//            println("tttttt: $tokens")
//            val numbers = tokens
//                .filter { it.isNotBlank() && it.toDoubleOrNull() != null}
//                .map { it.toFloat() }
//                //.filter { it != 0.0f }
//            val operators = tokens
//               .filter { it.isNotBlank() && it.toDoubleOrNull() == null }
//            println("numbers: $numbers")
//            println("operators: $operators")
//            val result = dd(operators, numbers)
//            println("asdasd: $result")
//
//            addItem( "$computingValue = $result")
//        return result.toString()
//    }

    fun addItem(str: String) {
        repository.list.add(str)
    }

    private fun tokenizeMathExpression(expression: String): List<String> {
        val tokens = mutableListOf<String>()
        var currentToken = ""
        var lastChar: Char? = null

        for (char in expression) {
            if (char.isDigit() || char == '.') {
                currentToken += char
            } else if (char in setOf('+', '*', '/', '^')) {
                if (lastChar == null || lastChar in setOf('(', '^')) {
                    // Operator unarny
                    currentToken = "$char$currentToken"
                } else {
                    // Operator binarny
                    tokens.add(currentToken)
                    tokens.add(char.toString())
                    currentToken = ""
                }
            } else if (char == '-') {
                if (lastChar == null || lastChar in setOf('(', '^')) {
                    // Operator unarny
                    currentToken += char
                } else {
                    // Operator binarny
                    tokens.add(currentToken)
                    tokens.add(char.toString())
                    currentToken = ""
                }
            }

            lastChar = char
        }

        if (currentToken.isNotEmpty()) {
            tokens.add(currentToken)
        }

        return tokens
    }

//    private fun dd(operators: List<String>, numbers: List<Float>): Float {
//        var result = numbers.firstOrNull() ?: 0.0f
//        val operatorsIterator = operators.iterator()
//        val numbersIterator = numbers.listIterator(1) // Start from the second number
//        while (operatorsIterator.hasNext() && numbersIterator.hasNext()) {
//            val operator = operatorsIterator.next()
//            val number = numbersIterator.next()
//            result = calculate(result, operator, number)
//        }
//
//        return result
//    }

//    private fun calculate(a: Float, operator: String, b: Float): Float =
//        when (operator) {
//            "+" -> add(a, b)
//            "-" -> if (b < 0) minus(a, -b) else minus(a, b)
//            "*" -> multiple(a, b)
//            "/" -> divide(a, b)
//            "^" -> power(a, b)
//            else -> a
//        }

    fun computing2(): String {
        val computingValue = currentChar.value ?: "0.0"

        val tokens = tokenizeMathExpression(computingValue)
        println("tttttt: $tokens")

        val result = calculateExpression(tokens)
        println("asdasd: $result")

        addItem("$computingValue = $result")
        return result.toString()
    }

    private fun calculateExpression(tokens: List<String>): Float {
        // Operatory i liczby są przechowywane na stosie, aby przestrzegać hierarchii operacji
        val operatorsStack = mutableListOf<String>()
        val numbersStack = mutableListOf<Float>()

        for (token in tokens) {
            if (token.toDoubleOrNull() != null) {
                // Token to liczba, dodaj do stosu liczb
                numbersStack.add(token.toFloat())
            } else {
                // Token to operator, przetwarzaj stos operacji
                while (operatorsStack.isNotEmpty() && hasHigherPrecedence(operatorsStack.last(), token)) {
                    val operator = operatorsStack.removeAt(operatorsStack.size - 1)
                    val b = numbersStack.removeAt(numbersStack.size - 1)
                    val a = numbersStack.removeAt(numbersStack.size - 1)
                    numbersStack.add(applyOperator(operator, a, b))
                }
                operatorsStack.add(token)
            }
        }

        // Wykonaj pozostałe operacje na stosie
        while (operatorsStack.isNotEmpty()) {
            val operator = operatorsStack.removeAt(operatorsStack.size - 1)
            val b = numbersStack.removeAt(numbersStack.size - 1)
            val a = numbersStack.removeAt(numbersStack.size - 1)
            numbersStack.add(applyOperator(operator, a, b))
        }
        return numbersStack.firstOrNull() ?: 0.0f
    }

    private fun hasHigherPrecedence(op1: String, op2: String): Boolean {
        val precedenceMap = mapOf("^" to 3, "*" to 2, "/" to 2, "+" to 1, "-" to 1)
        return precedenceMap.getOrDefault(op1, 0) >= precedenceMap.getOrDefault(op2, 0)
    }

    private fun applyOperator(operator: String, a: Float, b: Float): Float =
        when (operator) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> if (b != 0.0f) a / b else 0.0f
            "^" -> a.pow(b)
            else -> 0.0f
        }


    private fun add(a: Float, b: Float): Float = a.plus(b)
    private fun minus(a: Float, b: Float): Float { println("a: $a $b"); return a.minus(b) }
    private fun divide(a: Float, b: Float): Float = if (b == 0.0f) b else a.div(b)
    private fun power(a: Float, b: Float): Float = a.pow(b)
    private fun multiple(a: Float, b: Float): Float = a.times(b)



}