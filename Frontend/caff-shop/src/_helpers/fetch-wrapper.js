import { store, authActions } from '_store';

export const fetchWrapper = {
    get: request('GET'),
    post: request('POST'),
    put: request('PUT'),
    delete: request('DELETE')
};

function request(method) {
    return (url, body, isMedia, isCaffDown, param) => {
        const requestOptions = {
            method,
            headers: authHeader(url)
        };
        if (body) {
            if (isMedia) {
                requestOptions.headers['Accept'] = 'application/json';
                //requestOptions.headers['Content-Type'] = 'application/x-www-form-urlencoded';
                //requestOptions.headers['Access-Control-Allow-Headers'] = 'Authorization';
                //requestOptions.headers['Content-Type'] = 'multipart/form-data; boundary=WebAppBoundary';
                //requestOptions.headers['Content-Length'] = body.length;
                requestOptions.body = body;
            } else {
                requestOptions.headers['Access-Control-Allow-Origin'] = '*';
                requestOptions.headers['Access-Control-Allow-Methods'] = 'POST, PUT, GET, OPTIONS, DELETE';
                requestOptions.headers['Access-Control-Allow-Headers'] = 'Authorization, Content-Type';
                requestOptions.headers['Content-Type'] = 'application/json';
                requestOptions.body = JSON.stringify(body);
            }
        }
        console.log(body, method);
        if (isCaffDown) {
            return fetch(url, requestOptions).then(handleResponseCaff);
        } else if (param) {
            console.log(param);
            return fetch(url + '?' + new URLSearchParams(param), requestOptions).then(handleResponse);
        } else {
            return fetch(url, requestOptions).then(handleResponse);
        }
    };
}

// helper functions

function authHeader(url) {
    // return auth header with jwt if user is logged in and request is to the api url
    const token = authToken();
    const isLoggedIn = !!token;
    const isApiUrl = url.startsWith(process.env.REACT_APP_API_URL);
    if (isLoggedIn && isApiUrl) {
        return { Authorization: `Bearer ${token}` };
    } else {
        return {};
    }
}

function authToken() {
    return store.getState().auth.user?.accessToken;
}

function handleResponse(response) {
    return response.text().then((text) => {
        const data = text && JSON.parse(text);

        if (!response.ok) {
            if ([401, 403].includes(response.status) && authToken()) {
                // auto logout if 401 Unauthorized or 403 Forbidden response returned from api
                const logout = () => store.dispatch(authActions.logout());
                logout();
            }

            const error = (data && data.message) || response.statusText;
            return Promise.reject(error);
        }

        return data;
    });
}

function handleResponseCaff(response) {
    return response;
}
