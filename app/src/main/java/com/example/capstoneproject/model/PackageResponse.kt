package com.example.capstoneproject.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PackageResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("nama_paket")
    val packageName: String,
    @SerializedName("price")
    val price: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(packageName)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PackageResponse> {
        override fun createFromParcel(parcel: Parcel): PackageResponse {
            return PackageResponse(parcel)
        }

        override fun newArray(size: Int): Array<PackageResponse?> {
            return arrayOfNulls(size)
        }
    }
}
