package com.example.pedri.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.example.pedri.financask.R
import com.example.pedri.financask.extension.formataParaBrasileiro
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import com.example.pedri.financask.ui.ResumoView
import com.example.pedri.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*


class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        val transacoes: List<Transacao> = transacoesDeExemplo()

        configuraResumo(transacoes)

        configuraTransacao(transacoes)

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    val view: View = window.decorView
                    val viewCriada = LayoutInflater.from(this)
                            .inflate(R.layout.form_transacao, view as ViewGroup, false)

                    val ano = 2018
                    val mes = 2
                    val dia = 2

                    val hoje = Calendar.getInstance()

                    viewCriada.form_transacao_data
                            .setText(hoje.formataParaBrasileiro())

                    viewCriada.form_transacao_data
                            .setOnClickListener {
                                DatePickerDialog(this, DatePickerDialog.OnDateSetListener
                                { view, ano, mes, dia ->

                                    val dataSelecionada = Calendar.getInstance()
                                    dataSelecionada.set(ano, mes, dia)
                                    viewCriada.form_transacao_data
                                            .setText(dataSelecionada.formataParaBrasileiro())
                                }
                                        , ano, mes, dia).show()

                            }

                    val adapter = ArrayAdapter
                            .createFromResource(this,R.array.categorias_de_despesa,
                                    android.R.layout.simple_spinner_dropdown_item)
                    viewCriada.form_transacao_categoria.adapter = adapter



                    AlertDialog.Builder(this)
                            .setTitle(R.string.adiciona_despesa)
                            .setView(viewCriada)
                            .setPositiveButton("Adicionar", null)
                            .setNegativeButton("Cancelar", null)
                            .show()
                }

        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    Toast.makeText(this@ListaTransacoesActivity, "Clique receita", Toast
                            .LENGTH_LONG).show()
                }

    }

    private fun configuraResumo(transacoes: List<Transacao>) {
        val view: View = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraTransacao(transacoes: List<Transacao>) {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        return listOf(Transacao(valor = BigDecimal(300),
                tipo = Tipo.RECEITA),
                Transacao(valor = BigDecimal(100),
                        categoria = "Compras",
                        tipo = Tipo.DESPESA))
    }

}