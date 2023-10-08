import React from 'react';
import Snackbar from '@mui/material/Snackbar';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import CloseIcon from '@mui/icons-material/Close';

const Notification = ({ open, onClose, message }) => {
    const action = (
        <>
            <Button color='secondary' size='small' onClick={onClose}>
                UNDO
            </Button>
            <IconButton
                size='small'
                aria-label='close'
                color='inherit'
                onClick={onClose}
            >
                <CloseIcon fontSize='small' />
            </IconButton>
        </>
    );

    return (
        <Snackbar
            open={open}
            autoHideDuration={4000}
            onClose={onClose}
            message={message}
            action={action}
        />
    );
};

export default Notification;
