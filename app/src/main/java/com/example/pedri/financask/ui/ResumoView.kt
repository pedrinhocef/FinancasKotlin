package com.example.pedri.financask.ui

import android.view.View
import com.example.pedri.financask.extension.formataParaBrasileiro
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val view: View) {

    fun adicionaReceitaNoResumo(transacoes: List<Transacao>) {
        var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes){
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }
        view.resumo_card_receita.text = totalReceita.formataParaBrasileiro()
    }
}