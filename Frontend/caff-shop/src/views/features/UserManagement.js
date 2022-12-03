import PropTypes from 'prop-types';
// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Grid, Box, Link, Button, IconButton, Typography, Avatar } from '@mui/material';
import { List, ListItem, ListItemButton, ListItemText, ListItemAvatar } from '@mui/material';
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { red } from '@mui/material/colors';
import * as React from 'react';
import Add from '@mui/icons-material/Add';
import Delete from '@mui/icons-material/Delete';
import User from '@mui/icons-material/Person';
import MainCard from 'ui-component/cards/MainCard';
import SecondaryAction from 'ui-component/cards/CardSecondaryAction';
import { gridSpacing } from 'store/constant';
import MyIcon from './MyIcon';

import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import { userActions } from '_store';

const UserManagement = ({ isLoading }) => {
    const theme = useTheme();

    const dispatch = useDispatch();
    const { user: authUser } = useSelector((x) => x.auth);
    const { users: users, error: usersError } = useSelector((x) => x.users);

    useEffect(() => {
        dispatch(userActions.getAll());
    }, []);

    const handleDelete = (username) => {
        dispatch(userActions.deleteUser(username));
        dispatch(userActions.getAll());
    };

    const handleSelectedOpen = () => {
        console.log('clicked - no action');
    };

    return (
        <>
            <MainCard title="User management">
                <Grid container direction="column">
                    <Grid item sy={{ p: 5.0 }}>
                        <List sx={{ maxWidth: 450 }}>
                            {Object.keys(users).map((index) => {
                                return (
                                    <div key={index}>
                                        <ListItem>
                                            <ListItemAvatar onClick={() => handleSelectedOpen()}>
                                                <MyIcon icon={<User fontSize="inherit" />} />
                                            </ListItemAvatar>
                                            <ListItemButton onClick={() => handleSelectedOpen()}>{users[index].userName}</ListItemButton>
                                            <Box sx={{ maxWidth: 50 }} />
                                            <ListItemAvatar onClick={() => handleDelete(users[index].userName)} sx={{ m: 2 }}>
                                                <MyIcon color={red[400]} icon={<Delete fontSize="inherit" />} />
                                            </ListItemAvatar>
                                        </ListItem>
                                    </div>
                                );
                            })}
                        </List>
                    </Grid>
                </Grid>
            </MainCard>
        </>
    );
};

export default UserManagement;
