package models;

import android.os.Parcel;
import android.os.Parcelable;

public enum TeamRole implements Parcelable
{
	MANAGER, PLAYER, PLAYER_MANAGER;
	
	private TeamRole()
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
		dest.writeInt(ordinal());
	}
	
	public static final Creator<TeamRole> CREATOR = new Creator<TeamRole>()
	{
        @Override
        public TeamRole createFromParcel(final Parcel source) 
        {
            return TeamRole.values()[source.readInt()];
        }

        @Override
        public TeamRole[] newArray(final int size) 
        {
            return new TeamRole[size];
        }
    };
}
