import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import jwtDecode from 'jwt-decode';

import { fetchWrapper } from '_helpers/fetch-wrapper';
import { history } from '_helpers/history';

// create slice

const name = 'auth';
const initialState = createInitialState();
const reducers = createReducers();
const extraActions = createExtraActions();
const extraReducers = createExtraReducers();
const slice = createSlice({ name, initialState, reducers, extraReducers });

// exports

export const authActions = { ...slice.actions, ...extraActions };
export const authReducer = slice.reducer;

// implementation

function createInitialState() {
    return {
        // initialize state from local storage to enable user to stay logged in
        user: JSON.parse(localStorage.getItem('user')),
        error: null
    };
}

function createReducers() {
    return {
        logout
    };

    function logout(state) {
        state.user = null;
        localStorage.removeItem('user');
        history.navigate('/login');
    }
}

function createExtraActions() {
    const baseUrl = `${process.env.REACT_APP_API_URL}/api/auth`;

    return {
        login: login(),
        register: register(),
        refresh: refresh()
    };

    function login() {
        return createAsyncThunk(
            `${name}/login`,
            async ({ username, password }) => await fetchWrapper.post(`${baseUrl}/login`, { username, password })
        );
    }
    function register() {
        return createAsyncThunk(
            `${name}/register`,
            async ({ username, password }) => await fetchWrapper.post(`${baseUrl}/register`, { username, password })
        );
    }
    function refresh() {
        return createAsyncThunk(
            `${name}/refresh`,
            async ({ refreshToken }) => await fetchWrapper.post(`${baseUrl}/refresh`, { refreshToken })
        );
    }
}

function createExtraReducers() {
    return {
        ...login(),
        ...register(),
        ...refresh()
    };

    function login() {
        var { pending, fulfilled, rejected } = extraActions.login;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: (state, action) => {
                const _user = action.payload;
                const userData = { tokens: _user, data: jwtDecode(_user.accessToken) };
                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(_user));
                state.user = _user;

                // get return url from location state or default to home page
                const { from } = history.location.state || { from: { pathname: '/' } };
                history.navigate('/');
            },
            [rejected]: (state, action) => {
                state.error = action.error;
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
    function refresh() {
        var { pending, fulfilled, rejected } = extraActions.refresh;
        return {
            [pending]: (state) => {
                state.error = null;
            },
            [fulfilled]: () => {
                const user = action.payload;
                const userData = { tokens: _user, data: jwtDecode(_user.accessToken) };

                // store user details and jwt token in local storage to keep user logged in between page refreshes
                localStorage.setItem('user', JSON.stringify(user));
                state.user = user;

                const { from } = history.location.state || { from: { pathname: '/' } };
                history.navigate(from);
            },
            [rejected]: (state, action) => {
                state.error = action.error;
            }
        };
    }
}
