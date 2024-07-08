package com.example.messenger.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val uid: String = "",
    var nickName: String = "",
    var name: String = "",
    var surname: String = "",
    var occupation: String = "",
    var address: String = "",
    var phone: String = "",
    var eMail: String = "",
    var age: String = "",
    var imageURL: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(nickName)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(occupation)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(eMail)
        parcel.writeString(age)
        parcel.writeString(imageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
