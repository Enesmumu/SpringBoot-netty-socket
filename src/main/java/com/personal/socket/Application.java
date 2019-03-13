package com.personal.socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();

		config.setHostname("192.168.0.106"); // 部署到你的远程服务器正式发布环境时用服务器公网ip
		config.setPort(9099);
		final SocketIOServer server = new SocketIOServer(config);
		return server;
		/*
		 * config.setAuthorizationListener(new AuthorizationListener() {//类似过滤器
		 * 
		 * @Override public boolean isAuthorized(HandshakeData data) {
		 * //http://localhost:8081?username=test&password=test
		 * //例如果使用上面的链接进行connect，可以使用如下代码获取用户密码信息，本文不做身份验证 // String username =
		 * data.getSingleUrlParam("username"); // String password =
		 * data.getSingleUrlParam("password"); return true; } });
		 */
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
	}

}
