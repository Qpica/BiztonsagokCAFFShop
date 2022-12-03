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
        error: null
    };
}

function createExtraActions() {
    const baseUrl = `${process.env.REACT_APP_API_URL}/api/users`;

    return {
        getAll: getAll(),
        register: register(),
        deleteUser: deleteUser()
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
        return createAsyncThunk(
            `${name}/deleteUser`,
            async (username) => await fetchWrapper.delete(`${baseUrl}/${username}`, { username })
        );
    }
}

function createExtraReducers() {
    return {
        ...getAll(),
        ...register(),
        ...deleteUser()
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
        var { pending, fulfilled, rejected } = extraActions.register;
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
}
