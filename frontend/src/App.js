import React, { useState, useEffect } from 'react';
import { Outlet, useLocation } from 'react-router-dom';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Cookies from 'js-cookie';
import jwt from 'jwt-decode';
import Navbar from './components/Navbar/Navbar';
import Sidebar from './components/Sidebar/Sidebar';
import Rightbar from './components/Rightbar/Rightbar';

import { Box, createTheme, Stack, ThemeProvider } from '@mui/material';

const App = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const location = useLocation();
    useEffect(() => {
        const jwtToken = Cookies.get('jwtToken');
        setIsLoggedIn(!!jwtToken);
    }, [location]);

    const handleLogout = () => {
        Cookies.remove('jwtToken');
        setIsLoggedIn(false);
    };
    const [mode, setMode] = useState('light');
    
    const darkTheme = createTheme({
        palette: {
            mode: mode,
        },
    });
    return (
        
            <ThemeProvider theme={darkTheme}>
                <Box sx={{ height: '100vh' }} bgcolor={'background.default'} color={'text.primary'}>
                    <Navbar
                        isLoggedIn={isLoggedIn}
                        handleLogout={handleLogout}
                    />
                    <Stack
                        direction='row'
                        spacing={2}
                        justifyContent='space-between'
                    >
                        <Sidebar setMode={setMode} mode={mode} isLoggedIn={isLoggedIn}
                        handleLogout={handleLogout} />
                        <Outlet />

                    </Stack>
                </Box>
            </ThemeProvider>
    );
};

export default App;
