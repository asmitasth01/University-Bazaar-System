package example.com.bazaar.bean;

/**
 * Created by TheRealShrey on 11/30/16.
 */

public class ExchangeInfo {
    private String itemDescription;
    private String itemQuantity;
    private String exchange_ItemURL;
    private String senderUserName;
    private String receiveUserName;

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getExchange_ItemURL() {
        return exchange_ItemURL;
    }

    public void setExchange_ItemURL(String Exchange_ItemURL) {
        this.exchange_ItemURL = Exchange_ItemURL;
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getReceiveUserName() {
        return receiveUserName;
    }

    public void setReceiveUserName(String receiveUserName) {
        this.receiveUserName = receiveUserName;
    }


}
