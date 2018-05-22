package com.voice.applicetion;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.voice.applicetion.bean.Image;
import com.voice.applicetion.bean.bean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity implements EventListener {
    protected TextView txtResult;
    protected Button btn, stop;
    private static String DESC_TEXT = "精简版语音识别";
    private EventManager asr;
    private boolean logTime = true;
    private boolean enableOffline = false; // 测试离线命令词，需要改成true
    private ImageView imageView;
    private  String path;
    com.voice.applicetion.bean.Image DataBean;
    Image.DataBean dataBean1;
    String vvvv;
    @SuppressLint("HandlerLeak")
    private void start() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        String event = null;
        event = SpeechConstant.ASR_START; // 替换成测试的event

        if (enableOffline) {
            params.put(SpeechConstant.DECODER, 2);
        }
        params.put(SpeechConstant.ACCEPT_AUDIO_VOLUME, false);
        String json = "dsadsa"; // 可以替换成自己的json
        json = new org.json.JSONObject(params).toString(); // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0);
    }




    private void loadOfflineEngine() {
        Map<String, Object> params = new LinkedHashMap<String, Object>();
        params.put(SpeechConstant.DECODER, 2);
        params.put(SpeechConstant.ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH, "assets://baidu_speech_grammar.bsg");
        asr.send(SpeechConstant.ASR_KWS_LOAD_ENGINE, new org.json.JSONObject(params).toString(), null, 0, 0);

    }

    private void unloadOfflineEngine() {
        asr.send(SpeechConstant.ASR_KWS_UNLOAD_ENGINE, null, null, 0, 0); //
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPermission();
        asr = EventManagerFactory.create(this, "asr");
        asr.registerListener(this); //  EventListener 中 onEvent方法
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
                Toast.makeText(MainActivity.this, "请开始说话", Toast.LENGTH_SHORT).show();
            }
        });
        if (enableOffline) {
            loadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stops();
                Toast.makeText(MainActivity.this, "识别完成", Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this)
                        .load(""+vvvv)
                        .into(imageView);
            }
        });

    }
    private void stops()   {
        String textname = txtResult.getText().toString();
        Toast.makeText(this, "" + textname, Toast.LENGTH_SHORT).show();
        String url = "https://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=%E7%8C%AB&cl=&lm=&ie=utf-8&oe=utf-8&adpicid=&st=&z=&ic=&word=" + textname;
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 path = response.request().url().toString();
                String jsonstring=HttpUtils.getJsonContent(path);
                Log.i("aaa",""+path);
                Gson gson=new Gson();
                DataBean=gson.fromJson(jsonstring,Image.class);
                 dataBean1 = DataBean.getData().get(0);
                vvvv=dataBean1.getThumbURL().toString();
                Log.i("aaa",""+dataBean1.getThumbURL());
//                Intent intent=new Intent(MainActivity.this,MainActivity.class);
//                intent.putExtra("urlimage",""+dataBean1.getThumbURL());
//                startActivity(intent);
//                finish();
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        if (enableOffline) {
            unloadOfflineEngine(); // 测试离线命令词请开启, 测试 ASR_OFFLINE_ENGINE_GRAMMER_FILE_PATH 参数时开启
        }
    }
    //   EventListener  回调方法
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String logTxt = "name: " + name;
        if (params != null && !params.isEmpty()) {
            logTxt += " ;params :" + params;
            //            无反应程序
        }
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            if (params.contains("\"nlu_result\"")) {
                Toast.makeText(this, "12", Toast.LENGTH_SHORT).show();
                if (length > 0 && data.length > 0) {
                    logTxt += ", 语义解析结果：" + new String(data, offset, length);
                    Toast.makeText(this, "13", Toast.LENGTH_SHORT).show();
                }
            }
            printZhi("" + params.toString());
        } else if (data != null) {
            logTxt += " ;data length=" + data.length;
        }
    }
    private void printZhi(String text) {
        text += "\n";
        Gson gson = new Gson();
        bean bean = gson.fromJson(text, com.voice.applicetion.bean.bean.class);
        txtResult.setText(bean.getBest_result());
    }
    private void initView() {
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtResult.setText("");
        btn = (Button) findViewById(R.id.btn);
        stop = findViewById(R.id.stop);
        imageView = findViewById(R.id.mimageview);
        txtResult.setText(DESC_TEXT + "\n");
    }
    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        ArrayList<String> toApplyList = new ArrayList<String>();

        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                // 进入到这里代表没有权限.

            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // 此处为android 6.0以上动态授权的回调，用户自行实现。
    }

}
