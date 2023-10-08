import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import jwt from 'jwt-decode';
import Cookies from 'js-cookie';
import './LoginForm.css';
import { Snackbar } from '@mui/material';
import MuiAlert from '@mui/material/Alert';
import { Box } from '@mui/material';

const loginWithUser = (userEmail, userPassword) => {
    return fetch(`   /users/login?email=${userEmail}&password=${userPassword}`)
    .then((response) => {
        if (!response.ok) {
            if (response.status === 401) {
                throw new Error('Invalid credentials');
            } else {
                throw new Error('Network response was not ok');
            }
        }
        return response.text();
    })
    .then((data) => {
        return data;
    })
    .catch((error) => {
        console.error('Login error', error);
        throw error;
    });
};



const loginAUTH = (userEmail, userPassword) => {
    return fetch('/api/v1/auth/authenticate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "email": userEmail,
            "password": userPassword,
        }),
    }).then((response) => {
        if (!response.ok) {
            if (response.status === 401) {
                throw new Error('Invalid credentials');
            } else {
                throw new Error('Network response was not ok');
            }
        }
        return response.json();
    })
    .then((data) => {
        return data;
    })
    .catch((error) => {
        console.error('Login error', error);
        throw error;
    });
};

const LoginForm = () => {
    const navigate = useNavigate();
    const [sendButtonDisabled, setSendButtonDisabled] = useState(false);
    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [isSuccessSnackbar, setIsSuccessSnackbar] = useState(false);

    const onSubmit = (e) => {
        e.preventDefault();
        setSendButtonDisabled(true);
        const formData = new FormData(e.target);
        const userEmail = formData.get('email');
        const userPassword = formData.get('password');

        
        setSnackbarOpen(false);
        setSnackbarMessage('');
        setIsSuccessSnackbar(false);

        loginAUTH(userEmail, userPassword)
            .then((result) => {
                

                    Cookies.set('jwtToken', result.token, { expires: 7 });
                    setSnackbarOpen(true);
                    setSnackbarMessage('Login successful.');
                    setIsSuccessSnackbar(true);
                    setTimeout(() => {
                        setSnackbarOpen(false);
                        setIsSuccessSnackbar(false);
                        navigate('/profile');
                        window.location.reload();
                    }, 3000); 
                
            })
            .catch((err) => {
                setSnackbarOpen(true); 
                setSnackbarMessage(
                     err.message
                );
                setSendButtonDisabled(false);
                console.error('Login error', err);
            });
    };

    return (
        <Box flex={20} p={{ xs: 0, md: 2 }}>
            <form className='LoginForm' onSubmit={onSubmit}>
                

                <div className='control'>
                    <label htmlFor='email'>email address:</label>
                    <input type='email' name='email' id='email' />
                </div>

                <div className='control'>
                    <label htmlFor='password'>password:</label>
                    <input
                        type='password'
                        name='password'
                        id='password'
                        autoComplete=''
                    />
                </div>
                {snackbarMessage && (
                    <Snackbar
                        open={snackbarOpen && !isSuccessSnackbar}
                        autoHideDuration={5000} 
                        onClose={() => setSnackbarOpen(false)}
                    >
                        <MuiAlert
                            elevation={6}
                            variant='filled'
                            severity='error'
                            onClose={() => setSnackbarOpen(false)}
                        >
                            {snackbarMessage}
                        </MuiAlert>
                    </Snackbar>
                )}
                {isSuccessSnackbar && (
                    <Snackbar
                        open={snackbarOpen && isSuccessSnackbar}
                        autoHideDuration={3000} 
                        onClose={() => setSnackbarOpen(false)}
                    >
                        <MuiAlert
                            elevation={6}
                            variant='filled'
                            severity='success'
                            onClose={() => setSnackbarOpen(false)}
                        >
                            {snackbarMessage}
                        </MuiAlert>
                    </Snackbar>
                )}
                <div className='buttons'>
                    <button type='submit' disabled={sendButtonDisabled}>
                        Log in
                    </button>
                </div>
            </form>
        </Box>
    );
};

export default LoginForm;
