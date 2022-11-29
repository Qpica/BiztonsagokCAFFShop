import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';

import { fetchWrapper } from '_helpers/fetch-wrapper';
import { history } from '_helpers/history';

// create slice

const name = 'caff';
const initialState = createInitialState();
const reducers = createReducers();
const extraActions = createExtraActions();
const extraReducers = createExtraReducers();
const slice = createSlice({ name, initialState, reducers, extraReducers });

// exports

export const caffActions = { ...slice.actions, ...extraActions };
export const caffReducer = slice.reducer;

// implementation

function createInitialState() {
    return {
        allCaffPicture: null,
        caffPicture: null,
        caffPictureData: null,
        error: null
    };
}

function createReducers() {}

function createExtraActions() {
    const baseUrl = `${process.env.REACT_APP_API_URL}/api/caffPictures`;

    return {
        getAllCaffPicture: getAllCaffPicture(),
        getOneCaffPicture: getOneCaffPicture(),
        getOneCaffPictureData: getOneCaffPictureData()
    };

    function getAllCaffPicture() {
        return createAsyncThunk(`${name}/getAllCaffPicture`, async () => await fetchWrapper.get(`${baseUrl}`));
    }
    function getOneCaffPicture() {
        return createAsyncThunk(`${name}/getOneCaffPicture`, async ({ id }) => await fetchWrapper.get(`${baseUrl}/${id}`, { id }));
    }
    function getOneCaffPictureData() {
        return createAsyncThunk(`${name}/getOneCaffPictureData`, async ({ id }) => await fetchWrapper.get(`${baseUrl}/${id}/data`, { id }));
    }
}

function createExtraReducers() {
    return {
        getAllCaffPicture: getAllCaffPicture(),
        getOneCaffPicturer: getOneCaffPicture(),
        getOneCaffPictureData: getOneCaffPictureData()
    };

    function getAllCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.getAllCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                state.allCaffPicture = action.payload;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
    function getOneCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.getOneCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                state.caffPicture = action.payload;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
    function getOneCaffPictureData() {
        var { pending, fulfilled, rejected } = extraActions.getOneCaffPictureData;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                state.caffPictureData = action.payload;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
}
