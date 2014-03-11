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