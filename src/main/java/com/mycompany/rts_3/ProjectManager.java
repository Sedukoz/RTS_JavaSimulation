package com.mycompany.rts_3;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


// Testing runnable function
public class ProjectManager implements Runnable{
    public void run(){
        task_update();
        
    }
    
    // function to consume a message from the Intern
    public void task_update() {
        try {
            String exchangeDirect = "Exchange_1";
            String Key1 = "Intern_ProjectManager";
            ConnectionFactory factory = new ConnectionFactory();
            Connection con = factory.newConnection();
            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");
            String queueName = chan.queueDeclare().getQueue();
            chan.queueBind(queueName, exchangeDirect, Key1);
            chan.basicConsume(queueName, (x, msg) -> {
                String m = new String(msg.getBody(), "UTF-8");
                System.out.println("ProjectManager: Task Update received : " + m);
                Card_Send();
            try {
                    chan.close();
                    con.close();
                } catch (Exception e) {}
            },
                    x -> {
                    }
            );

        } catch (IOException ex) {
            Logger.getLogger(Intern.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            Logger.getLogger(Intern.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    // Function to send an SD card object to the designer
    public void Card_Send(){
        String exchangeDirect = "Exchange_1";
        String Message = "Render Task Done!";
        String Key1 = "ProjectManager_Designer";

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection con = factory.newConnection()) {

            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");
            
            SD_Card b = new SD_Card();
            byte[] byteArray = getByteArray(b);
            
            Random rand = new Random();
            int int_random = rand.nextInt(4);
            try {
                Thread.sleep(int_random*1000);
            } catch (Exception e) {
            }

            chan.basicPublish(exchangeDirect, Key1, false, null, byteArray);
            System.out.println("PROJECT MANAGER: SD CARD SENT ");

        } catch (Exception e) {
        };
        
        
    }
        
      public static byte[] getByteArray(SD_Card b) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(b);
        return out.toByteArray();
    }  
        
        
        
}
