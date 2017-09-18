package com.yf.utils;

import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Resources {
	   static Logger LOGGER = Logger.getLogger(Resources.class.getName());
		
		public static String getResources(String token) {
			final String CONTENT = "application/json";
			String id= Subscriptions.getId(token);
			String tok = "Bearer " +token;
	        
			OkHttpClient client = new OkHttpClient();
			
			Request request = new Request.Builder()
				    .url("https://management.azure.com"+id+"/resources?api-version=2017-05-10")
	                .addHeader("Authorization", tok)
	                .addHeader("Content-type", CONTENT)
	                .build();
			try {
	           Response response = client.newCall(request).execute();
				return response.body().string();
				}
			catch (Exception e){
				return "";
			}
		}
	       
		public static String[] getType(String token) {
			
			final String CONTENT = "application/json";
			String id= Subscriptions.getId(token);
			String tok = "Bearer "+token;
	        
			OkHttpClient client = new OkHttpClient();
			
			Request request = new Request.Builder()
				    .url("https://management.azure.com"+id+"/resources?api-version=2017-05-10")
	                .addHeader("Authorization", tok)
	                .addHeader("Content-type", CONTENT)
	                .build();
			try {
				Response response = client.newCall(request).execute();
				JSONObject jo = new JSONObject(response.body().string());
				JSONArray ja = jo.getJSONArray("value");
				String[] crits = new String[ja.length()];
				int n = 0;
				for(n = 0; n < ja.length(); n++)
				{
				    JSONObject  je= ja.getJSONObject(n);
					String Type = je.getString("type");
					crits[n] = Type;
				 
				}
				return crits;
				}
			catch (Exception e){
				return null;
			}
		}
			public static String getResid(String token, int arr) {
				
				final String CONTENT = "application/json";
				String id= Subscriptions.getId(token);
				String tok = "Bearer "+token;
		        
				OkHttpClient client = new OkHttpClient();
				
				Request request = new Request.Builder()
					    .url("https://management.azure.com"+id+"/resources?api-version=2017-05-10")
		                .addHeader("Authorization", tok)
		                .addHeader("Content-type", CONTENT)
		                .build();
				try {
					Response response = client.newCall(request).execute();
					JSONObject jo = new JSONObject(response.body().string());
					JSONArray ja = jo.getJSONArray("value");
					String[] crits = new String[ja.length()];
					int n = 0;
					for(n = 0; n < ja.length(); n++)
					{
					    JSONObject  je= ja.getJSONObject(n);
						String Type = je.getString("id");
						crits[n] = Type;
					 
					}
					String val = crits[arr];
					return val;
					}
				catch (Exception e){
					return null;
				}
			
			
		}
		

			
		
}