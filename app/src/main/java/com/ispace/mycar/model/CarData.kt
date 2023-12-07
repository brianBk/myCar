package com.ispace.mycar.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize

@VersionedParcelize
@Entity(tableName = "car_table")
data class CarData(
    @PrimaryKey(autoGenerate = true) val carId: Int,
    @ColumnInfo(name = "number_plate") val carPlates: String?,
    @ColumnInfo(name = "car_make") val carMake: String?,
    @ColumnInfo(name = "car_model") val carModel: String?,
    @ColumnInfo(name = "car_owner") val carOwner: String?,
    @ColumnInfo(name = "car_tare") val carTare: String?,
    @ColumnInfo(name = "car_weight") val carWeight: String?,
    @ColumnInfo(name = "date_purchased") val datePurchased: String?,
    /*@Embedded
    val fuel: FuelData*/

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(carId)
        parcel.writeString(carPlates)
        parcel.writeString(carMake)
        parcel.writeString(carModel)
        parcel.writeString(carOwner)
        parcel.writeString(carTare)
        parcel.writeString(carWeight)
        parcel.writeString(datePurchased)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarData> {
        override fun createFromParcel(parcel: Parcel): CarData {
            return CarData(parcel)
        }

        override fun newArray(size: Int): Array<CarData?> {
            return arrayOfNulls(size)
        }
    }
}
