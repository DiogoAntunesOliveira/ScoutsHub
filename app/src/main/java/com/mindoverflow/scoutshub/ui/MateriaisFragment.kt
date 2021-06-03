package com.mindoverflow.scoutshub.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Material
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




        val equipa = Material(null, "Tendas", 14)
        materiais.add(equipa)

        materiais.add(Material(1, "Bussolas", 34))
        materiais.add(Material(2, "Mochilas", 24))
        materiais.add(Material(3, "Mapa", 44))
        materiais.add(Material(4, "Garrafas de Agua", 27))
        materiais.add(Material(5, "Lanterna", 29))

        val buttonnextmaterial = rootView.findViewById<Button>(R.id.buttonNextMaterial)


        buttonnextmaterial.setOnClickListener{

            val intent = Intent(activity,ConfirmNewActivity::class.java)
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

            val idMaterial = rowView.findViewById<TextView>(R.id.textViewIdMaterial)
            val tipoMaterial = rowView.findViewById<TextView>(R.id.textViewTipo)
            val quantidade = rowView.findViewById<TextView>(R.id.textViewQuantidade)

            val image = rowView.findViewById<ImageView>(R.id.plusSelect)
            val imageselected = rowView.findViewById<ImageView>(R.id.plusSelected)

            imageselected.visibility = View.INVISIBLE



            idMaterial.text = materiais[position].idMaterial.toString()
            tipoMaterial.text = materiais[position].tipo
            quantidade.text = materiais[position].quantidade.toString()


            rowView.setOnClickListener {



                if (imageselected.isVisible)
                {
                    imageselected.visibility = View.INVISIBLE;
                    selectedRowsIds.remove(idMaterial.text as String)
                    rowView.setBackground(context?.let { it1 -> ContextCompat.getDrawable(it1, R.color.white) })


                    println("              ------  Estou invisivel  ------                    ")
                }
                else if(imageselected.isInvisible)
                {
                    imageselected.visibility = View.VISIBLE
                    selectedRowsIds.add(idMaterial.text as String)
                    rowView.setBackground(context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.rounded_corner) });
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