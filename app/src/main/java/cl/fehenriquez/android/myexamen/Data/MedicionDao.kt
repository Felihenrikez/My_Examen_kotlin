package cl.fehenriquez.android.myexamen.Data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MedicionDao {

    @Query("SELECT * FROM medicion ORDER BY fecha DESC")
        suspend fun getAll(): List<Medicion>

    @Query("SELECT * FROM medicion WHERE id = :id")
        suspend fun getById(id: Long): Medicion

    @Insert
    suspend fun insertar(medicion: Medicion)
    @Update
    suspend fun modificar(medicion: Medicion)
    @Delete
    suspend fun eliminar(medicion: Medicion)

}