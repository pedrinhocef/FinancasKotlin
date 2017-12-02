package com.example.pedri.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.pedri.financask.R
import com.example.pedri.financask.extension.formataParaBrasileiro
import com.example.pedri.financask.extension.limitaEmAte
import com.example.pedri.financask.model.Tipo
import com.example.pedri.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*


/**
 * Created by pedri on 13-Nov-17.
 */
class ListaTransacoesAdapter(transacoes: List<Transacao>,
                             context: Context) : BaseAdapter() {

    private val transacoes = transacoes
    private val context = context
    private val limiteDeCategoria = 14


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).
                inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        if(transacao.tipo == Tipo.RECEITA){
            viewCriada.transacao_categoria
                    .setTextColor(ContextCompat.getColor(context,R.color.receita))
        } else {
            viewCriada.transacao_categoria
                    .setTextColor(ContextCompat.getColor(context,R.color.despesa))
        }

        if(transacao.tipo == Tipo.RECEITA) {
            viewCriada.transacao_icone
                    .setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            viewCriada.transacao_icone
                    .setBackgroundResource(R.drawable.icone_transacao_item_despesa)
        }


        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDeCategoria)
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()

        return viewCriada

    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}