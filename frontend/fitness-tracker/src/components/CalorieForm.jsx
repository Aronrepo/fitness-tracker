import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import Notification from './Notification';
import { Box, Grid, Item, Typography } from '@mui/material';
import Cookies from 'js-cookie';
import DailyBarchart from './DailyBarchart';
import SearchBar from './SearchBar';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import {
    Add,
    SignalCellularConnectedNoInternet0BarSharp,
} from '@mui/icons-material';

const CalorieForm = () => {
    const [calories, setCalories] = useState(0);
    const [foodType, setFoodType] = useState('');
    const [open, setOpen] = useState(false);
    const [dailyCalorieInfos, setDailyCalorieInfos] = useState([
        {
            requiedCalorie: 0,
            dailyCalorieConsumption: 0,
        },
    ]);
    const [duration, setDuration] = useState('daily');
    const jwtToken = Cookies.get('jwtToken');

    const handleCaloriesChange = (event) => {
        setCalories(event.target.value);
    };
    const handleFoodTypeChange = (event) => {
        setFoodType(event.target.value);
    };

    const handleClick = () => {
        setOpen(true);
    };

    const handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        setOpen(false);
    };

    const fetchDailyCalories = () => {
        return fetch(`/analyze?duration=${duration}`, {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        }).then((res) => res.json());
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch('/calories/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({
                    foodType: foodType,
                    calories: calories,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to post calories.');
            }
            fetchDailyCalories().then((listedMeals) => {
                setDailyCalorieInfos(listedMeals);
                console.log(listedMeals);
            });
            const data = await response.json();
            console.log(data);
        } catch (error) {
            console.error('Error posting calories:', error);
        }
    };
    const Item = styled(Paper)(({ theme }) => ({
        backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
        ...theme.typography.body2,
        padding: theme.spacing(1),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    }));
    return (
        <>
            <Box flex={5} p={{ xs: 0, md: 6 }}>
                <Grid container spacing={2}>
                    <Grid container spacing={2}>
                        <Grid item xs={7}>
                            <Grid item xs={10}>
                                <Typography>
                                    <h5>Your Daily Summary</h5>
                                </Typography>
                            </Grid>
                            <form
                                className='calorie-form'
                                onSubmit={handleSubmit}
                            >
                                <SearchBar />
                                <label htmlFor='calories'>
                                    Enter Calories:
                                </label>
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

                                <Notification
                                    open={open}
                                    onClose={handleClose}
                                    message='Posted a meal'
                                />
                            </form>
                        </Grid>
                        <Grid item xs={7}>
                            <Grid item xs={4}>
                                <Typography><h5>Daily Barchart</h5></Typography>
                            </Grid>
                            <DailyBarchart listedMeals={dailyCalorieInfos} />
                        </Grid>
                    </Grid>
                </Grid>
            </Box>
        </>
    );
};

export default CalorieForm;
