import { useEffect, useState } from 'react';
import './ActivityDailyList.css';
import { Box, Stack, Skeleton } from '@mui/material';
import Cookies from 'js-cookie';


export default function ActivityDailyList() {
    const [listedMeals, setListedMeals] = useState([]);

    const jwtToken = Cookies.get('jwtToken');


    
    const requestOptions = {
        method: 'GET',
        headers: {
            Authorization: `Bearer ${jwtToken}`,
        },
    };

    const fetchMeals = () => {
        return fetch('/activities/daily', requestOptions).then((res) => res.json());
    };

    useEffect(() => {
        fetchMeals().then((listedActivities) => {
            setListedMeals(listedActivities);
            console.log(listedActivities);
        });
    }, []);

    const formatDateTime = (dateTimeString) => {
        const dateTime = new Date(dateTimeString);
        const date = dateTime.toLocaleDateString();
        const time = dateTime.toLocaleTimeString([], {
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit',
        });
        return `${date} ${time}`;
    };

    return (
        <Box flex={7} p={{ xs: 0, md: 2 }}>
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Activity</th>
                            <th>Duration(min)</th>
                            <th>Calories</th>
                            <th>Date & Time</th>
                        </tr>
                    </thead>
                    <tbody>
                        {listedMeals.map((data, index) => (
                            <tr key={data.id}>
                                <td>{data.activity}</td>
                                <td>{data.activityDuration}</td>
                                <td>{data.calories}</td>
                                <td>{formatDateTime(data.activityDateTime)}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </Box>
    );
}
