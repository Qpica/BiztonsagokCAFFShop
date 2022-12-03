// assets
import { IconUser } from '@tabler/icons';

// ==============================|| EXTRA PAGES MENU ITEMS ||============================== //

const profile = {
    id: 'profile',
    title: 'Profile',
    type: 'group',
    children: [
        {
            id: 'profile',
            title: 'My Profile',
            type: 'item',
            url: '/profile',
            icon: IconUser,
            breadcrumbs: false
        }
    ]
};

export default profile;
