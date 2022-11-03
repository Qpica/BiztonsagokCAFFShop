import { lazy } from 'react';

// project imports
import MainLayout from 'layout/MainLayout';
import Loadable from 'ui-component/Loadable';
import UserManagement from 'views/features/UserManagement';
import { element } from 'prop-types';
import Library from 'views/features/Library';

// dashboard routing
const DashboardDefault = Loadable(lazy(() => import('views/dashboard/Default')));

// utilities routing
const UserManagementPath = Loadable(lazy(() => import('views/features/UserManagement')));
const LibraryPath = Loadable(lazy(() => import('views/features/Library')));

// ==============================|| MAIN ROUTING ||============================== //

const MainRoutes = {
    path: '/',
    element: <MainLayout />,
    children: [
        {
            path: '/',
            element: <LibraryPath />
        },
        {
            path: 'dashboard',
            children: [
                {
                    path: 'default',
                    element: <DashboardDefault />
                }
            ]
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
