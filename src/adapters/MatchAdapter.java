package adapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.soccerman.R;

import models.Match;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MatchAdapter extends ArrayAdapter<Match>
{
	private ArrayList<Match> matches;
	
	public MatchAdapter(Context context, int textViewResourceId, ArrayList<Match> objects) 
	{
		super(context, textViewResourceId, objects);
		this.matches = objects;
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
			
			if(match_opponent != null)
			{
				match_opponent.setText(match.getOpponent());
			}
			if(match_venue != null)
			{
				match_venue.setText(match.getVenue() + " - ");
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
