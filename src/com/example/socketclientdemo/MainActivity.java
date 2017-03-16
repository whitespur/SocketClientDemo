package com.example.socketclientdemo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
    //private TextView toggleTextView;
    private EditText serverIP;
    private Button toggleButton;
    private Button volumnUpButton;
    private Button volumnDownButton;
    private Button sendButton;
    private EditText inputEditText;
    private OutputStream os ;  
    private Context mContext;
    private String TAG="Client_Main";
    private int status = 0;
    //private String IP = "10.7.92.212" ; 
    private String IP = "10.7.93.120" ; 
    private int Ports = 58888;          
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toggleTextView = (TextView)findViewById(R.id.toggleTV);
        serverIP = (EditText)findViewById(R.id.serverIP);
        toggleButton = (Button)findViewById(R.id.toggleButton);
        volumnUpButton = (Button)findViewById(R.id.VolumnUpButton);
        volumnDownButton = (Button)findViewById(R.id.VolumnDownButton);
        sendButton = (Button)findViewById(R.id.sendButton);
        inputEditText = (EditText)findViewById(R.id.inputET);
        
        IP=serverIP.getText().toString();
        Log.i(TAG,"input ip is "+serverIP.getText().toString());
        
        mContext = getApplicationContext();
        toggleButton.setOnClickListener(new Button.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               
                
                try {
                    IP=serverIP.getText().toString();
                    Log.i(TAG, "connect ip is "+IP);
                    
                     int timeout = 5000;       
                     InetSocketAddress  isa;
                    Socket s = new Socket();   
                    isa = new InetSocketAddress(IP, Ports);     
                    s.connect(isa, timeout);
                    
                    //Socket s = new Socket(IP , Ports);  
                    // 客户端启动ClientThread线程不断读取来自服务器的数据  
                    if(null != s){
                        new Thread( new ClientThread(s, handler)).start();
                        os = s.getOutputStream();  
                    }else{
                        Log.e(TAG,"socket s is null");
                    }
                   
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }                        
               
                Toast. makeText( mContext, "Connect Success", Toast.LENGTH_SHORT).show(); 
            }
        });
        volumnUpButton.setOnClickListener(new Button.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                inputEditText.setText("VOLUME+");
                try {
                    Log.i(TAG, "inputEditText is "+inputEditText.getText().toString());
                    os .write((inputEditText .getText().toString() + "\r\n").getBytes( "utf-8" ));
                    inputEditText.setText("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
                
            }
        });
        volumnDownButton.setOnClickListener(new Button.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                inputEditText.setText("VOLUME-");
                try {
                    Log.i(TAG, "inputEditText is "+inputEditText.getText().toString());
                    os .write((inputEditText .getText().toString() + "\r\n").getBytes( "utf-8" ));
                    inputEditText.setText("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } 
            }
        });
        sendButton.setOnClickListener(new Button.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                try {
                    Log.i(TAG, "inputEditText is "+inputEditText.getText().toString());
                    os .write((inputEditText .getText().toString() + "\r\n").getBytes( "utf-8" ));
                    inputEditText.setText("");
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }  
            }
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    private Handler handler = new Handler()  
    {  
        @Override  
        public void handleMessage(Message msg)  
        {  
            // 如果消息来自于子线程  
            if (msg.what == 0x123)  
            {  
                //TODO:
                Log.i(TAG,"Client handlerMessage is "+msg.obj.toString());
                // 将读取的内容追加显示在文本框中  
                //show .append(msg.obj .toString());  
            }  
        }  
    };  
}
