package com.iot.petsfinder;


import android.os.Parcel;
import android.os.Parcelable;

public class Parcelables implements Parcelable
{
    String LoginId;

    public Parcelables(String loginId) {
        LoginId = loginId;
    }

    public Parcelables(Parcel parcel){
        this.LoginId = parcel.readString();
    }

    public static final Creator CREATOR = new ClassLoaderCreator() {
        @Override
        public Object createFromParcel(Parcel source, ClassLoader loader) {
            return new Parcelables(source);
        }

        @Override
        public Object createFromParcel(Parcel source) {
            return new Parcelables(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };

    public Parcelables()
    {
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {

    }

    public String getLoginId()
    {
        return LoginId;
    }

    public void setLoginId(String loginId)
    {
        LoginId = loginId;
    }
}
