# Movie Score
<a href="https://score-server.github.io/movie-score/">Demo</a>
<br>
<br>
<img src="https://img.shields.io/badge/version-2.8.2-green.svg">
<img src="https://img.shields.io/github/last-commit/score-server/movie-score.svg">
<img src="https://img.shields.io/github/repo-size/score-server/movie-score.svg">

Movie streaming website with Java Spring

# User API

### Get User List <i>GET</i>

<p>Requires to be Logged in</p>
<pre>/api/user</pre>
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
        "admin": true
    },
    {
        "id": 3,
        "name": "ALFA",
        "role": 1,
        "videoPlayer": null,
        "lastLogin": null,
        "admin": false
    },
    {
        "id": 2,
        "name": "Testperson",
        "role": 1,
        "videoPlayer": null,
        "lastLogin": null,
        "admin": false
    }
]
</pre>
<hr>

### Get One User <i>GET</i>

<p>Requires to be Logged in</p>
<pre>/api/user/{userId}</pre>
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
    "admin": true
}
</pre>
<hr>

### Register User <i>POST</i>

<p>Requires Administrator SessionKey</p>
<pre>/api/user/new</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<p><b>name</b> String required</p>
<h4>Response</h4>
<p>Authkey</p>
<pre>
cac94142672fc9fae2c91daa8043f9bdacd8148efda9c2085862654662ee866e
</pre>
<hr>

### Get Current User <i>GET</i>

<pre>/api/user/current</pre>
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

### Login <i>POST</i>

<pre>/api/login</pre>
<h4>Params</b>
<p><b>name</b> String required</p>
<p><b>password</b> String required</p>
<h4>Response</h4>
<p>Logged in</p>
<pre>026b4940414868d15f23ace9ee568a1267a593cfcccf8c25151bfae7a16e1872</pre>
<p>Not logged in</p>
<pre>nok</pre>
<hr>

### Logout <i>POST</i>

<p>Requires to be Logged in</p>
<pre>/api/login/logout</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Logged out</p>
<pre>ok</pre>
<p>Not logged out</p>
<pre>nok</pre>

<hr>

# Movie API

### Get Movie List <i>GET</i>

<p>Requires to be Logged in</p>
<pre>/api/movie</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Movie List</p>
<pre>[
  {
    "id": 3,
    "title": "Solo A Star Wars Story",
    "backgroundImg": "https://image.tmdb.org/t/p/original/5DUqFLgkLsJxyqPCAcgTivZy2SX.jpg",
    "caseImg": "https://image.tmdb.org/t/p/original/3IGbjc5ZC5yxim5W0sFING2kdcz.jpg",
    "trailerKey": "dNW0B0HsvVs",
    "tmdbId": 348350,
    "year": "2018",
    "quality": "1080p",
    "runtime": 135,
    "popularity": 56.919,
    "voteAverage": 6.7,
    "filetype": "video/mp4",
    "descript": "Through a series of daring escapades deep within a dark and dangerous criminal underworld, Han Solo meets his mighty future copilot Chewbacca and encounters the notorious gambler Lando Calrissian.",
    "genres": [
      {
        "name": "Action"
      },
      {
        "name": "Adventure"
      },
      {
        "name": "Science Fiction"
      }
    ]
  },
  {
    "id": 6,
    "title": "The Hobbit The Battle of the Five Armies",
    "backgroundImg": "https://image.tmdb.org/t/p/original/uA8Qe2d9bKEdCFakjjdldy3P2pU.jpg",
    "caseImg": "https://image.tmdb.org/t/p/original/9zRzFJuaj0CHIOhAkcCcFTvyu2X.jpg",
    "trailerKey": "ZSzeFFsKEt4",
    "tmdbId": 122917,
    "year": "2014",
    "quality": "1080p",
    "runtime": 144,
    "popularity": 46.508,
    "voteAverage": 7.2,
    "filetype": "video/mp4",
    "descript": "Immediately after the events of The Desolation of Smaug, Bilbo and the dwarves try to defend Erebor's mountain of treasure from others who claim it: the men of the ruined Laketown and the elves of Mirkwood. Meanwhile an army of Orcs led by Azog the Defiler is marching on Erebor, fueled by the rise of the dark lord Sauron. Dwarves, elves and men must unite, and the hope for Middle-Earth falls into Bilbo's hands.",
    "genres": [
      {
        "name": "Action"
      },
      {
        "name": "Adventure"
      },
      {
        "name": "Fantasy"
      }
    ]
  }
]</pre>
<hr>


### Get one Movie <i>GET</i>
<p>Requires to be Logged in</p>
<pre>/api/movie/{movieId}</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Movie</p>
<pre>{
  "id": 4,
  "title": "The First Purge",
  "backgroundImg": "https://image.tmdb.org/t/p/original/1hJbE72WiRuWH11QPNiHsvt29xA.jpg",
  "caseImg": "https://image.tmdb.org/t/p/original/litjsBoiydO6JlO70uOX4N3WnNL.jpg",
  "trailerKey": "E-8e0cqUGTw",
  "tmdbId": 442249,
  "year": "2018",
  "quality": "1080p",
  "runtime": 97,
  "popularity": 40.847,
  "voteAverage": 5.8,
  "filetype": "video/mp4",
  "descript": "To push the crime rate below one percent for the rest of the year, the New Founding Fathers of America test a sociological theory that vents aggression for one night in one isolated community. But when the violence of oppressors meets the rage of the others, the contagion will explode from the trial-city borders and spread across the nation.",
  "genres": [
    {
      "name": "Action"
    },
    {
      "name": "Science Fiction"
    },
    {
      "name": "Thriller"
    },
    {
      "name": "Horror"
    }
  ]
}</pre>
<hr>

### Get Movie Video <i>GET</i>

<p>Requires to be Logged in</p>
<pre>/video/movie/{movieId}</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Video</p>
<hr>

### Get Episode Video <i>GET</i>

<p>Requires to be Logged in</p>
<pre>/video/episode/{movieId}</pre>
<h4>Params</h4>
<p><b>sessionId</b> String required</p>
<h4>Response</h4>
<p>Video</p>

