// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Grid, Link, Button, Avatar, Divider, Box } from '@mui/material';
import { List, ListItem, ListItemButton, ListItemText, ListItemAvatar } from '@mui/material';
import MuiTypography from '@mui/material/Typography';
import { red, yellow } from '@mui/material/colors';

// project imports
import SubCard from 'ui-component/cards/SubCard';
import MainCard from 'ui-component/cards/MainCard';
import SecondaryAction from 'ui-component/cards/CardSecondaryAction';
import { gridSpacing } from 'store/constant';

// assets
import SearchIcon from '@mui/icons-material/Search';
import UploadIcon from '@mui/icons-material/Upload';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';
import CommentIcon from '@mui/icons-material/ChatBubble';
import SearchSection from 'layout/MainLayout/Header/SearchSection';
import MyIcon from './MyIcon';
// ==============================|| TYPOGRAPHY ||============================== //

const Library = ({ isLoading }) => {
    const theme = useTheme();

    const handleEditOpen = () => {
        console.log('Need edit implementation.');
    };

    const handleDeleteOpen = () => {
        console.log('Need delete implementation.');
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
                            <Button sx={{ borderRadius: 10 }} variant="contained" startIcon={<SearchIcon />}>
                                Search
                            </Button>
                        </Grid>
                        <Grid item>
                            <Button sx={{ borderRadius: 10 }} variant="contained" startIcon={<UploadIcon />}>
                                Upload
                            </Button>
                        </Grid>
                    </Grid>
                </Grid>
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column">
                        <Grid item sy={{ p: 5.0 }}>
                            <Grid container spacing={gridSpacing}>
                                <Grid item xs={12} sm={6}>
                                    <SubCard title="CAFF item 1">
                                        <Grid container direction="column" spacing={1}>
                                            <Grid item>
                                                <MuiTypography variant="h5" gutterBottom>
                                                    filename: My_Test_Caff.caff
                                                </MuiTypography>
                                            </Grid>
                                            <Grid item>
                                                <MuiTypography variant="caption" gutterBottom>
                                                    Comments
                                                </MuiTypography>
                                            </Grid>
                                            <Grid item>
                                                <List sx={{ maxWidth: 450 }}>
                                                    <ListItem>
                                                        <ListItemAvatar>
                                                            <MyIcon color={yellow[400]} icon={<CommentIcon fontSize="sm" />} />
                                                        </ListItemAvatar>
                                                        <ListItemButton> Comment 1: Some comment here</ListItemButton>
                                                        <Box sx={{ maxWidth: 50 }} />
                                                        <ListItemAvatar onClick={handleEditOpen} sx={{ m: 0.25 }}>
                                                            <MyIcon icon={<EditIcon />} />
                                                        </ListItemAvatar>
                                                        <ListItemAvatar onClick={handleDeleteOpen} sx={{ m: 0.25 }}>
                                                            <MyIcon color={red[400]} icon={<DeleteIcon />} />
                                                        </ListItemAvatar>
                                                    </ListItem>
                                                </List>
                                            </Grid>
                                        </Grid>
                                    </SubCard>
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
