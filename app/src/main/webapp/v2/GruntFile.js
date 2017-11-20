module.exports = function(grunt) {
  var srcPath = 'src/**/*.js';
  var specsPath = 'tests/**/*spec.js';
  var helperPath = 'tests/helpers/*.js';
  grunt.initConfig({
    concat: {
      options: {
        separator: ';',
      },
      dist: {
        src: ['src/AppController.js', 'src/contants.js','src/app/**/*.js', 'src/**/*.js'],
        dest: 'dist/main.js',
      },
    },    
    uglify: {
      options: {
        // the banner is inserted at the top of the output
        banner: '/*! <%= grunt.template.today("dd-mm-yyyy") %> */\n'
      },
      dist: {
        files: {
          'dist/main.min.js': ['src/*/*.js']
        }
      },
      libs: {
        files: {
          'dist/libs.min.js': [
                              "../new_templates/assets/js/bootstrap.min.js",
                              "../new_templates/assets/js/typeahead-bs2.min.js", 
                              "../new_templates/assets/js/jquery-ui-1.10.3.custom.min.js",
                              "../new_templates/assets/js/jquery.ui.touch-punch.min.js",
                              "../new_templates/assets/js/jquery.gritter.min.js",
                              "../new_templates/assets/js/bootbox.min.js",
                              "../new_templates/assets/js/jquery.slimscroll.min.js",
                              "../new_templates/assets/js/jquery.slimscroll.min.js",
                              "../new_templates/assets/js/jquery.easy-pie-chart.min.js",
                              "../new_templates/assets/js/jquery.hotkeys.min.js",
                              "../new_templates/assets/js/bootstrap-wysiwyg.min.js",
                              "../new_templates/assets/js/select2.min.js",
                              "../new_templates/assets/js/date-time/bootstrap-datepicker.min.js",
                              //"../new_templates/assets/js/date-time/locales/bootstrap-datepicker.pt-BR.js",
                              "../new_templates/assets/js/fuelux/fuelux.spinner.min.js",
                              "../new_templates/assets/js/x-editable/bootstrap-editable.min.js",
                              "../new_templates/assets/js/x-editable/ace-editable.min.js",
                              "../new_templates/assets/js/jquery.maskedinput.min.js",
                              "../new_templates/assets/js/ace-elements.min.js",
                              "../new_templates/assets/js/date-time/bootstrap-timepicker.min.js",
                              "../new_templates/assets/js/ace.min.js",
                              "libs/angular/angular.min.js",
                              "libs/angular-bs/angular-bs.js",
                              'libs/angular-ui-date/src/date.js',
                              'libs/angular-resource.min.js',
                              'libs/angular-route.min.js',
                              //"libs/angular-br/br.js",
                              "libs/angular-ui-select2/src/select2.js",
                              "libs/jquery.price_format/jquery.price_format.1.8.min.js"
                              ]
        }
      }
    },
   jshint: {
      all: ['Gruntfile.js', 'lib/**/*.js', specsPath, srcPath]
    },    
    jasmine : {
      pivotal:{
          src : srcPath,
          options: {
            specs : specsPath,
            helpers : helperPath,
            vendor : 'dist/libs.min.js'
          }
      }
    },
    watch: {
       pivotal : {
            files: [specsPath, srcPath], 
            tasks: ['concat', 'jshint']
        }
    }
  });
  
  grunt.loadNpmTasks('grunt-contrib-jasmine');
  grunt.loadNpmTasks('grunt-contrib-jshint');
  grunt.loadNpmTasks('grunt-contrib-watch');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-concat');
  // Default task.
  //grunt.registerTask('default', ['jasmine', 'jshint', 'watch']);
  grunt.registerTask('test', ['jshint', 'jasmine'])
  grunt.registerTask('default', ['test'])  
};