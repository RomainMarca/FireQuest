package fr.wildcodeschool.firequest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public int scoreMax = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvBestPlayer = findViewById(R.id.tv_the_best);
        final TextView tvBestScore = findViewById(R.id.tv_best_score);
        final TextView tvbestName = findViewById(R.id.tv_best_name);
        Button btSave = findViewById(R.id.bt_save);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = findViewById(R.id.et_name);
                EditText editScore = findViewById(R.id.et_score);

                String etName = editName.getText().toString();
                String etScore = editScore.getText().toString();

                if (etName.isEmpty() || etScore.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Name or score is empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Player player = new Player(etName, Integer.parseInt(etScore));
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference playerRef = database.getReference("Player");
                    String key = playerRef.push().getKey();
                    playerRef.child(key).setValue(player);
                }

            }
        });

        /*final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference playerRef = database.getReference("Player");
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    Player player = playerSnapshot.getValue(Player.class);

                    assert player != null;
                    if (player.getScore() > scoreMax) {
                        scoreMax = player.getScore();
                        Toast.makeText(MainActivity.this, "1 " + player.getScore(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });*/


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference playerRef = database.getReference("Player");
        playerRef.orderByChild("score").limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot playerSnapshot : dataSnapshot.getChildren()) {
                    Player player = playerSnapshot.getValue(Player.class);

                    assert player != null;
                    tvBestScore.setText(String.valueOf(player.getScore()));
                    tvbestName.setText(player.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Button btIntent = findViewById(R.id.bt_intent);
        btIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMain2 = new Intent(MainActivity.this, Main2Activity.class);
                MainActivity.this.startActivity(goToMain2);
            }
        });


    }
}
