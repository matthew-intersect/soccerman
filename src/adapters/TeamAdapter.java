package adapters;

import java.util.ArrayList;

import com.example.soccerman.R;

import models.Team;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TeamAdapter extends ArrayAdapter<Team>
{
	private ArrayList<Team> teams;
	
	public TeamAdapter(Context context, int textViewResourceId, ArrayList<Team> objects) {
		super(context, textViewResourceId, objects);
		this.teams = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.team_list_item, null);
		}
		
		Team team = teams.get(position);
		
		if(team != null)
		{
			TextView team_name = (TextView) v.findViewById(R.id.list_team_name);
			TextView team_manager = (TextView) v.findViewById(R.id.list_team_manager);
			
			if(team_name != null)
			{
				team_name.setText(team.getName());
			}
			if(team_manager != null)
			{
				team_manager.setText(team.getManager());
			}
		}
		return v;
	}
}
