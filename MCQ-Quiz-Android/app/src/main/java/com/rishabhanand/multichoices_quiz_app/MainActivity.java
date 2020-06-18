package com.rishabhanand.multichoices_quiz_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.rishabhanand.multichoices_quiz_app.Adapter.CategoryAdapter;
import com.rishabhanand.multichoices_quiz_app.Common.Common;
import com.rishabhanand.multichoices_quiz_app.Common.SpaceDecoration;
import com.rishabhanand.multichoices_quiz_app.DBHelper.DBHelper;
import com.rishabhanand.multichoices_quiz_app.Model.Category;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recycler_category;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_setting)
        {
            showSettings();
        }
        return true;
    }

    private void showSettings() {
        View settings_layout = LayoutInflater.from(this)
                .inflate(R.layout.settings_layout,null);
        final CheckBox ckb_online_mode = (CheckBox) settings_layout.findViewById(R.id.ckb_online_mode);

        //load data from paper ,if not avaialabale just init default value
        ckb_online_mode.setChecked(Paper.book().read(Common.KEY_SAVE_ONLINE_MODE,false));

        //show dialog
        new MaterialStyledDialog.Builder(MainActivity.this)
                .setIcon(R.drawable.ic_settings_white_24dp)
                .setTitle("Settings")
                .setDescription("Please choose action")
                .setCustomView(settings_layout)
                .setNegativeText("DISMISS")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveText("SAVE")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if(ckb_online_mode.isChecked())
                        {
                            Common.isOnlineMode = true;
                        }
                        else
                        {
                            Common.isOnlineMode = false;
                        }

                        //Save
                        Paper.book().write(Common.KEY_SAVE_ONLINE_MODE, ckb_online_mode.isChecked());
                    }
                }).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init paper
        Paper.init(this);

        //Get value online mode
        Common.isOnlineMode = Paper.book().read(Common.KEY_SAVE_ONLINE_MODE,false);     // default false


        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Rishabh Quiz 2019");
        setSupportActionBar(toolbar);

        recycler_category=(RecyclerView)findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);
        recycler_category.setLayoutManager(new GridLayoutManager(this,2));

        //Get Screen Height
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels / 8; //Max size of item in category;
        CategoryAdapter adapter = new CategoryAdapter(MainActivity.this, DBHelper.getInstance(this).getAllCategory());
        int spaceInPixel = 4;
        recycler_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recycler_category.setAdapter(adapter);

    }
}
