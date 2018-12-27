package com.ebensz.shop.net.utils;

import com.ebensz.shop.net.socket.PacketDataOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PushMessage {
	private String    id;
	private String    userId;
	private int		  cmd;
	private String    name;
	private byte[]    content = null;

	public static final int   CMD_LOGIN		   			=    100;
	public static final int   CMD_LOGOUT     			=    101;
	public static final int   CMD_PLAYURL				= 	 200;
	public static final int   CMD_PLAYSONE	    		=    201;
	public static final int   CMD_GETPLAYLIST			=    202;
	public static final int   CMD_GETCURRENTPLAYING  	= 	 203;
	public static final int   CMD_SETPLAYMODE		    =    300;
	public static final int   CMD_SETCHILDLOCK			=    301;
	public static final int   CMD_SETEARLIGHT			=    302;
	public static final int   CMD_SETVOLUME				=    303;
	public static final int   CMD_GETPLAYMODE			=    400;
	public static final int   CMD_GETCHILDLOCKSTATUS	= 	 401;
	public static final int   CMD_GETEARLIGHTSTATUS		= 	 402;
	public static final int   CMD_GETLEDLIGHTSTATUS		=	 403;
	public static final int   CMD_GETVOLUME				=	 404;
	public static final int	  CMD_GETBATTERY			=    405;
	public static final int	  CMD_GETNEWESTMESSAGE		=    406;

	public static final String   NAME_LOGIN		   			=    "login";
	public static final String   NAME_LOGOUT     			=    "logout";
	public static final String   NAME_PLAYURL				= 	 "playURL";
	public static final String   NAME_PLAYSONE	    		=    "playSone";
	public static final String   NAME_GETPLAYLIST			=    "getPlayList";
	public static final String   NAME_GETCURRENTPLAYING  	= 	 "getCurrentPlaying";
	public static final String   NAME_SETPLAYMODE		    =    "setPlayMode";
	public static final String   NAME_SETCHILDLOCK			=    "setChildLock";
	public static final String   NAME_SETEARLIGHT			=    "setEarLight";
	public static final String   NAME_SETVOLUME				=    "setVolume";
	public static final String   NAME_GETPLAYMODE			=    "getPlayMode";
	public static final String   NAME_GETCHILDLOCKSTATUS	= 	 "getChildLockStatus";
	public static final String   NAME_GETEARLIGHTSTATUS		= 	 "getEarLightStatus";
	public static final String   NAME_GETLEDLIGHTSTATUS		=	 "getLedLightStatus";
	public static final String   NAME_GETVOLUME				=	 "getVolume";
	public static final String	 NAME_GETBATTERY			=    "getBattery";
	public static final String	 NAME_GETNEWESTMESSAGE		=    "getNewestMessage";

	public PushMessage(){
	}

	public PushMessage(String id,String userId, int cmd,String name){
		this.id 	= id;
		this.userId = userId;
		this.cmd 	= cmd;
		this.name 	= name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getCmd() {
		return cmd;
	}

	public void setCmd(int cmd) {
		this.cmd = cmd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(byte[] context){
		this.content = context;
	}

	public byte[] getContent(){
		return content;
	}

	public void getCurrentPlaying(String list,String song) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		PacketDataOutputStream pout = new PacketDataOutputStream(outputStream);
		pout.writeString(list,32);
		pout.writeString(song,32);
		this.content = outputStream.toByteArray();
		outputStream.close();
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

//	@Override
//	public String toString() {
//		return JSON.toJSONString(this);
//	}
}
