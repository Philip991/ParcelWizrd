package com.example.parcelwizrd.Model;

public class RiderModel {
    public String Id;
    public String Username;
    public String Email;
    public String FirstName;
    public String LastName;
    public String PhoneNumber;
    public String Address;
    public String City;
    public String State;
    public String Country;
    public String VehicleMake;
    public String VehicleColor;
    public String VehicleRegNo;

    public RiderModel(){

    }
    public RiderModel(String Id,String Username, String Email, String FirstName, String LastName, String PhoneNumber, String Address, String City, String State, String Country, String VehicleMake, String VehicleColor, String VehicleRegNo){
        this.Id=Id;
        this.Username = Username;
        this.Email= Email;
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.PhoneNumber=PhoneNumber;
        this.Address=Address;
        this.City=City;
        this.State=State;
        this.Country=Country;
        this.VehicleMake=VehicleMake;
        this.VehicleColor=VehicleColor;
        this.VehicleRegNo=VehicleRegNo;
    }
}
