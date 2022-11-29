import { lazy } from 'react';

// project imports
import MainLayout from 'layout/MainLayout';
import { PrivateRoute } from 'ui-component/PrivateRoute';
import Loadable from 'ui-component/Loadable';
import { element } from 'prop-types';

// utilities routing
const UserManagementPath = Loadable(lazy(() => import('views/features/UserManagement')));
const LibraryPath = Loadable(lazy(() => import('views/features/Library')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: (
        <PrivateRoute>
            <MainLayout />
        </PrivateRoute>
    ),
    children: [
        {
            path: '/',
            element: <LibraryPath />
        },
        {
            path: 'user-management',
            element: <UserManagementPath />
        },
        {
            path: 'library',
            element: <LibraryPath />
        }
    ]
};

export default MainRoutes;
