import React from 'react';
import { Box } from '@mui/material';
import videoBg from '../assets/videos/aboutVideo.mp4';

import "./About.css";

const About = () => {
    return (
        <Box flex={7} p={{ xs: 0, md: 2 }}>
            <h1 className='titleAbout'>About Us</h1>
            <video src={videoBg} autoPlay loop muted className='videoAbout' />

            <p>Welcome to our Fitness Tracker!</p>
            <p>
                We are passionate about health and fitness, and our goal is to
                help you achieve your fitness goals by providing a powerful and
                easy-to-use calorie counting tool.
            </p>
            <p>
                Our application allows you to track your daily calorie intake,
                helping you maintain a balanced diet and make healthier choices.
            </p>
            <p>Key Features:</p>
            <ul>
                <li>
                    Calorie Counter: Keep track of the calories you consume
                    throughout the day.
                </li>
                <li>
                    Personalized Goals: Set your daily calorie goals based on
                    your fitness objectives.
                </li>
                <li>
                    Food Database: Access a vast database of foods and their
                    calorie information.
                </li>
                <li>
                    Exercise Tracking: Monitor your workouts and calories
                    burned.
                </li>
                <li>
                    Progress Visualization: View your progress over time through
                    charts and graphs.
                </li>
            </ul>
            <p>
                Whether you're trying to lose weight, maintain your current
                weight, or simply lead a healthier lifestyle, our fitness
                tracker application is designed to support you every step of the
                way.
            </p>
            <p>
                We believe that fitness is not just a goal but a journey, and we
                are committed to being your reliable companion on this journey.
                Our team of fitness enthusiasts has worked hard to create a
                user-friendly and feature-rich app that caters to your unique
                needs.
            </p>
            <p>
                Thank you for choosing our Fitness Tracker Application. We're
                excited to join you on your path to better health and
                well-being!
            </p>
            <p>Stay fit, stay healthy!</p>
        </Box>
    );
};

export default About;
