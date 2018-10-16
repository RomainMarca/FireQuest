package fr.wildcodeschool.firequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends  ArrayAdapter<Player> {

    public Adapter(Context context, ArrayList<Player> items){
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        Player player = (Player) getItem(position);

        TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvScore = (TextView) convertView.findViewById(R.id.tv_score);

        assert player != null;
        tvName.setText(player.getName());
        tvScore.setText(String.valueOf(player.getScore()));

        return convertView;
    }
}
