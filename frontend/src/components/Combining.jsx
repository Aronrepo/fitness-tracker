import * as React from 'react';
import { BarPlot } from '@mui/x-charts/BarChart';
import { LinePlot } from '@mui/x-charts/LineChart';
import { ChartContainer } from '@mui/x-charts/ChartContainer';

import { ChartsXAxis } from '@mui/x-charts/ChartsXAxis';
import { ChartsYAxis } from '@mui/x-charts/ChartsYAxis';

export default function Combining({ listedMeals }) {
    const xAxisLabels = listedMeals.map((data) => data.caloriesIn);
    const series = [
        {
            type: 'bar',
            stack: '',
            yAxisKey: 'eco',
            data: 1000,
        },
        {
            type: 'bar',
            stack: '',
            yAxisKey: 'eco',
            data: 0,
            
        },
        {
            type: 'line',
            yAxisKey: 'eco',
            color: 'red',
            data: 2000,
            
        },
    ];
    return (
        <ChartContainer
            series={series}
            width={700}
            height={400}
            xAxis={[
                {
                    id: 'years',
                    data: [
                        'Today'
                    ],
                    scaleType: 'band',
                    valueFormatter: (value) => value.toString(),
                },
            ]}
            yAxis={[
                {
                    id: 'eco',
                    scaleType: 'linear',
                },
                {
                    id: 'pib',
                    scaleType: 'log',
                },
            ]}
        >
            <BarPlot />
            <LinePlot />
            <ChartsXAxis label='Years' position='bottom' axisId='years' />
            <ChartsYAxis label='' position='left' axisId='eco'  />
        </ChartContainer>
    );
}
