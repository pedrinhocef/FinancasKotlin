package com.example.pedri.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.pedri.financask.R
import com.example.pedri.financask.delegate.TransacaoDelegate
import com.example.pedri.financask.extension.converteParaCalendar
import com.example.pedri.financask.extension.formataParaBrasileiro
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val viewGroup: ViewGroup,
                              private val context: Context) {
    private val viewCriada = criaLayout()

    fun configuraDialog(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {
        configuraCampoData()
        configuraCampoCategoria(tipo)
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = if (tipo == Tipo.RECEITA) {
            R.string.adiciona_receita
        } else {
            R.string.adiciona_despesa
        }


        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton("Adicionar", { _, _ ->
                    val valorEmTexto = viewCriada.form_transacao_valor.text.toString()
                    val dataEmTexto = viewCriada.form_transacao_data.text.toString()
                    val categoriaEmTexto = viewCriada.form_transacao_categoria.selectedItem.toString()

                    val valor = converteCampoValor(valorEmTexto)
                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(tipo = tipo,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    transacaoDelegate.delegate(transacaoCriada)

                })
                .setNegativeButton("Cancelar", null)
                .show()
    }

    private fun configuraCampoCategoria(tipo: Tipo) {
        val categorias = if (tipo == Tipo.RECEITA) {
            R.array.categorias_de_receita
        } else {
            R.array.categorias_de_despesa
        }

        val adapter = ArrayAdapter
                .createFromResource(context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)
        viewCriada.form_transacao_categoria.adapter = adapter
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()
        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        viewCriada.form_transacao_data
                .setText(hoje.formataParaBrasileiro())

        viewCriada.form_transacao_data
                .setOnClickListener {
                    DatePickerDialog(context, DatePickerDialog.OnDateSetListener
                    { _, ano, mes, dia ->

                        val dataSelecionada = Calendar.getInstance()
                        dataSelecionada.set(ano, mes, dia)
                        viewCriada.form_transacao_data
                                .setText(dataSelecionada.formataParaBrasileiro())
                    }
                            , ano, mes, dia).show()

                }
    }

    private fun criaLayout(): View {
        return LayoutInflater.from(context)
                .inflate(R.layout.form_transacao, viewGroup, false)
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)

        } catch (excepetion: NumberFormatException) {
            Toast.makeText(context, "Falha ao fazer a conversão",
                    Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

}