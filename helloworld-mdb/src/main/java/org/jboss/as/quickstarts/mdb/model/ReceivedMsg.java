package org.jboss.as.quickstarts.mdb.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="RECEIVED_MSG")
@NamedQuery(name="ReceivedMsg.findAll", query="SELECT r FROM ReceivedMsg r")
public class ReceivedMsg implements Serializable {

  private static final long serialVersionUID = 776453409086177338L;

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="ID")
  private BigDecimal id;
  
  @Column(name="BODY")
  private String body;

  @Column(name="CORRELATION_ID")
  private String correlationId;

  @Column(name="MESSAGE_ID")
  private String messageId;

  @Column(name="TIMESTAMP")
  private BigDecimal timestamp;
  
  public BigDecimal getId() {
    return id;
  }
  public void setId(BigDecimal id) {
    this.id = id;
  }
  
  public String getBody() {
    return body;
  }
  public void setBody(String body) {
    this.body = body;
  }
  
  public String getCorrelationId() {
    return correlationId;
  }
  public void setCorrelationId(String correlationId) {
    this.correlationId = correlationId;
  }
  
  public String getMessageId() {
    return messageId;
  }
  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }
  
  public BigDecimal getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(BigDecimal timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((body == null) ? 0 : body.hashCode());
    result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
    result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ReceivedMsg other = (ReceivedMsg) obj;
    if (body == null) {
      if (other.body != null)
        return false;
    } else if (!body.equals(other.body))
      return false;
    if (correlationId == null) {
      if (other.correlationId != null)
        return false;
    } else if (!correlationId.equals(other.correlationId))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (messageId == null) {
      if (other.messageId != null)
        return false;
    } else if (!messageId.equals(other.messageId))
      return false;
    if (timestamp == null) {
      if (other.timestamp != null)
        return false;
    } else if (!timestamp.equals(other.timestamp))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return "ReceivedMsg [id=" + id + ", body=" + body + ", correlationId=" + correlationId
      + ", messageId=" + messageId + ", timestamp=" + timestamp + "]";
  }

}
