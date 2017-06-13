(function()
{
	/*
	 * Dependencies
	 */
	const gulp = require("gulp");
	const jshint = require("gulp-jshint");
	const sass = require("gulp-sass");
	const concat = require("gulp-concat");
	const rename = require("gulp-rename");
	const minifyPipeline = require("pipeline-minify-css");

	/*
	 * Variables
	 */
	let bowerPath = "./bower_components";

	/*
	 * Gulp Lint
	 */
	gulp.task("lint:app", function()
	{
		return gulp
			.src("components/**/*.vilarika.js")
			.pipe(jshint())
			.pipe(jshint.reporter("default"));
	});

	/*
	 * Gulp Sass
	 */
	gulp.task("sass:app", function()
	{
		return gulp
			.src(["components/main.scss"])
			.pipe(sass({outputStyle: "compressed"}))
			.pipe(rename("main.min.css"))
			.pipe(gulp.dest("dist/css"));
	});

	/*
	 * Gulp Concat
	 */
	gulp.task("concat:app", function()
	{
		return gulp
			.src(["components/**/*.vilarika.js"])
			.pipe(concat("main.js"))
			.pipe(gulp.dest("dist/js"));
	});

	gulp.task("concat:vendor", function()
	{
		return gulp
			.src([
				(bowerPath + "/jquery/dist/jquery.js"),
				(bowerPath + "/bootstrap/dist/js/bootstrap.js")
			])
			.pipe(concat("vendor.js"))
			.pipe(gulp.dest("dist/js"));
	});

	/*
	 * Gulp Concat CSS
	 */
	gulp.task("concat-css:vendor", function()
	{
		return gulp
			.src([
				(bowerPath + "/bootstrap/css/bootstrap.min.css"),
				(bowerPath + "/bootstrap/css/bootstrap-theme.min.css")
			])
			.pipe(minifyPipeline.minifyCSS({
				addSourceMaps: false,
				concat: true,
				concatFilename: "vendor.min.css"
			}))
			.pipe(gulp.dest("dist/css"));
	});

	/*
	 * Gulp Watch
	 */
	gulp.task("watch", function()
	{
		gulp.watch("components/**/*.vilarika.js", ["lint:app", "concat:app"]);
		gulp.watch("components/**/*.vilarika.scss", ["sass:app"]);
	});

	/*
	 * Default task
	 */
	gulp.task("vendor", ["concat-css:vendor", "concat:vendor"]);
	gulp.task("app", ["lint:app", "sass:app", "concat:app"]);
	gulp.task("default", ["vendor", "app"]);
})();