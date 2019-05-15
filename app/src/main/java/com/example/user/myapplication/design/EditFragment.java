package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.myapplication.R;
import com.example.user.myapplication.pages.SignIn;
import com.google.firebase.auth.FirebaseAuth;

public class EditFragment extends Fragment {

    Users users = new Users();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView nameEdit;
        TextView cityEdit;
        TextView mailEdit;
        Button btn_edit;
        Button btn_sign_out;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card_edit, parent, false));
            picture = itemView.findViewById(R.id.card_image_edit);
            nameEdit = itemView.findViewById(R.id.card_title_edit);
            cityEdit = itemView.findViewById(R.id.card_city_text_edit);
            mailEdit = itemView.findViewById(R.id.card_mail_text_edit);
            btn_edit = itemView.findViewById(R.id.action_button_edit);
            btn_sign_out = itemView.findViewById(R.id.sign_out_edit);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 1;

        private FirebaseAuth mAuth = FirebaseAuth.getInstance();


        View.OnClickListener onClickListenerEdit;
        View.OnClickListener onClickListenerSignOut;

        ContentAdapter() {

//            users.read();

        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.nameEdit.setText(users.getNameUser() + " " + users.getSurnameUser());
            holder.cityEdit.setText(users.getCityUser());
            holder.mailEdit.setText(users.getmailUser());


            onClickListenerEdit = v -> {
                Log.d("API", "edit");
                if (v.getId() == R.id.action_button_edit) {
                    Intent intent = new Intent(getActivity(), Edit.class);
                    startActivity(intent);
                }
            };
            holder.btn_edit.setOnClickListener(onClickListenerEdit);

            onClickListenerSignOut = v -> {
                Log.d("API", "signOut");
                if (v.getId() == R.id.sign_out_edit) {
                    Users.setUID(null);
                    mAuth.signOut();
                    Intent intent = new Intent(getActivity(), SignIn.class);
                    startActivity(intent);
                }
            };
            holder.btn_sign_out.setOnClickListener(onClickListenerSignOut);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
