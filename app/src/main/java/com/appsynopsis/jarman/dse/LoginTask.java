// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.appsynopsis.jarman.dse;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.appsynopsis.jarman.dse:
//            MainActivity, JSONParser, Apply

public class LoginTask extends AsyncTask<String, Void, JSONObject>
{

    private static String loginURL = "http://www.finefoodsbd.net/AppData/userloginNew.php";
    String auth;
    String boid;
    String ipo;
    Context context;
    private JSONParser jsonParser;
    MainActivity mainActivity;
    String mobileno;
    String name;
    private ProgressDialog progressDialog;
    String referenceno;
    private int responseCode=0;

    public LoginTask(MainActivity mainactivity, ProgressDialog progressdialog, Context context1)
    {
        auth = "12345jhkdfgdfoem123";
       // responseCode = 0;
        context = context1;
        mainActivity = mainactivity;
        progressDialog = progressdialog;
    }


@Override
    protected JSONObject doInBackground(String... arg0)
    {
        EditText ref = (EditText)mainActivity.findViewById(R.id.reference);
        EditText edittext = (EditText)mainActivity.findViewById( R.id.mobile);
         mobileno = edittext.getText().toString();
         referenceno = ref.getText().toString();
        List<NameValuePair> as=new ArrayList<>();
        as.add(new BasicNameValuePair("mobile", mobileno));
        as.add(new BasicNameValuePair("reference", referenceno));
        as.add(new BasicNameValuePair("auth", auth));
        jsonParser = new JSONParser();
        return jsonParser.getJSONFromUrl(loginURL, as);
    }


@Override
    public void onPostExecute(JSONObject jsonobject)
    {
        try {
            Log.d("RESPOnse",jsonobject.toString());

            if (!jsonobject.isNull("status")) {
                if (jsonobject.getString("status").equalsIgnoreCase("Success")) {

                    responseCode = 1;
                    JSONArray json = jsonobject.getJSONArray("UserData");
                    boid = json.getJSONObject(0).getString("boid");
                    name = json.getJSONObject(0).getString("name");
                    ipo = json.getJSONObject(0).getString("ipo");
                    Log.d("RESPONSE>>", (new StringBuilder()).append(boid).append(name).toString());
                }
            }

            if (responseCode == 1) {
                progressDialog.setMessage("Login successful");
                progressDialog.dismiss();
                Toast.makeText(context, "LOGGED IN", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(mainActivity.getApplicationContext(), Apply.class);

                Bundle bundle = new Bundle();
                bundle.putString("mobile", mobileno);
                bundle.putString("reference", referenceno);
                bundle.putString("auth", auth);
                bundle.putString("boid", boid);
                bundle.putString("name", name);
                bundle.putString("ipo", ipo);
                in.putExtras(bundle);
                context.startActivity(in);
                mainActivity.finish();
                // return;
            } else {
                progressDialog.setMessage("Can't login");
                progressDialog.dismiss();
                Toast.makeText(context, "Failure ", Toast.LENGTH_SHORT).show();
               // return;
            }
        }catch (JSONException e){

        }


    }


@Override
    public void onPreExecute()
    {
        progressDialog.show();
    }

}
