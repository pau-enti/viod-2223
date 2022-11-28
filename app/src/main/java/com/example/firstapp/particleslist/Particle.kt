package com.example.firstapp.particleslist

data class Particle(
    var name: String = "",
    val family: Family = Family.QUARK,
    val mass: Double = 0.0,
    val charge: String = "",
    val spin: String = ""
) {
    enum class Family {
        QUARK, LEPTON, GAUGE_BOSON, SCALAR_BOSON
    }

}
