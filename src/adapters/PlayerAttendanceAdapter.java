package adapters;

import java.util.ArrayList;
import com.example.soccerman.R;

import models.PlayerAttendance;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayerAttendanceAdapter extends ArrayAdapter<PlayerAttendance>
{
	private ArrayList<PlayerAttendance> playerAttendances;
	
	public PlayerAttendanceAdapter(Context context, int textViewResourceId, ArrayList<PlayerAttendance> objects) 
	{
		super(context, textViewResourceId, objects);
		this.playerAttendances = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.player_attendance_list_item, null);
		}
		
		PlayerAttendance playerAttendance = playerAttendances.get(position);
		
		if(playerAttendance != null)
		{
			TextView player_name = (TextView) v.findViewById(R.id.list_player_name);
			TextView attendance = (TextView) v.findViewById(R.id.list_player_response);
			
			if(player_name != null)
			{
				player_name.setText(playerAttendance.getName());
			}
			if(attendance != null)
			{
				attendance.setText(playerAttendance.getAttendance().getDisplayText());
			}
		}
		return v;
	}
}
