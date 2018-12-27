package com.ebensz.shop.net.utils;


import com.ebensz.shop.net.socket.PacketDataInputStream;
import com.ebensz.shop.net.socket.PacketDataOutputStream;
import com.google.gson.Gson;

import java.io.*;

public class Packet {
    private byte version;
    private byte type;
    private byte reply;
    private int cmd;
    private String id;
    private String name;
    private byte[] content;

    public static final int CMD_LOGIN = 100;
    public static final int CMD_LOGOUT = 101;
    public static final int CMD_PLAYURL = 200;
    public static final int CMD_PLAYSONE = 201;
    public static final int CMD_GETPLAYLIST = 202;
    public static final int CMD_GETCURRENTPLAYING = 203;
    public static final int CMD_SETPLAYMODE = 300;
    public static final int CMD_SETCHILDLOCK = 301;
    public static final int CMD_SETEARLIGHT = 302;
    public static final int CMD_SETLEDLIGHT = 303;
    public static final int CMD_SETVOLUME = 304;
    public static final int CMD_PLAY = 305;
    public static final int CMD_PAUSE = 306;
    public static final int CMD_PLAYNEXT = 307;
    public static final int CMD_PLAYPREV = 308;
    public static final int CMD_GETPLAYMODE = 400;
    public static final int CMD_GETCHILDLOCKSTATUS = 401;
    public static final int CMD_GETEARLIGHTSTATUS = 402;
    public static final int CMD_GETLEDLIGHTSTATUS = 403;
    public static final int CMD_GETVOLUME = 404;
    public static final int CMD_GETBATTERY = 405;
    public static final int CMD_GETPLAYSTATUS = 406;
    public static final int CMD_GETNEWESTMESSAGE = 500;

    public static final byte[] PING = new byte[]{'p','i','n','g'};
    public static final byte[] PONG = new byte[]{'p','o','n','g'};

    public static final String NAME_LOGIN = "1";
    public static final String NAME_LOGOUT = "logout";
    public static final String NAME_PLAYURL = "playURL";
    public static final String NAME_PLAYSONE = "playSone";
    public static final String NAME_GETPLAYLIST = "getPlayList";
    public static final String NAME_GETCURRENTPLAYING = "getCurrentPlaying";
    public static final String NAME_SETPLAYMODE = "setPlayMode";
    public static final String NAME_SETCHILDLOCK = "setChildLock";
    public static final String NAME_SETEARLIGHT = "setEarLight";
    public static final String NAME_SETLEDLIGHT = "setLedLight";
    public static final String NAME_PLAY = "play";
    public static final String NAME_PAUSE = "pause";
    public static final String NAME_PLAYNEXT = "playNext";
    public static final String NAME_PLAYPREV = "playPrev";
    public static final String NAME_SETVOLUME = "setVolume";
    public static final String NAME_GETPLAYMODE = "getPlayMode";
    public static final String NAME_GETCHILDLOCKSTATUS = "getChildLockStatus";
    public static final String NAME_GETEARLIGHTSTATUS = "getEarLightStatus";
    public static final String NAME_GETLEDLIGHTSTATUS = "getLedLightStatus";
    public static final String NAME_GETVOLUME = "getVolume";
    public static final String NAME_GETBATTERY = "getBattery";
    public static final String NAME_GETPLAYSTATUS = "getPlayStatus";
    public static final String NAME_GETNEWESTMESSAGE = "getNewestMessage";

    public static final byte TYPE_APP = 1;
    public static final byte TYPE_MACHINE = 2;
    public static final byte TYPE_SERVER = 3;
    public static final byte TYPE_WEB = 4;

    private final static int HEADER_LEN = 72;

    public Packet() {
        this.id = UUIDGenerator.generator();
        this.version = 1;
        this.reply = 0;
        this.type = TYPE_APP;
    }

    public static Packet parseBytes(byte[] bytes) throws IOException {
        Packet packet = new Packet();

        InputStream bin = new ByteArrayInputStream(bytes);
        try {
            PacketDataInputStream in = new PacketDataInputStream(bin);
            packet.version = in.readByte();
            packet.type = in.readByte();
            packet.reply = in.readByte();
            in.skipBytes(1);
            packet.cmd = in.readInt();
            packet.id = in.readString(32);
            packet.name = in.readString(32);
            packet.content = new byte[bytes.length - HEADER_LEN];
            in.read(packet.content);
        } finally {
            bin.close();
        }

        return packet;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getReply() {
        return reply;
    }

    public void setReply(byte reply) {
        this.reply = reply;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public byte[] toBytes() {
        int length = HEADER_LEN;
        if (content != null) {
            length += content.length;
        }
        ByteArrayOutputStream bout = new ByteArrayOutputStream(length);

        try {
            PacketDataOutputStream out = new PacketDataOutputStream(bout);
            out.writeByte(version);
            out.writeByte(type);
            out.writeByte(reply);
            out.writeByte(0);
            out.writeInt(cmd);
            out.writeString(id, 32);
            out.writeString(name, 32);
            if (content != null) {
                out.write(content);
            }

            return bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }



    public void getCurrentPlaying(byte[] bytes) throws IOException {
//        ByteArrayInputStream intputStream = new ByteArrayInputStream(bytes);
//        PacketDataInputStream pint = new PacketDataInputStream(intputStream);
//        pint.read(bytes,0, 32);
//        pout.writeString(song,32);
//        String list,String song
//        this.content = outputStream.toByteArray();
//        outputStream.close();
    }

    public void getPlayMode(int mode) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(mode);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getChildLockStatus(int status) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(status);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getEarLightStatus(int status) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(status);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getLedLightStatus(int status) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(status);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getVolume(int volume) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(volume);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getBattery(int battery) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(battery);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getPlayStatus(int status) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeInt(status);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

    public void getNewestMessage(long syncKey) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
        pout.writeLong(syncKey);
        this.content = outputStream.toByteArray();
        outputStream.close();
    }

}
