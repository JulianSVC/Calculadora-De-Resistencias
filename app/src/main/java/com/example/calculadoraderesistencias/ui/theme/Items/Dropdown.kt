package com.example.calculadoraderesistencias.ui.theme.Items
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraderesistencias.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

// Listas con los colores disponibles para cada banda
val colores1y2 = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
val colores3 = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
val colores4 = listOf("Dorado", "Plateado", "Ninguno")

@Composable
fun SistemaConDropdowns() {
    // Variables para guardar la selección de cada banda de color
    var seleccion1 by remember { mutableStateOf("Selecciona") }
    var seleccion2 by remember { mutableStateOf("Selecciona") }
    var seleccion3 by remember { mutableStateOf("Selecciona") }
    var seleccion4 by remember { mutableStateOf("Selecciona") }

    // Variable para almacenar el resultado del cálculo de la resistencia
    var resultadoResistencia by remember { mutableStateOf("") }

    // Layout principal en forma de columna centrada
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen del logo (opcional)
        Image(
            painter = painterResource(id = R.drawable.log),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 24.dp)
        )

        // Título principal
        Text(
            text = "Calculadora de Resistencias",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Cuatro dropdowns para seleccionar los colores de cada banda
        ColorDropdown("Banda 1", seleccion1, colores1y2) { seleccion1 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Banda 2", seleccion2, colores1y2) { seleccion2 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Multiplicador", seleccion3, colores3) { seleccion3 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Tolerancia", seleccion4, colores4) { seleccion4 = it }
        Spacer(modifier = Modifier.height(32.dp))

        // Botón que ejecuta el cálculo de la resistencia
        Button(
            onClick = {
                // Aquí se llama la función de cálculo
                resultadoResistencia = calcularResistencia(seleccion1, seleccion2, seleccion3, seleccion4)
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp)
        ) {
            Text("Calcular Resistencia", fontSize = 16.sp)
        }
        // Animación de aparición del resultado

        Spacer(modifier = Modifier.height(24.dp))
        AnimatedVisibility(
            visible = resultadoResistencia.isNotEmpty(),
            enter = fadeIn(animationSpec = tween(800)) + scaleIn(initialScale = 0.8f, animationSpec = tween(800)),
            exit = fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f, animationSpec = tween(300))
        ) {
            ResultadoCard(resultadoResistencia)
        }
   }
}

@Composable
fun ColorDropdown(
    label: String,                 // Etiqueta del dropdown
    seleccion: String,            // Valor seleccionado
    opciones: List<String>,       // Lista de colores disponibles
    onSeleccionar: (String) -> Unit // Callback para actualizar selección
) {
    var expandir by remember { mutableStateOf(false) } // Controla si el dropdown está abierto

    Column(modifier = Modifier.fillMaxWidth()) {
        // Etiqueta del dropdown con ícono
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ColorLens,
                contentDescription = "Seleccionar color",
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = label, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }

        // Caja principal del dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .background(Color(0xFFF7F7F7), RoundedCornerShape(10.dp))
                .clickable { expandir = true }
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = seleccion, modifier = Modifier.weight(1f), fontSize = 15.sp)
                Text(text = "▼", fontSize = 14.sp)
            }

            // Menú desplegable con opciones
            DropdownMenu(
                expanded = expandir,
                onDismissRequest = { expandir = false },
                modifier = Modifier.background(Color.White)
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                // Círculo de color representativo
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(colorFromName(opcion), CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(opcion)
                            }
                        },
                        onClick = {
                            onSeleccionar(opcion) // Actualiza la selección
                            expandir = false       // Cierra el menú
                        }
                    )
                }
            }
        }
    }
}

// Función que convierte el nombre del color en un Color visual
fun colorFromName(name: String): Color = when (name.lowercase()) {
    "negro" -> Color.Black
    "marrón" -> Color(0xFFA52A2A)
    "rojo" -> Color.Red
    "naranja" -> Color(0xFFFFA500)
    "amarillo" -> Color.Yellow
    "verde" -> Color.Green
    "azul" -> Color.Blue
    "violeta" -> Color(0xFF8A2BE2)
    "gris" -> Color.Gray
    "blanco" -> Color.White
    "dorado" -> Color(0xFFFFD700)
    "plateado" -> Color(0xFFC0C0C0)
    "ninguno" -> Color.LightGray
    else -> Color.LightGray
}
@Composable
fun ResultadoCard(resultado: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF7FF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Resultado",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = resultado,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E88E5)
            )
        }
    }
}

fun calcularResistencia(
    banda1: String,
    banda2: String,
    banda3: String,
    banda4: String
): String {
    val b1 = mapaColores[banda1]?.valor ?: return "Error: Banda 1 no válida"
    val b2 = mapaColores[banda2]?.valor ?: return "Error: Banda 2 no válida"
    val multiplicador = mapaColores[banda3]?.multiplicador ?: return "Error: Banda 3 no válida"
    val tolerancia = mapaColores[banda4]?.tolerancia ?: ""

    val valorBase = (b1 * 10 + b2) * multiplicador

    val resultado = when {
        valorBase >= 1_000_000 -> "${valorBase / 1_000_000} MΩ"
        valorBase >= 1_000 -> "${valorBase / 1_000} kΩ"
        else -> "$valorBase Ω"
    }

    return if (tolerancia.isNotEmpty()) "$resultado $tolerancia" else resultado
}

