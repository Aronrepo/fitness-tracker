import React, { useState, useEffect } from 'react';
import Button from '@mui/material/Button';
import './Profile.css';
import Snackbar from '@mui/material/Snackbar';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';
import Notification from '../../components/Notification/Notification';
import { Box, Stack, Skeleton } from '@mui/material';
import Cookies from 'js-cookie';
export default function Profile() {
    const [gender, setGender] = useState(0);
    const [weight, setWeight] = useState(0);
    const [birthDate, setBirthDate] = useState('');
    const [height, setHeight] = useState(0);
    const [duration, setDuration] = useState('week');
    const [open, setOpen] = useState(false);
    const [userProfileInfos, setUserProfileInfos] = useState({});
    const [snackbarError, setSnackbarError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

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
        return fetch(`/user/`, {
            method: 'GET',
            headers: {
                Authorization: `Bearer ${jwtToken}`,
            },
        }).then((res) => res.json());
    };


    useEffect(() => {
        fetchUserProfile().then((userProfileInfos) => {
            setUserProfileInfos(userProfileInfos);
            if (userProfileInfos) {
                setGender(userProfileInfos.gender);
                setWeight(userProfileInfos.weight);
                setBirthDate(userProfileInfos.birthDate);
                setHeight(userProfileInfos.height);
            }
        });
    }, []);

    const handleSubmit = async (event) => {
        setSuccessMessage(null);
        setSnackbarError(null);
        event.preventDefault();
        if (!gender) {
            setOpen(true);
            setSnackbarError("Please select your gender");
            return;
          }
          if (weight <=0) {
            setOpen(true);
            setSnackbarError("Please enter your weight");
            return;
          }
          if (!birthDate) {
            setOpen(true);
            setSnackbarError("Please enter your birthdate");
            return;
          }
          if (height <=0) {
            setOpen(true);
            setSnackbarError("Please enter your height");
            return;
          }
        try {
            const response = await fetch(`/user/`, {
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
                setOpen(true);
                setSnackbarError("Failed to update your profile.");
                throw new Error('Failed to post profile detail.');
            }
            setOpen(true);
            setSuccessMessage("Profile has been updated")
            
        } catch (error) {
            console.error('Error posting infos:', error);
        }
    };

    return (
        <Box flex={9} p={{ xs: 0, md: 2 }}>
            <form className='profile-form' onSubmit={handleSubmit}>
                <label htmlFor='gender'>Select gender:</label>
                <select
                    id='gender'
                    value={gender}
                    onChange={handleGenderChange}
                    className='gender-input'
                >
                    <option value=''>Select</option>
                    <option value='Woman'>Woman</option>
                    <option value='Man'>Man</option>
                </select>

                <label htmlFor='weight'>Enter weight:</label>

                <input
                    type='number'
                    id='weight'
                    value={weight}
                    onChange={handleWeightChange}
                    className='profile-input'
                />
                <label htmlFor='birthDate'>Enter Birth Date:</label>

                <input
                    type='date'
                    id='birthDate'
                    value={birthDate}
                    onChange={handleBirthDateChange}
                    className='profile-input'
                />
                <label htmlFor='height'>Enter height:</label>

                <input
                    type='number'
                    id='height'
                    value={height}
                    onChange={handleHeightChange}
                    className='profile-input'
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
                    snackbarOpen={open}
                    onClose={handleClose}
                    successMassage={successMessage}
                    snackbarError={snackbarError}
                />
            </form>
        </Box>
    );
}
