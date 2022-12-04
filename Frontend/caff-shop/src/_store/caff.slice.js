import AddComment from '@mui/icons-material/AddComment';
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
        searchPictures: null,
        caffPicture: null,
        caffPictureData: null,
        error: null,
        pending: false
    };
}

function createReducers() {}

function createExtraActions() {
    const baseUrl = `${process.env.REACT_APP_API_URL}/api/caffPictures`;

    return {
        getAllCaffPicture: getAllCaffPicture(),
        getOneCaffPicture: getOneCaffPicture(),
        getOneCaffPictureData: getOneCaffPictureData(),
        postCaffPicture: postCaffPicture(),
        editCaffPicture: editCaffPicture(),
        deleteCaffPicture: deleteCaffPicture(),
        addComment: addComment(),
        search: search()
    };

    function getAllCaffPicture() {
        return createAsyncThunk(`${name}/getAllCaffPicture`, async () => await fetchWrapper.get(`${baseUrl}`));
    }
    function getOneCaffPicture() {
        return createAsyncThunk(
            `${name}/getOneCaffPicture`,
            async (id) => await fetchWrapper.get(`${baseUrl}/${id}`, null, null, null, null, true)
        );
    }
    function getOneCaffPictureData() {
        return createAsyncThunk(
            `${name}/getOneCaffPictureData`,
            async (id) => await fetchWrapper.get(`${baseUrl}/${id}/data`, null, null, true)
        );
    }
    function postCaffPicture() {
        return createAsyncThunk(`${name}/postCaffPicture`, async (formData) => await fetchWrapper.post(`${baseUrl}`, formData, true));
    }
    function editCaffPicture() {
        return createAsyncThunk(
            `${name}/editCaffPicture`,
            async ({ id, formData }) => await fetchWrapper.put(`${baseUrl}/${id}`, formData, true)
        );
    }
    function deleteCaffPicture() {
        return createAsyncThunk(`${name}/deleteCaffPicture`, async (id) => await fetchWrapper.delete(`${baseUrl}/${id}`));
    }
    function addComment() {
        return createAsyncThunk(
            `${name}/addComment`,
            async ({ id, comment_value }) => await fetchWrapper.post(`${baseUrl}/${id}/comments`, { comment_value })
        );
    }
    function search() {
        return createAsyncThunk(`${name}/search`, async (title) => await fetchWrapper.get(`${baseUrl}/search`, null, null, null, title));
    }
}

function createExtraReducers() {
    return {
        ...getAllCaffPicture(),
        ...getOneCaffPicture(),
        ...getOneCaffPictureData(),
        ...postCaffPicture(),
        ...editCaffPicture(),
        ...deleteCaffPicture(),
        ...addComment(),
        ...search()
    };

    function getAllCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.getAllCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                if (action.payload._embedded) {
                    state.allCaffPicture = action.payload._embedded.caffPictureResponseDTOList;
                } else {
                    state.allCaffPicture = null;
                }
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
    /*
    function postCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.postCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }*/
    function postCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.postCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }
    function editCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.editCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }
    function deleteCaffPicture() {
        var { pending, fulfilled, rejected } = extraActions.deleteCaffPicture;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }
    function addComment() {
        var { pending, fulfilled, rejected } = extraActions.addComment;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }
    function search() {
        var { pending, fulfilled, rejected } = extraActions.search;
        return {
            [pending]: (state) => {
                state.error = null;
                state.pending = true;
            },
            [fulfilled]: (state, action) => {
                state.searchPictures = action.payload._embedded.caffPictureResponseDTOList;
                state.pending = false;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
                state.pending = false;
            }
        };
    }
}
