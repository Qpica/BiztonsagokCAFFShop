import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { history } from '_helpers/history';

import { fetchWrapper } from '_helpers/fetch-wrapper';

// create slice

const name = 'users';
const initialState = createInitialState();
const extraActions = createExtraActions();
const extraReducers = createExtraReducers();
const slice = createSlice({ name, initialState, extraReducers });

// exports

export const userActions = { ...slice.actions, ...extraActions };
export const usersReducer = slice.reducer;

// implementation

function createInitialState() {
    return {
        users: {},
        error: null,
        act_user: null,
        role: null,
        act_role: null
    };
}

function createExtraActions() {
    const baseUrl = `${process.env.REACT_APP_API_URL}/api/users`;

    return {
        getAll: getAll(),
        register: register(),
        deleteUser: deleteUser(),
        editUser: editUser(),
        getUser: getUser()
    };

    function getAll() {
        return createAsyncThunk(`${name}/getAll`, async () => await fetchWrapper.get(baseUrl));
    }
    function register() {
        return createAsyncThunk(
            `${name}/register`,
            async ({ username, password }) => await fetchWrapper.post(`${baseUrl}/register`, { username, password })
        );
    }
    function deleteUser() {
        return createAsyncThunk(`${name}/deleteUser`, async (username) => await fetchWrapper.delete(`${baseUrl}/${username}`));
    }
    function editUser() {
        return createAsyncThunk(`${name}/editUser`, async (username) => await fetchWrapper.put(`${baseUrl}/${username}`, { username }));
    }
    function getUser() {
        return createAsyncThunk(`${name}/getUser`, async (username) => await fetchWrapper.get(`${baseUrl}/${username}`));
    }
}

function createExtraReducers() {
    return {
        ...getAll(),
        ...register(),
        ...deleteUser(),
        ...editUser(),
        ...getUser()
    };

    function getAll() {
        var { pending, fulfilled, rejected } = extraActions.getAll;
        return {
            [pending]: (state) => {
                state.users = { loading: true };
            },
            [fulfilled]: (state, action) => {
                state.users = action.payload._embedded.userResponseDTOList;
            },
            [rejected]: (state, action) => {
                state.users = { error: action.error };
            }
        };
    }
    function register() {
        var { pending, fulfilled, rejected } = extraActions.register;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: () => {
                history.navigate(`/login`);
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
    function deleteUser() {
        var { pending, fulfilled, rejected } = extraActions.deleteUser;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: () => {},
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
    function editUser() {
        var { pending, fulfilled, rejected } = extraActions.editUser;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: () => {},
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
    function getUser() {
        var { pending, fulfilled, rejected } = extraActions.getUser;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                state.act_user = action.payload;
                state.role = action.payload.roles[0].roleName;
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
}
