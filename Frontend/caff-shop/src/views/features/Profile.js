// material-ui
import { useTheme } from '@mui/material/styles';
import MainCard from 'ui-component/cards/MainCard';
import { Button, Box, FormControl, InputLabel, OutlinedInput } from '@mui/material';
import { Formik } from 'formik';
import * as React from 'react';
import AnimateButton from 'ui-component/extended/AnimateButton';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { userActions } from '_store';
import jwtDecode from 'jwt-decode';

const Profile = ({ isLoading }) => {
    const theme = useTheme();
    const { user: authUser } = useSelector((x) => x.auth);

    const dispatch = useDispatch();

    return (
        <>
            <MainCard title="Profile">
                <Formik
                    initialValues={{
                        username: jwtDecode(authUser.accessToken).aud
                    }}
                    onSubmit={async (values, {}) => {
                        //dispatch(caffActions.postCaffPicture(formdata));
                    }}
                >
                    {({ values, handleBlur, handleChange, handleSubmit, isSubmitting }) => (
                        <form noValidate onSubmit={handleSubmit}>
                            <FormControl sx={{ ...theme.typography.customInput }}>
                                <InputLabel htmlFor="username">Username</InputLabel>
                                <OutlinedInput
                                    id="username"
                                    type="text"
                                    name="username"
                                    value={values.username}
                                    onBlur={handleBlur}
                                    onChange={handleChange}
                                    label="Username"
                                    inputProps={{}}
                                />
                            </FormControl>
                            <Box sx={{ mt: 2 }}>
                                <AnimateButton>
                                    <Button
                                        disableElevation
                                        disabled={isSubmitting}
                                        size="large"
                                        type="submit"
                                        variant="contained"
                                        color="secondary"
                                    >
                                        Save
                                    </Button>
                                </AnimateButton>
                            </Box>
                        </form>
                    )}
                </Formik>
            </MainCard>
        </>
    );
};

export default Profile;
