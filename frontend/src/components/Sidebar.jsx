import { Link as LinkRouter } from 'react-router-dom';

import {
    AccountBox,
    Checklist,
    ModeNight,
    Settings,
    ControlPoint,
} from '@mui/icons-material';
import {
    Box,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Switch,
} from '@mui/material';
import React from 'react';

const Sidebar = ({ mode, setMode, isLoggedIn, handleLogout }) => {
    return (
        <Box flex={1} p={2} sx={{ display: { xs: 'none', sm: 'block' } }}>
            <Box position='-webkit-sticky'>
                <List>
                    
                    {isLoggedIn && (
                        <List>
                            <ListItem disablePadding>
                                <ListItemButton button component={LinkRouter} to='/calorie'>
                                    <ListItemIcon>
                                        <ControlPoint />
                                    </ListItemIcon>
                                    <ListItemText primary='Add calorie' />
                                </ListItemButton>
                            </ListItem>
                            <ListItem disablePadding>
                                <ListItemButton button component={LinkRouter} to='/yourDailyCalorie'
                                    
                                >
                                    <ListItemIcon>
                                        <Checklist />
                                    </ListItemIcon>
                                    <ListItemText primary='Check calories' />
                                </ListItemButton>
                            </ListItem>
                            <ListItem disablePadding>
                                
                                <ListItemButton button component={LinkRouter} to='/activity' >
                                    <ListItemIcon>
                                        <ControlPoint />
                                    </ListItemIcon>
                                    <ListItemText primary='Add activity' />
                                </ListItemButton>
                            </ListItem>
                            <ListItem disablePadding>
                                <ListItemButton
                                button component={LinkRouter} to='/yourDailyActivity'
                                    
                                >
                                    <ListItemIcon>
                                        <Checklist />
                                    </ListItemIcon>
                                    <ListItemText primary='Check activities' />
                                </ListItemButton>
                            </ListItem>
                            <ListItem disablePadding>
                                <ListItemButton
                                    button component={LinkRouter} to='/analyze'
                                >
                                    <ListItemIcon>
                                        <Settings />
                                    </ListItemIcon>
                                    <ListItemText primary='soon data?' />
                                </ListItemButton>
                            </ListItem>
                            <ListItem disablePadding>
                                <ListItemButton
                                    button component={LinkRouter} to="/profile"
                                >
                                    <ListItemIcon>
                                        <AccountBox />
                                    </ListItemIcon>
                                    <ListItemText primary='Profile' />
                                </ListItemButton>
                            </ListItem>
                        </List>
                    )}
                    <ListItem disablePadding>
                        <ListItemButton component='a' href='#simple-list'>
                            <ListItemIcon>
                                <ModeNight />
                            </ListItemIcon>
                            <Switch
                                onChange={(e) =>
                                    setMode(mode === 'light' ? 'dark' : 'light')
                                }
                            />
                        </ListItemButton>
                    </ListItem>
                </List>
            </Box>
            
        </Box>
    );
};

export default Sidebar;
