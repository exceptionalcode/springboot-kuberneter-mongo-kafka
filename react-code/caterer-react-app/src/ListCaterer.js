import React from "react";


class ListCaterer extends React.Component{
    constructor(){
      super();
      this.state={
        caterer:null
      }
    }
  
    componentDidMount(){
      fetch('http://localhost:8088/api/caterer/all').then((response)=>{
        response.json().then((result)=>{
            console.log(result);
            this.setState({caterer:result})
        })
      })
    }
  
    render(){
      return(
        <div className="App">
        <h1>
          Fetch All caterer for Hunza Consulting 
        </h1>
        <center>
          <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Caterer Name</th>
              <th>City Name</th>
              <th>Max Guest</th>
              <th>Mobile Number</th>
             
            </tr>
          </thead> <tbody>{
          this.state.caterer ?
          this.state.caterer.map((item,i)=>
          <tr key={i}>
          <td>{ item.id }</td>
          <td>{ item.name }</td>
          <td>{ item.location.cityName }</td>
          <td>{ item.capacity.maxGuest }</td>
          <td>{ item.contactInfo.mobileNumber }</td>
        </tr>
          ): <tr><td colSpan="5">Loading...</td></tr>
        }
         </tbody>
         </table>
         </center>
        </div>
      )
    }
  }
  

export default ListCaterer;