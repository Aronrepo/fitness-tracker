import React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import './CalorieDailyList.css';
import { Box } from '@mui/material';
import './DailyBarchart.css';

export default function DailyBarchart({ listedMeals }) {
    if (!listedMeals || listedMeals.length === 0) {
        return null;
    }

    return (
        <Box flex={1} p={{ xs: 0, md: 2 }}>
            <BarChart
                p={{ tickFontSize: 10, }}
                
                series={[
                    {
                        data: [listedMeals[0]?.requiredCalorie || 0],
                        stack: 'A',
                        label: 'Opt cals',
                    },
                    {
                        data: [300],
                        stack: 'A',
                        label: 'Cals out',
                    },
                    {
                        data: [listedMeals[0]?.dailyCalorieConsumption || 0],
                        label: 'Cals in',
                        color: 'red',
                    },
                ]}
                width={500}
                height={350}
            />
        </Box>
    );
}

