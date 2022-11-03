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

const UserManagement = ({ isLoading }) => {
    const theme = useTheme();

    const [openDelete, setOpenDelete] = React.useState(false);

    const handleDeleteOpen = () => {
        setOpenDelete(true);
    };
    const handleDeleteClose = () => {
        setOpenDelete(false);
    };

    const handleSelectedOpen = () => {
        console.log('clicked - no action');
    };
    const MyDeleteDialog = () => {
        return (
            <Dialog
                open={openDelete}
                onClose={handleDeleteClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{'Confirm delete?'}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description"> </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleDeleteClose}>Cancel</Button>
                    <Button onClick={handleDeleteClose}>Delete</Button>
                </DialogActions>
            </Dialog>
        );
    };

    return (
        <>
            <MyDeleteDialog />
            <MainCard title="User management" secondary={<MyIcon icon={<Add fontSize="inherit" />} />}>
                <Grid container direction="column">
                    <Grid item sy={{ p: 5.0 }}>
                        <List sx={{ maxWidth: 450 }}>
                            <ListItem>
                                <ListItemAvatar onClick={handleSelectedOpen}>
                                    <MyIcon icon={<User fontSize="inherit" />} />
                                </ListItemAvatar>
                                <ListItemButton onClick={handleSelectedOpen}> User 1</ListItemButton>
                                <Box sx={{ maxWidth: 50 }} />
                                <ListItemAvatar onClick={handleDeleteOpen} sx={{ m: 2 }}>
                                    <MyIcon color={red[400]} icon={<Delete fontSize="inherit" />} />
                                </ListItemAvatar>
                            </ListItem>
                            <ListItem>
                                <ListItemButton>User 2</ListItemButton>
                            </ListItem>
                        </List>
                    </Grid>
                </Grid>
            </MainCard>
        </>
    );
};

export default UserManagement;
