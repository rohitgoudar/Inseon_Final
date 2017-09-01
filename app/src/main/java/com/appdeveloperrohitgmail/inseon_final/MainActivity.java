package com.appdeveloperrohitgmail.inseon_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    String username,password,username_shared,password_shared;
    Boolean network1;

    EditText Edit_username,Edit_password;
    Button sign;

    DatabaseReference LoginRef;
    FirebaseDatabase LoginData;
    SharedPreferences sharedPreference_Login,sharedPreference_Retrive_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Edit_username = (EditText)findViewById(R.id.username);
        Edit_password = (EditText)findViewById(R.id.password);

        network1 = haveNetworkConnection();
        if (network1) {
            Store_From_Firebase_Login();

        } else {
            Retrive_From_Shared_Login();
        }
        sign = (Button) findViewById(R.id.signin);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth_Signin();
            }
        });

    }

    public void Store_From_Firebase_Login()
    {
        sharedPreference_Login = getSharedPreferences("storeFromFirebase_Login", Context.MODE_PRIVATE);
        final SharedPreferences.Editor store_from_firebase_Login_editor = sharedPreference_Login.edit();

        LoginData = FirebaseDatabase.getInstance();
        LoginRef = LoginData.getReference("userid1");
        LoginRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getKey().equals("username")) {
                        username= dataSnapshot1.getValue().toString();
                        store_from_firebase_Login_editor.putString("username_login",username);
                    }
                    if (dataSnapshot1.getKey().equals("userpassword")) {
                        password= dataSnapshot1.getValue().toString();
                        store_from_firebase_Login_editor.putString("password_login",password);
                    }
                }
                store_from_firebase_Login_editor.apply();
                Retrive_From_Shared_Login();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void Retrive_From_Shared_Login()
    {
        sharedPreference_Retrive_Login = getSharedPreferences("storeFromFirebase_Login", Context.MODE_PRIVATE);
        username_shared = sharedPreference_Retrive_Login.getString("username_login","");
        password_shared = sharedPreference_Retrive_Login.getString("password_login","");
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public void Auth_Signin()
    {
        Log.d("username",""+(Edit_username.getText().toString()));
        Log.d("username-fire",""+username);
        if((Edit_username.getText().toString()).equals(username_shared))
        {
            Log.d("password",""+(Edit_password.getText().toString()));
            Log.d("password-fire",""+password);
            if((Edit_password.getText().toString()).equals(password_shared)) {
                Log.d("password",""+(Edit_password.getText().toString()));
                Log.d("password-fire",""+password);
                Toast.makeText(MainActivity.this, "signing in", Toast.LENGTH_SHORT).show();
                Intent sign = new Intent(MainActivity.this, Survey_Sync.class);
                startActivity(sign);
            }
            else {
                Toast.makeText(MainActivity.this, "Password Wrong", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Username Wrong", Toast.LENGTH_SHORT).show();
        }
    }

}
