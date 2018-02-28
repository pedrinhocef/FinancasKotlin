package com.example.pedri.financask.model

import java.math.BigDecimal


class Resumo(private val transacoes: List<Transacao>) {

    fun receita() : BigDecimal {
        val somaDeReceita = transacoes
                .filter { transacao -> transacao.tipo == Tipo.RECEITA }
                .sumByDouble { transacao -> transacao.valor.toDouble() }
        return BigDecimal(somaDeReceita)
    }

    fun despesa() : BigDecimal {
        val somaDespesa = transacoes
                .filter { transacao -> transacao.tipo == Tipo.DESPESA }
                .sumByDouble { transacao -> transacao.valor.toDouble() }
        return BigDecimal(somaDespesa)
    }

    fun total() : BigDecimal{
         return receita().subtract(despesa())
    }

}
