package com.example.pedri.financask.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
import java.text.SimpleDateFormat
import java.util.*


class ListaTransacoesActivity : AppCompatActivity() {


    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraTransacao()

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
                            .createFromResource(this, R.array.categorias_de_despesa,
                                    android.R.layout.simple_spinner_dropdown_item)
                    viewCriada.form_transacao_categoria.adapter = adapter



                    AlertDialog.Builder(this)
                            .setTitle(R.string.adiciona_despesa)
                            .setView(viewCriada)
                            .setPositiveButton("Adicionar", { dialog, which ->
                                val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                                val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                                val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()


                                var valor = try {
                                    BigDecimal(valorEmTexto)

                                } catch (excepetion: NumberFormatException) {
                                    Toast.makeText(this, "Falha ao fazer a conversão",
                                            Toast.LENGTH_LONG).show()
                                    BigDecimal.ZERO
                                }
                                val formatoBrasileiro = SimpleDateFormat("dd/MM/yyyy")
                                val dataConvertida = formatoBrasileiro.parse(dataEmTexto)
                                val data = Calendar.getInstance()
                                data.time = dataConvertida

                                val transacaoCriada = Transacao(tipo = Tipo.DESPESA, valor = valor,
                                        data = data, categoria = categoriaEmTexto)

                                atualizaTransacoes(transacaoCriada)
                                lista_transacoes_adiciona_menu.close(true)

                            })
                            .setNegativeButton("Cancelar", null)
                            .show()
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