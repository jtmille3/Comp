'use strict';
var lrSnippet = require('grunt-contrib-livereload/lib/utils').livereloadSnippet;
var mountFolder = function (connect, dir) {
    return connect.static(require('path').resolve(dir));
};

module.exports = function (grunt) {
    // load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // configurable paths
    var yeomanConfig = {
        app: 'web/app',
        dist: 'web/comp'
    };

    grunt.initConfig({
        yeoman: yeomanConfig,
        watch: {
            livereload: {
                files: [
                    '<%= yeoman.app %>/*.html',
                    '{.tmp,<%= yeoman.app %>}/styles/*.css',
                    '{.tmp,<%= yeoman.app %>}/scripts/*.js',
                    '<%= yeoman.app %>/images/*.{png,jpg,jpeg}'
                ],
                tasks: ['livereload']
            },
            handlebars: {
                files: [
                    '<%= yeoman.app %>/templates/**/*.html'
                ],
                tasks: ['handlebars']
            },
            less: {
                files: [
                    '<%= yeoman.app %>/less/**/*.less',
                    '<%= yeoman.app %>/components/bootstrap/less/**/*.less'
                ],
                tasks: ['less:development']
            }
        },
        connect: {
            options: {
                port: 9000
            },
            livereload: {
                options: {
                    middleware: function (connect) {
                        return [
                            lrSnippet,
                            mountFolder(connect, '.tmp'),
                            mountFolder(connect, 'app')
                        ];
                    }
                }
            },
            test: {
                options: {
                    middleware: function (connect) {
                        return [
                            mountFolder(connect, '.tmp'),
                            mountFolder(connect, 'test')
                        ];
                    }
                }
            },
            dist: {
                options: {
                    middleware: function (connect) {
                        return [
                            mountFolder(connect, 'dist')
                        ];
                    }
                }
            }
        },
        open: {
            server: {
                url: 'http://localhost:<%= connect.options.port %>'
            }
        },
        clean: {
            dist: ['.tmp', '<%= yeoman.dist %>/*'],
            server: '.tmp',
            build: ['<%= yeoman.dist %>/less', '<%= yeoman.dist %>/templates', '<%= yeoman.dist %>/test', '<%= yeoman.dist %>/.gitignore']
        },
        requirejs: {
            build: {
                // Options: https://github.com/jrburke/r.js/blob/master/build/example.build.js
                options: {
                    almond: false,
                    wrap: false,
                    baseUrl: 'scripts',
                    appDir: '<%= yeoman.app %>',
                    dir: '<%= yeoman.dist %>',
                    replaceRequireScript: [
                        {
                            files: ['<%= yeoman.dist %>/index.html'],
                            module: 'main'
                        },
                        {
                            files: ['<%= yeoman.dist %>/admin.html'],
                            module: 'admin'
                        }
                    ],
                    modules: [
                        {
                            name: 'main'
                        },
                        {
                            name: 'admin'
                        }
                    ]
                }
            }
        },
        useminPrepare: {
            html: ['<%= yeoman.app %>/index.html', '<%= yeoman.app %>/admin.html'],
            options: {
                dest: '<%= yeoman.dist %>'
            }
        },
        usemin: {
            html: ['<%= yeoman.dist %>/*.html'],
            css: ['<%= yeoman.dist %>/styles/*.css'],
            options: {
                dirs: ['<%= yeoman.dist %>']
            }
        },
        imagemin: {
            dist: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= yeoman.app %>/img',
                        src: '*.{png,jpg,jpeg}',
                        dest: '<%= yeoman.dist %>/img'
                    }
                ]
            }
        },
        cssmin: {
            dist: {
                files: {
                    '<%= yeoman.dist %>/styles/main.css': [
                        '.tmp/styles/*.css',
                        '<%= yeoman.app %>/styles/*.css'
                    ]
                }
            }
        },
        htmlmin: {
            dist: {
                options: {
                },
                files: [
                    {
                        expand: true,
                        cwd: '<%= yeoman.app %>',
                        src: '*.html',
                        dest: '<%= yeoman.dist %>'
                    }
                ]
            }
        },
        copy: {
            dist: {
                files: [
                    {
                        expand: true,
                        dot: true,
                        cwd: '<%= yeoman.app %>',
                        dest: '<%= yeoman.dist %>',
                        src: [
                            '*.{ico,txt}',
                            '.htaccess'
                        ]
                    }
                ]
            }
        },
        bower: {
            rjsConfig: 'app/scripts/main.js',
            indent: '    '
        },
        handlebars: {
            compile: {
                options: {
                    namespace: 'comp',
                    amd: false,
                    partialRegex: /^_/
                },
                files: {
                    '<%= yeoman.app %>/scripts/templates.js': ['<%= yeoman.app %>/templates/**/*.html']
                }
            }
        },
        less: {
            development: {
                options: {
                },
                files: {
                    '<%= yeoman.app %>/styles/main.css': '<%= yeoman.app %>/less/main.less'
                }
            },
            production: {
                options: {
                    yuicompress: true
                },
                files: {
                    '<%= yeoman.dist %>/styles/main.css': '<%= yeoman.app %>/less/main.less'
                }
            }
        }
    });

    grunt.renameTask('regarde', 'watch');

    grunt.registerTask('server', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['open', 'connect:dist:keepalive']);
        }

        grunt.task.run([
            'clean:server',
            'livereload-start',
            'connect:livereload',
            'open',
            'watch'
        ]);
    });

    grunt.registerTask('test', [
        'clean:server',
        'connect:test',
        'mocha'
    ]);

    grunt.registerTask('build', [
        'clean:dist',
        'less:production',
        'handlebars',
        'requirejs',
        'useminPrepare',
        'imagemin',
        'cssmin',
        'htmlmin',
        'concat',
        'uglify',
        'copy',
        'usemin',
        'clean:build'
    ]);

    grunt.registerTask('development', [
        'less:development',
        'handlebars'
    ]);

    grunt.registerTask('default', ['build']);
};
