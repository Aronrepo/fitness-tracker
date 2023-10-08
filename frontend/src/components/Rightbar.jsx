import {
   
    Box,
   
} from '@mui/material';
import React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import './CalorieDailyList.css';
import './DailyBarchart.css';

const Rightbar = () => {
    return (
        <Box flex={2} p={20} sx={{ display: { xs: 'none', sm: 'block' } }}>
            <Box position='fixed' width={300}>
                <BarChart
                    p={{ tickFontSize: 30 }}
                    series={[
                        {
                            data: [2000],
                            stack: 'A',
                            label: 'optimal',
                        },
                        {
                            data: [300],
                            stack: 'A',
                            label: 'calorie out',
                        },

                        {
                            data: [1000],
                            label: 'calories in',
                            color: 'red',
                        },
                    ]}
                    width={400}
                    height={350}
                />
            </Box>
        </Box>
    );
};

export default Rightbar;
