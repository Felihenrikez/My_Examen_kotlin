package cl.fehenriquez.android.myexamen

import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.fehenriquez.android.myexamen.Data.Medicion
import cl.fehenriquez.android.myexamen.ui.ListaMedicionViewModel
import java.time.LocalDate

import androidx.compose.foundation.layout.*

import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.toSize

import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           PageInicioUI()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PageInicioUI() {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 20.dp,
                vertical = 30.dp
            )) {

        Image(  painter = painterResource(id = R.drawable.medidor),
            contentDescription = "icono medicion")
        Text(
            text = context.getString(R.string.app_name),
            fontWeight = FontWeight.ExtraBold,
            fontSize=25.sp)

        Spacer(modifier = Modifier.height(200.dp))
        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            Button(onClick = {  }) {
                Text(text = context.getString(R.string.btn_agregarLectura))
            }
            Button(onClick = { }) {
                Text(text = context.getString(R.string.btn_Listar_mediciones))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = false)
@Composable
fun AddUI(
    vmListaMediciones : ListaMedicionViewModel =viewModel(factory = ListaMedicionViewModel.Factory)
){
    val context = LocalContext.current
    var medicion by remember { mutableStateOf(Medicion(null, 0, LocalDate.now(), "")) }

    var cantidad: String by remember { mutableStateOf("") }
    var medicionstring: String by remember { mutableStateOf("") }


    var isExpanded by remember { mutableStateOf(false) }


    var mExpanded by remember { mutableStateOf(false) }
    val categorias = listOf(context.getString(R.string.text_agua), context.getString(R.string.text_electricidad), context.getString(R.string.text_gas))
    var categoria by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    OutlinedTextField(
        value = categoria,
        onValueChange = { categoria = it },
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { coordinates ->
                // Este valor se utiliza para asignar al
                // menú desplegable el mismo ancho
                mTextFieldSize = coordinates.size.toSize()
            },
        label = { Text(context.getString(R.string.text_seleccionar)) },
        trailingIcon = {
            Icon(icon, "Descripción del contenido",
                Modifier.clickable { mExpanded = !mExpanded })
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(100.dp)
            .height(200.dp),
    ) {


        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            categorias.forEach { label ->
                DropdownMenuItem(text = { Text(label) },onClick = {
                    categoria = label
                    mExpanded = false
                })
            }
        }

        TextField(
            value = medicionstring,
            onValueChange = { medicionstring = it },
            label = { context.getString(R.string.cantidad_lectura) })



        Row(){
            Button(onClick = {
                vmListaMediciones.insertarMedicion(
                    Medicion(
                        null,
                        medicionstring.toInt(),
                        LocalDate.now(),
                        medicion.categoria
                    )
                )
            }) {
                Text(context.getString(R.string.btn_agregarLectura))
            }

        }
       
    }
}












@Composable
fun AppMedicionesUI(
    vmListaMediciones : ListaMedicionViewModel =viewModel(factory = ListaMedicionViewModel.Factory)
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        vmListaMediciones.obtenerMediciones()
    }

    LazyColumn {
        items(vmListaMediciones.mediciones) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                val imagenId =when (it.categoria) {
                    context.getString(R.string.text_agua)-> R.drawable.agua
                    context.getString(R.string.text_electricidad)-> R.drawable.electricidad
                    context.getString(R.string.text_gas) -> R.drawable.gas
                            else -> R.drawable.medidor
                }
            Image(painter = painterResource(id = imagenId) ,
                contentDescription =null ,
                modifier = Modifier.size(24.dp))
            Text(it.check.toString())
            Text(it.fecha.toString())
            }
        }

    }
}

