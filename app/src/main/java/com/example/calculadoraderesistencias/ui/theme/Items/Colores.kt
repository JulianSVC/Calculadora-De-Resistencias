package com.example.calculadoraderesistencias.ui.theme.Items
//Esta clase Tiene como proposito establecer los valores correspondientes dependiendo el coloro
//Seleccionado con su respectiva banda y lo que corresponde para que sea facil el calculo.
data class BandaColor(
    val valor: Int,
    val multiplicador: Double,
    val tolerancia: String
)

// Mapa de colores con los valores correspondientes para cada banda respectivamente de la tabla de valores
val mapaColores = mapOf(
    "Negro" to BandaColor(0, 1.0, ""),
    "Marrón" to BandaColor(1, 10.0, "±1%"),
    "Rojo" to BandaColor(2, 100.0, "±2%"),
    "Naranja" to BandaColor(3, 1_000.0, ""),
    "Amarillo" to BandaColor(4, 10_000.0, ""),
    "Verde" to BandaColor(5, 100_000.0, "±0.5%"),
    "Azul" to BandaColor(6, 1_000_000.0, "±0.25%"),
    "Violeta" to BandaColor(7, 10_000_000.0, "±0.1%"),
    "Gris" to BandaColor(8, 100_000_000.0, "±0.05%"),
    "Blanco" to BandaColor(9, 1_000_000_000.0, ""),
    "Dorado" to BandaColor(-1, 0.1, "±5%"),
    "Plateado" to BandaColor(-2, 0.01, "±10%"),
    "Ninguno" to BandaColor(-3, 1.0, "±20%")
)
