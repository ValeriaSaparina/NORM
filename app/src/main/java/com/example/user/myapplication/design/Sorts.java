package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.myapplication.DBHelper;
import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Sorts extends Fragment {


    private static final int LENGTH = 22;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        onSaveInstanceState(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;
        Button btn;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sorts, parent, false));
            text = itemView.findViewById(R.id.category_sort);
            btn = itemView.findViewById(R.id.add_category);

        }


    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private FirebaseDatabase database = FirebaseDatabase.getInstance();
        private DatabaseReference myRef = database.getReference();
        private FirebaseAuth mAuth = FirebaseAuth.getInstance();


        List<String> myCategoriesList;
        List<String> myIdList;
        Categories cat = new Categories();

        DBHelper dbHelper = new DBHelper(Objects.requireNonNull(getActivity()).getApplicationContext());
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String uID;
        String STR = "";

        ContentAdapter() {
            uID = mAuth.getUid();
            myCategoriesList = cat.getMyCategoriesList();
            myIdList = cat.getMyIdList();
            for (String str : myCategoriesList) Log.d("API", "myCatList: " + str);
            for (String str : myIdList) Log.d("API", "myIdList: " + str);

           // categoriesList = cat.getCategoriesList();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.text.setText(myCategoriesList.get(position));

            AtomicBoolean flag = new AtomicBoolean(true);
            final Cursor[] c = new Cursor[1];

            View.OnClickListener onClickListener = v -> {
                if (flag.get()) {
                    Log.d("API", "--- Insert in mytable: ---");
                    cv.put("name", holder.text.getText().toString());
                    cv.put("idCat", myIdList.get(position));
                    long rowID = db.insert("mytable", null, cv);
                    Log.d("API", "row inserted, ID = " + rowID);
                    Log.d("API", "--- Rows in mytable: ---");
                    c[0] = db.query("myTable", null, null, null, null, null, null);

                    // ставим позицию курсора на первую строку выборки
                    // если в выборке нет строк, вернется false
                    if (c[0].moveToFirst()) {

                        // определяем номера столбцов по имени в выборке
                        int idColIndex = c[0].getColumnIndex("id");
                        int nameColIndex = c[0].getColumnIndex("name");
                        int idCatColIndex = c[0].getColumnIndex("idCat");

                        do {
                            // получаем значения по номерам столбцов и пишем все в лог
                            Log.d("API",
                                    "ID = " + c[0].getInt(idColIndex) +
                                            ", name = " + c[0].getString(nameColIndex) +
                                            ", idCAt = " + c[0].getString(idCatColIndex));
                            STR += c[0].getString(idCatColIndex) + ", ";
                            Log.d("API", "STR = " + STR);
                        } while (c[0].moveToNext());
                        STR = STR.substring(0, STR.length() - 2);
                        CardContentFragment.setCatStr(STR);
                        STR = "";
                        Log.d("API", "STR = " + STR);
                    } else
                        Log.d("API", "0 rows");
                    c[0].close();
                    holder.btn.setText("Удалить");
                    flag.set(false);
                } else {
                    db.execSQL("delete " + "from myTable");
                    if (!c[0].moveToNext()) Toast.makeText(getContext(), "delete", Toast.LENGTH_LONG).show();
                    holder.btn.setText("Добавить");
                    CardContentFragment.setCatStr("");

                    flag.set(true);
                }
                c[0].close();
           };
            holder.btn.setOnClickListener(onClickListener);
        }

        @Override
        public int getItemCount() {
            return Math.toIntExact(LENGTH);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        DBHelper dbHelper = new DBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int clearCount = db.delete("myTable", null, null);
        Log.d("API", "deleted rows count = " + clearCount);
    }

}

