package com.example.parcelwizrd.Model;

public class UserOrderModel {
    public String pickUp;
    public String dropOff;
    public String pickUpFirstName;
    public String pickUpLastName;
    public String pickUpNumber;
    public String pickUpEmail;
    public String deliveryFirstName;
    public String deliveryLastName;
    public String deliveryNumber;
    public String deliveryEmail;

   /* public String getPickUp(){
        return pickUp;
    }
    public void setPickUp(String pickUp){
        this.pickUp =pickUp;
    }
    public String getDropOff(){
        return dropOff;
    }
    public void setDropOff(String dropOff){
        this.dropOff=dropOff;
    }
    public String getPickUpFirstName(){
        return pickUpFirstName;
    }
    public void setPickUpFirstName(String pickUpFirstName){
        this.pickUpFirstName=pickUpFirstName;
    }
    public String getPickUpLastName(){
        return pickUpLastName;
    }
    public void setPickUpLastName(String pickUpLastName){
        this.pickUpLastName=pickUpLastName;
    }
    public String getPickUpNumber(){
        return pickUpNumber;
    }
    public void setPickUpNumber(String pickUpNumber){
        this.pickUpNumber=pickUpNumber;
    }
    public String getPickUpEmail(){
        return pickUpEmail;
    }
    public void setPickUpEmail(String pickUpEmail){
        this.pickUpEmail=pickUpEmail;
    }
    public String getDeliveryFirstName(){
        return deliveryFirstName;
    }
    public void setDeliveryFirstName(String deliveryFirstName){
        this.deliveryFirstName=deliveryFirstName;
    }
    public String getDeliveryLastName(){
        return deliveryLastName;
    }
    public void setDeliveryLastName(String deliveryLastName){
        this.deliveryLastName=deliveryLastName;
    }
    public String getDeliveryNumber(){
        return deliveryNumber;
    }
    public void setDeliveryNumber(String deliveryNumber){
        this.deliveryNumber=deliveryNumber;
    }
    public String getDeliveryEmail(){
        return deliveryEmail;
    }
    public void setDeliveryEmail(String deliveryEmail){
        this.deliveryEmail=deliveryEmail;
    }

    */

    public UserOrderModel(){

    }

    public UserOrderModel(String pickUp, String dropOff, String pickUpFirstName, String pickUpLastName, String pickUpNumber, String pickUpEmail, String deliveryFirstName, String deliveryLastName, String deliveryNumber, String deliveryEmail){
        this.pickUp=pickUp;
        this.dropOff= dropOff;
        this.pickUpFirstName=pickUpFirstName;
        this.pickUpLastName=pickUpLastName;
        this.pickUpNumber=pickUpNumber;
        this.pickUpEmail=pickUpEmail;
        this.deliveryFirstName=deliveryFirstName;
        this.deliveryLastName=deliveryLastName;
        this.deliveryNumber=deliveryNumber;
        this.deliveryEmail=deliveryEmail;
    }

}

