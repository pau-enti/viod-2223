package com.example.firstapp.particleslist

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.firstapp.R
import com.example.firstapp.databinding.ItemParticleBinding
import com.example.firstapp.quotes.QuotesActivity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class ParticlesRecyclerViewAdapter(val activity: Activity) :
    RecyclerView.Adapter<ParticlesRecyclerViewAdapter.ParticleVH>() {

    private var particles: List<Particle> = arrayListOf()
    private var ad: InterstitialAd? = null

    inner class ParticleVH(binding: ItemParticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val name = binding.particleName
        val image = binding.particleImage
        val card = binding.particleCardView
    }

    fun loadAd() {
        val request = AdRequest.Builder().build()

        InterstitialAd.load(
            activity,
            "ca-app-pub-3940256099942544/1033173712",
            request,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(loadedAd: InterstitialAd) {
                    ad = loadedAd
                }
            })
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

        holder.image.setColorFilter(activity.getColor(colorId))
        holder.card.setOnLongClickListener {
            showRenameDialog(particle, position)
            true
        }

        holder.card.setOnClickListener {
            val intent = Intent(activity, QuotesActivity::class.java)

            ad?.let {
                it.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        activity.startActivity(intent)
                        ad = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        activity.startActivity(intent)
                        ad = null
                    }
                }

                it.show(activity)
            } ?: activity.startActivity(intent)
        }
    }

    private fun showRenameDialog(particle: Particle, position: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Particle name")

        val editText = EditText(activity)
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

    @SuppressLint("NotifyDataSetChanged")
    fun updateParticlesList(particles: List<Particle>) {
        this.particles = particles
        notifyDataSetChanged()
    }
}