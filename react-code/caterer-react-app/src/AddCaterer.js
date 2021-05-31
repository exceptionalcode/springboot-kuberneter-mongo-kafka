import React, { Component } from "react";

class AddCaterer extends Component{
    constructor(){
        super();
       this.state={
           name:'react caterer',
           location:{
                cityName:'new jersey',
                streetNameNumber:'street099',
                postalCode:452001
            },
          capacity:{
                minGuest:1,
                maxGuest:5
            },
            contactInfo:{
                phoneNumber:123123,
                mobileNumber:123123,
                emailAddress:'react@some.com'
            }
        }
       }
      
    save(){
        fetch('http://localhost:8088/api/caterer',{
            method:'Post',
            headers:{
                'Content-Type':'application/json'
            },
            body:JSON.stringify(this.state)
        }).then((result)=>{
            result.json().then((response)=>{
                if(result.ok){
                alert('Caterer Saved');
            }
            })
        })
    }
    
    render() {
        return (
                <div>
                <center>
                <h1>Add Caterer Details</h1>
                <div>
                <input type='text'  name='name' onChange={(e)=>{this.setState({name:e.target.value})}}
                placeholder='Caterer Name'/> <br/>
                <input type='text'  name='cityName' onChange={(e)=>{this.setState({location:e.target.value})}}
                placeholder='City Name'/> <br/>
                <input type='text'  name='streetNameNumber' onChange={(e)=>{this.setState({streetNameNumber:e.target.value})}}
                placeholder='Street Name and No'/> <br/>
                <input type='text'  name='postalCode' onChange={(e)=>{this.setState({postalCode:e.target.value})}}
                placeholder='Postal Code'/> <br/>
                <input type='text'  name='minGuest' onChange={(e)=>{this.setState({minGuest:e.target.value})}}
                placeholder='Min Guest'/> <br/>
                <input type='text'  name='maxGuest' onChange={(e)=>{this.setState({maxGuest:e.target.value})}}
                placeholder='Max Guest'/> <br/>
                <input type='text'  name='phoneNumber' onChange={(e)=>{this.setState({phoneNumber:e.target.value})}}
                placeholder='Phone No'/> <br/>
                <input type='text'  name='mobileNumber' onChange={(e)=>{this.setState({mobileNumber:e.target.value})}}
                placeholder='Mobile No'/> <br/>
                <input type='text'  name='emailAddress' onChange={(e)=>{this.setState({emailAddress:e.target.value})}}
                placeholder='Email Address'/> <br/>
                <button onClick={()=>{this.save()}}>Save New Caterer</button>
                </div>
                </center>
            </div>
            );
        }
    }


export default AddCaterer;