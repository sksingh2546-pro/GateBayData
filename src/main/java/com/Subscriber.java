package com;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;

import com.google.gson.Gson;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client blocking API.
 */
public  class Subscriber implements MqttCallback {

    private final int qos = 1;
    private String topic = "kbeacon/publish/D03304015482";
    private MqttClient client;

  
    public Subscriber() throws MqttException {
        String host = String.format("tcp://%s:%d", "3.19.240.19", 1883);
        String username = "his";
        String password = "Cybershot@903";
        String clientId = "MQTT-Java-Example";
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setCleanSession(true);
        conOpt.setUserName(username);
        conOpt.setPassword(password.toCharArray());

        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        this.client.connect(conOpt);

        this.client.subscribe(this.topic, qos);
    }

    
/*    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
    }*/

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        try {
            new Subscriber();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
    }

    /**
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException 
     {

        Gson gson=new Gson();
        MessageModel messageMedel=gson.fromJson(new String(message.getPayload()), MessageModel.class);
       int count=0;
        if(messageMedel.getMsg().equals("advData")) {
        	

for(Device device:messageMedel.getObj()) {
	count++;

    try {
    char[] uuid=device.getUuid().trim().toCharArray();
    String battery="0";
    String cd="";
    String t1="0";
    String mId1="0";
    String mnId1="0";
    String mId2="0";
    String mnId2="0";
    String mcAddress="";
    String major_id="";
    String minor_id="";

    int bt=0,time1=0,dmnId2=0,dmId2=0;
    for(int i=0;i<uuid.length;i++) {
    	String first=String.valueOf(uuid[0]);
    	if(first.equals("7"))
    	{
    		if(uuid.length-3<i) 
    		{
    			battery+=String.valueOf(uuid[i]);
                System.out.println("count:-"+count);
                mcAddress=device.getDmac();
                System.out.println(device.getMajorID());
                System.out.println(device.getMinorID());
                System.out.println(device.getRefpower());
                System.out.println(device.getRssi());
                System.out.println(device.getTime());
                System.out.println(device.getType());
                System.out.println(device.getUuid());
                insertDeviceInform(device.getDmac(),String.valueOf(device.getMinorID())
                        ,String.valueOf(device.getMajorID()));
    		}
    	}
    	else
    		{
    		    if(i>=0&&i<2){
    		        cd+=String.valueOf(uuid[i]);
                }

    		    if(i>1&&i<4){
    		        t1+=String.valueOf(uuid[i]);
                }
    		    if(i>7&&i<12){
                    mnId2+=String.valueOf(uuid[i]);
                }
    		    if(i>3&&i<8){
                    mId2+=String.valueOf(uuid[i]);
                }
                major_id=String.valueOf(device.getMajorID());
                minor_id=String.valueOf(device.getMinorID());

    		}
    }
	  bt=Integer.parseInt(battery,16);
	  time1=Integer.parseInt(t1,16);
	  dmnId2=Integer.parseInt(mnId2,16);
	  dmId2=Integer.parseInt(mId2,16);

	  if(bt>0){
      insertDevice(bt,mcAddress);

      }
	  else{
          distancing(major_id,minor_id,time1,String.valueOf(dmId2),String.valueOf(dmnId2));
      }
    System.out.println("battery:"+bt);
     System.out.println("cd:"+cd);
     System.out.println("t1:"+time1);
     System.out.println("majorId2:"+dmnId2);
     System.out.println("minorId1:"+dmId2);
    }catch (Exception e) {
	System.out.println(e);
	}
}

        }
        

    }
    
    public static String insertDevice(int battery,String mac_address)
    {
		  String message="Unsuccessful";
	    	try {
	    	 CloseableHttpClient client = HttpClients.createDefault();
	    	 HttpPost httpPost = new HttpPost("http://192.168.0.127:8045/api/insertDeviceDAta");
		       JSONObject json = new JSONObject();
		       json.put("battery",battery);
		       json.put("mac_address",mac_address);
		       
		       StringEntity entity = new StringEntity(json.toString());
		       httpPost.setEntity(entity);//}
		       httpPost.setHeader("Accept", "application/json");
		       httpPost.setHeader("Content-type", "application/json");
		       CloseableHttpResponse response = client.execute(httpPost);
		       //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
		       if(response.getStatusLine().getStatusCode()==200) {
		    	   System.out.println("Successfull");
		    	   message="Successfull";
		    	  
		       }else {System.out.println("Unsuccessful");
		       message="Unsuccessful";
		       }
		       //.close();
		       client.close();
	    	}catch (Exception e) {
			System.out.println("server not Working");	
	    	}
			return message;
	    	
	    }

    public static String distancing(String major_id,String minor_id,int time,String major_id2,String minor_id2)
    {
        String message="Unsuccessful";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://192.168.0.127:8045/api/insertSocialDistancing");
            JSONObject json = new JSONObject();
            json.put("major_id",major_id);
            json.put("minor_id",minor_id);
            json.put("time",time);
            json.put("major_id2",major_id2);
            json.put("minor_id2",minor_id2);

            StringEntity entity = new StringEntity(json.toString());
            httpPost.setEntity(entity);//}
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
            if(response.getStatusLine().getStatusCode()==200) {
                System.out.println("Successfull");
                message="Successfull";

            }else {System.out.println("Unsuccessful");
                message="Unsuccessful";
            }
            client.close();
        }catch (Exception e) {
            System.out.println("server not Working");
        }
        return message;

    }public static String insertDeviceInform(String mac_address,String minor_id,String major_id)
    {
        String message="Unsuccessful";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://192.168.0.127:8045/api/insertQuarantine");
            JSONObject json = new JSONObject();
            json.put("mac_address",mac_address);
            json.put("minor_id",minor_id);
            json.put("major_id",major_id);

            StringEntity entity = new StringEntity(json.toString());
            httpPost.setEntity(entity);//}
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            CloseableHttpResponse response = client.execute(httpPost);
            //assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
            if(response.getStatusLine().getStatusCode()==200) {
                System.out.println("Successfull");
                message="Successfull";

            }else {System.out.println("Unsuccessful");
                message="Unsuccessful";
            }
            client.close();
        }catch (Exception e) {
            System.out.println("server not Working");
        }
        return message;

    }
}
