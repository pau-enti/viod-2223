package com.example.firstapp.particleslist

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ItemParticleBinding

class ParticlesRecyclerViewAdapter(
    val particles: List<Particle>,
    val context: Context
) :
    RecyclerView.Adapter<ParticlesRecyclerViewAdapter.ParticleVH>() {

    inner class ParticleVH(binding: ItemParticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val name = binding.particleName
        val image = binding.particleImage
        val card = binding.particleCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticleVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemParticleBinding.inflate(layoutInflater, parent, false)
        return ParticleVH(binding)
    }

    override fun onBindViewHolder(holder: ParticleVH, position: Int) {
        val particle = particles[position]
        holder.name.text = particle.name

        val colorId = when (particle.family) {
            Particle.Family.QUARK -> R.color.quarks
            Particle.Family.LEPTON -> R.color.leptons
            Particle.Family.GAUGE_BOSON -> R.color.gauge_bosons
            Particle.Family.SCALAR_BOSON -> R.color.scalar_boson
        }

        holder.image.setColorFilter(context.getColor(colorId))
        holder.card.setOnLongClickListener {
            showRenameDialog(particle, position)
            true
        }
    }

    private fun showRenameDialog(particle: Particle, position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Particle name")

        val editText = EditText(context)
        editText.setText(particle.name)
        builder.setView(editText)

        builder.setPositiveButton("Ok") { _, _ ->
            particle.name = editText.text.toString()
            notifyItemChanged(position)
        }
        builder.setNegativeButton("Cancel") { _, _ ->

        }
        builder.show()

    }

    override fun getItemCount(): Int {
        return particles.size
    }
}