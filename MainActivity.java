package com.example.princesaoud.dictionnaryapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    MenuItem menuSetting;
    Toolbar toolbar;
    BookmarkFragment bookmarkFragment;
    DictionaryFragment dictionaryFragment;
    DetailsFragment detailsFragment;
    String value = "Value from MainActivity";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dictionaryFragment = new DictionaryFragment();
        detailsFragment = new DetailsFragment();
        bookmarkFragment = BookmarkFragment.getNewInstance(dbHelper);

        goToFragment(dictionaryFragment, true);


        dictionaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClik(String value) {
                String id = (String) Global.getState(MainActivity.this, "lang");
                int dicType = id == null ? R.id.item_english_french: Integer.valueOf(id);
                goToFragment(DetailsFragment.getInstance(value,dbHelper, dicType), false);
            }
        });

        bookmarkFragment.setOnClickListener(new FragmentListener() {
            @Override
            public void onItemClik(String value) {
                String id = (String) Global.getState(MainActivity.this, "lang");
                int dicType = id == null ? R.id.item_english_french: Integer.valueOf(id);
                goToFragment(DetailsFragment.getInstance(value,dbHelper, dicType), false);
            }
        });

        EditText et_search = findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dictionaryFragment.filterValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);

        String id = (String) Global.getState(this, "lang");

        if (id != null)
            onOptionsItemSelected(menu.findItem(Integer.valueOf(id)));
        else {
            ArrayList<String> source = dbHelper.getWord(R.id.item_english_french);
            dictionaryFragment.resetDataSource(source);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = 0;
        if (item != null)
            id = item.getItemId();
        if (R.id.action_settings == id) return true;
        Global.saveState(this, "lang", id + "");

        ArrayList<String> source = dbHelper.getWord(id);
        //noinspection SimplifiableIfStatement
        if (id == R.id.item_french_english) {
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.french_english));
        } else if (id == R.id.item_english_french) {
            dictionaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.english_french));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();

        int id = item.getItemId();
        if (id == R.id.nav_bookmark) {
//            bookmarkFragment = new BookmarkFragment();
            if (!activeFragment.equals(BookmarkFragment.class.getSimpleName()))
                goToFragment(bookmarkFragment, false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void goToFragment(Fragment fragment, boolean isTop) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (!isTop)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container).getClass().getSimpleName();
        if (activeFragment.equals(BookmarkFragment.class.getSimpleName())) {
            menuSetting.setVisible(false);
            toolbar.findViewById(R.id.et_search).setVisibility(View.GONE);
            toolbar.setTitle("Bookmark");
        } else {
            menuSetting.setVisible(true);
            toolbar.findViewById(R.id.et_search).setVisibility(View.VISIBLE);
            toolbar.setTitle("");
        }

        return true;
    }
}
