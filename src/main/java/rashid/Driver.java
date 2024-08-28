package rashid;

import javax.jms.*;
import javax.naming.InitialContext;

public class Driver {

    public static void main(final String[] args) throws Exception {
        Connection connection = null;
        InitialContext initialContext = null;
        try {
            // Step 1. Create an initial context to perform the JNDI lookup.
            initialContext = new InitialContext();

            // Step 2. Perform a lookup on the queue
            Queue queue = (Queue) initialContext.lookup("queue/exampleQueue");

            // Step 3. Perform a lookup on the Connection Factory
            ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

            // Step 4.Create a JMS Connection
            connection = cf.createConnection();

            // Step 5. Create a JMS Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Step 6. Create a JMS Message Producer
            MessageProducer producer = session.createProducer(queue);

            // Step 7. Create a Text Message
            TextMessage message = session.createTextMessage("This is a not text message");

            System.out.println("Sent message: " + message.getText());

            // Step 8. Send the Message
            producer.send(message);

//             Step 9. Create a JMS Message Consumer
            MessageConsumer messageConsumer = session.createConsumer(queue);

            // Step 10. Start the Connection
            connection.start();

            // Step 11. Receive the message
            TextMessage messageReceived = (TextMessage) messageConsumer.receive(10000);

            System.out.println("Received message: " + messageReceived.getText());
        } finally {
            // Step 12. Be sure to close our JMS resources!
            if (initialContext != null) {
                initialContext.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}