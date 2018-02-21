package com.example.pedri.financask.model

import java.math.BigDecimal
import java.util.Calendar

class Transacao(val valor:BigDecimal,
                val categoria: String = "indefinida",
                val tipo: Tipo,
                val data: Calendar = Calendar.getInstance())
