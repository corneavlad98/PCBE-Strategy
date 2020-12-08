package jms;

import javax.jms.JMSException;


public class TestApp {

//    public static void main(String[] args) throws Exception
//    {
//        Publisher publisher1 = new Publisher();
//        publisher1.create("publisher-multipleConsumers", "multipleConsumers.t");
//
//        Subscriber subscriber1 = new Subscriber();
//        Subscriber subscriber2 = new Subscriber();
//        subscriber1.create("subscriber1-multipleConsumers", "multipleConsumers.t");
//        subscriber2.create("subscriber2-multipleConsumers", "multipleConsumers.t");
//
//       try {
//            publisher1.sendName("Mark", "Wolf");
//
//            String greeting1 = subscriber1.getGreeting(1000);
//            String greeting2 = subscriber2.getGreeting(1000);
//
//            System.out.println("\n" + greeting1);
//            System.out.println(greeting2);
//
//            if(greeting1.equals("Hello Mark Wolf!") && greeting2.equals("Hello Mark Wolf!")) {
//                System.out.println("correct greetings");
//            }
//            else {
//                System.out.println("incorrect greetings");
//            }
//
//            publisher1.closeConnection();
//            subscriber1.closeConnection();
//            subscriber2.closeConnection();
//        } catch (JMSException e) {
//            System.out.println ("a JMS Exception occurred");
//        }
//    }
}
