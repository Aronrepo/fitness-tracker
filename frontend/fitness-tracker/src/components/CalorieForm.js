import React, { useState } from 'react';
import Button from '@mui/material/Button';
import './CalorieForm.css';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
const CalorieForm = () => {
    const [calories, setCalories] = useState(0);
    const [foodType, setFoodType] = useState('');
    // const [foodItems, setFoodItems, calories] = useCaloryCounted([]);
    const [open, setOpen] = React.useState(false);

    const handleCaloriesChange = (event) => {
        setCalories(event.target.value);
    };
    const handleFoodTypeChange = (event) => {
        setFoodType(event.target.value);
    };

    //Notify parts
    const handleClick = () => {
        setOpen(true);
    };

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    const action = (
        <React.Fragment>
            <Button color='secondary' size='small' onClick={handleClose}>
                UNDO
            </Button>
            <IconButton
                size='small'
                aria-label='close'
                color='inherit'
                onClick={handleClose}
            >
                <CloseIcon fontSize='small' />
            </IconButton>
        </React.Fragment>
    );

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('/calories/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    foodType: foodType,
                    calories: calories,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to post calories.');
            }

            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error posting calories:', error);
        }
    };

    return (
        <form className='calorie-form' onSubmit={handleSubmit}>
            <label htmlFor='calories'>Enter Calories:</label>
            <input
                type='number'
                id='calories'
                value={calories}
                onChange={handleCaloriesChange}
                className='calorie-input'
            />

            <label htmlFor='foodType'>Enter food:</label>

            <input
                type='text'
                id='foodType'
                value={foodType}
                onChange={handleFoodTypeChange}
                className='food-input'
            />

            <Button
                variant='contained'
                type='submit'
                className='submit-button'
                onClick={handleClick}
            >
                Post Calories
            </Button>

            <Snackbar
                open={open}
                autoHideDuration={4000}
                onClose={handleClose}
                message='Posted a meal'
                action={action}
            />
            
        </form>
    );
};

export default CalorieForm;
