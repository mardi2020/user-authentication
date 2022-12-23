

import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import GlobalStyles from '@mui/material/GlobalStyles';
import Container from '@mui/material/Container';
import Charts from "./chart";
import {useEffect, useState} from "react";
import {getAllUsers} from "../api/api";

export default function AdminPage({ isLoggedIn, setIsLoggeIn, accessToken, setAccessToken }) {
    const [users, setUsers] = useState([])

    useEffect(() => {
        getAllUsers(accessToken, setAccessToken).then((res) => {
            setUsers(res.data)
        })
    }, [accessToken, setAccessToken])

    return (
        <React.Fragment>
            <GlobalStyles styles={{ ul: { margin: 0, padding: 0, listStyle: 'none' } }} />
            <CssBaseline />
            {/* Hero unit */}
            <Container disableGutters maxWidth="sm" component="main" sx={{ pt: 8, pb: 6 }}>
                <Typography
                    component="h1" variant="h2"
                    align="center" color="text.primary" gutterBottom>
                    사용자 조회
                </Typography>
                <Charts users={users}></Charts>
            </Container>
            {/* End hero unit */}
            {/* Footer */}
            <Container
                maxWidth="md"
                component="footer"
                sx={{
                    borderTop: (theme) => `1px solid ${theme.palette.divider}`,
                    mt: 8,
                    py: [3, 6],
                }}
            >
            </Container>
            {/* End footer */}
        </React.Fragment>
    );
}