package com.example.parcelwizrd.Model;

public class UserModel {
    public String Username;
    public String Email;
    public String FirstName;
    public String LastName;
    public String ID;
    public String PhoneNumber;
    public String Address;
    public String City;
    public String State;
    public String Country;

    /*public String getUsername(){
        return Username;
    }
    public void setUsername(String username){
        this.Username=username;
    }
    public String getEmail(){
        return Email;
    }
    public void setEmail(String email){
        this.Email=email;
    }
    public String getFirstName(){
        return FirstName;
    }
    public void setFirstName(String firstName){
        this.FirstName= firstName;
    }
    public String getLastName(){
        return LastName;
    }
    public void setLastName(String lastName){
        this.LastName=lastName;
    }
    public String getID(){
        return ID;
    }
    public void setID(String id){
        this.ID=id;
    }
    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.PhoneNumber=phoneNumber;
    }
    public String getAddress(){
        return Address;
    }
    public void setAddress(String address){
        this.Address=address;
    }
    public String getCity(){
        return City;
    }
    public void setCity(String city){
        this.City=city;
    }
    public String getState(){
        return State;
    }
    public void setState(String state){
        this.State=state;
    }
    public String getCountry(){
        return Country;
    }
    public void setCountry(String country){
        this.Country=country;
    }
     */



   public UserModel(){

    }

    public UserModel(String Username, String Email, String FirstName, String LastName, String ID, String PhoneNumber, String Address, String City, String State, String Country){
        this.Username= Username;
        this.Email= Email;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.ID=ID;
        this.PhoneNumber=PhoneNumber;
        this.Address=Address;
        this.City=City;
        this.State=State;
        this.Country=Country;
    }
}
