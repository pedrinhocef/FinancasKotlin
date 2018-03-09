package com.example.pedri.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.pedri.financask.R
import com.example.pedri.financask.delegate.TransacaoDelegate
import com.example.pedri.financask.model.Transacao
import com.example.pedri.financask.ui.ResumoView
import com.example.pedri.financask.ui.adapter.ListaTransacoesAdapter
import com.example.pedri.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*


class ListaTransacoesActivity : AppCompatActivity() {


    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraTransacao()

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                            .configuraDialog(object : TransacaoDelegate{
                                override fun delegate(transacao: Transacao) {
                                    atualizaTransacoes(transacao)
                                    lista_transacoes_adiciona_menu.close(true)
                                }
                            })

                }

        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    Toast.makeText(this@ListaTransacoesActivity, "Clique receita", Toast
                            .LENGTH_LONG).show()
                }

    }

    private fun atualizaTransacoes(transacao: Transacao) {
        transacoes.add(transacao)
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
    }

}