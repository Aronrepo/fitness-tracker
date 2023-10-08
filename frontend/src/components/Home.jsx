import React from 'react';
import './Home.css';
import { Box } from '@mui/material';
import videoBg from '../assets/videos/homeVideo.mp4';


const Home = () => {
    return (
        <Box flex={7} p={{ xs: 0, md: 2 }}>
            <div>
                <div className='homeTitle'>
                    <h1>Welcome to TrackFit!</h1>
                </div>
                <div className='container'>
                                <video src={videoBg} autoPlay loop muted className='homeVideo' />

                    <p>
                        Good health starts with what you eat. Want to eat more
                        mindfully? Track meals, learn about your habits, and
                        reach your goals with TrackFit..
                    </p>
                </div>
            </div>
        </Box>
    );
};

export default Home;
