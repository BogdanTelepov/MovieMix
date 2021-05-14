package ru.btelepov.moviemix.data.database.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.btelepov.moviemix.models.serials.SerialItem

@Entity(tableName = "favorites_serial_table")
class FavoriteSerialEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var serialItem: SerialItem
) {
}