import React, { Component } from 'react';
import './App.css';

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {temp: 0, desc: '', icon: ''}
  }

  componentDidMount() {
    fetch('http://api.openweathermap.org/data/2.5/weather?q=London&units=Metric&APIkey=YOUR_KEY')
  	.then(response => response.json()) 
  	.then(responseData => {
    	this.setState({ 
   	    temp: responseData.main.temp,
        desc: responseData.weather[0].description,
        icon: responseData.weather[0].icon          
 	    }); 
	  });
  }
  
  render() {
    const imgSrc = 'http://openweathermap.org/img/w/' + this.state.icon + '.png';

    return (
      <div className="App">
        <p>Temperature: {this.state.temp}</p>
        <p>Description: {this.state.desc}</p>
        <img src={imgSrc} />
      </div>
    );
  }
}

export default App;
