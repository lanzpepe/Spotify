package com.elano.spotify.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Jess on 12/19/2017.
 */
data class SongInfo (
        var id: Int,
        var name: String,
        var singer: String,
        var album: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(singer)
        parcel.writeString(album)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongInfo> {
        override fun createFromParcel(parcel: Parcel): SongInfo {
            return SongInfo(parcel)
        }

        override fun newArray(size: Int): Array<SongInfo?> {
            return arrayOfNulls(size)
        }
    }
}