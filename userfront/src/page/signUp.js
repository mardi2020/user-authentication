import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {register} from "../api/api";
import {useState} from "react";
import {Navigate} from "react-router";


const theme = createTheme();

export default function SignUp() {

    const [registered, setRegistered] = useState(false)
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            name: data.get('name'),
            email: data.get('email'),
            password: data.get('password'),
        });
        register(data.get('email'), data.get('password'), data.get('name')).then((res) => {
            alert(res.data.message)
            setRegistered(true)
        }).catch((e) => {
            alert('중복된 이름이나 이메일 입니다')
        })
    };

    if (registered) {
        return <Navigate to="/" />;
    }

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box sx={{ marginTop: 8, display: 'flex',
                        flexDirection: 'column', alignItems: 'center',}} >

                    <Typography component="h1" variant="h5"> 회원가입 </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    autoComplete="name" name="name"
                                    required fullWidth
                                    id="name" label="이름" autoFocus/>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required fullWidth
                                    id="email" label="이메일"
                                    name="email" autoComplete="email"/>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required fullWidth
                                    name="password" label="비밀번호"
                                    type="password" id="password" autoComplete="new-password"/>
                            </Grid>
                        </Grid>
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                            회원가입
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}