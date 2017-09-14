package com.spring.common.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.ScheduledMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * @author chenhaiyan
 * @date 2016年4月5日
 */
@Component("mqQueueSender")
public class MqQueueSender {
    private static final Logger logger = LoggerFactory.getLogger(MqQueueSender.class);

    @Autowired(required = false)
    @Qualifier("jmsQueueTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 
     * @author chenhaiyan
     * @param queueName
     * @param message
     */
    public void send(String queueName, final String message) {
        logger.info("queue : " + queueName + "\nmessage : " + message);

        jmsTemplate.send(queueName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
    
    /**
     * 延迟投放
     * @author chenhaiyan
     * @param queueName
     * @param message
     * @param delayMillSeconds 延迟毫秒数
     */
    public void send(String queueName, final String message,final long delayMillSeconds) {
        logger.info("queue : " + queueName + "\nmessage : " + message);

        jmsTemplate.send(queueName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
            	Message msg = session.createTextMessage(message);
            	msg.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delayMillSeconds);
            	return msg;
            }
        });
    }
}
