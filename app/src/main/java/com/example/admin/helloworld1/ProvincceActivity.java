package com.example.admin.helloworld1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProvincceActivity extends AppCompatActivity {

    private int[] cids = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private String currentlevel="provincce";
    private int pid=0;

    //private List<String> data2=new ArrayList();
    private int[] pids = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    private List<String> data=new ArrayList<>();
    private TextView textView;
    private ListView listView;

    //private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.textView = (TextView) findViewById(R.id.acd);
        this.listView = (ListView) findViewById(R.id.list_view);

        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener((parent,view,position,id)-> {
            Log.i("点击了哪一个", "" + position + ":" + ProvincceActivity.this.pids[position] + ":" + ProvincceActivity.this.data.get(position));
            pid = ProvincceActivity.this.pids[position];
            currentlevel = "city";
            getData(adapter);
/*
                Intent intent = new Intent(ProvincceActivity.this,CityActivity.class);
                intent.putExtra("pid",ProvincceActivity.this.pids[position]);

                if (currentlevel=="city"){
                    intent.putExtra("cid",cids[position]);
                }
                startActivity(intent);
*/

        });

        //this.button=(Button)findViewById(R.id.Buttom);
        //this.button.setOnClickListener((v)->{
          //  startActivity(new Intent(ProvincceActivity.this,CityActivity.class));
        //});

        getData(adapter);
    }

    private void getData(ArrayAdapter<String> adapter) {
        String weatherUrl =currentlevel=="city"? "http://guolin.tech/api/china"+pid:"http://guolin.tech/api/china/";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();

               parseJSONObject(responseText);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                       // textView.setText(responseText);
                        adapter.notifyDataSetChanged();
                    }
                });

            }


        });
    }

    private void parseJSONObject(String responseText) {
        JSONArray jsonArray=null;
        this.data.clear();
        try{
            jsonArray = new JSONArray(responseText);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject =null;
                jsonObject=jsonArray.getJSONObject(i);
                this.data.add(jsonObject.getString("name"));
                if(currentlevel=="city"){
                    this.cids[i]=jsonObject.getInt("id");
                }else {
                    this.pids[i] = jsonObject.getInt("id");
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
