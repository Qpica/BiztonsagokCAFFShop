// material-ui
import { useTheme } from '@mui/material/styles';
import MainCard from 'ui-component/cards/MainCard';
import { Button, Box, FormControl, InputLabel, OutlinedInput, TextField, Grid } from '@mui/material';
import MuiTypography from '@mui/material/Typography';
import { Formik } from 'formik';
import * as React from 'react';
import AnimateButton from 'ui-component/extended/AnimateButton';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { userActions } from '_store';
import jwtDecode from 'jwt-decode';
import { gridSpacing } from 'store/constant';
import { useState } from 'react';
import EditIcon from '@mui/icons-material/Edit';
import SaveIcon from '@mui/icons-material/Save';

const Profile = ({ isLoading }) => {
    const theme = useTheme();
    const { user: authUser } = useSelector((x) => x.auth);
    const { users: users, error: usersError, act_user: actUser } = useSelector((x) => x.users);

    const dispatch = useDispatch();

    return (
        <>
            <MainCard title="Profile">
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column">
                        <Grid item sx={{ m: 2.0 }}>
                            <Grid container direction="column">
                                <Grid item sx={{ m: 1.5 }}>
                                    <MuiTypography variant="h5" gutterBottom>
                                        User: {jwtDecode(authUser.accessToken).aud}
                                    </MuiTypography>
                                </Grid>
                                <Grid item sx={{ m: 1.5 }}>
                                    <MuiTypography variant="h5" gutterBottom>
                                        Role: {actUser.roles[0].roleName}
                                    </MuiTypography>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </>
    );
};

export default Profile;
