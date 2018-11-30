<h1>UserApi</h1>
<h3>Get User List <i>GET</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/user</pre>
<h4>Params</h4>
<p><b>name</b> String not required</p>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>User List</p>
<pre>
[
    {
        "id": 1,
        "name": "Administrator",
        "role": 2,
        "videoPlayer": null,
        "lastLogin": "2018-11-07T13:32:51.000+0000",
        "sexabig": true,
        "admin": true
    },
    {
        "id": 3,
        "name": "ALFA",
        "role": 1,
        "videoPlayer": null,
        "lastLogin": null,
        "sexabig": true,
        "admin": false
    },
    {
        "id": 2,
        "name": "Testperson",
        "role": 1,
        "videoPlayer": null,
        "lastLogin": null,
        "sexabig": false,
        "admin": false
    }
]
</pre>
<hr>
<h3>Get One User <i>GET</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/user/{userId}</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>User</p>
<pre>
{
    "id": 1,
    "name": "Administrator",
    "role": 2,
    "videoPlayer": null,
    "lastLogin": "2018-11-07T13:32:51.000+0000",
    "sexabig": true,
    "admin": true
}
</pre>
<hr>
<h3>Register User <i>POST</i></h3>
<p>Requires Administrator SessionKey</p>
<pre>movie.scorewinner.ch/api/user/new</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<p><b>name</b> String required</p>
<h4>Response</h4>
<p>Authkey</p>
<pre>
cac94142672fc9fae2c91daa8043f9bdacd8148efda9c2085862654662ee866e
</pre>
<hr>
<h3>Get Current User <i>GET</i></h3>
<pre>movie.scorewinner.ch/api/user/current</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>User</p>
<pre>
{
  "id": 3,
  "name": "Administrator",
  "role": 2,
  "admin": true
}
</pre>
<hr>
<h3>Login <i>POST</i></h3>
<pre>movie.scorewinner.ch/api/login</pre>
<h4>Params</b>
<p><b>name</b> String required</p>
<p><b>password</b> String required</p>
<h4>Response</h4>
<p>Logged in</p>
<pre>026b4940414868d15f23ace9ee568a1267a593cfcccf8c25151bfae7a16e1872</pre>
<p>Not logged in</p>
<pre>nok</pre>
<hr>
<h3>Logout <i>POST</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/login/logout</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Logged out</p>
<pre>ok</pre>
<p>Not logged out</p>
<pre>nok</pre>
