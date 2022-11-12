// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Grid, Link, Button, Avatar, Divider, Box } from '@mui/material';
import { List, ListItem, ListItemButton, ListItemText, ListItemAvatar } from '@mui/material';
import MuiTypography from '@mui/material/Typography';
import { red, yellow } from '@mui/material/colors';

import * as React from 'react';
import SubCard from 'ui-component/cards/SubCard';
import MainCard from 'ui-component/cards/MainCard';
import SecondaryAction from 'ui-component/cards/CardSecondaryAction';
import { gridSpacing } from 'store/constant';

// assets
import SearchIcon from '@mui/icons-material/Search';
import UploadIcon from '@mui/icons-material/Upload';
import EditIcon from '@mui/icons-material/Edit';
import AddCommentIcon from '@mui/icons-material/AddComment';
import DeleteIcon from '@mui/icons-material/Delete';
import CommentIcon from '@mui/icons-material/ChatBubble';
import SearchSection from 'layout/MainLayout/Header/SearchSection';
import MyIcon from './MyIcon';

const sampleItems = [
    {
        id: 1,
        title: 'CAFF item: SAMPLE',
        fileName: 'sample1.caff',
        fileUrl: 'www.example.com/sample1.caff',
        comments: [
            {
                id: 1,
                message: 'Sample comment'
            }
        ]
    },
    {
        id: 2,
        title: 'Second CAFF item',
        fileName: 'sample2.caff',
        fileUrl: 'www.example.com/sample2.caff',
        comments: [
            {
                id: 1,
                message: 'Comment 1'
            },
            {
                id: 2,
                message: 'Comment number #2'
            }
        ]
    }
];

const Library = ({ isLoading }) => {
    const theme = useTheme();

    const [items, setItems] = React.useState(sampleItems);
    const handleEditCommentOpen = ({ item, comment }) => {
        console.log('Need edit implementation.');
    };
    const handleDeleteCommentOpen = ({ item, comment }) => {
        var newItems = items.filter((i) => i.id !== item.id);

        var newComments = item.comments.filter((c) => c.id !== comment.id);
        item.comments = newComments;
        newItems.push(item);
        setItems(newItems);
    };
    const onEditItem = (item) => {
        console.log('Need edit implementation.');
    };
    const onDeleteItem = (item) => {
        var newItems = items.filter((i) => i.id !== item.id);
        setItems(newItems);
    };

    return (
        <MainCard title="Library">
            <Grid item sy={{ m: 1.0 }}>
                <Grid container direction="column" columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                    <Grid container direction="row" onClick={() => handleToggle()}>
                        <Grid item>
                            <SearchSection />
                        </Grid>
                        <Grid item>
                            <Button sx={{ borderRadius: 10, width: 100, height: 40, m: 0.5 }} variant="contained">
                                Search
                            </Button>
                        </Grid>
                        <Grid item sx={{ width: 100 }} />
                        <Grid item>
                            <Button
                                sx={{ borderRadius: 10, width: 120, height: 40, m: 0.5 }}
                                variant="contained"
                                startIcon={<UploadIcon />}
                            >
                                Upload
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column">
                        <Grid item sy={{ p: 5.0 }}>
                            <Grid container spacing={gridSpacing}>
                                <Grid item xs={12} sm={12}>
                                    {items.map((item) => {
                                        return (
                                            <div key={item.id}>
                                                <SubCard title={item.title}>
                                                    <Grid container direction="column" spacing={1}>
                                                        <Grid item>
                                                            <Grid container direction="row" spacing={1}>
                                                                <Grid item xs={12} sm={6}>
                                                                    <MuiTypography variant="h5" gutterBottom>
                                                                        filename: {item.fileName}
                                                                    </MuiTypography>
                                                                </Grid>
                                                                <Grid item xs={12} sm={6}>
                                                                    <Grid container direction="row" spacing={1}>
                                                                        <Grid item>
                                                                            <Button
                                                                                sx={{ borderRadius: 10, width: 100, height: 40 }}
                                                                                variant="contained"
                                                                                onClick={() => onEditItem(item)}
                                                                                startIcon={<EditIcon />}
                                                                            >
                                                                                Edit
                                                                            </Button>
                                                                        </Grid>
                                                                        <Grid item>
                                                                            <Button
                                                                                sx={{ borderRadius: 10, width: 100, height: 40 }}
                                                                                variant="contained"
                                                                                onClick={() => onDeleteItem(item)}
                                                                                startIcon={<DeleteIcon />}
                                                                            >
                                                                                Delete
                                                                            </Button>
                                                                        </Grid>
                                                                    </Grid>
                                                                </Grid>
                                                            </Grid>
                                                        </Grid>
                                                        <Grid item>
                                                            <MuiTypography variant="caption" gutterBottom>
                                                                Comments
                                                            </MuiTypography>
                                                        </Grid>
                                                        <Grid item>
                                                            <List sx={{ maxWidth: 450 }}>
                                                                {item.comments.map((comment) => {
                                                                    return (
                                                                        <div key={comment.id}>
                                                                            <ListItem>
                                                                                <ListItemAvatar>
                                                                                    <MyIcon
                                                                                        color={yellow[400]}
                                                                                        icon={<CommentIcon fontSize="sm" />}
                                                                                    />
                                                                                </ListItemAvatar>
                                                                                <ListItemButton sx={{ maxWidth: 550 }}>
                                                                                    {comment.message}
                                                                                </ListItemButton>
                                                                                <Box sx={{ width: 10 }} />

                                                                                <ListItemAvatar
                                                                                    onClick={() => handleEditCommentOpen({ item, comment })}
                                                                                    sx={{ m: 0.25 }}
                                                                                >
                                                                                    <Link>Rename</Link>
                                                                                </ListItemAvatar>
                                                                                <ListItemAvatar
                                                                                    onClick={() =>
                                                                                        handleDeleteCommentOpen({ item, comment })
                                                                                    }
                                                                                    sx={{ m: 0.25 }}
                                                                                >
                                                                                    <Link color={red[400]}>Delete</Link>
                                                                                </ListItemAvatar>
                                                                            </ListItem>
                                                                        </div>
                                                                    );
                                                                })}
                                                            </List>
                                                        </Grid>
                                                    </Grid>
                                                </SubCard>
                                            </div>
                                        );
                                    })}
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </MainCard>
    );
};

export default Library;
