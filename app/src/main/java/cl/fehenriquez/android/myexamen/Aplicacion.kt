package cl.fehenriquez.android.myexamen

import android.app.Application
import androidx.room.Room
import cl.fehenriquez.android.myexamen.Data.BaseDatos
class Aplicacion: Application() {

    val bd by lazy { Room.databaseBuilder(this,BaseDatos::class.java,"medicion.db").build()}
    val medicionDao by lazy { bd.medicionDao() }
}