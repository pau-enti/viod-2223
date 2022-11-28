package com.example.firstapp.particleslist

class Particle(
    var name: String,
    val family: Family,
    val mass: Double,
    val charge: String,
    val spin: String
) {
    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON
    }

}
