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
import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeoutException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Designer implements Runnable {
benchmark_var bb;

    public Designer(benchmark_var b) {
        this.bb = b;
    }
    //testing runnable function
    public void run() {
        //render();
        
    }

    // Render task update publishing
    public void render() {
        String exchangeDirect = "Exchange_1";
        String Message = "Render Task Done!";
        String Key1 = "Designer_Intern";

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection con = factory.newConnection()) {

            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");

            System.out.println("DESIGNER: Message sent to Intern");
            
            Random rand = new Random();
            int int_random = rand.nextInt(4);
            try {
                Thread.sleep(int_random*1000);
            } catch (Exception e) {
            }
            
            chan.basicPublish(exchangeDirect, Key1, false, null, Message.getBytes());
            new_card();

        } catch (Exception e) {
        };
    }
    
    // new SD Card consuming function (Object)
    public void new_card(){
    try {
            String exchangeDirect = "Exchange_1";
            String Key1 = "ProjectManager_Designer";
            ConnectionFactory factory = new ConnectionFactory();
            Connection con = factory.newConnection();
            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");
            String queueName = chan.queueDeclare().getQueue();
            chan.queueBind(queueName, exchangeDirect, Key1);
            chan.basicConsume(queueName, (x, msg) -> {
                //String m = new String(msg.getBody(), "UTF-8");
                 byte[] byteArray = msg.getBody();
        try {
            SD_Card b = (SD_Card) deserialize(byteArray);
            
            System.out.println("DESIGNER: received SD Card ");
            System.out.println("DESIGNER: SD Card Label: " + b.Task);
            
            if (!bb.closing_time) {
          new Thread(new Intern()).start();
          try{Thread.sleep(1000);} catch (Exception E){}
          new Thread(new ProjectManager()).start();
          render();
          //new Thread(new Designer()).start();
           
            }
            

        }
         catch (Exception e) {}
                //if (closing_time){
                    try {
                    chan.close();
                    con.close();
                    
                    studio_close();
                            
                } catch (Exception e) {}
                //}
                

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
    
    
    // Test another message to be sent to a Studio class
    public void studio_close() {
        String exchangeDirect = "Exchange_1";
        String Message = "WORK IS DONE FOR THE DAY!";
        String Key1 = "Designer_Studio";

        ConnectionFactory factory = new ConnectionFactory();
        try (Connection con = factory.newConnection()) {

            Channel chan = con.createChannel();
            chan.exchangeDeclare(exchangeDirect, "direct");

            System.out.println("DESIGNER: Message sent to Studio");
            
            
            chan.basicPublish(exchangeDirect, Key1, false, null, Message.getBytes());
          

        } catch (Exception e) {
        };
    }
    
    // Deserailization of the SD card object
    public Object deserialize(byte[] byteArray) throws IOException, ClassNotFoundException, TimeoutException {
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
