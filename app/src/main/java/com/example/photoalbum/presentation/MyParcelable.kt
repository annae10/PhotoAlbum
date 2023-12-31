package com.example.photoalbum.presentation

import android.os.Parcel
import android.os.Parcelable

class MyParcelable private constructor(`in`: Parcel) : Parcelable {
    private val mData: Int = `in`.readInt()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(out: Parcel, flags: Int) {
        out.writeInt(mData)
    }

    companion object CREATOR: Parcelable.Creator<MyParcelable?> {
        override fun createFromParcel(`in`: Parcel): MyParcelable? {
            return MyParcelable(`in`)
        }

        override fun newArray(size: Int): Array<MyParcelable?> {
            return arrayOfNulls(size)
        }
    }
}
