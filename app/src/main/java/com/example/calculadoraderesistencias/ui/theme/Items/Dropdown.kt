package com.example.calculadoraderesistencias.ui.theme.Items
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
val colores1y2 = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
val colores3 = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
val colores4 = listOf("Dorado", "Plateado", "Ninguno")

@Composable
fun SistemaConDropdowns() {
    var seleccion1 by remember { mutableStateOf("Selecciona") }
    var seleccion2 by remember { mutableStateOf("Selecciona") }
    var seleccion3 by remember { mutableStateOf("Selecciona") }
    var seleccion4 by remember { mutableStateOf("Selecciona") }

    var resultadoResistencia by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.log),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 24.dp)
        )
        Text(
            text = "Calculadora de Resistencias",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        ColorDropdown("Banda 1", seleccion1, colores1y2) { seleccion1 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Banda 2", seleccion2, colores1y2) { seleccion2 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Multiplicador", seleccion3, colores3) { seleccion3 = it }
        Spacer(modifier = Modifier.height(16.dp))
        ColorDropdown("Tolerancia", seleccion4, colores4) { seleccion4 = it }
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // Aquí irá el cálculo de la resistencia
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(vertical = 8.dp)
        ) {
            Text("Calcular Resistencia", fontSize = 16.sp)
        }

        if (resultadoResistencia.isNotEmpty()) {
            Text(
                text = "Resultado: $resultadoResistencia",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

@Composable
fun ColorDropdown(
    label: String,
    seleccion: String,
    opciones: List<String>,
    onSeleccionar: (String) -> Unit
) {
    var expandir by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
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

            DropdownMenu(
                expanded = expandir,
                onDismissRequest = { expandir = false },
                modifier = Modifier.background(Color.White)
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                            onSeleccionar(opcion)
                            expandir = false
                        }
                    )
                }
            }
        }
    }
}
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
