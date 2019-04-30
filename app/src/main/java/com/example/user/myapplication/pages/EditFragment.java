package com.example.user.myapplication.pages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.design.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class EditFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView name;
        TextView description;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));
            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);
            description = itemView.findViewById(R.id.card_text);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 1;
        private String nameUser;
        private String lastnameUser;
        private String aboutUser;
        private String emailUser;

        private FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        private Users users = new Users(Objects.requireNonNull(user).getUid());



        ContentAdapter(Context context) {

            lastnameUser = users.getLastnameUser();
            aboutUser = users.getAboutUser();
            nameUser = users.getNameUser();

            emailUser = user.getEmail();
            Log.d("API", "userName: " + nameUser);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            nameUser = users.getName(user.getUid());
//            lastnameUser = users.getLastname(user.getUid());
//            aboutUser = users.getAbout(user.getUid());
//            emailUser = user.getEmail();
            String fullname = users.getNameUser() + " " + users.getLastnameUser();
            Log.d("API", "fullname: " + fullname);
            Log.d("API", "getUserNAme() = " + users.getNameUser());
            Log.d("API", "getLastnameUser() = " + users.getLastnameUser());
            holder.name.setText(fullname);
//            Log.d("API", "namEUSER: null");
//            Log.d("API", "nameAAA: " + users.getNameUser());
//            Log.d("API", "AAAAAAAAAAAAAAAAAA");
            holder.description.setText(aboutUser);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
