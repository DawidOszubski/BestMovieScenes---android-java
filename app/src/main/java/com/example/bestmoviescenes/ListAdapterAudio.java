package com.example.bestmoviescenes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class ListAdapterAudio extends ArrayAdapter<Audio> {




    public ListAdapterAudio(Context context, ArrayList<Audio> audioArrayList){
        super(context, R.layout.list_audio,audioArrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Audio audio = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_audio,parent,false);
        }

        ImageView characterImg = convertView.findViewById(R.id.characterImg);
        TextView audioText = convertView.findViewById(R.id.audioTitle);
        final ToggleButton favouriteBtn = convertView.findViewById(R.id.favouriteButton);

        favouriteBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    Toast.makeText(getContext(), "Dodane do ulubionych ", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getContext(), "Usuniete z ulubionych ", Toast.LENGTH_SHORT).show();
            }
        });

        characterImg.setImageResource(audio.characterImg);
        audioText.setText(audio.audioText);

        return convertView;
    }
}
