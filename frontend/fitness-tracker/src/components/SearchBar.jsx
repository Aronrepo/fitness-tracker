import TextField from '@mui/material/TextField';
import Autocomplete from '@mui/material/Autocomplete';
import React, { useState, useEffect, useRef } from 'react';
import { Box, Stack } from '@mui/material';
import ReactDOM from 'react-dom';

export default function SearchBar({onChange}) {
    const [options, setOptions] = useState([]);
    const [selectedFoodType, setSelectedFoodType] = useState();
    const [nutrientOptions, setNutrientOptions] = useState();

    const getData = (searchTerm) => {
        fetch(
            'https://trackapi.nutritionix.com/v2/search/instant?query=' +
                searchTerm,
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                    'x-app-id': 'cac56224',
                    'x-app-key': '9013b573d4d851716cdcc94515cbe4a3',
                },
            }
        )
            .then((res) => {
                if (!res.ok) {
                    throw new Error('Network response was not ok');
                }
                return res.json();
            })
            .then((myJson) => {
                console.log(myJson);
                const updatedOptions = myJson?.common.map((p) => p.food_name);
                setOptions(myJson.common);
            })
            .catch((error) => {
                console.error('Error fetching data:', error);
            });
    }; // kibányászni

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

    const onInputChange = (_, value, reason) => {
        console.log('input lefut');
        if (value) {
            getData(value);
        } else {
            setOptions([]);
        }
    };

    useEffect(() => {
        if (selectedFoodType) {
            getNutrients(selectedFoodType.food_name, setNutrientOptions);
            onChange?.(selectedFoodType)
        }
    }, [selectedFoodType]);

    return (
        <div>
            <Autocomplete
                id='combo-box-demo'
                options={options}
                onChange={(_, foodType) => setSelectedFoodType(foodType)}
                onInputChange={onInputChange}
                getOptionLabel={(option) => `${option.food_name} `}
                renderOption={(props, option) => (
                    <Box component="li" sx={{ '& > img': { mr: 2, flexShrink: 0 } }} {...props}>
                        <img
                            key={option.food_name}
                            loading='lazy'
                            width='20'
                            src={option.photo.thumb}
                            alt={option.food_name}
                        />
                        {option.food_name}
                    </Box>
                )}
                style={{ width: 300 }}
                renderInput={(params) => (
                    <TextField
                        {...params}
                        label='search for food to get calorie'
                        variant='outlined'
                    />
                )}
                noOptionsText={"didn't find yet "}
            />
        </div>
    );
}

const getNutrients = (searchTerm, setNutrientOptions) => {
    fetch('https://trackapi.nutritionix.com/v2/natural/nutrients', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'x-app-id': 'cac56224',
            'x-app-key': '9013b573d4d851716cdcc94515cbe4a3',
        },
        body: `{"query":"${searchTerm}"}`,
    })
        .then((res) => {
            if (!res.ok) {
                throw new Error('Network response was not ok');
            }
            return res.json();
        })
        .then((myJson) => {
            console.log(myJson);

            setNutrientOptions(myJson);
        })
        .catch((error) => {
            console.error('Error fetching data:', error);
        });
};
