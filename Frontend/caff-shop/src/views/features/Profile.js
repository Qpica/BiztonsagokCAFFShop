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

    const handleEdit = () => {
        console.log('implemetation needed - network call');
    };

    const dispatch = useDispatch();

    return (
        <>
            <MainCard title="Profile">
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column">
                        <Grid item sx={{ m: 2.0 }}>
                            <MuiTypography sx={{ fontWeight: 'bold' }}>Modify your personal data</MuiTypography>
                        </Grid>
                        <Grid item sx={{ m: 2.0 }}>
                            <Grid container direction="column">
                                <Grid item sx={{ m: 1.5 }}>
                                    <TextField label="Username" defaultValue={jwtDecode(authUser.accessToken).aud} variant="standard" />
                                </Grid>
                                <Grid item sx={{ m: 1.5 }}>
                                    <TextField id="standard" label="Role" defaultValue="Hello World" variant="standard" />
                                </Grid>
                            </Grid>
                        </Grid>
                        <Grid item sx={{ m: 2.0 }}>
                            <Button
                                sx={{ borderRadius: 10, width: 100, height: 40 }}
                                variant="contained"
                                onClick={() => handleEdit()}
                                startIcon={<SaveIcon />}
                            >
                                Save
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
            </MainCard>
        </>
    );
};

export default Profile;
