import PropTypes from 'prop-types';
import { useState } from 'react';

// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Avatar, Box, Button, List, Grid, Typography } from '@mui/material';

// third-party
import Chart from 'react-apexcharts';

// project imports
import MainCard from 'ui-component/cards/MainCard';
import SkeletonTotalOrderCard from 'ui-component/cards/Skeleton/EarningCard';

import ChartDataMonth from './chart-data/total-order-month-line-chart';
import ChartDataYear from './chart-data/total-order-year-line-chart';

// assets
import LocalMallOutlinedIcon from '@mui/icons-material/LocalMallOutlined';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import DirectionItem from './DirectionItem';

const CardWrapper = styled(MainCard)(({ theme }) => ({
    backgroundColor: theme.palette.primary.dark,
    color: '#fff',
    overflow: 'hidden',
    position: 'relative',
    '&>div': {
        position: 'relative',
        zIndex: 5
    },
    '&:after': {
        content: '""',
        position: 'absolute',
        width: 210,
        height: 210,
        background: theme.palette.primary[800],
        borderRadius: '50%',
        zIndex: 1,
        top: -85,
        right: -95,
        [theme.breakpoints.down('sm')]: {
            top: -105,
            right: -140
        }
    },
    '&:before': {
        content: '""',
        position: 'absolute',
        zIndex: 1,
        width: 210,
        height: 210,
        background: theme.palette.primary[800],
        borderRadius: '50%',
        top: -125,
        right: -15,
        opacity: 0.5,
        [theme.breakpoints.down('sm')]: {
            top: -155,
            right: -70
        }
    }
}));

// ==============================|| DASHBOARD - TOTAL ORDER LINE CHART CARD ||============================== //
var directions = ['Marseilles', 'Nizza', 'Monaco'];

const DirectionFromCard = ({ isLoading }) => {
    const theme = useTheme();

    const [timeValue, setTimeValue] = useState(false);
    const handleChangeTime = (event, newValue) => {
        setTimeValue(newValue);
    };

    return (
        <>
            {isLoading ? (
                <SkeletonTotalOrderCard />
            ) : (
                <CardWrapper border={false} content={false}>
                    <Box sx={{ p: 5.25 }}>
                        <Grid container direction="column">
                            <Grid item sy={{ p: 5.0 }}>
                                <Typography variant="h4" color="white">
                                    Please select your origin
                                </Typography>
                            </Grid>
                            <Grid item sy={{ p: 15.0 }}>
                                <Grid container direction="column">
                                    <Grid item sy={{ p: 5.0 }}>
                                        <DirectionItem title={directions[0]} disableGutters sx={{ py: 0 }}></DirectionItem>
                                    </Grid>
                                    <Grid item sy={{ p: 5.0 }}>
                                        <DirectionItem title={directions[1]} disableGutters sx={{ py: 0 }}></DirectionItem>
                                    </Grid>
                                    <Grid item sy={{ p: 5.0 }}>
                                        <DirectionItem title={directions[2]} disableGutters sx={{ py: 0 }}></DirectionItem>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Box>
                </CardWrapper>
            )}
        </>
    );
};

DirectionFromCard.propTypes = {
    isLoading: PropTypes.bool
};

export default DirectionFromCard;
