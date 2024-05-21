package cl.fehenriquez.android.myexamen.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cl.fehenriquez.android.myexamen.Aplicacion
import cl.fehenriquez.android.myexamen.Data.Medicion
import cl.fehenriquez.android.myexamen.Data.MedicionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListaMedicionViewModel(private val medicionDao: MedicionDao): ViewModel() {

    var mediciones by mutableStateOf(listOf<Medicion>())
    var medicion by mutableStateOf(Medicion(null,null,null,null))

    fun insertarMedicion(medicion: Medicion){
        viewModelScope.launch (Dispatchers.IO){
            medicionDao.insertar(medicion)
            obtenerMediciones()

        }
    }

    fun actualizarMedicion(medicion: Medicion){
        viewModelScope.launch (Dispatchers.IO){
            medicionDao.modificar(medicion)
        }


    }
    fun eliminarMedicion(medicion: Medicion){
            viewModelScope.launch (Dispatchers.IO){
                medicionDao.eliminar(medicion)
                obtenerMediciones()
            }
    }
    fun obtenerMedicion(id:Long):Medicion{
            viewModelScope.launch (Dispatchers.IO){

                medicion =medicionDao.getById(id)

            }
        return medicion

    }

    fun obtenerMediciones():List<Medicion>{
            viewModelScope.launch (Dispatchers.IO){
                mediciones = medicionDao.getAll()
            }
            return mediciones
    }

    companion object{
            val Factory : ViewModelProvider.Factory =viewModelFactory {
                initializer {
                    val savedStateHandle = createSavedStateHandle()
                    val aplicacion =(this[APPLICATION_KEY] as Aplicacion)
                    ListaMedicionViewModel(aplicacion.medicionDao)
                }

            }
    }
}