package com.example.user.myapplication.design;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.myapplication.API.EventResponse;
import com.example.user.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Content extends AppCompatActivity {
    static final String BASE_URL = "https://api.timepad.ru/";
    private static final int LENGTH = 50;
    static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static List<EventResponse> myList = new ArrayList<>();

//    void events() {
//        {
//
//            Users users = new Users();
//            if (mAuth.getCurrentUser() != null) {
//                Users.setUID(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
//            } else {
//                Users.setUID(null);
//            }
//
//            try {
//                if (users.getNameUser() == null) {
//                    Users.setUID(mAuth.getCurrentUser().getUid());
//                    users.read();
//                } else {
//                    Users.setUID(mAuth.getCurrentUser().getUid());
//                    Log.d("API", "name: " + users.getNameUser() + " " + users.getSurnameUser()
//                            + "; city: " + users.getCityUser() + "; Uid: " + Users.getUID());
//                    users.write();
//                }
//            } catch (NullPointerException ex) {
//                Log.d("API", "exception: " + null);
//            }
//
//            Log.d("API", "nameWrite: " + users.getNameUser());
//
//            names = new String[LENGTH];
//            categories = new String[LENGTH];
//            dates = new String[LENGTH];
//            links = new String[LENGTH];
//
//            Log.d("API", "start");
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//            API api = retrofit.create(API.class);
//            List<String> cityList = new ArrayList<>();
//
//            cityList.add("Казань");
//            Call<EventsResponse> call;
//
//            Log.d("API", "categoryStr: " + categoriesStr);
//            if (!(categoriesStr.equals(""))) {
//                call = api.eventList(LENGTH, cityList, categoriesStr);
//            } else call = api.eventList(LENGTH, cityList);
//
//            call.enqueue(new Callback<EventsResponse>() {
//                @Override
//                public void onResponse(@NonNull Call<EventsResponse> call, @NonNull Response<EventsResponse> response) {
//                    try {
//                        EventsResponse eventsResponse = response.body();
//
//                        Log.d("API", "raw response: " + response.raw().toString());
//                        if (eventsResponse == null) Log.d("API", "response is null");
//                        else {
//                            myList = eventsResponse.getValues();
//                            for (EventResponse er : myList) {
//                                Log.d("API", "id = " + er.getId() + " name = " + er.getName() + " url = " + er.getUrl() + " img = " + er.getPoster_image().getDefault_url());
//                            }
//                            Log.d("API", "event list is " + eventsResponse.getTotal() + " length");
//                            for (int i = 0; i < LENGTH; i++) {
//                                final String[] str = {""};
//                                char[] dst=new char[10];
//                                names[i] = myList.get(i).getName();
//                                categories[i] = myList.get(i).getCategories().get(0).getName();
//                                links[i] = myList.get(i).getUrl();
//                                myList.get(i).getStarts_at().getChars(0, 10, dst, 0);
//                                for(char c : dst) str[0] += c;
//                                dates[i] = str[0];
//                                Log.d("API", "STR: " + dates[i]);
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<EventsResponse> call, @NonNull Throwable t) {
//                    Log.d("API", "failed");
//                }
//            });
//
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.des);

        CardContentFragment rSum = new CardContentFragment();
        getSupportFragmentManager().beginTransaction().remove(rSum).commit();
        // Adding Toolbar to Main screen
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Setting ViewPager for each Tabs
        ViewPager viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
    // Add Fragments to Tabs
    @SuppressLint("CommitTransaction")
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new CardContentFragment(), "События");
        adapter.addFragment(new Sorts(), "Фильтры");
        adapter.addFragment(new MyContentFragment(), "Мое");
        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }



    static class Adapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @SuppressLint("CommitTransaction")
        Adapter(FragmentManager manager) {
            super(manager);
//            manager.beginTransaction().replace(R.id.viewpager, new Fragment()).commit();
        }

            @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == R.id.action_update) {
            Intent intent = new Intent(this, Content.class);
            startActivity(intent);
        }

        if(id == R.id.action_pa) {
            Intent intent = new Intent(this, PA.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

