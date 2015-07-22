// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: braces fieldsfirst space lnc 

package com.appsynopsis.jarman.dse;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.appsynopsis.jarman.dse:
//            Apply, JSONParser

public class ApplyTask extends AsyncTask<String, Void, Integer>
{

    private static String applyURL = "http://www.finefoodsbd.net/AppData/application.php";
    String auth;
    String boid;
    String cash;
    Context context;
    private JSONParser jsonParser;
    Apply mainActivity;
    String mobileno;
    String name,ipo;
    private ProgressDialog progressDialog;
    String referenceno;
    private int responseCode;

    public ApplyTask(Apply apply, ProgressDialog progressdialog, String s, String s1, String s2, String s3,
            String s4, String s5,String s6)
    {
        auth = "12345jhkdfgdfoem123";
        responseCode = 0;
        context = apply;
        mobileno = s;
        referenceno = s1;
        auth = s2;
        mainActivity = apply;
        boid = s3;
        cash = s5;
        ipo=s6;
        name = s4;
        progressDialog = progressdialog;
    }
@Override
   public Integer doInBackground(String... arg0) {
    //String ipo = ((TextView) mainActivity.findViewById(R.id.ipo)).getText().toString().replace("\n", "").replace(" ", "");
    if (ipo.equals("")) {
        return 2;
    } else {


        List<NameValuePair> arraylist = new ArrayList();
        arraylist.add(new BasicNameValuePair("mobile_no", mobileno));
        arraylist.add(new BasicNameValuePair("reference", referenceno));
        arraylist.add(new BasicNameValuePair("ipo_name", ipo));
        arraylist.add(new BasicNameValuePair("client_name", name));
        arraylist.add(new BasicNameValuePair("auth", auth));
        jsonParser = new JSONParser();
        JSONObject json = jsonParser.getJSONFromUrl(applyURL, arraylist);
        try {
            if (!json.isNull("status")) {
                if (json.getString("status").equalsIgnoreCase("Success")) {
                    return 1;

                }else if(json.getString("status").equalsIgnoreCase("Repeat")){
                    return 4;

                } else {
                    return 3;
                }
            }else{return 3;}


        } catch (Exception e) {
            return 3;
        }
    }


}

    protected void onPostExecute(Integer integer)
    {
        if (integer.intValue() == 1)
        {
            progressDialog.setMessage("Successfully applied");
            progressDialog.dismiss();
            Toast.makeText(context, "Successfully applied", Toast.LENGTH_LONG).show();

        }
        if (integer.intValue() == 2)
        {
            Toast.makeText(context, "Need an IPO name to apply", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();

        }
        if(integer.intValue() == 4){
            Toast.makeText(context, "Can't apply again...... \n You already applied once!!", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        } if(integer.intValue() == 3)
        {
            progressDialog.setMessage("Can't login");
            progressDialog.dismiss();
            Toast.makeText(context, "Failure ", Toast.LENGTH_LONG).show();

        }
    }


@Override
    protected void onPreExecute()
    {
        progressDialog.show();
    }

}
