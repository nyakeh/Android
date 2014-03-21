package com.example.gauge;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.os.AsyncTask;

public class AsyncUserLogin extends AsyncTask <Void, Void, String> {
	MainActivity mainActivity;
	public AsyncUserLogin(MainActivity mainActivity) {
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

	@Override
	protected String doInBackground(Void... params) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext localContext = new BasicHttpContext();
		HttpGet httpGet = new HttpGet("http://www.cheesejedi.com/rest_services/get_big_cheese.php?puzzle=1");
		String text = null;
		try {	
			HttpResponse response = httpClient.execute(httpGet, localContext);	
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