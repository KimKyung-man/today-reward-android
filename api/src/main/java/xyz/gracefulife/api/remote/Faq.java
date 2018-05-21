package xyz.gracefulife.api.remote;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Faq implements Parcelable {
  private String id;
  private String question;
  private String answer;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.question);
    dest.writeString(this.answer);
  }

  protected Faq(Parcel in) {
    this.id = in.readString();
    this.question = in.readString();
    this.answer = in.readString();
  }

  public static final Creator<Faq> CREATOR = new Creator<Faq>() {
    @Override public Faq createFromParcel(Parcel source) {
      return new Faq(source);
    }

    @Override public Faq[] newArray(int size) {
      return new Faq[size];
    }
  };
}
