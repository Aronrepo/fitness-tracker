import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import React, { useState, useEffect, useRef } from 'react';
import { Box, Stack } from '@mui/material';
import ReactDOM from "react-dom";

export default function App() {
  const [options, setOptions] = useState([]);
  const [optionsPicture, setOptionsPicture] = useState([]);
  const previousController = useRef();

  const getData = (searchTerm) => {
    if (previousController.current) {
      previousController.current.abort();
    }
    const controller = new AbortController();
    const signal = controller.signal;
    previousController.current = controller;
    fetch("https://trackapi.nutritionix.com/v2/search/instant?query="+ searchTerm, {
      method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'x-app-id': 'cac56224',
                'x-app-key': '9013b573d4d851716cdcc94515cbe4a3',
            },
    })
    .then((res) => {
      if (!res.ok) {
        throw new Error("Network response was not ok");
      }
      return res.json();
    })
    .then((myJson) => {
        console.log(myJson);
      const updatedOptions = myJson?.common.map((p) =>  p.food_name);
      setOptions(myJson.common);
      
      
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
  };

  const renderOption = (option) => (
    <div>
      <img
        src={option.photo.thumb}
        alt={option.food_name}
        style={{ marginRight: '10px', width: '40px', height: '40px' }}
      />
      {option.food_name}
    </div>
  );


  const onInputChange = (event, value, reason) => {
    if (value) {
      getData(value);
    } else {
      setOptions([]);
    }
  };

  return (
    <div>
      <Autocomplete
        id="combo-box-demo"
        options={options}
        onInputChange={onInputChange}
        getOptionLabel={(option) => `${option.food_name} `}
        style={{ width: 300 }}
        renderInput={(params) => (
          <TextField {...params} label="search for food to get calorie" variant="outlined" />
        )}
        noOptionsText={"didn't find yet "}
      />
    </div>
  );
}

const top100Films = {"foods": [
  {
      }
]}
