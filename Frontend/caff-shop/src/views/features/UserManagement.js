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

const sampleUsers = [
    { id: 1, name: 'Kovacs Zoltan' },
    { id: 2, name: 'Nagy Bela' },
    { id: 3, name: 'Minta Jozef' }
];

const UserManagement = ({ isLoading }) => {
    const theme = useTheme();

    //const [users, setUsers] = React.useState(sampleUsers);
    const [selectedUserId, setSelectedUserId] = React.useState(null);
    const [openDelete, setOpenDelete] = React.useState(false);

    const dispatch = useDispatch();
    const { user: authUser } = useSelector((x) => x.auth);
    const { users: users, error: usersError } = useSelector((x) => x.users);

    useEffect(() => {
        dispatch(userActions.getAll());
    }, []);

    const handleDeleteOpen = (id) => {
        setOpenDelete(true);
        setSelectedUserId(id);
    };
    const handleDeleteClose = () => {
        setOpenDelete(false);
    };
    const handleDeleteConfirm = () => {
        console.log('Network call here');
        var newUsers = users.filter((e) => e.id !== selectedUserId);
        console.log(newUsers);
        setUsers(newUsers);
        handleDeleteClose();
    };

    const handleSelectedOpen = () => {
        console.log('clicked - no action');
    };
    const MyDeleteDialog = () => {
        return (
            <Dialog
                open={openDelete}
                onClose={() => handleDeleteClose()}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{'Confirm delete?'}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description"> </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => handleDeleteClose()}>Cancel</Button>
                    <Button onClick={() => handleDeleteConfirm()}>Delete</Button>
                </DialogActions>
            </Dialog>
        );
    };

    return (
        <>
            <MyDeleteDialog />
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
                                            <ListItemAvatar onClick={() => handleDeleteOpen(item.userName)} sx={{ m: 2 }}>
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
