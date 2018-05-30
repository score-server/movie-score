<h1>Info</h1>
<h4>Application Name</h4>
<p>Haudi</p>
<p>Logo: <img src="https://github.com/Wetwer/movie-db-api/blob/master/doc/img/330px-MaxMoritz.png" width="60px"></p>
<p>URL: <a target="_blank" href="http://scorewinner.ch">scorewinner.ch</a></p>
<hr>
<h4>Video Player</h4>
<p><a target="_blank" href="https://docs.fluidplayer.com/">Fluid Player</a></p>
Style:
<pre>rel="stylesheet" href="http://cdn.fluidplayer.com/v2/current/fluidplayer.min.css" type="text/css"</pre>
Code:
<pre>src="http://cdn.fluidplayer.com/v2/current/fluidplayer.min.js"</pre>
<hr>
<h4>Style: Bootstrap 4</h4>
<p><a target="_blank" href="https://www.w3schools.com/bootstrap4/">Bootstrap</a></p>
<hr>
<h4>Movie File Naming</h4>
<p>[Movie Name] [release year] [movie quality].mp4</p>
<p>Example: <b>Deadpool 2 2018 1080p.mp4</b></p>
<hr>
<p>For Movie import set path with POST Request:</p>
<pre>/movie/add/path?path=/home/movieLibrary</pre>
<hr>
<h4>Password Encrypting</h4>
<p>Passwords encoded with custom SHA2 Encryptor:</p>
<b>Very Safe (Not Crackable)</b>
<pre>
private StringBuffer getEncoded(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    StringBuffer hexString = new StringBuffer();
    for (byte hashA : getDigest(s)) {
        String hex = Integer.toHexString(0xff & hashA);
        if (hex.length() == 1) {
            hexString.append('0');
        }
        hexString.append(hex);
    }
    return hexString;
}
</pre>
<pre>
private byte[] getDigest(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    return MessageDigest.getInstance("SHA-256").digest(s.getBytes("UTF-8"));
}
</pre>
<hr>
<h4>Api Documentation</h4>
<p>doc/api.html</p>
<hr>
<h4>Installation Manual</h4>
<p>doc/installation.html</p>
