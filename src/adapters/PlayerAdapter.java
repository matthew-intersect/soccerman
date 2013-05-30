package adapters;

import java.util.ArrayList;
import com.example.soccerman.R;

import models.Player;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayerAdapter extends ArrayAdapter<Player>
{
	private ArrayList<Player> players;
	
	public PlayerAdapter(Context context, int textViewResourceId, ArrayList<Player> objects) 
	{
		super(context, textViewResourceId, objects);
		this.players = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.player_list_item, null);
		}
		
		Player player = players.get(position);
		
		if(player != null)
		{
			TextView playerName = (TextView) v.findViewById(R.id.list_player_name);
			
			if(playerName != null)
			{
				playerName.setText(player.getName());
			}
		}
		return v;
	}
}
