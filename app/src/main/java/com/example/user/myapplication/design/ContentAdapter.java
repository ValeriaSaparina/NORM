package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.user.myapplication.DBHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class ContentAdapter extends RecyclerView.Adapter<Sorts.ViewHolder> {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    List<String> myCategoriesList;
    List<Integer> myIdList;

    public static final Sorts.ContentAdapter self = this;

    DBHelper dbHelper = new DBHelper(Objects.requireNonNull(getActivity()).getApplicationContext());
    ContentValues cv = new ContentValues();
    SQLiteDatabase db = dbHelper.getWritableDatabase();

    String uID;
    String STR = "";
    String STR2 = "";

    void setNamesAndIds(List<String> names, List<Integer> ids) {
        this.myCategoriesList = names;
        this.myIdList = ids;
        notifyDataSetChanged();
    }


    ContentAdapter() {
        uID = mAuth.getUid();
        myCategoriesList = new ArrayList<>();
        myCategoriesList.add("");
        myIdList = new ArrayList<>();
//            myCategoriesList = SignIn.cat.getCategoryName();
//            myIdList = SignIn.cat.getCategoryId();
//            for (String str : myCategoriesList) Log.d("API", "myCatList: " + str);
//            for (int i : myIdList) Log.d("API", "myIdList: " + i);
    }

    @NonNull
    @Override
    public Sorts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Sorts.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Sorts.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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
                if (c[0].moveToFirst()) {

                    int idColIndex = c[0].getColumnIndex("id");
                    int nameColIndex = c[0].getColumnIndex("name");
                    int idCatColIndex = c[0].getColumnIndex("idCat");

                    do {
                        Log.d("API",
                                "ID = " + c[0].getInt(idColIndex) +
                                        ", name = " + c[0].getString(nameColIndex) +
                                        ", idCAt = " + c[0].getString(idCatColIndex));
                        STR += c[0].getString(idCatColIndex) + ", ";
                        Log.d("API", "STR = " + STR);
                    } while (c[0].moveToNext());
                    STR = STR.substring(0, STR.length() - 2);
                    CardContentFragment.setCatStr(STR);
                    STR2 = STR;
                    events = new Events(CardContentFragment.LENGTH, STR);
                    Log.d("API", "STR2: " + STR2);
                    STR = "";
                    Log.d("API", "STR = " + STR);
                } else
                    Log.d("API", "0 rows");
                c[0].close();
                holder.btn.setText("Удалить");
                flag.set(false);
                c[0].close();
            } else {
                db.execSQL("delete " + "from myTable");
                if (!c[0].moveToNext())
                    Toast.makeText(getContext(), "delete", Toast.LENGTH_LONG).show();
                holder.btn.setText("Добавить");
                CardContentFragment.setCatStr("");

                flag.set(true);
            }
            c[0].close();
            Log.d("API", "STR2: " + STR2);
            Events events = new Events(50, STR2);
            names = events.getNames();
            dates = events.getDates();
            categories = events.getCategories();
            links = events.getLinks();
//                CardContentFragment card = new CardContentFragment();
//                card.getFragmentManager().getFragments().clear();
//                while (names[position] != null) {
//
//                    holder.cardName.setText("name " + names[position]);
//
//                    holder.cardDate.setText("date " + dates[position]);
//
//                    holder.cardCat.setText("cat " + categories[position]);
//                    Log.d("API", "SUCCESS");
//                }
        };
        holder.btn.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return 22;
    }
}