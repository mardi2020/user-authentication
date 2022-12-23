import React, {Component, useCallback, useEffect, useState} from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainPage from './page/mainPage';
import MyInfo from "./page/myInfo";
import SignIn from "./page/login";
import SignUp from "./page/signUp";
import AdminPage from "./page/adminPage";
import GlobalHeader from "./page/header";
import {getMyInfo} from "./api/api";
import Find from "./page/find";
import GroupPage from "./page/groupPage";

function App() {
  const [accessToken, setAccessToken] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(undefined);

  const handleLogout = useCallback(() => {
      setAccessToken('')
      setIsLoggedIn(false)
      setUser(undefined)
  }, [])

  useEffect(() => {
    if (accessToken !== '') {
      getMyInfo(accessToken, setAccessToken).then((res) => {
        setUser(res.data)
        console.log(res.data)
      })
    }
  }, [accessToken])

  return (
    <div className="App">
      <BrowserRouter>
        <GlobalHeader user={user} isLoggedIn={isLoggedIn} handleLogout={handleLogout} accessToken={accessToken} setAccessToken={setAccessToken}></GlobalHeader>
        <Routes>
          <Route path="/" element={<MainPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
            <Route path="/myInfo" element={<MyInfo user={user} isLoggedIn={isLoggedIn} handleLogout={handleLogout} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
            <Route path="/login" element={<SignIn isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken} />}></Route>
            <Route path="/join" element={<SignUp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
            <Route path="/admin" element={<AdminPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
          <Route path="/find" element={<Find isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
          <Route path="/groups" element={<GroupPage isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} accessToken={accessToken} setAccessToken={setAccessToken}  />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
