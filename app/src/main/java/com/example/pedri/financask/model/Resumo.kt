package com.example.pedri.financask.model

import java.math.BigDecimal


class Resumo(private val transacoes: List<Transacao>) {

    val receita: BigDecimal get() = somaPor(Tipo.RECEITA)

    val despesa: BigDecimal get() = somaPor(Tipo.DESPESA)

    val total: BigDecimal get() = receita.subtract(despesa)

    private fun somaPor(tipo: Tipo) : BigDecimal {
        val somaDeTransacoesPeloTipo = transacoes
                .filter { it.tipo == tipo }
                .sumByDouble { it.valor.toDouble() }
        return BigDecimal(somaDeTransacoesPeloTipo)
    }

}
