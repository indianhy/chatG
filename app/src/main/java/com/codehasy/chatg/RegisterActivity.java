package com.codehasy.chatg;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codehasy.chatg.Database.AppDatabase;
import com.codehasy.chatg.Database.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import static com.codehasy.chatg.StartActivity.db;

public class RegisterActivity extends AppCompatActivity {

    MaterialEditText username,email,password;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btn_register=findViewById(R.id.btn_register);

        auth=FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u=username.getText().toString();
                String e=email.getText().toString();
                String p=password.getText().toString();

                if(TextUtils.isEmpty(u)||TextUtils.isEmpty(p)||TextUtils.isEmpty(e))
                    Toast.makeText(RegisterActivity.this,"All Fields are required!!",Toast.LENGTH_SHORT).show();
                else if (p.length()<8)
                    Toast.makeText(RegisterActivity.this, "Password too short!! ", Toast.LENGTH_SHORT).show();
                else register(u,e,p);

            }
        });

    }

    public void register(final String username, String email, final String password){
        if(password.length()<8) Toast.makeText(this, "Password short", Toast.LENGTH_SHORT).show();

        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            String userId=firebaseUser.getEmail();
                            userId=userId.split("@")[0];

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userId);
                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",userId);
                            map.put("imageURL","default");
                            map.put("username",username);
                            map.put("password",password);



                            reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                            final User user = new User();
                            user.id=userId;
                            user.image=null;
                            user.username=username;
                            user.password=password;

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    db.userDao().insertAll(user);

                                }
                            }).run();

                        }
                        else
                            Toast.makeText(RegisterActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
