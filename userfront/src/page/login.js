import * as React from 'react';
import { Navigate } from "react-router";
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import {Link} from "react-router-dom";
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {login} from "../api/api";
import {useState} from "react";

const theme = createTheme();

export default function SignIn({ isLoggedIn, setIsLoggedIn, accessToken, setAccessToken }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const handleSubmit = (e) => {
        e.preventDefault();

        // console.log(isLoggedIn);
        login(email, password).then((res) => {
                setIsLoggedIn(true);
                setAccessToken(res.data["access-token"]);
            }).catch((error) => {
                alert('로그인 실패');
        })
    };

    if (isLoggedIn) {
        return <Navigate to="/" />;
    }

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box sx={{marginTop: 8, display: 'flex', flexDirection: 'column', alignItems: 'center',}}>
                    <Typography component="h1" variant="h5">
                        Login
                    </Typography>
                    <Box component="form" noValidate sx={{ mt: 1 }} onSubmit={handleSubmit}>
                        <TextField
                            margin="normal" required fullWidth id="email" value={email}
                            label="아이디" name="email" autoComplete="email" onChange={e => setEmail(e.target.value)}
                            autoFocus/>
                        <TextField
                            margin="normal" required fullWidth value={password}
                            name="password" label="비밀번호" onChange={e => setPassword(e.target.value)}
                            type="password" id="password"/>
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} >
                            로그인
                        </Button>
                        <Grid container>
                            <Grid item xs>
                                <Link to="/find" variant="body2" underline="none" style={{ textDecoration: "none", color: "black"}}>
                                    🔍 아이디 찾기
                                </Link>
                            </Grid>
                            <Grid item>
                                <Link to="/join" variant="body2" underline="none" style={{ textDecoration: "none", color: "black"}}>
                                    📍회원가입
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}