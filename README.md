<h1>movie-db-api URL mapping</h1>

<h2>Login System</h2>
<h4>Login</h4>
<p>POST
<pre>/login?user=?&password=?</pre>

<h4>Register</h4>
<p>POST
<pre>/register?user=?&password=?</pre>


<h2>User</h2>
<h4>Get all Users</h4>
<p>GET
<pre>/user</pre>

<h4>Get one User</h4>
<p>GET
<pre>/user/[userId]</pre>

<h4>Get current User</h4>
<p>GET
<pre>/currentUser</pre>

<h4>Get current User</h4>
<p>POST
<pre>/user/[userId]/role/[role]</pre>


<h2>Movie</h2>
<h4>Get all Movies<h4>
<p>GET
<pre>/movie</pre>

<h4>Get one Movie<h4>
<p>GET
<pre>/movie/[movieId]</pre>

<h4>Search Movies<h4>
<p>GET
<pre>/user/search/[querry]</pre>


<h2>Request</h2>
<h4>Get all Requests<h4>
<p>GET
<pre>/request</pre>

<h4>Get Requests by UserId<h4>
<p>GET
<pre>/request/[userId]</pre>

<h4>Get one Request by Id<h4>
<p>GET
<pre>/request/[requestId]</pre>
