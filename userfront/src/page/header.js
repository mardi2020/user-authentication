import React, {useState} from 'react';
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import {Link} from "react-router-dom";
import Button from "@mui/material/Button";
import {logout} from "../api/api";

const titles = {
    "ROLE_USER": "MYUSER",
    "ROLE_ADMIN": "ADMIN"
};
export default function GlobalHeader({user, isLoggedIn, handleLogout, accessToken, setAccessToken}) {
    const [role, setRole] = useState("ROLE_USER");

    const handleClickLogout = (e) => {
        e.preventDefault();
        logout(accessToken, setAccessToken).then((res) => {
            handleLogout()
        })
    }

    return (
        <AppBar
            position="static" color="default" elevation={0}
            sx={{borderBottom: (theme) => `1px solid ${theme.palette.divider}`}}>
            <Toolbar sx={{flexWrap: 'wrap'}}>
                <Typography variant="h6" color="inherit" noWrap sx={{flexGrow: 1}}>
                    <Link to='/' style={{textDecoration: "none", color: "black"}}>{`${titles[role]}`}</Link>
                </Typography>
                {user?.role === "ROLE_ADMIN" && (
                    <Link to="/admin" style={{textDecoration: "none"}}>
                        <Button href="#" variant="outlined" sx={{my: 1, mx: 1.5}}>
                            관리자페이지
                        </Button>
                    </Link>
                )}
                {!isLoggedIn && (
                    <>
                        <Link to="/login" style={{textDecoration: "none"}}>
                            <Button href="#" variant="outlined" sx={{my: 1, mx: 1.5}}>
                                로그인
                            </Button>
                        </Link>
                        <Link to="/join" style={{textDecoration: "none"}}>
                            <Button href="#" variant="outlined" sx={{my: 1, mx: 1.5}}>
                                회원가입
                            </Button>
                        </Link>
                    </>
                )}
                {isLoggedIn && (
                    <>
                        <Link to='/groups' style={{textDecoration: "none", color: "black"}}
                              variant="button" color="text.primary"
                              sx={{my: 1, mx: 1.5}} underline="none"
                        >
                            <Button href="#" variant="outlined" sx={{my: 1, mx: 1.5}}>
                                내 그룹
                            </Button>
                        </Link>
                        <Link to='/myInfo' style={{textDecoration: "none", color: "black"}}
                              variant="button" color="text.primary"
                              sx={{my: 1, mx: 1.5}} underline="none"
                        >
                            <Button href="#" variant="outlined" sx={{my: 1, mx: 1.5}}>
                                내프로필
                            </Button>
                        </Link>
                        <Button href="#" onClick={handleClickLogout} variant="outlined" sx={{my: 1, mx: 1.5}}>
                            로그아웃
                        </Button>
                    </>
                )}
            </Toolbar>
        </AppBar>
    );
}