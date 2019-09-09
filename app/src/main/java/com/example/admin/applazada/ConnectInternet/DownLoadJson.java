package com.example.admin.applazada.ConnectInternet;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//hamdowloadjson khi truyen duong dan
public class DownLoadJson extends AsyncTask<String,Void,String> {
    String duongdan;// duong dan nhan vao table cua csdl
    List<HashMap<String,String>> attrs; // nhan du lieu post len server dang keyvalue
    boolean method;// xem day la phuong thuc dang get hay dang Post
    public  DownLoadJson(String duongdan){
        this.duongdan=duongdan;
        method=true;
    }
    public DownLoadJson(String duongdan, List<HashMap<String,String>> attrs){
        this.duongdan=duongdan;
        this.attrs=attrs;
        method=false;

    }
    @Override
    protected String doInBackground(String... strings) {
        String data="";
        StringBuilder dulieu=new StringBuilder("");
        try {
            URL url= new URL(duongdan);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();

           // methodGet(httpURLConnection);
            if(method==true){
                data=methodGet(httpURLConnection);
            }
            else  data=methodPost(httpURLConnection);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        Log.d("du lieu input",data);
        return data.toString();

    }
    private String methodGet(HttpURLConnection connection){
        String data="";
        InputStream inputStream=null;
        StringBuilder dulieu=new StringBuilder("");
        try {
            connection.connect();
            inputStream=connection.getInputStream();
            InputStreamReader reader=new InputStreamReader(inputStream);
            BufferedReader bufferedReader=new BufferedReader(reader);
            String line="";
            while((line=bufferedReader.readLine())!=null){
                dulieu.append(line);
            }
            data=dulieu.toString();
            bufferedReader.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    private String methodPost(HttpURLConnection connection){
        String data="";
        InputStream inputStream=null;
        OutputStream outputStream=null;
        String key="";
        String value="";
        try {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            Uri.Builder uri=new Uri.Builder();
            int count =attrs.size();
            for(int i=0;i<count;i++){
                for(Map.Entry<String,String> values:attrs.get(i).entrySet()){// su dung Map de gui nhieu du lieu dang kevyvalue
                    key=values.getKey();
                    value=values.getValue();
                    uri.appendQueryParameter(key,value);
                }

            }
            connection.connect();
            String dulieuPost=uri.build().getEncodedQuery();
            outputStream=connection.getOutputStream();
            OutputStreamWriter writer=new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter=new BufferedWriter(writer);
            bufferedWriter.write(dulieuPost);
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();
            outputStream.close();
            data=methodGet(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
