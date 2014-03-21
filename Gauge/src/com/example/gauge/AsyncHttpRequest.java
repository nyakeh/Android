package com.example.gauge;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncHttpRequest extends AsyncTask <String, Void, String> {
	MainActivity mainActivity;
	public AsyncHttpRequest(MainActivity mainActivity) {
		super();
		this.mainActivity = mainActivity;
	}
	protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {

		InputStream in = entity.getContent();

		StringBuffer out = new StringBuffer();
		int n = 1;
		while (n>0) {
			byte[] b = new byte[4096];
			n =  in.read(b);
			if (n>0) out.append(new String(b, 0, n));
		}
		return out.toString();
	}
	
	public void Login(String email, String password)
	{
		JSONObject jsonArg = new JSONObject();
		try {
			jsonArg.put("Email", email);
			jsonArg.put("Password", password);
		} catch (JSONException e) {
			Log.d("Json building login JSON object exception", e.getMessage());
		}
		this.execute("POST","http://mortgagecalculator.cloudapp.net/api/login", jsonArg.toString());
	}
	
	public void Register(String email, String forename, String surname, String password)
	{
		JSONObject jsonArg = new JSONObject();
		try {
			jsonArg.put("Email", email);
			jsonArg.put("Forename", forename);
			jsonArg.put("Surname", surname);
			jsonArg.put("Password", password);
			jsonArg.put("AccountTypeId", 1);
			jsonArg.put("Active", true);
		} catch (JSONException e) {
			Log.d("Json building register JSON object exception", e.getMessage());
		}
		this.execute("POST","http://mortgagecalculator.cloudapp.net/api/account", jsonArg.toString());
	}
	
	public void Calculate(String property_value, String deposit, String term, String interest_rate, String fees, String accountId)
	{
		JSONObject jsonArg = new JSONObject();
		try {
			jsonArg.put("AccountId", accountId);
			jsonArg.put("CustomerReference", "00000000-0000-0000-0000-000000000000");
			jsonArg.put("HouseValue", property_value);
			jsonArg.put("Deposit", deposit);
			jsonArg.put("InterestRate", interest_rate);
			jsonArg.put("Term", term);
			jsonArg.put("Fees", fees);
			jsonArg.put("MortgageType", "1");
			jsonArg.put("Source", "Gauge Android App");
		} catch (JSONException e) {
			Log.d("Json building calculate JSON object exception", e.getMessage());
		}
		this.execute("POST","http://mortgagecalculator.cloudapp.net/api/mortgage", jsonArg.toString());
	}
	
	public void Compare(String property_value, String deposit, String term, String accountId)
	{
		JSONObject jsonArg = new JSONObject();
		try {
			jsonArg.put("AccountId", accountId);
			jsonArg.put("CustomerReference", "00000000-0000-0000-0000-000000000000");
			jsonArg.put("HouseValue", property_value);
			jsonArg.put("Deposit", deposit);
			jsonArg.put("MortgageType", "1");
			jsonArg.put("Source", "Gauge Android App");
		} catch (JSONException e) {
			Log.d("Json building calculate JSON object exception", e.getMessage());
		}
		this.execute("POST","http://mortgagecalculator.cloudapp.net/api/mortgage", jsonArg.toString());
	}

	@Override
	protected String doInBackground(String... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		
		HttpRequestBase request = null;
		
		if(params[0] == "POST") 
		{
			HttpPost httpPost = new HttpPost(params[1]);
			httpPost.setHeader("Content-Type", "application/json");
			try {
				httpPost.setEntity(new StringEntity(params[2]));
			} catch (UnsupportedEncodingException ex) {
				Log.d("Building httpPost - Unable to encode JsoArgs as StringEntity", ex.getMessage());
			}
			request = httpPost;
		}  else {
			HttpGet httpGet = new HttpGet(params[1]);
			request = httpGet;	 
		}
		
		String text = null;
		try {	
			HttpResponse response = httpClient.execute(request, localContext);
			HttpEntity entity = response.getEntity();
			text = getASCIIContentFromEntity(entity);
		} catch (Exception e) {
			return e.getLocalizedMessage();
		}
		return text;
	}

	protected void onPostExecute(String results) {
		this.mainActivity.handleResponse(results);
	}
}