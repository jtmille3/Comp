'use strict';
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
                    middleware: function(connect) {
                        return [
                            connect.static('.tmp'),
                            connect().use('/bower_components', connect.static('./bower_components')),
                            connect.static(config.app)
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
            build: ['<%= yeoman.dist %>/less', '<%= yeoman.dist %>/templates', '<%= yeoman.dist %>/test', '<%= yeoman.dist %>/.gitignore'],
            imagemin: ['<%= yeoman.dist %>/img']
        },
        requirejs: {
            build: {
                options: {
                    almond: true,
                    wrap: false,
                    baseUrl: 'scripts',
                    appDir: '<%= yeoman.app %>',
                    dir: '<%= yeoman.dist %>',
                    replaceRequireScript: [
                        {
                            files: ['<%= yeoman.dist %>/index.html'],
                            module: 'main',
                            modulePath: 'scripts/main'
                        },
                        {
                            files: ['<%= yeoman.dist %>/admin.html'],
                            module: 'admin',
                            modulePath: 'scripts/admin'
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
                options: {
                    cache: false
                },
                files: [
                    {
                        expand: true,
                        cwd: '<%= yeoman.app %>/img',
                        src: '**/*.{png,jpg,jpeg}',
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
        rev: {
            options: {
                encoding: 'utf8',
                algorithm: 'md5',
                length: 8
            },
            assets: {
                files: [{
                    src: [
                        '<%= yeoman.dist %>/scripts/{,*/!(require)}*.js',
                        '<%= yeoman.dist %>/styles/{,*/}*.css',
                        '<%= yeoman.dist %>/images/{,*/}*.{png,jpg,jpeg,gif,webp}',
                        '<%= yeoman.dist %>/fonts/{,*/}*.*'
                    ]
                }]
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

    grunt.registerTask('server', function (target) {
        if (target === 'dist') {
            return grunt.task.run(['open', 'connect:dist:keepalive']);
        }

        grunt.task.run([
            'clean:server',
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
        'useminPrepare',
        'clean:imagemin',
        'imagemin',
        'cssmin',
        'htmlmin',
        'requirejs',
        'concat',
        'uglify',
        'copy',
        'rev',
        'usemin',
        'clean:build'
    ]);

    grunt.registerTask('development', [
        'less:development',
        'handlebars'
    ]);

    grunt.registerTask('default', ['build']);
};
