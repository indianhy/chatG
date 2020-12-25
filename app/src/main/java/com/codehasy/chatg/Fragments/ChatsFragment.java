package com.codehasy.chatg.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codehasy.chatg.Adapters.UserAdapter;
import com.codehasy.chatg.Model.Chat;
import com.codehasy.chatg.Model.User;
import com.codehasy.chatg.Notifications.Token;
import com.codehasy.chatg.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import static com.codehasy.chatg.StartActivity.db;

public class ChatsFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;

    FirebaseUser fuser;
    DatabaseReference reference;

    private List<String> userslist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        userslist =db.chatDao().loadRecieverForSender(fuser.getEmail().split("@")[0]);

        if(userslist==null) {
            reference = FirebaseDatabase.getInstance().getReference("Chats");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    userslist.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Chat chat = snapshot.getValue(Chat.class);
                        if (chat.getSender().equals(fuser.getEmail().split("@")[0])) {
                            //if(!userslist.contains(chat.getReceiver()))
                            userslist.add(chat.getReceiver());
                        }
                        if (chat.getReceiver().equals(fuser.getEmail().split("@")[0])) {
                            //if(!userslist.contains(chat.getSender()))
                            userslist.add(chat.getSender());
                        }
                    }

                    readChats();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else readChats();
        updateToken(FirebaseInstanceId.getInstance().getToken());


        return view;
    }

    private void updateToken(String token) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Tokens");
        Token token1=new Token(token);
        databaseReference.child(fuser.getEmail().split("@")[0]).setValue(token1);
    }

    private void readChats() {
        mUsers = new ArrayList<>();
        List<com.codehasy.chatg.Database.User> users=db.userDao().loadAllByIds(userslist);

        if(users!=null) {
            for (com.codehasy.chatg.Database.User k : users) {
                System.out.println(k);
                User o = new User();
                o.setId(k.id);
                o.setPassword(k.password);
                o.setUsername(k.username);
                o.setImageURL(k.image);

                mUsers.add(o);

            }
            userAdapter = new UserAdapter(getContext(), mUsers);
            recyclerView.setAdapter(userAdapter);
        }else
        {   reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);

                    for (String id : userslist) {

                        System.out.println("OOOOOOOOOO   " + id);
                        if (user.getId().equals(id)) {
                            if (mUsers.size() != 0) {
                                if (!mUsers.contains(user)) mUsers.add(user);
                                /*for(int i=0;i<mUsers.size();i++){
                                    User uk= mUsers.get(i);
                                    if(!user.getId().equals(uk.getId())){
                                        mUsers.add(user);
                                    }
                                }*/
                            } else
                                mUsers.add(user);
                        }
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
}
