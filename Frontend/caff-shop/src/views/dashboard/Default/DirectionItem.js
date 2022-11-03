import PropTypes from 'prop-types';
import { useState, useEffect } from 'react';
// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Avatar, Box, List, Grid, ListItem, ListItemAvatar, ListItemText, Typography } from '@mui/material';

// assets
import LocationTwoToneIcon from '@mui/icons-material/LocationOnTwoTone';

const DirectionItem = ({ isLoading, title }) => {
    const theme = useTheme();

    const [selected, setSelected] = useState(false);

    const handleToggle = () => {
        setSelected((prevSelected) => !prevSelected);
    };

    function SelectIcon(props) {
        const isSelected = props.selected;
        if (isSelected) {
            return (
                <>
                    <ListItemAvatar>
                        <Avatar
                            variant="rounded"
                            sx={{
                                ...theme.typography.commonAvatar,
                                ...theme.typography.largeAvatar,
                                backgroundColor: theme.palette.warning.dark,
                                color: theme.palette.warning.light
                            }}
                        >
                            <LocationTwoToneIcon fontSize="inherit" />
                        </Avatar>
                    </ListItemAvatar>
                </>
            );
        } else {
            return (
                <>
                    <ListItemAvatar>
                        <Avatar
                            variant="rounded"
                            sx={{
                                ...theme.typography.commonAvatar,
                                ...theme.typography.largeAvatar,
                                backgroundColor: theme.palette.warning.dark,
                                color: theme.palette.warning.light
                            }}
                        >
                            <LocationTwoToneIcon fontSize="inherit" />
                        </Avatar>
                    </ListItemAvatar>
                </>
            );
        }
    }

    return (
        <Grid container direction="row" onClick={() => handleToggle()}>
            <Grid item ys={12}>
                <SelectIcon isSelected={false}></SelectIcon>
            </Grid>
            <Grid item ys={48} alignContent="center" justifyContent="center">
                <Typography variant="h6" color="white">
                    {title}
                </Typography>
            </Grid>
            <Grid item ys={24} alignContent="center" justifyContent="center">
                {selected == true && (
                    <Typography variant="h6" color="white">
                        (selected)
                    </Typography>
                )}
            </Grid>
        </Grid>
    );
};

DirectionItem.propTypes = {
    isLoading: PropTypes.bool,
    title: PropTypes.string
};

export default DirectionItem;
