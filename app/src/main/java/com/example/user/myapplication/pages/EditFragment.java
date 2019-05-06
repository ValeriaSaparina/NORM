package com.example.user.myapplication.pages;

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
import com.example.user.myapplication.design.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.atomic.AtomicBoolean;

public class EditFragment extends Fragment {
    static String fullname;

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
        Button btn_delete;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card_edit, parent, false));
            picture = itemView.findViewById(R.id.card_image_edit);
            nameEdit = itemView.findViewById(R.id.card_title_edit);
            cityEdit = itemView.findViewById(R.id.card_city_text_edit);
            mailEdit = itemView.findViewById(R.id.card_mail_text_edit);
            btn_delete = itemView.findViewById(R.id.action_button_edit);
        }
    }

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final int LENGTH = 1;
//        private String emailUser;

        private FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();


        View.OnClickListener onClickListener;


        ContentAdapter() {
//            emailUser = user.getEmail();
            Users users = new Users();
            users.read(mAuth.getUid());


            fullname = users.getNameUser() + " " + users.getSurnameUser();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Users users = new Users();
            users.read(mAuth.getUid());
            Log.d("API", mAuth.getUid());
            Log.d("API", "fullname: " + fullname);
            Log.d("API", "getUserNAme() = " + users.getNameUser());
            Log.d("API", "getSurnameUser() = " + users.getSurnameUser());
            Log.d("API", "mail: " + users.getmailUser());
            holder.nameEdit.setText(fullname);
            holder.cityEdit.setText(users.getCityUser());
            holder.mailEdit.setText(users.getmailUser());

            AtomicBoolean flag = new AtomicBoolean(false);

            onClickListener = v -> {
                Log.d("API", "AAA");
                if (v.getId() == R.id.tile_delete_button) {
                    holder.nameEdit.setClickable(true);
                    holder.cityEdit.setClickable(true);
                    holder.mailEdit.setClickable(true);

                    holder.nameEdit.setFocusable(true);
                    holder.cityEdit.setFocusable(true);
                    holder.mailEdit.setFocusable(true);

                    holder.nameEdit.setLongClickable(true);
                    holder.cityEdit.setLongClickable(true);
                    holder.mailEdit.setLongClickable(true);



                    holder.btn_delete.setText("Сохранить");

                    flag.set(true);
                }
            };
            holder.btn_delete.setOnClickListener(onClickListener);

            if (flag.get()) {
                onClickListener = v -> {
                    if (v.getId() == R.id.tile_delete_button) {
                        String[] str;
                        String str2 = (String) holder.nameEdit.getText();
                        str = str2.split(" ");

                        Users.setNameUser(str[0]);
                        Users.setSurnameUser(str[1]);
                        Users.setCityUser((String) holder.cityEdit.getText());
                        Users.setMailUser((String) holder.mailEdit.getText());

                        users.write(mAuth.getUid());
                        holder.nameEdit.setText(users.getNameUser() + users.getSurnameUser());
                        holder.mailEdit.setText(users.getmailUser());
                        holder.cityEdit.setText(users.getCityUser());
                        holder.btn_delete.setText("Изменить");
                    }
                };
                holder.btn_delete.setOnClickListener(onClickListener);
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
