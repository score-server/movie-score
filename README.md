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

<h4>Set User Role</h4>
<p>POST
<pre>/user/[userId]/role/[role]</pre>


<h2>Movie</h2>
<h4>Get all Movies</h4>
<p>GET
<pre>/movie</pre>

<h4>Get one Movie</h4>
<p>GET
<pre>/movie/[movieId]</pre>

<h4>Search Movies</h4>
<p>GET
<pre>/movie/search/[querry]</pre>

<h4>Import Movies</h4>
<p>POST
<pre>/movie/add</pre>
<i>Open</i>

<h4>Set Import Path</h4>
<p>POST
<pre>/movie/path?path=?</pre>
<i>Open</i>


<h2>Request</h2>
<h4>Get all Requests</h4>
<p>GET
<pre>/request</pre>

<h4>Get Requests by UserId</h4>
<p>GET
<pre>/request/[userId]</pre>

<h4>Get one Request by Id/</h4>
<p>GET
<pre>/request/[requestId]</pre>

<h4>Add a Request (Requires user to be logged in)</h4>
<p>POST
<pre>/request/add?request=?</pre>

<h4>Close Request</h4>
<p>POST
<pre>/request/[requestId]/close</pre>
<i>Open</i>
