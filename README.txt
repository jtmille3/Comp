npm install -g grunt && \
npm install -g grunt-cli && \
npm install -g bower && \
npm install -g yo && \
bower install && \
npm install && \
grunt build

make sure you have the latest version of node and more importantly NPM.  ~1.4.3

git clone https://github.com/jtmille3/Comp.git

cd Comp

bower install && npm install

grunt development # will compile handlebars and less
grunt watch # will watch for changes to handlebars and less templates

Compile your java into Comp/web/WEB-INF/classes
The main method can be found in EmbeddedServer.java.

Open a web browser to http://localhost:8080/app/index.html
