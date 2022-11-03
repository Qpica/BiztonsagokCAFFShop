// assets
import { IconBook2, IconUser } from '@tabler/icons';

// constant
const icons = {
    IconBook2,
    IconUser
};

// ==============================|| UTILITIES MENU ITEMS ||============================== //

const utilities = {
    id: 'features',
    title: 'Features',
    type: 'group',
    children: [
        {
            id: 'user-management',
            title: 'User management',
            type: 'item',
            url: '/user-management',
            icon: icons.IconUser,
            breadcrumbs: false
        },
        {
            id: 'library',
            title: 'Library',
            type: 'item',
            url: '/library',
            icon: icons.IconBook2,
            breadcrumbs: false
        }
    ]
};

export default utilities;
