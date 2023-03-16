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
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Intern implements Runnable {
// Test runnable function
    public void run() {
        relay();
    }

    // function to consume message from Designer
    public void relay() {
        try {
            String exchangeDirect = "Exchange_1";
            String Key1 = "Designer_Intern";
            ConnectionFactory factory = new ConnectionFactory();
            Connection con = factory.newConnection();
            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");
            String queueName = chan.queueDeclare().getQueue();
            chan.queueBind(queueName, exchangeDirect, Key1);
            chan.basicConsume(queueName, (x, msg) -> {
                String m = new String(msg.getBody(), "UTF-8");
                System.out.println("INTERN: Render notification acquired " + m);
                second_relay();
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

    // Function to relay message to Project manager
    public void second_relay() {
        String exchangeDirect = "Exchange_1";
        String Message = "Render Task Done!";
        String Key1 = "Intern_ProjectManager";

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection con = factory.newConnection()) {

            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");
            
            Random rand = new Random();
            int int_random = rand.nextInt(4);
            try {
                Thread.sleep(int_random*1000);
            } catch (Exception e) {
            }

            chan.basicPublish(exchangeDirect, Key1, false, null, Message.getBytes());
            System.out.println("INTERN: Message delivered to Project Manager");

        } catch (Exception e) {
        };
    }
}
