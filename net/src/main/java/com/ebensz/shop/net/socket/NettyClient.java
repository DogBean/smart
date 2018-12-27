package com.ebensz.shop.net.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.ebensz.shop.net.utils.ApiConstants;
import com.ebensz.shop.net.utils.Constants;
import com.ebensz.shop.net.utils.Packet;
import com.ebensz.shop.net.utils.SPUtils;
import com.ebensz.shop.net.utils.SignUtil;
import com.ebensz.shop.net.utils.UUIDGenerator;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteOrder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.Constant;

/**
 * @Description:
 * @Author Guoyong.Lin
 * @Time 2018/12/25
 */
public class NettyClient implements INettyClient {
    private final String TAG = NettyClient.class.getSimpleName();
    private static NettyClient mInstance;
    private Bootstrap bootstrap;
    private Channel channel;
    private String host;
    private int port;
    private HandlerThread workThread = null;
    private Handler mWorkHandler = null;
    private NettyClientHandler nettyClientHandler;
    private final String ACTION_SEND_TYPE = "action_send_type";
    private final String ACTION_SEND_MSG = "action_send_msg";
    private final String ACTION_SEND_PING = "action_send_ping";
    private final int MESSAGE_INIT = 0x1;
    private final int MESSAGE_CONNECT = 0x2;
    private final int MESSAGE_SEND = 0x3;
    private final int MESSAGE_LOGIN = 0x4;
    private Handler.Callback mWorkHandlerCallback = new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_INIT: {
                    NioEventLoopGroup group = new NioEventLoopGroup();
                    bootstrap = new Bootstrap();
                    bootstrap.channel(NioSocketChannel.class);
                    bootstrap.group(group);
                    bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 2048, 0, 4, 0, 4, true));
                            p.addLast(new LengthFieldPrepender(ByteOrder.LITTLE_ENDIAN, 4, 0, false));
                            p.addLast(new ByteArrayDecoder());
                            p.addLast(new ByteArrayEncoder());
                            p.addLast(nettyClientHandler);
                        }
                    });
                    break;
                }
                case MESSAGE_CONNECT: {
                    try {
                        if (TextUtils.isEmpty(host) || port == 0) {
                            Exception exception = new Exception("Netty host | port is invalid");
                            throw exception;
                        }
                        channel = bootstrap.connect(new InetSocketAddress(host,
                                port)).sync().channel();
                    } catch (Exception e) {
                        Log.e(TAG, "connect failed  " + e.getMessage() + "  reconnect delay");
                        e.printStackTrace();
                        sendReconnectMessage();
                    }
                    break;
                }
                case MESSAGE_SEND: {
                    String sendMsg = msg.getData().getString(ACTION_SEND_MSG);
                    byte[] ping = msg.getData().getByteArray(ACTION_SEND_PING);
                    Packet packet = new Gson().fromJson(sendMsg, Packet.class);
                    int cmd = msg.getData().getInt(ACTION_SEND_TYPE);
                    try {
                        if (channel != null && channel.isOpen()) {
                            if (ping != null && ping.length > 0) {
                                Log.e(TAG, "send ping");
                                channel.writeAndFlush(ping).sync();
                            } else {
                                channel.writeAndFlush(packet.toBytes()).sync();
                                Log.e(TAG, "send succeed " + constructMessage(sendMsg));
                            }
                        } else {
                            throw new Exception("channel is null | closed");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "send failed " + e.getMessage());
                        sendReconnectMessage();
                        e.printStackTrace();
                    } finally {
//                        if (Constant.MT.HAND_SHAKE.getType() == mt)
//                            sendMessage(mt, sendMsg, Constant.DELAY_HAND_SHAKE);
                    }
                    break;
                }

                case MESSAGE_LOGIN: {
                    try {
                        if (channel != null && channel.isOpen()) {
                            Packet packet = new Packet();
                            packet.setCmd(Packet.CMD_LOGIN);
                            packet.setName(Packet.NAME_LOGIN);
                            packet.setId(UUIDGenerator.generator());

                            ByteArrayOutputStream bout = new ByteArrayOutputStream();
                            PacketDataOutputStream out = new PacketDataOutputStream(bout);
                            String token = "1";//SPUtils.getInstance().getString(Constants.TOKEN);
                            String nonc = UUIDGenerator.generator();
                            String sign = SignUtil.getSign(token, nonc);
                            out.writeString(token, 32);
                            out.writeString(nonc, 32);
                            out.writeString(sign, 32);
                            packet.setContent(bout.toByteArray());

                            channel.writeAndFlush(packet.toBytes());
                            Log.d(TAG, "login succeed");
                        } else {
                            throw new Exception("channel is null | closed");
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "send failed " + e.getMessage());
                        sendReconnectMessage();
                        e.printStackTrace();
                    } finally {
//                        if (Constant.MT.HAND_SHAKE.getType() == mt)
//                            sendMessage(mt, sendMsg, Constant.DELAY_HAND_SHAKE);
                    }
                }
            }
            return true;
        }
    };

    private NettyClient() {
        init();
    }

    public synchronized static NettyClient getInstance() {
        if (mInstance == null)
            mInstance = new NettyClient();
        return mInstance;
    }

    private void init() {
        workThread = new HandlerThread(NettyClient.class.getName());
        workThread.start();
        mWorkHandler = new Handler(workThread.getLooper(), mWorkHandlerCallback);
        nettyClientHandler = new NettyClientHandler();
        nettyClientHandler.setConnectStatusListener(new OnConnectStatusListener() {
            @Override
            public void onDisconnected() {
                sendReconnectMessage();
            }
        });
        mWorkHandler.sendEmptyMessage(MESSAGE_INIT);
    }

    @Override
    public void connect(String host, int port) {
        this.host = host;
        this.port = port;
        mWorkHandler.sendEmptyMessage(MESSAGE_CONNECT);
    }

    @Override
    public void addDataReceiveListener(OnDataReceiveListener listener) {
        if (nettyClientHandler != null)
            nettyClientHandler.addDataReceiveListener(listener);
    }

    private void sendReconnectMessage() {
        mWorkHandler.sendEmptyMessageDelayed(MESSAGE_CONNECT, 1000);
    }

    @Override
    public void sendMessage(String msg, long delayed) {
        if (TextUtils.isEmpty(msg))
            return;
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = MESSAGE_SEND;
        bundle.putString(ACTION_SEND_MSG, msg);
        message.setData(bundle);
        mWorkHandler.sendMessageDelayed(message, delayed);
    }

    @Override
    public void ping(byte[] ping) {
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = MESSAGE_SEND;
        bundle.putByteArray(ACTION_SEND_PING, ping);
        message.setData(bundle);
        mWorkHandler.sendMessageDelayed(message, 0);
    }

    @Override
    public void sendLogin() {
        Message message = new Message();
        Bundle bundle = new Bundle();
        message.what = MESSAGE_LOGIN;
        mWorkHandler.sendMessageDelayed(message, 0);
    }

    private String constructMessage(String json) {
        String message = null;
        //与后台协议好，如何设置校验部分，然后和json一起发给服务器
        return json;
    }

}