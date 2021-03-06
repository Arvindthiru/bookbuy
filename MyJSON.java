package eocom.example.storephp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.util.Log;

public class MyJSON {
	  static InputStream is = null;
	  static JSONObject jObj = null;
	  static String json = "";
	  
	  // constructor
	  public MyJSON() {
	  }
	
	  // function get json from url
	    // by making HTTP POST or GET method
	  public JSONObject makeHttpRequest(String url, String method, List<NameValuePair> params) {
		
		  // Making HTTP request
		  try {
			// check for request method
			  if(method == "POST"){
				  DefaultHttpClient httpClient = new DefaultHttpClient();
				  HttpPost httpPost = new HttpPost(url);
				  httpPost.setEntity(new UrlEncodedFormEntity(params));
				  Log.d("url", url);
				  HttpResponse httpResponse = httpClient.execute(httpPost);
				  HttpEntity httpEntity = httpResponse.getEntity();
				 is = httpEntity.getContent();
			  } else if(method == "GET") {
				  //request method is GET
				  DefaultHttpClient httpClient = new DefaultHttpClient();
				  String paramString = URLEncodedUtils.format(params, "utf-8");
				  url += "?" + paramString;
				  Log.d("url", url);
				  HttpGet httpGet = new HttpGet(url);
				  
				  HttpResponse httpResponse = httpClient.execute(httpGet);
				  HttpEntity httpEntity = httpResponse.getEntity();
				  is = httpEntity.getContent();
			  }
		  }catch (UnsupportedEncodingException e) {
			  e.printStackTrace();
		  }catch (ClientProtocolException e) {
			  e.printStackTrace ();
		  }catch (IOException e) {
			  e.printStackTrace();
		  }
          
		  try {
			  BufferedReader reader = new BufferedReader(new InputStreamReader(
					  is, "iso-8859-1"),8);
			  StringBuilder sb = new StringBuilder();
			  String line = null;
			  while ((line = reader.readLine()) != null) {
				  sb.append(line + "\n");
			  }
			  is.close();
			  json = sb.toString();
			  } catch (Exception e) {
				  Log.e("Buffer Error", "Error converting result" + e.toString());
			  }
		// try parse the string to a JSON object
		  /*try { jObj = new JSONObject(json.substring(3));
		  
		  }	catch (JSONException e) {
			  Log.e("JSON PArser", "Error Parsing data" + e.toString());
		  }
		  Log.d("Json", jObj.toString());*/
		  
		  //check
		  try {
		        jObj = new JSONObject(json);
		    } catch (Exception e) {
		        Log.e("JSON Parser", "Error parsing data " + e.toString());
		        try {
		            jObj = new JSONObject(json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1));
		        } catch (Exception e0) {
		            Log.e("JSON Parser0", "Error parsing data [" + e0.getMessage()+"] "+json);
		            Log.e("JSON Parser0", "Error parsing data " + e0.toString());
		            try {
		                jObj = new JSONObject(json.substring(1));
		            } catch (Exception e1) {
		                Log.e("JSON Parser1", "Error parsing data [" + e1.getMessage()+"] "+json);
		                Log.e("JSON Parser1", "Error parsing data " + e1.toString());
		                try {
		                    jObj = new JSONObject(json.substring(2));
		                } catch (Exception e2) {
		                    Log.e("JSON Parser2", "Error parsing data [" + e2.getMessage()+"] "+json);
		                    Log.e("JSON Parser2", "Error parsing data " + e2.toString());
		                    try {
		                        jObj = new JSONObject(json.substring(3));
		                    } catch (Exception e3) {
		                        Log.e("JSON Parser3", "Error parsing data [" + e3.getMessage()+"] "+json);
		                        Log.e("JSON Parser3", "Error parsing data " + e3.toString());
		                    }
		                }
		            }
		        }
		    }
		  return jObj;
	  }
  }
