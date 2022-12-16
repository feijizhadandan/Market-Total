const personalInfo = {
    state: {
        // 用户基本信息
        userDetails: {
            id: -1,
            userName: '飞机炸弹',
            introduction: "你好，我是飞机炸弹",
            photoUrl: "https://pic2.zhimg.com/v2-cd4c7ce9f3ba888cd20ff5d8c81590a1_r.jpg",
            address: "小谷围派出所",
            gender: 2,  // 1：男 2：女
            phone: "13248957845",
            email: "215628266@qq.com",
            followerCount: 0,
            experienceValue: 0,
        },
        // Token
        token: localStorage.getItem('token'),
        // 登录状态（默认为已登录状态）
        hasLogin: true
    },
    // 类似计算属性，可以返回加工后的值
    getters: {
        getLoginStatus(state) {
            console.log(state.hasLogin)
            return state.hasLogin;
        }
    },  
    // 更改状态只能通过 mutations 进行更改
    mutations: {
        changePhotoUrl(state, para) {
            state.photoUrl = para
        },
        // 更新 token 值
        setToken(state, newToken) {
            state.token = newToken
            // 将 token 存到本地缓存中
            localStorage.token = newToken;
        },
        // 修改登录状态
        changeLoginStatus(state, status) {
        state.hasLogin = status
      }
    },
    // 可以用于发送异步请求
    actions: {
    },
}

export default personalInfo