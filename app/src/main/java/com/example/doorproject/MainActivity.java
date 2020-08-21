package com.example.doorproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference database, door_status;
    String homeAccount = "test1";
    Button doorButtonBTN;
    String doorStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "Begin");

        initialize();
        getCurrentDoorStatus();
    }

    void initialize(){
        database = FirebaseDatabase.getInstance().getReference();
        door_status = FirebaseDatabase.getInstance().getReference("test1/door_status");
        doorButtonBTN = findViewById(R.id.doorButton1);

    }

    void getCurrentDoorStatus(){

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Door door = new Door();

                    door.setAlias(ds.getValue(Door.class).getAlias());
                    door.setDoor_status(ds.getValue(Door.class).getDoor_status());
                    door.setLatest_command(ds.getValue(Door.class).getLatest_command());

                    Log.i(TAG, door.getDoor_status());
                    Log.i(TAG, door.getAlias());
                    Log.i(TAG, door.getLatest_command());

                    doorStatus = door.getDoor_status();

                    setButtonColor();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "Failed to read value.", databaseError.toException());

            }
        });
    }

    private void setButtonColor() {
        if (doorStatus.equals("locked")){
            doorButtonBTN.setBackgroundColor(Color.GREEN);
            doorButtonBTN.setText("LOCKED");
        }

        if (doorStatus.equals("unlocked")){
            doorButtonBTN.setBackgroundColor(Color.RED);
            doorButtonBTN.setText("UNLOCKED");
        }
    }

    public void toggleDoor(View view){
        if (doorStatus.equals("locked")){
            database.child(homeAccount).child("latest_command").setValue("unlock");
        }else{
            database.child(homeAccount).child("latest_command").setValue("lock");
        }
    }
}