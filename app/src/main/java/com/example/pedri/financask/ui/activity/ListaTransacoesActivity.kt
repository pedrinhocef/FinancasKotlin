package com.example.pedri.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.pedri.financask.R
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import com.example.pedri.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal


class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        val transacoes = transacoesDeExemplo()

        configuraTransacao(transacoes)

    }

    private fun configuraTransacao(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(Transacao(valor = BigDecimal(20.5),
                tipo = Tipo.DESPESA),
                Transacao(valor = BigDecimal(900),
                        categoria = "Salario de qualque mes mesmo",
                        tipo = Tipo.RECEITA))
    }

}