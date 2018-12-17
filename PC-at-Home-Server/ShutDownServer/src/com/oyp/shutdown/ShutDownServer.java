package com.oyp.shutdown;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ShutDownServer {

	static ServerSocket serverSocket = null;// 服务socket
	static DataInputStream dataInput = null;// 输入流
	static DataOutputStream dataOutput = null;// 输出流

	public static void main(String[] args) {
		try {
			// 监听30000端口
			serverSocket = new ServerSocket(30000);
			System.out.println("ShutDownServer is listening  port 30000............");

			while (true) {
				// 获取客户端套接字
				Socket clientSocket = serverSocket.accept();
				String send_msg = "";
				try {
					// 获取输入流,读取客户端传来的数据
					dataInput = new DataInputStream(
							clientSocket.getInputStream());
					String msg = dataInput.readUTF();
					System.out.println(msg);
					// 判断输入，进行相应的操作
					dataOutput = new DataOutputStream(
							clientSocket.getOutputStream());
					if ("shutdown".equals(msg)) {
						shutdown();
						// 发送消息回Android端
						send_msg = "60秒后关机 ";
					} else if ("reboot".equals(msg)) {
						reboot();
						send_msg = "60秒后重启";
					} else if ("cancel".equals(msg)) {
						cancel();
						send_msg = "取消关机或重启";
					}
				} catch (Exception e) {
				} finally {
					try {
						if (dataOutput != null) {
							dataOutput.writeUTF(send_msg);
							dataOutput.close();
						}
						dataInput.close();
						// 关闭连接
						clientSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 关机
	private static void shutdown() throws IOException {
		Runtime.getRuntime().exec("shutdown -s -t 60");
		System.out.println("shutdown ,60 seconds later ");
	}

	// 重启
	private static void reboot() throws IOException {
		Runtime.getRuntime().exec("shutdown -r -t 60");
		System.out.println("reboot ,60 seconds later ");
	}

	// 取消关机或重启
	private static void cancel() throws IOException {
		Runtime.getRuntime().exec("shutdown -a");
		System.out.println("cancel shutdown or restart");
	}

}
