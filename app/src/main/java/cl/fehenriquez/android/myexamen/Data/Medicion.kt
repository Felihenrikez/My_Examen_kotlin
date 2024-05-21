package cl.fehenriquez.android.myexamen.Data


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
class Medicion(
    @PrimaryKey(autoGenerate = true) var id:Long?=null,
    var check: Int?,
    var fecha: LocalDate?,
    var categoria:String?,
)
