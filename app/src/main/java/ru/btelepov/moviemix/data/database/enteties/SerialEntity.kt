package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.serials.SerialItem
import ru.btelepov.moviemix.models.serials.SerialResponse

@Entity(tableName = "serials_table")
class SerialEntity(

    var serialResponse: SerialResponse
) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}