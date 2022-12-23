

import * as React from 'react';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import GlobalStyles from '@mui/material/GlobalStyles';
import Container from '@mui/material/Container';
import MyInfoForm from "./myInfoForm";
import {Navigate} from "react-router";
export default function MyInfo({ isLoggedIn, handleLogout, user, accessToken, setAccessToken }) {
    if (!isLoggedIn) {
        return <Navigate to="/login" />;
    }
    return (
        <React.Fragment>
            <GlobalStyles styles={{ ul: { margin: 0, padding: 0, listStyle: 'none' } }} />
            <CssBaseline />
            {/* Hero unit */}
            <Container disableGutters maxWidth="sm" component="main" sx={{ pt: 8, pb: 6 }}>
                <Typography
                    component="h1" variant="h2"
                    align="center" color="text.primary" gutterBottom>
                     내 정보
                </Typography>
                <MyInfoForm user={user} handleLogout={handleLogout} accessToken={accessToken} setAccessToken={setAccessToken}></MyInfoForm>
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
