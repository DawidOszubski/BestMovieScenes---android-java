package com.example.bestmoviescenes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import java.io.InputStream;
import java.util.ArrayList;

public class AudioActivity extends AppCompatActivity {

    int index;
    int[] characterImg;
    String[] audioText;
    MediaPlayer mediaPlayer;
    VideoView videoView;
    int i =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


         videoView = (VideoView) findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);


        Intent intent = getIntent();
        if(intent != null){
            index = Integer.parseInt(intent.getStringExtra("ClickedIndex"));
        }
        switch (index){
            case 0:
                 characterImg = new int[]{R.drawable.osiol, R.drawable.shrek_icon, R.drawable.osiol2, R.drawable.adas,R.drawable.osiol, R.drawable.adas, R.drawable.adas, R.drawable.adas,};
                 audioText = new String[]{"to mój ogon jest, urwiesz mi i cossssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", "z dwojga złego lepiej tą stroną", "niebieski kwiat i kolce", "ble ble","to mój ogon jest, bo mi urwiesz", "jakis inny text", "Dawid to przekozak", "ble ble"};
                 //linearLayout.setBackgroundResource(R.drawable.shrek_background);
                 break;
            case 1:
                 characterImg = new int[]{R.drawable.adas, R.drawable.adas, R.drawable.adas, R.drawable.adas,};
                 audioText = new String[]{"gdzie ten mały", "bleeeeeeeeeeee", "Dawid to przekozak", "ble ble"};
                //linearLayout.setBackgroundResource(R.drawable.shrek2_background);
                 break;
            case 2:
                characterImg = new int[]{R.drawable.adas, R.drawable.adas, R.drawable.adas, R.drawable.adas,};
                audioText = new String[]{"bleee", "bleeeeeeeeeeee", "Dawid to przekozak", "ble ble"};
                break;
            case 3:
                characterImg = new int[]{R.drawable.adas, };
                audioText = new String[]{"bleee", };
                break;
            case 4:
                characterImg = new int[]{R.drawable.numernabis2, R.drawable.numernabis, R.drawable.obelix, R.drawable.rudobrody,R.drawable.numernabis, R.drawable.skryba, R.drawable.idea, R.drawable.harimatis, R.drawable.numernabis3};
                audioText = new String[]{"Gdzie ten mały?", "Normalnie ulga, że weź", "Miałem nic nie mówić to szczekam sobie","Ostatnim razem mieliśmy pecha, trafili się nam galowie...","Nie to nie są niewolnicy...","Jak to jest być skrybą?","Będzie tego! Będzie tego!", "Widać mnie, nie widać..", "Chyba oberwałem"};
                break;
            case 5:
                characterImg = new int[]{};
                audioText = new String[]{};
                break;
        }
        ArrayList<Audio> audioArrayList = new ArrayList<>();

        for (int i = 0; i < characterImg.length; i++) {
            Audio audio = new Audio(characterImg[i], audioText[i]);
            audioArrayList.add(audio);
        }
        final ListView listView = findViewById(R.id.listviewAudio);
        ListAdapterAudio listAdapterAudio = new ListAdapterAudio(AudioActivity.this, audioArrayList);
        listView.setAdapter(listAdapterAudio);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                String text = audioText[i];
                String audioTitle = text.replaceAll("\\s+", "").replaceAll(",+", "").replaceAll("\\?+", "").replaceAll("\\.+", "").replaceAll("!+", "")
                        .replaceAll("ó+", "o").replaceAll("ł+", "l").replaceAll("ą+", "a").replaceAll("ć+", "c")
                        .replaceAll("ń+", "n").replaceAll("ś+", "s").replaceAll("ź+", "z").replaceAll("ż+", "z")
                        .replaceAll("ę+", "e").toLowerCase();
                //Toast.makeText(AudioActivity.this, "Text: "+ audioTitle, Toast.LENGTH_LONG).show();
                videoView.requestFocus();
                playMp4(audioTitle);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        videoView.setVisibility(View.GONE);
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                });
               }
        });



    }

    public void myClickHandler(View v)
    {


        LinearLayout vwParentRow = (LinearLayout)v.getParent();

        Button btnChild = (Button)vwParentRow.getChildAt(1);

    }

   @Override
    public void onStop() {
        super.onStop();
        if(mediaPlayer != null && mediaPlayer.isPlaying())
        {mediaPlayer.stop();}

    }

    private void playMp4(String nameOfFile){
        int resourceId = this.getResources().getIdentifier(nameOfFile, "raw", this.getPackageName());
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + resourceId);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }


}
