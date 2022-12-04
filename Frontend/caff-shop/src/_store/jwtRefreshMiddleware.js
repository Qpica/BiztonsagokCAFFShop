import jwtDecode from 'jwt-decode';
import moment from 'moment';

import { authActions } from '_store';

const jwtRefreshMiddleware = ({ dispatch, getState }) => {
    return (next) => (action) => {
        if (typeof action === 'function') {
            const access_token = getState().auth.user.accessToken;
            if (access_token) {
                const decoded = jwtDecode(access_token);
                //TODO
                if (decoded.exp && decoded.exp - moment().unix() < 10) {
                    const isStillRefreshing = getState().auth.user.refreshingToken;
                    if (!isStillRefreshing) {
                        dispatch(authActions.refresh())
                            .then(() => {
                                return next(action);
                            })
                            .catch((error) => {
                                return dispatch(authActions.logout());
                            });
                    }
                }
            }
        }
        return next(action);
    };
};

export default jwtRefreshMiddleware;
