package com;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GateBayDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(GateBayDataApplication.class, args);
		   try {
				new Subscriber();
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
