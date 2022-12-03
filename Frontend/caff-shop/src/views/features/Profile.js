// material-ui
import { useTheme } from '@mui/material/styles';
import MainCard from 'ui-component/cards/MainCard';
import { Button, Box, FormControl, InputLabel, OutlinedInput, TextField, Grid } from '@mui/material';
import { Formik } from 'formik';
import * as React from 'react';
import AnimateButton from 'ui-component/extended/AnimateButton';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { userActions } from '_store';
import jwtDecode from 'jwt-decode';
import { gridSpacing } from 'store/constant';

const Profile = ({ isLoading }) => {
    const theme = useTheme();
    const { user: authUser } = useSelector((x) => x.auth);

    const dispatch = useDispatch();

    return (
        <>
            <MainCard title="Profile">
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column">
                        <Grid item sy={{ p: 5.0 }}>
                            <Grid container spacing={gridSpacing}>
                                <Grid item xs={12} sm={12}>
                                    <TextField
                                        disabled
                                        id="standard-disabled"
                                        label="Username"
                                        defaultValue={jwtDecode(authUser.accessToken).aud}
                                        variant="standard"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={12}>
                                    <TextField disabled id="standard-disabled" label="Role" defaultValue="Hello World" variant="standard" />
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
