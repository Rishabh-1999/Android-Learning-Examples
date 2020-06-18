package com.example.balanceviewer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.balanceviewer.Adapters.PersonAdapter;
import com.example.balanceviewer.App_Into.App_Intro;
import com.example.balanceviewer.Class.Person;
import com.example.balanceviewer.DB.PersonDB;
import com.example.balanceviewer.Fragments.ListFrag;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {
    TextView tvAmt,tvNamePerson;
    TextInputEditText etAmt;
    Button btnUpdate;
    ListFrag listFrag;
    Fragment list,update,detail;
    FragmentManager fragmentManager;
    FloatingActionButton fab;
    private ActionMode mActionMode;
    final String MY_PREF_BALANCE_VIEWER="com.rishabhanand.balanceviewer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionMode=null;

        //Changes on Action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setTitle("Balance Viewer");
        actionBar.setDisplayUseLogoEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);

        // Create a shared Preferences to know whether it is first or not
        SharedPreferences settings = getSharedPreferences("MY_PREF_BALANCE_VIEWER", Context.MODE_PRIVATE);

        //get value from shared Preferences by keeping default true
        Boolean firstStart = settings.getBoolean("firstTime", true);

        // checking firstTimeBalanceViewer
        if (firstStart || firstStart==null) {
            // changing shared Preferences to false
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();

            //Changing Activity to Introduction Activity
            Intent intent = new Intent(getApplicationContext(), App_Intro.class);
            startActivity(intent);
        }
            tvAmt = (TextView) findViewById(R.id.tvAmt);
            etAmt = (TextInputEditText) findViewById(R.id.etAmt);
            tvNamePerson = (TextView) findViewById(R.id.tvNamePerson);
            btnUpdate = (Button) findViewById(R.id.btnUpdate);
            fab = (FloatingActionButton) findViewById(R.id.fab);

            fragmentManager = this.getSupportFragmentManager();
            list = fragmentManager.findFragmentById(R.id.listFrag);
            update = fragmentManager.findFragmentById(R.id.addPerson);
            detail = fragmentManager.findFragmentById(R.id.detailFrag);
            fragmentManager.beginTransaction()
                    .show(list)
                    .hide(update)
                    .hide(detail)
                    .commit();

            listFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);

            //Set Floating button function
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addPersonCustomDialog(v);
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data = etAmt.getText().toString().trim();
                    if (!data.isEmpty()) {
                        PersonDB db = new PersonDB(MainActivity.this);
                        db.open();
                        db.updateEntry(Integer.toString(db.find_index(tvNamePerson.getText().toString().trim())), tvNamePerson.getText().toString().trim(), data);
                        db.close();
                        tvAmt.setText(data);
                        closeKeyboard();
                        showToast("Data Update");
                        etAmt.setText("");
                        listFrag.notifyDataChanged();
                    } else
                        showToast("First Enter Updated Value");
                }
            });
    }

    private void addPersonCustomDialog(View v) {
        final AlertDialog.Builder alert =new AlertDialog.Builder(this);
        View mView= getLayoutInflater().inflate(R.layout.addnewpersondialog,null);

        final EditText text_add =(EditText)mView.findViewById(R.id.addPersonEditText);
        Button btn_ok = (Button)mView.findViewById(R.id.addPersonOK);
        Button btn_cancle = (Button)mView.findViewById(R.id.addPersonCancle);
        text_add.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        alert.setView(mView);

        final AlertDialog alertDialog =alert.create();
        alertDialog.setCanceledOnTouchOutside(true);

        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=text_add.getText().toString().trim();
                if(name.isEmpty())
                    showToast("Plz Enter Value");
                else {
                    PersonDB db = new PersonDB(getApplicationContext());
                    db.open();
                    db.createEntry(name, "0");
                    db.close();
                    notifyDataChange();
                    alertDialog.dismiss();
                }
            }
        });
        alertDialog.show();
    }

    // Method works when we work on listFrag items
    @Override
    public void onItemClicked(int index) {
        PersonDB db=new PersonDB(this);
        db.open();
        Person name=db.getIndexData(index);
        db.close();
        tvNamePerson.setText(name.getName());
        tvAmt.setText(name.getBalance());

        // To unhide detailFrag and updateFrag
        fragmentManager.beginTransaction()
                .show(list)
                .show(update)
                .show(detail)
                .commit();
    }

    //Custom Toast View
    public void showToast(String message) {
        View toastView=getLayoutInflater().inflate(R.layout.toast,(ViewGroup)findViewById(R.id.customtoast));
        TextView tvToast=toastView.findViewById(R.id.tvToast);
        tvToast.setText(message);
        Toast toast=new Toast(MainActivity.this);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL,0,0);
        toast.show();
    }

    // Method to close Keyboard
    public void closeKeyboard()
    {
        View view=this.getCurrentFocus();
        if(view!=null)
        {
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    // Method to notify listFrag that changes has been done
    public void notifyDataChange()
    {
        listFrag.notifyDataChanged();
        fragmentManager.beginTransaction()
                .show(list)
                .hide(update)
                .hide(detail)
                .commit();
    }

    @Override
    public void changeActionBar() {
        mActionMode = startActionMode(mActionModeCallback);
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.main,menu);
            mode.setTitle("     "+tvNamePerson.getText().toString());
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch ((item.getItemId())) {
                case R.id.action_delete:
                    deletePerson();
                    break;
                    case R.id.action_share :shareBalance();
                        break;
                        default:
                            break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
                mActionMode=null;
            ActionBar actionBar = getSupportActionBar();
            actionBar.setIcon(R.mipmap.ic_launcher);
            actionBar.setTitle("Balance Viewer");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
        }
    };

    private void shareBalance() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String sharebody="Balance Data";
        String sharesubject=tvNamePerson.getText().toString()+" your balance is "+tvAmt.getText().toString()+".";

        intent.putExtra(Intent.EXTRA_TEXT,sharesubject);
        intent.putExtra(Intent.EXTRA_SUBJECT,sharebody);

        startActivity(Intent.createChooser(intent,"Share Using"));
    }

    void deletePerson() {
        final String name1=tvNamePerson.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                PersonDB db=new PersonDB(MainActivity.this);
                db.open();
                int n=db.find_index(name1);
                if(n!=-1)
                    db.deleteEntry(String.valueOf(n));
                notifyDataChange();
                db.close();
                showToast(name1+" data Deleted");
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Exit");
        builder.setMessage("Are You Sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                System.exit(0);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

