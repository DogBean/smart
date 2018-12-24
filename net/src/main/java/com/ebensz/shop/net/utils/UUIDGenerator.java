package com.ebensz.shop.net.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

//生成UUID的函数，生成规则：
//   0、1、2、3、| 4、5、6 、7 | 8 、9、10、11
//   time        | machine     | inc
//
//
public class UUIDGenerator {

	private static AtomicInteger atomicInteger;

	static {
		if (atomicInteger == null) {
			atomicInteger = new AtomicInteger();
		}
		atomicInteger.set(0);
	}

	public static String generator() {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(intToHexString((int) (System.currentTimeMillis() / 1000)));

		final int machinePiece;// 机器码块
		final int processPiece;// 进程块
		StringBuilder sb = new StringBuilder();

		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface ni = e.nextElement();
				sb.append(ni.toString());
			}
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		machinePiece = sb.toString().hashCode() << 16; // 将得到所有接口的字符串进行取散列值

		int processId = new java.util.Random().nextInt();

		ClassLoader loader = UUIDGenerator.class.getClassLoader();
		int loaderId = loader != null ? System.identityHashCode(loader) : 0;

		sb = new StringBuilder();
		sb.append(Integer.toHexString(processId));
		sb.append(Integer.toHexString(loaderId));
		processPiece = sb.toString().hashCode() & 0xFFFF;

		stringBuilder.append(intToHexString(machinePiece | processPiece));

		stringBuilder.append(intToHexString(atomicInteger.incrementAndGet()));

		return stringBuilder.toString().toLowerCase();
	}

	private static String intToHexString(int s) {
		String hex = Integer.toHexString(s);
		int l = hex.length();
		String append = "00000000";
		if (l < 8) {
			hex = append.substring(0, 8 - l) + hex;
		}
		return hex;
	}
}