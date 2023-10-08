import React, { useState } from 'react';
import Button from '@mui/material/Button';
import './ActivityForm.css';

import Notification from '../Notification';
import { Box, Stack, Skeleton } from '@mui/material';
import Cookies from 'js-cookie';

const ActivityForm = () => {
    const [calories, setCalories] = useState(0);
    const [activity, setActivity] = useState('');
    const [open, setOpen] = React.useState(false);
    const jwtToken = Cookies.get('jwtToken');

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

    const handleCaloriesChange = (event) => {
        setCalories(event.target.value);
    };
    const handleActivityTypeChange = (event) => {
        setActivity(event.target.value);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('/activities/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({
                    "activity": activity,
                    "calories": calories,
                }),
            });

            if (!response.ok) {
                console.log(jwtToken);
                throw new Error('Failed to post activity.');
            }

            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error posting calories:', error);
        }
    };

    return (
        <Box flex={9} p={{ xs: 0, md: 2 }}>
            <form className='calorie-form' onSubmit={handleSubmit}>
                <label htmlFor='calories'>Enter Calories:</label>
                <input
                    type='number'
                    id='calories'
                    value={calories}
                    onChange={handleCaloriesChange}
                    className='input'
                />

                <label htmlFor='activity'>Enter your activity:</label>
                <input
                    type='string'
                    id='activity'
                    value={activity}
                    onChange={handleActivityTypeChange}
                    className='input'
                />
                <Button
                    variant='contained'
                    type='submit'
                    className='submit-button'
                    onClick={handleClick}
                >
                    Post Activity
                </Button>

                <Notification
                    open={open}
                    onClose={handleClose}
                    message='Posted an activity'
                />
            </form>
        </Box>
    );
};

export default ActivityForm;
