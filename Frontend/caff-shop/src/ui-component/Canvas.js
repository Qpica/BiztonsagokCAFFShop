import React from 'react';
import PropTypes from 'prop-types';
import { drawerClasses } from '@mui/material';

const Canvas = ({ item, draw }) => {
    const canvas = React.useRef();

    React.useEffect(() => {
        const context = canvas.current.getContext('2d');
        draw(context, item);
    });

    return <canvas ref={canvas} height={item.height} width={item.width} />;
};

/*Canvas.propTypes = {
    draw: PropTypes.func.isRequired,
    height: PropTypes.number.isRequired,
    width: PropTypes.number.isRequired
};*/

export default Canvas;
