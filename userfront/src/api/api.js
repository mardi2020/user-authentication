import axios from "axios";

const userServiceUrl = "http://localhost:8000/user-service";
const findInfoService = "http://localhost:8000/find-info-service"
const groupServiceUrl = "http://localhost:8000/group-service";
const joinServiceUrl = "http://localhost:8000/join-service";
const userManageServiceUrl = "http://localhost:8000/user-manage-service";

export const register = (email, password, name) => {
    return axios.post(joinServiceUrl + "/join", {
        email,
        password,
        name,
    })
};

export const leave = async (token, setToken) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.delete(joinServiceUrl + '/leave', {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}


export const login = (email, password) => {
    return axios.post(userServiceUrl + "/login", {
        email,
        password
    }, {withCredentials: true})
};

export const getMyInfo = async (token, setToken) => {
    const realToken = await checkTokenAndRefresh(token, setToken)

    const userId = await axios.get(userServiceUrl + "/token/check", {
        headers: {
            ...getAuthHeader(token)
        },
        withCredentials: true
    })

    return await axios.get(findInfoService + `/my/${userId.data}`, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
};

export const getUserInfoByIds = async (token, setToken, ids) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    const userInfos = []
    for (let i = 0; i < ids.length ; i++ ) {
        const id = ids[i]
        const userInfo = await axios.get(findInfoService + `/${id}`, {
            headers: {
                ...getAuthHeader(realToken)
            },
            withCredentials: true
        })
        userInfos.push(userInfo.data)
    }
    return userInfos
}

export const changePassword = async (token, setToken, user, pw) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.patch(findInfoService + "/password", {
        email: user.email,
        password: pw,
        name: user.name
    }, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}

export const changeName = async (token, setToken, user, name) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.patch(findInfoService + "/name", {
        id: user.userId,
        name: name
    }, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}

export const logout = async (token, setToken) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.post(userServiceUrl + "/logout", {}, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true})
};


export const getAllUsers = async (token, setToken) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.get(userManageServiceUrl + "/admin", {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}

export const findUser = async (name) => {
    return await axios.get(findInfoService + "/email", {
        params: {
            name
        }
    })
}

export const findUserIdByName = async (name) => {
    return await axios.get(findInfoService + "/id", {
        params: {
            name
        }
    })
}


export const getGroupInfo = async (token, setToken) => {
    const user = await getMyInfo(token, setToken)
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.get(groupServiceUrl + `/group/${user.data.userId}`, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}

export const createGroup = async (token, setToken, groupName) => {
    const user = await getMyInfo(token, setToken)
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.post(groupServiceUrl + `/group`, {
        userId: user.data.userId,
        groupName: groupName,
    }, {
        headers: {
            ...getAuthHeader(realToken)
        },
        withCredentials: true
    })
}

export const joinGroup = async (token, setToken, groupId, userName) => {
    const realToken = await checkTokenAndRefresh(token, setToken)
    const userId = await findUserIdByName(userName)
    return await axios.post(groupServiceUrl + `/group/${groupId}/${userId.data}`,
        {},
        {
            headers: {
                ...getAuthHeader(realToken)
            },
            withCredentials: true
        })
}

export const leaveGroup = async (token, setToken, groupId) => {
    const user = await getMyInfo(token, setToken)
    const realToken = await checkTokenAndRefresh(token, setToken)
    return await axios.delete(groupServiceUrl + `/group/${groupId}/${user.data.userId}`,
        {
            headers: {
                ...getAuthHeader(realToken)
            },
            withCredentials: true
        })
}

const checkTokenAndRefresh = async (token, setToken) => {
    try {
        await axios.get(userServiceUrl + "/token/check", {
            headers: {
                ...getAuthHeader(token)
            },
            withCredentials: true
        })
    } catch (e) {
        const res = await axios.get(userServiceUrl + "/token/reissue", {
            headers: {
                ...getAuthHeader(token)
            },
            withCredentials: true
        })
        setToken(res.data.accessToken)
        return res.data.accessToken
    }
    return token
}

const getAuthHeader = (token) => {
    return {
        Authorization: `Bearer ${token}`
    }
}
