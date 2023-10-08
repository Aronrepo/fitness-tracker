import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import './CalorieForm.css';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Notification from './Notification';
import { Box, Stack, Skeleton } from '@mui/material';
import Cookies from 'js-cookie';
export default function Profile() {
    const [gender, setGender] = useState(0);
    const [weight, setWeight] = useState(0);
    const [birthDate, setBirthDate] = useState(0);
    const [height, setHeight] = useState(0);
    const [duration, setDuration] = useState('week');
    const [open, setOpen] = useState(false);
    const [userProfileInfos, setUserProfileInfos] = useState({});

    const jwtToken = Cookies.get('jwtToken');

    const handleGenderChange = (event) => {
        setGender(event.target.value);
    };

    const handleWeightChange = (event) => {
        setWeight(event.target.value);
    };

    const handleBirthDateChange = (event) => {
        setBirthDate(event.target.value);
    };

    const handleHeightChange = (event) => {
        setHeight(event.target.value);
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

    const fetchUserProfile = () => {
        return fetch(`/user`, {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        }).then((res) => res.json());
    };


    ///////////////// még endpointot kell csinálni 
    useEffect(() => {
        /* fetchUserProfile().then((userProfileInfos) => {
            setUserProfileInfos(userProfileInfos);
        }); */
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`/user`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: `Bearer ${jwtToken}`,
                },
                body: JSON.stringify({
                    gender: gender,
                    weight: weight,
                    height: height,
                    birthDate: birthDate,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to post calories.');
            }
        } catch (error) {
            console.error('Error posting infos:', error);
        }
    };

    return (
        <Box flex={9} p={{ xs: 0, md: 2 }}>
            <form className='calorie-form' onSubmit={handleSubmit}>
                <label htmlFor='gender'>Select gender:</label>
                <select
                    id='gender'
                    value={gender}
                    onChange={handleGenderChange}
                    className='calorie-input'
                >
                    <option value=''>Select</option>
                    <option value='Women'>Women</option>
                    <option value='Men'>Men</option>
                </select>

                <label htmlFor='weight'>Enter weight:</label>

                <input
                    defaultValue={
                        userProfileInfos ? userProfileInfos.weight : null
                    }
                    type='number'
                    id='weight'
                    value={weight}
                    onChange={handleWeightChange}
                    className='food-input'
                />
                <label htmlFor='birthDate'>Enter Birth Date:</label>

                <input
                    defaultValue={
                        userProfileInfos ? userProfileInfos.birthDate : null
                    }
                    type='date'
                    id='birthDate'
                    value={birthDate}
                    onChange={handleBirthDateChange}
                    className='food-input'
                />
                <label htmlFor='height'>Enter height:</label>

                <input
                    defaultValue={
                        userProfileInfos ? userProfileInfos.height : null
                    }
                    type='number'
                    id='height'
                    value={height}
                    onChange={handleHeightChange}
                    className='food-input'
                />

                <Button
                    variant='contained'
                    type='submit'
                    className='submit-button'
                    onClick={handleClick}
                >
                    Update Profile
                </Button>

                <Notification
                    open={open}
                    onClose={handleClose}
                    message='Updated Profile'
                />
            </form>
        </Box>
    );
}
