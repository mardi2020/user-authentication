import * as React from 'react';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import {FormHelperText, Button} from '@mui/material/';
import {useCallback, useState} from "react";
import {changeName, changePassword, leave, logout} from "../api/api";

export default function MyInfoForm({ user, handleLogout, accessToken, setAccessToken }) {
    const [name, setName] = useState(user.name)
    const [password, setPassword] = useState('')
    const [rePassword, setRePassword] = useState('')

    const handleClickPasswordChange = useCallback(() => {
        if (password !== rePassword) {
            alert('패스워드가 불일치 합니다')
            return
        }
        changePassword(accessToken, setAccessToken, user, password).then((res) => {
            alert('비릴번호 변경 성공')
            logout(accessToken, setAccessToken).then(res => {
                handleLogout()
            })
        }).catch((e) => {
            alert('비밀번호가 너무 짧습ㄴ니다')
        })

    }, [accessToken, setAccessToken, user, handleLogout, password, rePassword])

    const handleClickNameChange = useCallback(() => {
        changeName(accessToken, setAccessToken, user, name).then((res) => {
            alert('이름 변경 성공')
            logout(accessToken, setAccessToken).then(res => {
                handleLogout()
            })
        })
    }, [accessToken, setAccessToken, user, handleLogout, name])

    const handleClickLeave = useCallback(() => {
        alert('회원탈퇴')
        leave(accessToken, setAccessToken).then((res) => {
            alert('회원탈퇴 되었습니다')
            handleLogout()
        }).catch((e) => {
            alert('회원탈퇴에 실패하였습니다.')
        })
    }, [accessToken, setAccessToken, handleLogout])

    return (
        <React.Fragment>
            <Grid container spacing={3}>
                <Grid item xs={12}>
                    <FormHelperText>이름</FormHelperText>
                    <TextField
                        id="firstName" name="firstName" fullWidth value={name} onChange={(e) => {setName(e.target.value)}} variant="standard"/>
                </Grid>
                <Grid item xs={12}>
                    <FormHelperText>이메일</FormHelperText>
                    <TextField
                        disabled={true}
                        id="email" name="email" fullWidth value={user.email} variant="standard"/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormHelperText>변경할 비빌번호</FormHelperText>
                    <TextField
                        margin="normal" required fullWidth name="password" type="password" id="password"
                        autoComplete="current-password" value={password} onChange={(e) => {setPassword(e.target.value)}}/>
                </Grid>
                <Grid item xs={12} sm={6}>
                    <FormHelperText>비밀번호 재입력</FormHelperText>
                    <TextField
                        margin="normal" required fullWidth name="re-password" type="password" id="re-password"
                        value={rePassword} onChange={(e) => {setRePassword(e.target.value)}} autoComplete="current-password"/>
                </Grid>
                <Grid align="center" item xs={12} sm={4}>
                    <Button onClick={handleClickPasswordChange} style={{color: "gray"}}>비밀번호 수정하기</Button>
                </Grid>

                <Grid align="center" item xs={12} sm={4}>
                    <Button onClick={handleClickNameChange} style={{color: "gray"}}>이름 수정하기</Button>
                </Grid>
                <Grid align="center" item xs={12} sm={4}>
                    <Button onClick={handleClickLeave} style={{color: "gray"}}>회원탈퇴하기</Button>
                </Grid>
            </Grid>
        </React.Fragment>
    );
}