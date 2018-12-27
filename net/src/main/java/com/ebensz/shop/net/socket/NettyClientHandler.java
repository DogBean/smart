package com.ebensz.shop.net.socket;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ebensz.shop.net.utils.Packet;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Constant;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/25
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private final String TAG = "Netty";
    private INettyClient.OnConnectStatusListener statusListener;
    private List<INettyClient.OnDataReceiveListener> listeners = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //channelActive()方法将会在连接被建立并且准备进行通信时被调用。
        Log.d(TAG, "channel active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        byte[] bytes = (byte[]) msg;
        if (Arrays.equals(bytes, Packet.PONG)) {
            Log.e(TAG, "channelRead receive pong" );
        } else {
            //channelRead()方法是在数据被接收的时候调用。
            Packet packet = Packet.parseBytes(bytes);
            callListeners(packet);
            Log.d(TAG, "channelRead receive packet:" + (packet == null ? null : packet.toString()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //exceptionCaught()事件处理方法是当出现Throwable对象才会被调用，
        //即当Netty由于IO错误或者处理器在处理事件时抛出的异常时。
        //在大部分情况下，捕获的异常应该被记录下来并且把关联的channel给关闭掉。
        ctx.close();
        Log.e(TAG, "Unexpected exception from downstream : "
                + cause.getMessage());
        if (statusListener != null)//连接异常时触发onDisconnected()
            statusListener.onDisconnected();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
        Log.d(TAG, "channelReadComplete");
    }

    //遍历监听器List，触发拥有正确msgType 的OnDataReceiveListener，
    //回调 void onDataReceive(int mt, String json);方法
    private void callListeners(final Packet packet) {
        for (final INettyClient.OnDataReceiveListener listener : listeners)
            if (listener != null)
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {//主线程中进行
                        listener.onDataReceive(packet);
                    }
                });
    }

    //绑定OnDataReceiveListener
    public void addDataReceiveListener(INettyClient.OnDataReceiveListener listener) {
        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    //绑定OnConnectStatusListener
    public void setConnectStatusListener(INettyClient.OnConnectStatusListener listener) {
        statusListener = listener;
    }
}
