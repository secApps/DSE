// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.appsynopsis.jarman.dse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

// Referenced classes of package com.appsynopsis.jarman.dse:
//            LoginTask

public class MainActivity extends Activity
{

    public MainActivity()
    {
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {
                if (isNetworkAvailable()) {
                    ProgressDialog progressDialog = new ProgressDialog(
                            MainActivity.this);
                    progressDialog.setMessage("Logging in...");
                    LoginTask loginTask = new LoginTask(MainActivity.this,
                            progressDialog, MainActivity.this);
                    loginTask.execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }


            {


            }
        });
    }
}
