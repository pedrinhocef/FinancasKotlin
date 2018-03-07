package com.example.pedri.financask.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.limitaEmAte(caracteres: Int) : String{
    if(this.length > caracteres){
        val primeiraLetra = 0
        return "${this.substring(primeiraLetra, caracteres)}..."
    }
    return this
}

fun String.converteParaCalendar() : Calendar {
    val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
    val dataConvertida = formatoBrasileiro.parse(this)
    val data = Calendar.getInstance()
    data.time = dataConvertida
    return data
}