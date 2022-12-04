import React, { useState } from 'react';

const ViewImage = ({ file }) => {
    const [view, setView] = useState(null);

    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
        setView(reader.result);
    };

    return (
        <div>
            <img src={view} alt="view" width="200px" height="200px" />
        </div>
    );
};

export default ViewImage;
