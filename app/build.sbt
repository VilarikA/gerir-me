name := "gerir-me"

version := "0.1.22"

organization := "Lift"

scalaVersion := "2.9.1"

// If using JRebel with 0.1.0 of the sbt web plugin
//jettyScanDirs := Nil
// using 0.2.4+ of the sbt web plugin
scanDirectories in Compile := Nil


// you can also add multiple repositories at the same time
resolvers ++= Seq("snapshots"     at "http://oss.sonatype.org/content/repositories/snapshots",
                  "staging"       at "http://oss.sonatype.org/content/repositories/staging",
                  "releases"      at "http://oss.sonatype.org/content/repositories/releases"
                 )

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

seq(webSettings :_*)

libraryDependencies ++= {
  val liftVersion = "2.4"
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "container", // For Jetty 7
    // "org.eclipse.jetty" % "jetty-servlet" % "7.3.1.v20110307",    
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "org.quartz-scheduler" % "quartz" % "2.1.5" % "compile",
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile",
    "org.mortbay.jetty" % "jetty" % "6.1.26" % "test",
    "junit" % "junit" % "4.7" % "test",
    "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default", // Logging
    "net.liftweb" %% "lift-scalate" % liftVersion,
    "org.scala-tools.testing" %% "specs" % "1.6.9" % "test",
    "com.h2database" % "h2" % "1.2.147",
    "postgresql" % "postgresql" % "9.1-901.jdbc4" % "runtime" ,
    "net.databinder" %% "dispatch-http" % "0.8.8",
    "net.liftweb" %% "lift-widgets" % liftVersion % "compile",
    "javax.jms" % "jms" % "1.1" % "compile" from "http://www.datanucleus.org/downloads/maven2/javax/jms/jms/1.1/jms-1.1.jar",
    "org.apache.activemq" % "activemq-core" % "5.5.1"  % "compile",
    "net.sf.jasperreports" % "jasperreports" % "4.1.2" % "compile",
    "com.lowagie" % "itext" % "2.1.7" % "compile",
    "com.github.philcali" %% "scalendar" % "0.1.2",
    "net.sf.ofx4j" % "ofx4j" % "1.3" % "compile"//,
    // "org.scala-lang" % "scala-reflect" % "2.10.0-M4"
  )
}

// by default, it listens on port 8080; use the following to override
port in container.Configuration := 7171