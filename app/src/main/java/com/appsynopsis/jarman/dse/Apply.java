// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.appsynopsis.jarman.dse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// Referenced classes of package com.appsynopsis.jarman.dse:
//            ApplyTask, MainActivity

public class Apply extends Activity
{

    String auth;
    String boid;
    TextView boids;
    String cash;
    TextView cashs;
    String mobile;
    String name,ipo;
    TextView names;
    String reference;
    TextView refs,ipos;

    public Apply()
    {
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_apply);
        bundle = getIntent().getExtras();
        mobile = bundle.getString("mobile");
        reference = bundle.getString("reference");
        auth = bundle.getString("auth");
        boid = bundle.getString("boid");
        name = bundle.getString("name");
        ipo = bundle.getString("ipo");
        boids = (TextView)findViewById(R.id.boid);
        names = (TextView)findViewById(R.id.name);
        cashs = (TextView)findViewById(R.id.cash);
        refs = (TextView)findViewById(R.id.ref);
        ipos=(TextView)findViewById(R.id.ipo);
        ipos.setText(ipo);
        boids.setText((new StringBuilder()).append("BOID: ").append(boid).toString());
        names.setText(name);
        cashs.setText(mobile);
        refs.setText((new StringBuilder()).append("Ref no.:").append(reference).toString());
        ((Button)findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {



            public void onClick(View view)
            {
                if (isNetworkAvailable())
                {
                    ProgressDialog progressDialog = new ProgressDialog(
                            Apply.this);
                    progressDialog.setMessage("Applying......");
                    ApplyTask applyTask = new ApplyTask(Apply.this,
                            progressDialog,mobile, reference,auth,boid,name,cash,ipo);
                    applyTask.execute();
                } else
                {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            

        });
        ((Button)findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {



            public void onClick(View view)
            {
                Intent intent = new Intent(Apply.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            

        });
    }
}
