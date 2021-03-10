package uk.co.idv.otp.entities.delivery;

public interface DeliveryRequest {

    String getDeliveryMethodType();

    String getDeliveryMethodValue();

    String getMessageText();

}
