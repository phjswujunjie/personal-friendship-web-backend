const header = {
    headers: {
        token:window.localStorage.getItem("token")
    }
}
Vue.component('headers', {
    template:"<nav class=\"navbar navbar-default\" style='margin-bottom: 10px'>\n" +
        "    <div class=\"container\">\n" +
        "        <!-- Brand and toggle get grouped for better mobile display -->\n" +
        "        <div class=\"navbar-header\">\n" +
        "            <button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\" aria-expanded=\"false\">\n" +
        "                <span class=\"sr-only\">Toggle navigation</span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "                <span class=\"icon-bar\"></span>\n" +
        "            </button>\n" +
        "        </div>\n" +
        "\n" +
        "        <!-- Collect the nav links, forms, and other content for toggling -->\n" +
        "        <div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\" style='position: relative'>\n" +
        "            <ul class=\"nav navbar-nav\">\n" +
        "                <li class=\"background-style\" style='position: absolute;left: 200px'  title=\"主页\"><a href='https://localhost:8443/homepage' style='cursor: pointer'><span class=\"glyphicon glyphicon-home\"  style=\"font-size: 23px;\"></span></a></li>\n" +
        "                <li class=\"background-style\" style='position: absolute;left: 400px' title=\"周围\"><a href='https://localhost:8443/around' style='cursor: pointer'><span class=\"glyphicon glyphicon-globe\" style=\"font-size: 23px;\"></span></a></li>\n" +
        "            </ul>\n" +
        "            <ul class=\"nav navbar-nav navbar-right\">\n" +
        "                <li v-if=\"status===true\" v-cloak class=\"background-style\" style='position: absolute;left: 600px' title=\"个人\"><a  style='cursor: pointer'><img :src=\"image\" style='position:relative;bottom: 4px'  width=\"32px\" height=\"32px\" class=\"img-rounded\" alt=\"头像\"/></a></li>\n" +
        "                <li v-else v-cloak class=\"background-style\" style='position: absolute;left: 600px' title=\"个人\"><a href='https://localhost:8443/login'  style='cursor: pointer'><span class=\"glyphicon glyphicon-user\" style=\"font-size: 23px;\"></span></a></li>\n" +
        "                <li v-if=\"status===true\" class=\"background-style\" style='position: absolute;left: 800px'   title=\"发博客\" ><a href='https://localhost:8443/createBlog'   style='cursor: pointer'><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 23px;\"></span></a></li>\n" +
        "                <li v-else class=\"background-style\"   title=\"发博客\" style='position: absolute;left: 800px' ><a href='https://localhost:8443/login' style='cursor: pointer'><span class=\"glyphicon glyphicon-edit\" style=\"font-size: 23px;\"></span></a></li>\n" +
        "            </ul>\n" +
        "<ul  v-if=\"status===true\" class=\"nav navbar-nav navbar-right\" style=\"position: relative; left: 170px\">\n" +
        "        <li class=\"dropdown\">\n" +
        "          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\"><span class=\"glyphicon glyphicon-cog\" style=\"font-size: 23px;\"></span></a>\n" +
        "          <ul class=\"dropdown-menu\">\n" +
        "            <li><a href=\"#\"></a></li>\n" +
        "            <li><a href=\"https://localhost:8443/personalInfo\">账号设置</a></li>\n" +
        "            <li><a href=\"https://localhost:8443/editPhoto\">设置头像</a></li>\n" +
        "            <li><a href=\"#\">账号安全</a></li>\n" +
        "            <li><a @click='loginOut' style='cursor: pointer'>退出登录</a></li>\n" +
        "          </ul>\n" +
        "        </li>\n" +
        "      </ul>" +
        "        </div><!-- /.navbar-collapse -->\n" +
        "\n" +
        "    </div><!-- /.container-fluid -->\n" +
        "</nav>",
    data:function () {
        return {
            status:false,
            image:'',
        }
    },
    methods:{
        //退出登录,将token设置为空字符串即可
        loginOut: function () {
            window.localStorage.setItem("token", "")
            location.reload()
        }
    },
    created(){
        console.log("开始发送请求")
        //当头部被创建的时候向后端发送请求得到头像地址
        axios.get("https://localhost:8443/users/avatars", header)
            .then(res => {
                if(res.data.code === 20001){
                    this.image = "https://localhost:8443/static/upload/" + res.data.data.avatar
                    this.status = true
                }
            })
    }
})

new Vue({
    el:"#header",
})