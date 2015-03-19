npm update -g grunt
npm update -g grunt-cli
npm update -g bower
npm update -g yo

make sure you have the latest version of node and more importantly NPM.  ~1.4.3

git clone https://github.com/jtmille3/Comp.git

cd Comp

bower install && npm install

grunt development # will compile handlebars and less
grunt watch # will watch for changes to handlebars and less templates

Compile your java into Comp/web/WEB-INF/classes
The main method can be found in EmbeddedServer.java.

Open a web browser to http://localhost:8080/app/index.html

TODO:

Fix bug with playoffs
Fix bug with standings when no games have been played
Fix bug with admin for ghost goals
