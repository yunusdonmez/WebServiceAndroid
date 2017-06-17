package com.genesis.yazlab;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

public class BroadcastService extends Service {
	private static final String TAG = "BroadcastService";
	public static final String BROADCAST_ACTION = "com.genesis.yazlab.displayevent";
	private final Handler handler = new Handler();
	 public static List<Ev> evList=new ArrayList<Ev>();
	Intent intent;
	JSONArray dizi2=new JSONArray();
	
	@Override
	public void onCreate() {
		super.onCreate();
		
    	intent = new Intent(BROADCAST_ACTION);	
	}
	
    @Override
    public void onStart(Intent intent, int startId) {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 1000); // 1 second
   
    }

    private Runnable sendUpdatesToUI = new Runnable() {
    	public void run() {
    		DisplayLoggingInfo();    		
    	    handler.postDelayed(this, 3000); // 3 seconds
    	}
    };    
    
    private void DisplayLoggingInfo() {
    	Log.d(TAG, "entered DisplayLoggingInfo");

    	AsyncHttpClient client=new AsyncHttpClient();
	    client.get("http://192.168.43.69:8080/WebService/webservice/getExample/getListValue",new TextHttpResponseHandler() 
	    {

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseBody, Throwable error) {
				// TODO Auto-generated method stub
				super.onFailure(statusCode, headers, responseBody, error);
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,String jsonSonuc) {
				
				try {
					JSONArray dizi=new JSONArray(jsonSonuc);
					dizi2=dizi;
					//JSONObject p = new JSONObject(jsonSonuc);
					/*JSONArray d=p.getJSONArray("listem");
				   */
					/*evList.clear();
				for(int i=0;i<dizi.length();i++){
					Ev evler=new Ev();
					
					evler.setEvId(dizi.getJSONObject(i).getInt("evId"));
					evler.setEvIl(dizi.getJSONObject(i).getString("evIl"));
					evler.setEvEmlak(dizi.getJSONObject(i).getString("evEmlak"));
					evler.setEvAlan(dizi.getJSONObject(i).getInt("evAlan"));
					evler.setOdaSayisi(dizi.getJSONObject(i).getInt("odaSayisi"));
					evler.setBinaYasi(dizi.getJSONObject(i).getInt("binaYasi"));
					evler.setBulKat(dizi.getJSONObject(i).getInt("bulKat"));
					evler.setFiyat(dizi.getJSONObject(i).getDouble("fiyat"));
	                evler.setEvAciklama(dizi.getJSONObject(i).getString("evAciklama"));
	                JSONArray temp=dizi.getJSONObject(i).getJSONArray("resim");
	               for(int j=0;j<temp.length();j++){
	            	   evler.setResim("http://10.40.242.15:8080/WebService"+temp.getString(j));
	               }
	                evList.add(evler);
	                //Toast.makeText(getApplicationContext(),"boyut:"+"evid "+dizi.getJSONObject(i).getInt("evId") ,Toast.LENGTH_LONG).show();		
				}*/
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    	
	    }
	    
	    );
	    
    	intent.putExtra("list",dizi2+"");
	    
    	sendBroadcast(intent);
    }
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {		
        handler.removeCallbacks(sendUpdatesToUI);		
		super.onDestroy();
	}	
}
