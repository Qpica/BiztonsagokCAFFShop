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
    Switch,
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
import BuyIcon from '@mui/icons-material/ShoppingBag';
import DownloadIcon from '@mui/icons-material/ArrowDownward';
import SearchSection from 'layout/MainLayout/Header/SearchSection';
import MyIcon from './MyIcon';
import AnimateButton from 'ui-component/extended/AnimateButton';

import { useDispatch, useSelector } from 'react-redux';
import { useEffect, useRef } from 'react';
import { caffActions, userActions } from '_store';
import ViewImage from 'ui-component/ViewFile';
import { width } from '@mui/system';

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
    const [commentsEnabled, setCommentsEnabled] = React.useState(false);
    const [items, setItems] = React.useState(sampleItems);
    const { user: authUser } = useSelector((x) => x.auth);
    const {
        allCaffPicture: caffPics,
        pending: postCaffPending,
        caffPicture: caffPic,
        searchPictures: searchPics
    } = useSelector((x) => x.caff);
    const { users: users, error: usersError, act_user: actUser } = useSelector((x) => x.users);

    const canvasRef = useRef(null);
    const fileRef = useRef(null);
    const image = null;
    const context = null;
    var search;

    useEffect(() => {
        const canvas = canvasRef.current;
        //context = canvas.getContext('2d');
        /* <canvas
                                                                                      width={item.width}
                                                                                      height={item.height}
                                                                                      ref={(el) => (this.canvas = el)}
                                                                                  />*/
    }, []);

    const handleDraw = (item) => {
        const ctx = this.canvas.getContext('2d');
        const imageData = ctx.createImageData(item.width, item.height);
        imageData.data = item.caffData.preview;
        ctx.putImageData(imageData, 0, 0);
    };

    const handleUploadOpen = (item) => {
        setOpenUploadForm(true);
        setEditCaffForm(item);
    };
    const handleUploadClose = () => {
        setOpenUploadForm(false);
        setEditCaffForm(null);
    };

    const handleSearch = () => {
        dispatch(caffActions.search({ Title: 'a' }));
    };

    const handleBuy = (item) => {
        item.isDownloadEnabled = true;
        console.log('clicked');
        console.log(items);
    };

    const handleDownload = (item) => {
        const linkArray = item._links.self.href.split('/');
        dispatch(caffActions.getOneCaffPicture(linkArray[5]));
    };

    const onDeleteItem = (item) => {
        const linkArray = item._links.self.href.split('/');
        dispatch(caffActions.deleteCaffPicture(linkArray[5]));
        console.log(linkArray[5]);
    };

    const dispatch = useDispatch();
    const caffPictures = useSelector((x) => x.caff.allCaffPicture);

    useEffect(() => {
        dispatch(caffActions.getAllCaffPicture());
    }, [postCaffPending]);

    useEffect(() => {
        dispatch(userActions.getUser(jwtDecode(authUser.accessToken).aud));
    }, []);

    const UploadForm = () => {
        return (
            <>
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
                                const editData = {
                                    //title: values.title,
                                    //description: values.description,
                                    price: parseInt(values.price)
                                    //ownerUserName: jwtDecode(authUser.accessToken).aud
                                };
                                if (!editCaffForm) {
                                    formdata.append('title', values.title);
                                    formdata.append('description', values.description);
                                    formdata.append('caffFile', values.file);
                                    formdata.append('ownerUserName', jwtDecode(authUser.accessToken).aud);
                                }
                                formdata.append('price', values.price);
                                if (editCaffForm) {
                                    const linkArray = editCaffForm._links.self.href.split('/');
                                    console.log(editData);
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
                                        {!editCaffForm && (
                                            <>
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
                                            </>
                                        )}
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
            </>
        );
    };

    return (
        <>
            <UploadForm />
            <MainCard title="Library">
                <Grid item sy={{ m: 1.0 }}>
                    <Grid container direction="column" columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
                        <Grid container direction="row" sx={{ m: 2.0 }}>
                            <Grid item>
                                <SearchSection />
                            </Grid>
                            <Grid item>
                                <Button
                                    sx={{ borderRadius: 10, width: 100, height: 40, m: 0.5 }}
                                    variant="contained"
                                    onClick={() => handleSearch()}
                                >
                                    Search
                                </Button>
                            </Grid>
                            <Grid item sx={{ width: 30 }} />
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
                        <Grid item sx={{ m: 1.5 }}>
                            <Switch value={commentsEnabled} onClick={() => setCommentsEnabled(!commentsEnabled)}></Switch>
                            Show comments
                        </Grid>
                    </Grid>
                    <Grid item sy={{ m: 1.0 }} sx={{ m: 2.0 }}>
                        <Grid container direction="column">
                            <Grid item sy={{ p: 5.0 }}>
                                <Grid container spacing={gridSpacing}>
                                    <Grid item xs={12} sm={12}>
                                        {searchPics
                                            ? searchPics.map((item, index) => {
                                                  return (
                                                      <div key={index}>
                                                          <SubCard title={item.title}>
                                                              <Grid container direction="column">
                                                                  <Grid item>
                                                                      <Box sx={{ m: 2.5 }}>
                                                                          <Grid container direction="row">
                                                                              <Grid item xs={12} sm={2}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      Description:
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item xs={12} sm={6}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      {item.description}
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                          </Grid>
                                                                          <Grid container direction="row">
                                                                              <Grid item xs={12} sm={2}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      Price:
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item xs={12} sm={6}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      {item.price} $
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                          </Grid>
                                                                      </Box>
                                                                  </Grid>
                                                                  <Grid item>
                                                                      <Grid container direction="row" spacing={0}>
                                                                          <Grid item>
                                                                              <Grid container direction="row" spacing={1}>
                                                                                  {(!item.isDownloadEnabled ||
                                                                                      item.isDownloadEnabled == null) && (
                                                                                      <Grid item>
                                                                                          <Button
                                                                                              sx={{
                                                                                                  borderRadius: 10,
                                                                                                  width: 150,
                                                                                                  height: 40
                                                                                              }}
                                                                                              variant="contained"
                                                                                              onClick={() => handleDraw(item)}
                                                                                              startIcon={<BuyIcon />}
                                                                                          >
                                                                                              Buy
                                                                                          </Button>
                                                                                      </Grid>
                                                                                  )}
                                                                                  {item.isDownloadEnabled && (
                                                                                      <Grid item>
                                                                                          <Button
                                                                                              sx={{
                                                                                                  borderRadius: 10,
                                                                                                  width: 150,
                                                                                                  height: 40
                                                                                              }}
                                                                                              variant="contained"
                                                                                              onClick={() => handleDownload(item)}
                                                                                              startIcon={<DownloadIcon />}
                                                                                          >
                                                                                              Download
                                                                                          </Button>
                                                                                      </Grid>
                                                                                  )}
                                                                                  {actUser.roles[0].roleName == 'ROLE_ADMINISTRATOR' ? (
                                                                                      <>
                                                                                          <Grid item>
                                                                                              <Button
                                                                                                  sx={{
                                                                                                      borderRadius: 10,
                                                                                                      width: 100,
                                                                                                      height: 40
                                                                                                  }}
                                                                                                  variant="contained"
                                                                                                  onClick={() => handleUploadOpen(item)}
                                                                                                  startIcon={<EditIcon />}
                                                                                              >
                                                                                                  Edit
                                                                                              </Button>
                                                                                          </Grid>
                                                                                          <Grid item>
                                                                                              <Button
                                                                                                  sx={{
                                                                                                      borderRadius: 10,
                                                                                                      width: 100,
                                                                                                      height: 40
                                                                                                  }}
                                                                                                  variant="contained"
                                                                                                  onClick={() => onDeleteItem(item)}
                                                                                                  startIcon={<DeleteIcon />}
                                                                                              >
                                                                                                  Delete
                                                                                              </Button>
                                                                                          </Grid>{' '}
                                                                                      </>
                                                                                  ) : null}
                                                                              </Grid>
                                                                          </Grid>
                                                                      </Grid>
                                                                  </Grid>
                                                                  <Grid item sx={{ m: 2.0 }}>
                                                                      {commentsEnabled && (
                                                                          <>
                                                                              <Grid item>
                                                                                  <MuiTypography variant="caption" gutterBottom>
                                                                                      Comments
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item>
                                                                                  <List sx={{ maxWidth: 450 }}>
                                                                                      {item.userCommentList.map((comment, index) => {
                                                                                          return (
                                                                                              <div key={index}>
                                                                                                  <ListItem>
                                                                                                      <ListItemAvatar>
                                                                                                          <MyIcon
                                                                                                              color={yellow[400]}
                                                                                                              icon={
                                                                                                                  <CommentIcon fontSize="sm" />
                                                                                                              }
                                                                                                          />
                                                                                                      </ListItemAvatar>
                                                                                                      <ListItemButton
                                                                                                          sx={{ maxWidth: 550 }}
                                                                                                      >
                                                                                                          {comment.ownerName}:{' '}
                                                                                                          {comment.comment_value}
                                                                                                      </ListItemButton>
                                                                                                  </ListItem>
                                                                                              </div>
                                                                                          );
                                                                                      })}
                                                                                  </List>
                                                                              </Grid>
                                                                              <Formik
                                                                                  initialValues={{
                                                                                      comment: ''
                                                                                  }}
                                                                                  onSubmit={async (values, {}) => {
                                                                                      const linkArray = item._links.self.href.split('/');
                                                                                      dispatch(
                                                                                          caffActions.addComment({
                                                                                              id: linkArray[5],
                                                                                              comment_value: values.comment
                                                                                          })
                                                                                      );
                                                                                      values.comment = '';
                                                                                  }}
                                                                              >
                                                                                  {({
                                                                                      values,
                                                                                      handleSubmit,
                                                                                      isSubmitting,
                                                                                      handleBlur,
                                                                                      handleChange
                                                                                  }) => (
                                                                                      <>
                                                                                          <form noValidate onSubmit={handleSubmit}>
                                                                                              <FormControl
                                                                                                  sx={{ ...theme.typography.customInput }}
                                                                                              >
                                                                                                  <InputLabel htmlFor="comment">
                                                                                                      Write something...
                                                                                                  </InputLabel>
                                                                                                  <OutlinedInput
                                                                                                      id="comment"
                                                                                                      type="text"
                                                                                                      name="comment"
                                                                                                      value={values.comment}
                                                                                                      onBlur={handleBlur}
                                                                                                      onChange={handleChange}
                                                                                                      label="Comment"
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
                                                                                                          Add Comment
                                                                                                      </Button>
                                                                                                  </AnimateButton>
                                                                                              </Box>
                                                                                          </form>
                                                                                      </>
                                                                                  )}
                                                                              </Formik>
                                                                          </>
                                                                      )}
                                                                  </Grid>
                                                              </Grid>
                                                          </SubCard>
                                                      </div>
                                                  );
                                              })
                                            : caffPics &&
                                              caffPics.map((item, index) => {
                                                  return (
                                                      <div key={index}>
                                                          <SubCard title={item.title}>
                                                              <Grid container direction="column">
                                                                  <Grid item>
                                                                      <Box sx={{ m: 2.5 }}>
                                                                          <Grid container direction="row">
                                                                              <Grid item xs={12} sm={2}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      Description:
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item xs={12} sm={6}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      {item.description}
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                          </Grid>
                                                                          <Grid container direction="row">
                                                                              <Grid item xs={12} sm={2}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      Price:
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item xs={12} sm={6}>
                                                                                  <MuiTypography variant="h5" gutterBottom>
                                                                                      {item.price} $
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                          </Grid>
                                                                      </Box>
                                                                  </Grid>
                                                                  <Grid item>
                                                                      <Grid container direction="row" spacing={0}>
                                                                          <Grid item>
                                                                              <Grid container direction="row" spacing={1}>
                                                                                  <Grid item>
                                                                                      <Button
                                                                                          sx={{
                                                                                              borderRadius: 10,
                                                                                              width: 150,
                                                                                              height: 40
                                                                                          }}
                                                                                          variant="contained"
                                                                                          onClick={() => handleDownload(item)}
                                                                                          startIcon={<DownloadIcon />}
                                                                                      >
                                                                                          Download
                                                                                      </Button>
                                                                                  </Grid>
                                                                                  {actUser.roles[0].roleName == 'ROLE_ADMINISTRATOR' ? (
                                                                                      <>
                                                                                          <Grid item>
                                                                                              <Button
                                                                                                  sx={{
                                                                                                      borderRadius: 10,
                                                                                                      width: 100,
                                                                                                      height: 40
                                                                                                  }}
                                                                                                  variant="contained"
                                                                                                  onClick={() => handleUploadOpen(item)}
                                                                                                  startIcon={<EditIcon />}
                                                                                              >
                                                                                                  Edit
                                                                                              </Button>
                                                                                          </Grid>
                                                                                          <Grid item>
                                                                                              <Button
                                                                                                  sx={{
                                                                                                      borderRadius: 10,
                                                                                                      width: 100,
                                                                                                      height: 40
                                                                                                  }}
                                                                                                  variant="contained"
                                                                                                  onClick={() => onDeleteItem(item)}
                                                                                                  startIcon={<DeleteIcon />}
                                                                                              >
                                                                                                  Delete
                                                                                              </Button>
                                                                                          </Grid>{' '}
                                                                                      </>
                                                                                  ) : null}
                                                                              </Grid>
                                                                          </Grid>
                                                                      </Grid>
                                                                  </Grid>
                                                                  <Grid item sx={{ m: 2.0 }}>
                                                                      {commentsEnabled && (
                                                                          <>
                                                                              <Grid item>
                                                                                  <MuiTypography variant="caption" gutterBottom>
                                                                                      Comments
                                                                                  </MuiTypography>
                                                                              </Grid>
                                                                              <Grid item>
                                                                                  <List sx={{ maxWidth: 450 }}>
                                                                                      {item.userCommentList.map((comment, index) => {
                                                                                          return (
                                                                                              <div key={index}>
                                                                                                  <ListItem>
                                                                                                      <ListItemAvatar>
                                                                                                          <MyIcon
                                                                                                              color={yellow[400]}
                                                                                                              icon={
                                                                                                                  <CommentIcon fontSize="sm" />
                                                                                                              }
                                                                                                          />
                                                                                                      </ListItemAvatar>
                                                                                                      <ListItemButton
                                                                                                          sx={{ maxWidth: 550 }}
                                                                                                      >
                                                                                                          {comment.ownerName}:{' '}
                                                                                                          {comment.comment_value}
                                                                                                      </ListItemButton>
                                                                                                  </ListItem>
                                                                                              </div>
                                                                                          );
                                                                                      })}
                                                                                  </List>
                                                                              </Grid>
                                                                              <Formik
                                                                                  initialValues={{
                                                                                      comment: ''
                                                                                  }}
                                                                                  onSubmit={async (values, {}) => {
                                                                                      const linkArray = item._links.self.href.split('/');
                                                                                      dispatch(
                                                                                          caffActions.addComment({
                                                                                              id: linkArray[5],
                                                                                              comment_value: values.comment
                                                                                          })
                                                                                      );
                                                                                      values.comment = '';
                                                                                  }}
                                                                              >
                                                                                  {({
                                                                                      values,
                                                                                      handleSubmit,
                                                                                      isSubmitting,
                                                                                      handleBlur,
                                                                                      handleChange
                                                                                  }) => (
                                                                                      <>
                                                                                          <form noValidate onSubmit={handleSubmit}>
                                                                                              <FormControl
                                                                                                  sx={{ ...theme.typography.customInput }}
                                                                                              >
                                                                                                  <InputLabel htmlFor="comment">
                                                                                                      Write something...
                                                                                                  </InputLabel>
                                                                                                  <OutlinedInput
                                                                                                      id="comment"
                                                                                                      type="text"
                                                                                                      name="comment"
                                                                                                      value={values.comment}
                                                                                                      onBlur={handleBlur}
                                                                                                      onChange={handleChange}
                                                                                                      label="Comment"
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
                                                                                                          Add Comment
                                                                                                      </Button>
                                                                                                  </AnimateButton>
                                                                                              </Box>
                                                                                          </form>
                                                                                      </>
                                                                                  )}
                                                                              </Formik>
                                                                          </>
                                                                      )}
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
