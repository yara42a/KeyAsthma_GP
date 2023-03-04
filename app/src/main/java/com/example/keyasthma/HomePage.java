package com.example.keyasthma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keyasthma.databinding.ActivityHomePageBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomePage extends DrawerBaseActivity {
    ActivityHomePageBinding activityHomePageBinding;
    DatabaseReference reference;
    int Dlevel, Hlevel;
    TextView dust,humidity, AirQ;
    String D,H;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityHomePageBinding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(activityHomePageBinding.getRoot());
        allocateActivityTitle("HOME");

        AirQ = findViewById(R.id.airquality);

        dust = (TextView) findViewById(R.id.dustlevel);
        humidity = (TextView) findViewById(R.id.humiditylevel);
        reference = FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                D = snapshot.child("DHT").child("dust").getValue().toString();
                H = snapshot.child("DHT").child("humidity").getValue().toString();

                Hlevel = Integer.parseInt(H);
                Dlevel = Integer.parseInt(D);

                Airquality(Dlevel,Hlevel);
            }

            private void Airquality(int dlevel, int hlevel) {

                if(dlevel <= 0  || hlevel >= 100 || hlevel==0){
                    Toast.makeText(HomePage.this, "Something Wrong happened in a hardware," +
                            " Please check!", Toast.LENGTH_LONG).show();
                }
                else if (dlevel >= 1000 || hlevel >= 75) {
                    if(dlevel >= 1000 && hlevel >= 75){
                        dust.setText(" ABNORMAL ");
                        humidity.setText("ABNORMAL");
                    }else if(hlevel >= 75 && dlevel <= 1000){
                        humidity.setText(" ABNORMAL ");
                        dust.setText("NORMAL");
                    }else if(hlevel <= 75 && dlevel >= 1000){
                        humidity.setText(" NORMAL ");
                        dust.setText("ABNORMAL");
//                        dust.setBackgroundColor(Color.RED);
                    }
                    AirQ.setBackgroundColor(Color.RED);
                    AirQ.setText("AIR QUALITY IS ABNORMAL");
                    Notificationabnormal();
                }   else if (dlevel <= 1000 || hlevel <= 75)  {
                    humidity.setText("NORMAL");
                    dust.setText("NORMAL");
                    AirQ.setBackgroundColor(Color.GREEN);
                    AirQ.setText("AIR QUALITY IS NORMAL");
                }

            }
            
            private void Notificationabnormal() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("My Notification",
                            "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(HomePage.this,
                        "My Notification");
                builder.setContentTitle("! WARNING !");
                builder.setAutoCancel(true);
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                String message = "Abnormal air quality Please check your child's environment";
                builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
                Intent intent = new Intent(HomePage.this, HomePage.class);
                PendingIntent pi = PendingIntent.getActivity(HomePage.this,0,intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pi);
                NotificationManager mNotificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, builder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "Something Wrong happened!", Toast.LENGTH_LONG).show();
            }
        });
    }
}