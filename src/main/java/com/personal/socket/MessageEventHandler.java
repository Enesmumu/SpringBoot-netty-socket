package com.personal.socket;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

@Component
public class MessageEventHandler {
	public static SocketIOServer socketIoServer;
	static ArrayList<UUID> listClient = new ArrayList<>();
	static final int limitSeconds = 60;

	@SuppressWarnings("static-access")
	@Autowired
	public MessageEventHandler(SocketIOServer server) {
		this.socketIoServer = server;
	}

	@OnConnect
	public void onConnect(SocketIOClient client) throws InterruptedException {
		listClient.add(client.getSessionId());
		System.out.println("客户端:" + client.getSessionId() + "已连接");
		for (int i = 0; i < 10; i++) {
			System.out.println("发送" + i);
			sendBuyLogEvent();
		}
	}

	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		System.out.println("客户端:" + client.getSessionId() + "断开连接");
	}

	@OnEvent(value = "messageevent")
	public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
		System.out.println("发来消息：" + data.getMsgContent());
		socketIoServer.getClient(client.getSessionId()).sendEvent("messageevent", "back data");
	}

	public static void sendBuyLogEvent() { // 这里就是向客户端推消息了
		String dateTime = String.valueOf(new Date().getTime());
		for (UUID clientId : listClient) {
			if (socketIoServer.getClient(clientId) == null)
				continue;
			System.out.println("发送" + dateTime);
			socketIoServer.getClient(clientId).sendEvent("chatevent", dateTime);
		}
	}
}
