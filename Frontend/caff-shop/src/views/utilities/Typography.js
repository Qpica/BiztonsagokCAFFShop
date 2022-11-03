// material-ui
import { useTheme, styled } from '@mui/material/styles';
import { Grid, Link, List, ListItem, ListItemText, ListItemAvatar, Avatar } from '@mui/material';
import MuiTypography from '@mui/material/Typography';

// project imports
import SubCard from 'ui-component/cards/SubCard';
import MainCard from 'ui-component/cards/MainCard';
import SecondaryAction from 'ui-component/cards/CardSecondaryAction';
import { gridSpacing } from 'store/constant';

// assets
import StorefrontTwoToneIcon from '@mui/icons-material/StorefrontTwoTone';

// ==============================|| TYPOGRAPHY ||============================== //

const Typography = ({ isLoading }) => {
    const theme = useTheme();
    return (
        <MainCard title="Basic Typography" secondary={<SecondaryAction link="https://next.material-ui.com/system/typography/" />}>
            <Grid container direction="column">
                <Grid item sy={{ p: 5.0 }}>
                    <List sx={{ py: 0 }}>
                        <ListItem alignItems="center" disableGutters sx={{ py: 0 }}>
                            <ListItemAvatar>
                                <Avatar
                                    variant="rounded"
                                    sx={{
                                        ...theme.typography.commonAvatar,
                                        ...theme.typography.largeAvatar,
                                        backgroundColor: theme.palette.warning.light,
                                        color: theme.palette.warning.dark
                                    }}
                                >
                                    <StorefrontTwoToneIcon fontSize="inherit" />
                                </Avatar>
                            </ListItemAvatar>
                            <ListItemText
                                sx={{
                                    py: 0,
                                    mt: 0.45,
                                    mb: 0.45
                                }}
                                primary={<MuiTypography variant="h4">$203k</MuiTypography>}
                                secondary={
                                    <MuiTypography
                                        variant="subtitle2"
                                        sx={{
                                            color: theme.palette.grey[500],
                                            mt: 0.5
                                        }}
                                    >
                                        Total Income
                                    </MuiTypography>
                                }
                            />
                        </ListItem>
                    </List>
                </Grid>
                <Grid item sy={{ p: 5.0 }}>
                    <Grid container spacing={gridSpacing}>
                        <Grid item xs={12} sm={6}>
                            <SubCard title="Heading">
                                <Grid container direction="column" spacing={1}>
                                    <Grid item>
                                        <MuiTypography variant="h1" gutterBottom>
                                            h1. Heading
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="h2" gutterBottom>
                                            h2. Heading
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="h3" gutterBottom>
                                            h3. Heading
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="h4" gutterBottom>
                                            h4. Heading
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="h5" gutterBottom>
                                            h5. Heading
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="h6" gutterBottom>
                                            h6. Heading
                                        </MuiTypography>
                                    </Grid>
                                </Grid>
                            </SubCard>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <SubCard title="Sub title">
                                <Grid container direction="column" spacing={1}>
                                    <Grid item>
                                        <MuiTypography variant="subtitle1" gutterBottom>
                                            subtitle1. Lorem ipsum dolor sit connecter adieu siccing eliot. Quos blanditiis tenetur
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="subtitle2" gutterBottom>
                                            subtitle2. Lorem ipsum dolor sit connecter adieu siccing eliot. Quos blanditiis tenetur
                                        </MuiTypography>
                                    </Grid>
                                </Grid>
                            </SubCard>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <SubCard title="Body">
                                <Grid container direction="column" spacing={1}>
                                    <Grid item>
                                        <MuiTypography variant="body1" gutterBottom>
                                            bodym inventore consectetu
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="body2" gutterBottom>
                                            body2 inventore consectetur, neq
                                        </MuiTypography>
                                    </Grid>
                                </Grid>
                            </SubCard>
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <SubCard title="Extra">
                                <Grid container direction="column" spacing={1}>
                                    <Grid item>
                                        <MuiTypography variant="button" display="block" gutterBottom>
                                            button text
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="caption" display="block" gutterBottom>
                                            caption text
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography variant="overline" display="block" gutterBottom>
                                            overline text
                                        </MuiTypography>
                                    </Grid>
                                    <Grid item>
                                        <MuiTypography
                                            variant="body2"
                                            color="primary"
                                            component={Link}
                                            href="https://berrydashboard.io"
                                            target="_blank"
                                            display="block"
                                            underline="hover"
                                            gutterBottom
                                        >
                                            https://berrydashboard.io
                                        </MuiTypography>
                                    </Grid>
                                </Grid>
                            </SubCard>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </MainCard>
    );
};

export default Typography;
