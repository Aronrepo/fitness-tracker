import { useEffect, useState } from 'react';
import { BarChart } from '@mui/x-charts/BarChart';
import './CalorieDailyList.css';
import { Box } from '@mui/material';
import Cookies from 'js-cookie';

export default function CalorieDailyList() {
    const [listedMeals, setListedMeals] = useState([]);

    const jwtToken = Cookies.get('jwtToken');


    
    const requestOptions = {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${jwtToken}`,
        },
    };

    const fetchMeals = () => {
        return fetch('/calories/', requestOptions).then((res) => res.json());
    };

    useEffect(() => {
        fetchMeals().then((listedMeals) => {
            setListedMeals(listedMeals);
            console.log(listedMeals);
        });
    }, []);

    const xAxisLabels = listedMeals.map((data) => data.foodType);
    const caloriesData = listedMeals.map((data) => data.calories);

    return (
        <Box flex={9} p={{ xs: 0, md: 2 }}>
            <div className='calorie-daily-list-container'>
                {listedMeals.length > 0 ? (
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
                                xAxis={[
                                    {
                                        scaleType: 'band',
                                        data: xAxisLabels,
                                    },
                                ]}
                                series={[
                                    {
                                        data: caloriesData,
                                    },
                                ]}
                                width={500}
                                height={300}
                            />
                        </div>
                    </>
                ) : (
                    <img
                        src='https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExbjg3NXM0bGY2aTk5eHM4cGRvZXAwbm53MWprMGE2anNhbGxhdzNxMSZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/5AtXMjjrTMwvK/giphy.gif'
                        alt='Your Image'
                        className='image'
                    />
                )}
            </div>
        </Box>
    );
}
