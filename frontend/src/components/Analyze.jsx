import { useEffect, useState } from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import './CalorieDailyList.css';
import { Box, Stack, Skeleton } from '@mui/material';
import Cookies from 'js-cookie';

export default function Analyze() {
    const [listedMeals, setListedMeals] = useState([
        { caloriesIn: 3000, caloriesOut: 400, optimalCalories: 2000 },
        { caloriesIn: 2000, caloriesOut: 400, optimalCalories: 2000 },
        { caloriesIn: 2500, caloriesOut: 300, optimalCalories: 2000 },
        { caloriesIn: 1000, caloriesOut: 400, optimalCalories: 2000 },
        { caloriesIn: 3200, caloriesOut: 400, optimalCalories: 2000 },
        { caloriesIn: 3100, caloriesOut: 400, optimalCalories: 2000 },
        { caloriesIn: 3300, caloriesOut: 400, optimalCalories: 2000 },
    ]);

    const jwtToken = Cookies.get('jwtToken');

    const requestOptions = {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${jwtToken}`,
        },
    };

    const fetchMeals = () => {
        return fetch('/dailyCalorie', requestOptions).then((res) => res.json());
    };



    const caloriesIns = listedMeals.map((data) => data.caloriesIn);
    const caloriesOutList = listedMeals.map((data) => data.caloriesOut);

    return (
        <Box flex={9} p={{ xs: 0, md: 2 }}>
            <div className='calorie-daily-list-container'>
                {listedMeals && (
                    <>
                        <table className='calorie-table'>
                            <thead>
                                <tr>
                                    <th>Food Type</th>
                                    <th>Calories</th>
                                </tr>
                            </thead>
                            <tbody>
                                {listedMeals.map((data, index) => (
                                    <tr key={index}>
                                        <td>{data.foodType}</td>
                                        <td>{data.calories}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                        <div className='calorie-bar-chart'>
                            
                            <BarChart
                                p={{ tickFontSize: 30 }}
                                series={[
                                    {
                                        data: caloriesIns,
                                        stack: 'A',
                                        label: 'optimal',
                                    },
                                    {
                                        data: caloriesOutList,
                                        stack: 'A',
                                        label: 'calorie out',
                                    },

                                    {
                                        data: caloriesIns,
                                        label: 'calories in',
                                        color: 'red',
                                    },
                                ]}
                                width={600}
                                height={350}
                            />
                        </div>
                    </>
                )}
            </div>
        </Box>
    );
}
