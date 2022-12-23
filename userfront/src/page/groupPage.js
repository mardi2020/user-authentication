
import * as React from 'react';
import Button from '@mui/material/Button';
import CssBaseline from '@mui/material/CssBaseline';
import Typography from '@mui/material/Typography';
import GlobalStyles from '@mui/material/GlobalStyles';
import Container from '@mui/material/Container';
import Charts from "./chart";
import {useEffect, useState} from "react";
import {
    createGroup,
    getGroupInfo,
    getUserInfoByIds,
    joinGroup,
    leaveGroup
} from "../api/api";
import {Navigate} from "react-router";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";

export default function GroupPage({ isLoggedIn, accessToken, setAccessToken }) {
    const [group, setGroup] = useState(undefined)
    const [users, setUsers] = useState([])

    const [refresh, setRefresh] = useState(false)

    useEffect(() => {
        getGroupInfo(accessToken, setAccessToken).then(res => {
            const group = res.data
            setGroup(group)
            getUserInfoByIds(accessToken, setAccessToken, group.users).then(users => {
                setUsers(users)
            })
        }).catch(e => {
            setGroup(undefined)
        })
    }, [accessToken, setAccessToken, refresh])

    const handleCreateGroup = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            groupName: data.get('groupName'),
        });
        createGroup(accessToken, setAccessToken, data.get('groupName')).then((res) => {
            alert(res.data)
            setRefresh(true)
        }).catch((e) => {
            alert('그룹이 생성되지 못했다')
        })
    };

    const handleInviteUser = (event) => {
        event.preventDefault();
        const data = new FormData(event.currentTarget);
        console.log({
            name: data.get('name'),
        });
        joinGroup(accessToken, setAccessToken, group.groupId, data.get('name')).then((res) => {
            alert(res.data)
            setRefresh(true)
        }).catch((e) => {
            alert('그 사람은 없습니다.')
        })
    };

    const handleClickLeaveGroup = (event) => {
        event.preventDefault();
        leaveGroup(accessToken, setAccessToken, group.groupId).then((res) => {
            alert(res.data)
            setRefresh(true)
        }).catch((e) => {
            alert('그룹 나가기에 실패하였다.')
        })
    };

    if (!isLoggedIn) {
        return <Navigate to="/" />;
    }

    if (refresh) {
        return <Navigate to="/" />;
    }
    return (
        <React.Fragment>
            <GlobalStyles styles={{ ul: { margin: 0, padding: 0, listStyle: 'none' } }} />
            <CssBaseline />
            {/* Hero unit */}
            {!group && (
                <Box sx={{ marginTop: 8, display: 'flex',
                    flexDirection: 'column', alignItems: 'center',}} >

                    <Typography component="h1" variant="h5"> 그룹 생성 </Typography>
                    <Box component="form" noValidate onSubmit={handleCreateGroup} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    autoComplete="groupName" name="groupName"
                                    required fullWidth
                                    id="groupName" label="그룹명" autoFocus/>
                            </Grid>
                        </Grid>
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                            그룹생성
                        </Button>
                    </Box>
                </Box>
            )}
            {group && (
                <Box sx={{ marginTop: 8, display: 'flex',
                    flexDirection: 'column', alignItems: 'center',}} >

                    <Typography component="h1" variant="h5">{`${group.groupName}`}</Typography>
                    <Charts users={users}></Charts>
                    <Box component="form" noValidate onSubmit={handleInviteUser} sx={{ mt: 3 }}>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    autoComplete="name" name="name"
                                    required fullWidth
                                    id="name" label="사용자이름" autoFocus/>
                            </Grid>
                        </Grid>
                        <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                            그룹초대
                        </Button>
                    </Box>

                    <Button href="#" onClick={handleClickLeaveGroup} variant="outlined" sx={{my: 1, mx: 1.5}}>
                        그룹 나가기
                    </Button>
                </Box>
            )}
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