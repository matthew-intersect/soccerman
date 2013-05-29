package adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.soccerman.R;

import library.MatchFunctions;
import library.UserFunctions;
import models.Attendance;
import models.Match;
import models.TeamRole;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MatchAdapter extends ArrayAdapter<Match>
{
	private ArrayList<Match> matches;
	private TeamRole teamRole;
	
	public MatchAdapter(Context context, int textViewResourceId, ArrayList<Match> objects, TeamRole teamRole) 
	{
		super(context, textViewResourceId, objects);
		this.matches = objects;
		this.teamRole = teamRole;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.match_list_item, null);
		}
		
		Match match = matches.get(position);
		
		if(match != null)
		{
			TextView match_opponent = (TextView) v.findViewById(R.id.list_match_opponent);
			TextView match_venue = (TextView) v.findViewById(R.id.list_match_venue);
			TextView match_time = (TextView) v.findViewById(R.id.list_match_time);
			TextView match_response = (TextView) v.findViewById(R.id.match_response);
			
			if(match_opponent != null)
			{
				match_opponent.setText(match.getOpponent());
			}
			if(match_venue != null)
			{
				match_venue.setText(match.getVenue() + " - ");
			}
			if(match_response != null && teamRole != TeamRole.MANAGER)
			{
				String currentUser = new UserFunctions().getLoggedInUserId(getContext());
				Attendance attendance = new MatchFunctions().getPlayerMatchAttendance(match.getId(), currentUser);
				if(attendance == Attendance.YES)
				{
					match_response.setText("Playing");
					match_response.setTextColor(Color.GREEN);
				}
				else if(attendance == Attendance.NO)
				{
					match_response.setText("Not Playing");
					match_response.setTextColor(Color.RED);
				}
				else
				{
					match_response.setText("-");
				}
			}
			if(match_time != null)
			{
				Calendar time = match.getTime();
				match_time.setText(new SimpleDateFormat("EEE, d MMM - h:mm a").format(time.getTime()));
			}
		}
		return v;
	}
}
