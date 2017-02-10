package com.k4ench.pswrd;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by enchanter-kiran on 7/2/17.
 */
public class Categorieslist  implements Parcelable {
    private String name,phone,invoice,description;
    protected Categorieslist(Parcel in) {
        name = in.readString();
        phone = in.readString();
        invoice = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(invoice);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Categorieslist> CREATOR = new Creator<Categorieslist>() {
        @Override
        public Categorieslist createFromParcel(Parcel in) {
            return new Categorieslist(in);
        }

        @Override
        public Categorieslist[] newArray(int size) {
            return new Categorieslist[size];
        }
    };

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
