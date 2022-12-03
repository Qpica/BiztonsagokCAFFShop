// material-ui
import { useTheme, styled } from '@mui/material/styles';
import {
    Grid,
    Link,
    Button,
    Avatar,
    Box,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Checkbox,
    Divider,
    FormControl,
    FormControlLabel,
    FormHelperText,
    IconButton,
    InputAdornment,
    InputLabel,
    OutlinedInput,
    Stack,
    Typography,
    useMediaQuery
} from '@mui/material';
import { List, ListItem, ListItemButton, ListItemText, ListItemAvatar } from '@mui/material';
import MuiTypography from '@mui/material/Typography';
import { red, yellow } from '@mui/material/colors';
import PhotoCamera from '@mui/icons-material/PhotoCamera';
import { Formik } from 'formik';
import jwtDecode from 'jwt-decode';

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
import AnimateButton from 'ui-component/extended/AnimateButton';

import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useRef } from 'react';
import { caffActions, userActions } from '_store';
import ViewImage from 'ui-component/ViewFile';

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
    const [openUploadForm, setOpenUploadForm] = React.useState(false);
    const [editCaffForm, setEditCaffForm] = React.useState(null);
    const [items, setItems] = React.useState(sampleItems);
    const { user: authUser } = useSelector((x) => x.auth);
    const { allCaffPicture: caffPics, pending: postCaffPending } = useSelector((x) => x.caff);
    const fileRef = useRef(null);

    const handleUploadOpen = (item) => {
        setOpenUploadForm(true);
        setEditCaffForm(item);
    };
    const handleUploadClose = () => {
        setOpenUploadForm(false);
        setEditCaffForm(null);
    };

    const onDeleteItem = (item) => {
        const linkArray = item._links.self.href.split('/');
        dispatch(caffActions.deleteCaffPicture(linkArray[5]));
    };

    const dispatch = useDispatch();
    const caffPictures = useSelector((x) => x.caff.allCaffPicture);

    useEffect(() => {
        dispatch(caffActions.getAllCaffPicture());
        //dispatch(caffActions.getOneCaffPicture({ id: 1 }));
    }, [postCaffPending]);

    const UploadForm = () => {
        return (
            <Dialog
                open={openUploadForm}
                onClose={() => handleUploadClose()}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogContent>
                    <Formik
                        initialValues={
                            editCaffForm
                                ? {
                                      title: editCaffForm.title,
                                      description: editCaffForm.description,
                                      price: editCaffForm.price
                                  }
                                : {
                                      title: '',
                                      description: '',
                                      price: 0,
                                      file: null
                                  }
                        }
                        onSubmit={async (values, {}) => {
                            var formdata = new FormData();
                            formdata.append('title', values.title);
                            formdata.append('description', values.description);
                            if (!editCaffForm) {
                                formdata.append('caffFile', values.file);
                            }
                            formdata.append('ownerUserName', jwtDecode(authUser.accessToken).aud);
                            formdata.append('price', values.price);
                            if (editCaffForm) {
                                const linkArray = editCaffForm._links.self.href.split('/');
                                console.log(formdata);
                                dispatch(caffActions.editCaffPicture({ id: linkArray[5], formData: formdata }));
                            } else {
                                dispatch(caffActions.postCaffPicture(formdata));
                            }
                            //console.log(values.file);
                            handleUploadClose();
                        }}
                    >
                        {({ values, handleBlur, handleChange, handleSubmit, isSubmitting, setFieldValue, setFieldTouched }) => (
                            <>
                                {editCaffForm ? <h3>Edit</h3> : <h3>Create</h3>}
                                <form noValidate onSubmit={handleSubmit}>
                                    <FormControl fullWidth sx={{ ...theme.typography.customInput }}>
                                        <InputLabel htmlFor="title">Title</InputLabel>
                                        <OutlinedInput
                                            id="title"
                                            type="text"
                                            name="title"
                                            value={values.title}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            label="Title"
                                            inputProps={{}}
                                        />
                                    </FormControl>
                                    <FormControl fullWidth sx={{ ...theme.typography.customInput }}>
                                        <InputLabel htmlFor="description">Description</InputLabel>
                                        <OutlinedInput
                                            id="description"
                                            type="text"
                                            name="description"
                                            value={values.description}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            label="Description"
                                            inputProps={{}}
                                        />
                                    </FormControl>
                                    <FormControl fullWidth sx={{ ...theme.typography.customInput }}>
                                        <InputLabel htmlFor="price">Price</InputLabel>
                                        <OutlinedInput
                                            id="price"
                                            type="int"
                                            name="price"
                                            value={values.price}
                                            onBlur={handleBlur}
                                            onChange={handleChange}
                                            label="Price"
                                            inputProps={{}}
                                        />
                                    </FormControl>
                                    {!editCaffForm && (
                                        <FormControl fullWidth sx={{ ...theme.typography.customInput }}>
                                            <OutlinedInput
                                                ref={fileRef}
                                                id="file"
                                                type="file"
                                                name="file"
                                                onBlur={handleBlur}
                                                //onChange={handleChange}
                                                onChange={(event) => {
                                                    //const fileData = event.currentTarget.files[0];
                                                    setFieldValue('file', event.target.files[0]);
                                                    //setFieldValue('file', { data: fileData, fileName: '_caff' });
                                                    //setTimeout(() => setFieldTouched(file, true));
                                                    //console.log(fileData, 'ss');
                                                }}
                                                label="File"
                                                inputProps={{}}
                                            />
                                        </FormControl>
                                    )}
                                    <Box sx={{ mt: 2 }}>
                                        <AnimateButton>
                                            <Button
                                                disableElevation
                                                disabled={isSubmitting}
                                                fullWidth
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
                            </>
                        )}
                    </Formik>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => handleUploadClose()}>Cancel</Button>
                </DialogActions>
            </Dialog>
        );
    };

    return (
        <>
            <UploadForm />
            <MainCard title="Library">
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column" columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                        <Grid container direction="row">
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
                                    onClick={() => handleUploadOpen()}
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
                                        {caffPics &&
                                            caffPics.map((item, index) => {
                                                return (
                                                    <div key={index}>
                                                        <SubCard title={item.title}>
                                                            <Grid container direction="column" spacing={1}>
                                                                <Grid item>
                                                                    <Grid container direction="row" spacing={1}>
                                                                        <Grid item xs={12} sm={6}>
                                                                            <MuiTypography variant="h5" gutterBottom>
                                                                                file...
                                                                                {
                                                                                    //filename: {item.fileName}
                                                                                }
                                                                            </MuiTypography>
                                                                        </Grid>
                                                                        <Grid item xs={12} sm={6}>
                                                                            <Grid container direction="row" spacing={1}>
                                                                                <Grid item>
                                                                                    <Button
                                                                                        sx={{ borderRadius: 10, width: 100, height: 40 }}
                                                                                        variant="contained"
                                                                                        onClick={() => handleUploadOpen(item)}
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
                                                                        {item.userCommentList.map((comment) => {
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
                                                                                            onClick={() =>
                                                                                                handleEditCommentOpen({ item, comment })
                                                                                            }
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
        </>
    );
};

export default Library;
