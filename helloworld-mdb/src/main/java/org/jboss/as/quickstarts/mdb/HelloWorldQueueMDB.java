/*
 * JBoss, Home of Professional Open Source Copyright 2015, Red Hat, Inc. and/or its affiliates, and
 * individual contributors by the @authors tag. See the copyright.txt in the distribution for a full
 * listing of individual contributors. Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable
 * law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 * for the specific language governing permissions and limitations under the License.
 */
package org.jboss.as.quickstarts.mdb;

import java.math.BigDecimal;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.as.quickstarts.mdb.model.ReceivedMsg;
import org.jboss.ejb3.annotation.ResourceAdapter;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * A simple Message Driven Bean that asynchronously receives and processes the messages that are
 * sent to the queue.
 * </p>
 *
 * @author Serge Pagop (spagop@redhat.com)
 *
 */
@MessageDriven(name = "HelloWorldQueueMDB", activationConfig = {
  @ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/ivt/IVTQueue"),
  @ActivationConfigProperty(propertyName = "connectionFactoryLookup",
    propertyValue = "java:/jms/ivt/IVTCF"),
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
  @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "true")})
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@ResourceAdapter("wmq.jmsra")
public class HelloWorldQueueMDB implements MessageListener {

  // private final static Logger LOGGER = LogManager.getLogger(HelloWorldQueueMDB.class.toString());
  private final static Logger LOGGER = LoggerFactory.getLogger(HelloWorldQueueMDB.class);

  @PersistenceContext
  EntityManager entityManager;
  
  /**
   * @see MessageListener#onMessage(Message)
   */
  public void onMessage(Message rcvMessage) {
    try {
      ReceivedMsg receivedMsg = generatePersistentMsg(rcvMessage);
      if (rcvMessage instanceof TextMessage) {
        LOGGER.info("Received Message from queue: {}", receivedMsg);
      } else {
        LOGGER.warn("Message of wrong type: {}", rcvMessage.getClass().getName());
      }
      entityManager.persist(receivedMsg);
      LOGGER.info("Persisted Message: {}", receivedMsg);
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }

  private ReceivedMsg generatePersistentMsg(Message rcvMessage) throws JMSException {
    ReceivedMsg receivedMsg = new ReceivedMsg();

    receivedMsg.setCorrelationId(rcvMessage.getJMSCorrelationID());
    receivedMsg.setMessageId(rcvMessage.getJMSMessageID());
    receivedMsg.setTimestamp(new BigDecimal(rcvMessage.getJMSTimestamp()));

    if (rcvMessage instanceof TextMessage) {
      receivedMsg.setBody(((TextMessage) rcvMessage).getText());
    } else {
      receivedMsg.setBody(rcvMessage.getBody(String.class));
    }

    return receivedMsg;
  }
}
