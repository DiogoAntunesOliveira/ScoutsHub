package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Material
import com.mindoverflow.scoutshub.ui.Atividades.CreateNewActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MateriaisFragment : Fragment() {


    var selectedRowsIds : ArrayList<String> = ArrayList()
    var materiais : MutableList<Material> = arrayListOf()
    lateinit var adapter : MaterialAdapter
    var valorrecebido : String? = null
    var nomeRecebido : String? = null
    var descricaoRecebida : String? = null
    var dataRecebida : String? = null
    var materiaisselecionados : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            valorrecebido = it.getString(TESTE_DIC_KEY)
            dataRecebida = it.getString(dataAtividade)
            nomeRecebido = it.getString(nomeCompleto)
            descricaoRecebida = it.getString(descricao)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val rootView = inflater.inflate(R.layout.fragment_materiais, container, false)

        val listaMateriais = rootView.findViewById<ListView>(R.id.listViewMaterial)
        adapter = MaterialAdapter()

        listaMateriais.adapter = adapter

        materiaisselecionados = rootView.findViewById(R.id.numeroselecionado)


        materiais.add(Material(0, "Corda", 14))
        materiais.add(Material(1, "Kit Primeiros Socorros", 34))
        materiais.add(Material(2, "Mochila", 24))
        materiais.add(Material(3, "Saco de Dormir", 44))
        materiais.add(Material(4, "Tenda", 27))

        val buttonnextmaterial = rootView.findViewById<Button>(R.id.buttonNextMaterial)


        buttonnextmaterial.setOnClickListener{

            val intent = Intent(activity, CreateNewActivity::class.java)
            intent.putExtra("IDs", selectedRowsIds)
            intent.putExtra("descricao", descricaoRecebida)
            intent.putExtra("nomeCompleto" , nomeRecebido)
            intent.putExtra("dataAtividade" , dataRecebida)
            val fragment = MateriaisFragment()
            val bundle = Bundle().apply {
                putString("dataAtividade", dataAtividade)
                putString("nomeCompleto", nomeCompleto)
                putString("descricao", descricao)
            }
            fragment.arguments = bundle
            activity?.startActivity(intent)


        }

        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class MaterialAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return materiais.size
        }

        override fun getItem(position: Int): Any {
            return materiais[position]

        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {


            val rowView = layoutInflater.inflate(R.layout.row_materiais, parent, false)
            val tipoMaterial = rowView.findViewById<TextView>(R.id.textViewTipo)


            val contraint = rowView.findViewById<ConstraintLayout>(R.id.rowconstrain)
            val image = rowView.findViewById<ImageView>(R.id.plusSelect)
            val imageselected = rowView.findViewById<ImageView>(R.id.plusSelected)

            imageselected.visibility = View.INVISIBLE


            when(materiais[position].idMaterial){
                0 -> image.setImageResource(R.drawable.corda_materiais)
                1 -> image.setImageResource(R.drawable.medkit_materiais)
                2 -> image.setImageResource(R.drawable.mochila_materiais)
                3 -> image.setImageResource(R.drawable.sacodedormir_materiais)
                4 -> image.setImageResource(R.drawable.tenda_materiais)
            }



            tipoMaterial.text = materiais[position].tipo


            rowView.setOnClickListener {



                if (imageselected.isVisible)
                {
                    imageselected.visibility = View.INVISIBLE;
 //                   selectedRowsIds.remove(materiais[position].idMaterial as String)
                    contraint.setBackground(context?.let { it1 -> ContextCompat.getDrawable(it1, R.color.white) })
                    materiaisselecionados?.text = (Integer.parseInt(materiaisselecionados?.text.toString())-1).toString()

                    println("              ------  Estou invisivel  ------                    ")
                }
                else if(imageselected.isInvisible)
                {
                    imageselected.visibility = View.VISIBLE
//                    selectedRowsIds.add(materiais[position].idMaterial as String)
                    contraint.setBackground(context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.rounded_corner) });
                    materiaisselecionados?.text = (Integer.parseInt(materiaisselecionados?.text as String)+1).toString()

                    println("              -------  Estou visivel  ------                     ")
                }
                else{
                }

                when (imageselected.visibility) {
                //    View.VISIBLE -> selectedRowsIds.add(idMaterial.text as String) ;
                //    View.INVISIBLE -> selectedRowsIds.remove(idMaterial.text as String)
                    else -> {
                        for (element in selectedRowsIds) {
                            println(element)
                        }
                    }
                }



            }

            return rowView
        }
    }


    companion object{
        var dataAtividade = "data"
        var descricao = "descricao"
        var nomeCompleto = "nome"
        const val TESTE_DIC_KEY = "teste_dic_key"
    }
}