import PropTypes from 'prop-types';
// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Avatar } from '@mui/material';
import * as React from 'react';

const MyIcon = (props) => {
    const theme = useTheme();
    var color, bgColor;
    if (props.color != null) {
        color = 'white';
        bgColor = props.color;
    } else {
        color = 'white';
        bgColor = theme.palette.warning.dark;
    }
    return (
        <Avatar
            variant="rounded"
            sx={{
                ...theme.typography.commonAvatar,
                ...theme.typography.largeAvatar,
                backgroundColor: bgColor,
                color: color
            }}
        >
            {props.icon}
        </Avatar>
    );
};

export default MyIcon;
