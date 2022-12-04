import { configureStore } from '@reduxjs/toolkit';

import { authReducer } from './auth.slice';
import { usersReducer } from './users.slice';
import { caffReducer } from './caff.slice';
import customizationReducer from 'store/customizationReducer';
import thunk from 'redux-thunk';
import jwtRefreshMiddleware from './jwtRefreshMiddleware';

export * from './auth.slice';
export * from './users.slice';
export * from './caff.slice';

export const store = configureStore({
    reducer: {
        auth: authReducer,
        users: usersReducer,
        caff: caffReducer,
        customization: customizationReducer
    },
    middleware: [thunk, jwtRefreshMiddleware]
});
