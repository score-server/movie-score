<h1>UserApi</h1>
<h3>Get User List <i>GET</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/user</pre>
<b>Params</b>
<p><i>name</i> String not required</p>
<p>User List</p>
<pre>
[
  {
    "id": 3,
    "name": "Administrator",
    "role": 2,
    "admin": true
  },
  {
    "id": 4,
    "name": "Low",
    "role": 1,
    "admin": false
  }
]
</pre>
<hr>
<h3>Get One User <i>GET</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/user</pre>
<h4>Response</h4>
<p>User</p>
<pre>
{
  "id": 4,
  "name": "Low",
  "role": 1,
  "admin": false
}
</pre>
<hr>
<h3>Login <i>POST</i></h3>
<pre>movie.scorewinner.ch/api/login
<b>Params</b>
<p><i>name</i> String required</p>
<p><i>password</i> String required</p>
<h4>Response</h4>
<p>Logged in</p>
<pre>ok</pre>
<p>Not logged in</p>
<pre>nok</pre>
<hr>
<h3>Logout <i>POST</i></h3>
<p>Requires to be Logged in</p>
<pre>movie.scorewinner.ch/api/login/logout</pre>
<h4>Response</h4>
<p>Logged out</p>
<pre>ok</pre>
<p>Not logged out</p>
<pre>nok</pre>