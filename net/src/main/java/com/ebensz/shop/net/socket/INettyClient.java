package com.ebensz.shop.net.socket;

import com.ebensz.shop.net.utils.Packet;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/25
 */
public interface INettyClient {

    public static final String HOST = "192.168.4.134";
    public static int PORT = 7667;

    void connect(String host, int port);//1. 建立连接

    void sendMessage(int mt, String msg, long delayed);//2. 发送消息

    void sendLogin();//2. 登陆

    void addDataReceiveListener(OnDataReceiveListener listener);//3. 为不同的请求添加监听器

    interface OnDataReceiveListener {
        void onDataReceive(Packet packet);//接收到数据时触发
    }

    interface OnConnectStatusListener {
        void onDisconnected();//连接异常时触发
    }
}
