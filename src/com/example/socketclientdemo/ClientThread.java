
package com.example.socketclientdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

public class ClientThread implements Runnable {
    // ���̸߳������Socket
    private Socket s;

    private Handler handler;

    // ���߳��������Socket����Ӧ��������
    BufferedReader br = null;

    public ClientThread(Socket s, Handler handler) throws IOException {
        this.s = s;
        this.handler = handler;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run() {
        try {
            String content = null;
            // ���϶�ȡSocket�������е����ݡ�
            while ((content = br.readLine()) != null) {
                // ÿ���������Է�����������֮�󣬷�����Ϣ֪ͨ���������ʾ������
                Message msg = new Message();
                msg.what = 0x123;
                msg.obj = content;
                handler.sendMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
