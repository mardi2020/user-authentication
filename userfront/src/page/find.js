import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import TextField from '@mui/material/TextField';
import Grid from '@mui/material/Grid';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Container from '@mui/material/Container';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import {findUser} from "../api/api";

const theme = createTheme();

export default function Find() {
    const handleSubmit = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            email: data.get('name'),
        });
        findUser(data.get('name')).then((res) => {
            alert(res.data)
        }).catch((e) => {
            alert('찾을 수 없습니다')
        })
    };

    return (
        <ThemeProvider theme={theme}>
            <Container component="main" maxWidth="xs">
                <CssBaseline />
                <Box sx={{ marginTop: 8, display: 'flex',
                        flexDirection: 'column', alignItems: 'center',}} >

                    <Typography component="h1" variant="h5"> 어아디 찾기 </Typography>
                    <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    required fullWidth
                                    id="name" label="이름"
                                    name="name" autoComplete="name"/>
                            </Grid>
                        </Grid>
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                            아이디 찾기
                        </Button>
                    </Box>
                </Box>
            </Container>
        </ThemeProvider>
    );
}