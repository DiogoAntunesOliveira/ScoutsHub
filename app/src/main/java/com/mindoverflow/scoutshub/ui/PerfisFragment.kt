package com.mindoverflow.scoutshub.ui

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.Image
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.mindoverflow.scoutshub.R
import com.mindoverflow.scoutshub.models.Perfil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PerfisFragment : Fragment() {

    //var perfis : MutableList<Perfil> = arrayListOf()
    //lateinit var adapter : PerfilAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_perfil_utilizador, container, false)

        val image0 = R.drawable.bryce_canyon
        val image1 = R.drawable.cathedral_rock
        val image2 = R.drawable.death_valley
        val image3 = R.drawable.fitzgerald_marine_reserve
        val image4 = R.drawable.grand_canyon

        val images = arrayListOf(image0, image1, image2, image3, image4)

        val nomeUtilizador = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
        val dtNasc = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
        val genero = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
        val contacto = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
        val morada = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
        val codigoPostal = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
        val nin = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
        val totalAtivParticip = rootView.findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)

        val perfil = Perfil(null, "Jorge", "30/5/2000", "M", 919923205, "Praceta Madalena Fonseca 120 Rés do chão", "9560-010", 123456789, 6, 5)

        nomeUtilizador.text = perfil.nome
        dtNasc.text = perfil.dtNasc
        genero.text = perfil.genero
        contacto.text = perfil.contacto.toString()
        morada.text = perfil.morada
        codigoPostal.text = perfil.codigoPostal
        nin.text = perfil.nin.toString()
        totalAtivParticip.text = perfil.totalAtivParticip.toString()

        val carousel = rootView.findViewById<Carousel>(R.id.carousel)


        carousel.setAdapter(object : Carousel.Adapter {
            override fun count(): Int {
                // need to return the number of items we have in the carousel

                return images.size
            }

            override fun populate(view: View, index: Int) {
                // need to implement this to populate the view at the given index
                val imageView = view as ImageView
                return imageView.setImageResource(images[index])
            }

            override fun onNewItem(index: Int) {
                // called when an item is set

            }
        })

        return rootView
    }


/*    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch (Dispatchers.Main){
            adapter.notifyDataSetChanged()
        }
    }

    inner class PerfilAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return perfis.size
        }

        override fun getItem(position: Int): Any {
            return perfis[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rowView = layoutInflater.inflate(R.layout.row_perfis, parent, false)

            //val idPerfil = rowView.findViewById<TextView>(R.id.textViewIdPerfil)
            val nomeUtilizador = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroNome)
            val dtNasc = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroDataNasc)
            val genero = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroGenero)
            val contacto = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroContacto)
            val morada = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroMorada)
            val codigoPostal = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroCodigoPostal)
            val nin = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroNin)
            val totalAtivParticip = rowView.findViewById<TextView>(R.id.textViewPerfilEscuteiroTotalAtivParticip)
            //val idEquipa = rowView.findViewById<TextView>(R.id.textViewIdEquipa)

            //idPerfil.text = perfis[position].idPerfil.toString()
            nomeUtilizador.text = perfis[position].nome
            dtNasc.text = perfis[position].dtNasc
            genero.text = perfis[position].genero
            contacto.text = perfis[position].contacto.toString()
            morada.text = perfis[position].morada
            codigoPostal.text = perfis[position].codigoPostal
            nin.text = perfis[position].dtNasc
            totalAtivParticip.text = perfis[position].totalAtivParticip.toString()
            //idEquipa.text = perfis[position].idEquipa.toString()

            return rowView
        }

    }*/
}