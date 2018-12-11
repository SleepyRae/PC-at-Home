package com.oyp.shutdown;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ShutDownServer {

	static ServerSocket serverSocket = null;// ����socket
	static DataInputStream dataInput = null;// ������
	static DataOutputStream dataOutput = null;// �����

	public static void main(String[] args) {
		try {
			// ����30000�˿�
			serverSocket = new ServerSocket(30000);
			System.out.println("ShutDownServer is listening  port 30000............");

			while (true) {
				// ��ȡ�ͻ����׽���
				Socket clientSocket = serverSocket.accept();
				String send_msg = "";
				try {
					// ��ȡ������,��ȡ�ͻ��˴���������
					dataInput = new DataInputStream(
							clientSocket.getInputStream());
					String msg = dataInput.readUTF();
					System.out.println(msg);
					// �ж����룬������Ӧ�Ĳ���
					dataOutput = new DataOutputStream(
							clientSocket.getOutputStream());
					if ("shutdown".equals(msg)) {
						shutdown();
						// ������Ϣ��Android��
						send_msg = "60���ػ� ";
					} else if ("reboot".equals(msg)) {
						reboot();
						send_msg = "60�������";
					} else if ("cancel".equals(msg)) {
						cancel();
						send_msg = "ȡ���ػ�������";
					}
				} catch (Exception e) {
				} finally {
					try {
						if (dataOutput != null) {
							dataOutput.writeUTF(send_msg);
							dataOutput.close();
						}
						dataInput.close();
						// �ر�����
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

	// �ػ�
	private static void shutdown() throws IOException {
		Runtime.getRuntime().exec("shutdown -s -t 60");
		System.out.println("shutdown ,60 seconds later ");
	}

	// ����
	private static void reboot() throws IOException {
		Runtime.getRuntime().exec("shutdown -r -t 60");
		System.out.println("reboot ,60 seconds later ");
	}

	// ȡ���ػ�������
	private static void cancel() throws IOException {
		Runtime.getRuntime().exec("shutdown -a");
		System.out.println("cancel shutdown or restart");
	}

}
