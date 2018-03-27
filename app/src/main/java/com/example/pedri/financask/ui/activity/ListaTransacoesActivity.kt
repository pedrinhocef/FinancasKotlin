package com.example.pedri.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.example.pedri.financask.R
import com.example.pedri.financask.delegate.TransacaoDelegate
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import com.example.pedri.financask.ui.ResumoView
import com.example.pedri.financask.ui.adapter.ListaTransacoesAdapter
import com.example.pedri.financask.ui.dialog.AdicionaTransacaoDialog
import com.example.pedri.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*


class ListaTransacoesActivity : AppCompatActivity() {


    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraTransacao()
        configuraFab()

    }

    private fun configuraFab() {
        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DESPESA)
                }

        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                .chama(tipo, object : TransacaoDelegate {
                    override fun delegate(transacao: Transacao) {
                        transacoes.add(transacao)
                        atualizaTransacoes()
                        lista_transacoes_adiciona_menu.close(true)
                    }
                })
    }

    private fun atualizaTransacoes() {
        configuraResumo()
        configuraTransacao()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraTransacao() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
        lista_transacoes_listview.setOnItemClickListener { parent, view, position, id ->
            val transacao = transacoes[position]

            AlteraTransacaoDialog(window.decorView as ViewGroup, this)
                    .chama(transacao, object : TransacaoDelegate {
                        override fun delegate(transacao: Transacao) {
                            transacoes[position] = transacao
                            atualizaTransacoes()
                        }

            })
        }
    }

}