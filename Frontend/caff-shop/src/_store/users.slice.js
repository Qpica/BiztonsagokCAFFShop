import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { register } from 'serviceWorker';

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
        register: register()
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
}

function createExtraReducers() {
    return {
        ...getAll(),
        ...register()
    };

    function getAll() {
        var { pending, fulfilled, rejected } = extraActions.getAll;
        return {
            [pending]: (state) => {
                state.users = { loading: true };
            },
            [fulfilled]: (state, action) => {
                state.users = action.payload;
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
            [fulfilled]: () => {},
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
}
