import scoverage.ScoverageSbtPlugin.ScoverageKeys
import sbtassembly.AssemblyPlugin.autoImport._

organization := "com.vpon"

name := "vpon-mapping"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.vpon"                  %   "vpon-ipdb"               % "0.1-SNAPSHOT",
  "com.vpon"                  %   "vpon-uadb"               % "0.1-SNAPSHOT",
  "org.teleal.cling"          %   "cling-core"              % "1.0.5",
  "mobi.mtld.da"              %   "deviceatlas-carrierapi"  % "1.0.1",
  "mobi.mtld.da"              %   "deviceatlas-common"      % "1.0",
  "mobi.mtld.da"              %   "deviceatlas-deviceapi"   % "2.0.1",
  "org.teleal"                %   "teleal-common"           % "1.0.13",
  "org.apache.commons"        %   "commons-lang3"           % "3.4",
  "commons-codec"             %   "commons-codec"           % "1.10",
  "commons-validator"         %   "commons-validator"       % "1.5.0",
  "ch.qos.logback"            %   "logback-classic"         % "1.0.7",
  "com.google.guava"          %   "guava"                   % "18.0",
  "com.maxmind.geoip2"        %   "geoip2"                  % "2.1.0",
  "com.nrinaudo"              %%  "scala-csv"               % "0.1.3",
  "org.apache.commons"        %   "commons-jexl"            % "2.1.1",
  "org.scalatest"             %%  "scalatest"               % "2.2.1"         % "test",
  "org.mockito"               %   "mockito-all"             % "1.8.4"         % "test"
)

resolvers ++= Seq(
  "Vpon Test Artifactory" at "http://192.168.101.29:8081/artifactory/vpon-test",
  "typesafe.com" at "http://repo.typesafe.com/typesafe/maven-releases/"
)

//logLevel := Level.Debug

scalacOptions += "-deprecation"

javaOptions in run ++= Seq("-verbosegc", "-XX:+PrintGCDetails", "-Xloggc:gc.log")

ScoverageKeys.coverageExcludedPackages := "<empty>"
ScoverageKeys.coverageMinimum := 80
ScoverageKeys.coverageFailOnMinimum := false

assemblyMergeStrategy in assembly := {
  // Classes
  case n if n.startsWith("org/apache/commons/logging")      => MergeStrategy.first
  case n if n.startsWith("org/slf4j/impl")                  => MergeStrategy.first

  // Package Dependency Messages
  case n if n.startsWith("META-INF/maven/")                 => MergeStrategy.discard
  case n if n.startsWith("META-INF/plexus/")                => MergeStrategy.discard
  case n if n.startsWith("META-INF/sisu/")                  => MergeStrategy.discard
  case n if n.startsWith("META-INF/DEPENDENCIES")           => MergeStrategy.discard

  // Signature files
  case n if n.startsWith("META-INF/BCKEY.DSA")              => MergeStrategy.discard
  case n if n.startsWith("META-INF/BCKEY.SF")               => MergeStrategy.discard
  case n if n.startsWith("META-INF/DEV.DSA")                => MergeStrategy.discard
  case n if n.startsWith("META-INF/DEV.SF")                 => MergeStrategy.discard
  case n if n.startsWith("META-INF/ECLIPSEF.RSA")           => MergeStrategy.discard
  case n if n.startsWith("META-INF/ECLIPSEF.SF")            => MergeStrategy.discard
  case n if n.startsWith("META-INF/eclipse.inf")            => MergeStrategy.discard
  case n if n.startsWith("META-INF/MANIFEST.MF")            => MergeStrategy.discard

  // License files
  case n if n.startsWith("META-INF/mailcap")                => MergeStrategy.discard
  case n if n.startsWith("META-INF/NOTICE")                 => MergeStrategy.discard
  case n if n.startsWith("META-INF/INDEX.LIST")             => MergeStrategy.discard
  case n if n.startsWith("META-INF/LICENSE")                => MergeStrategy.discard
  case n if n.startsWith("about_files/")                    => MergeStrategy.discard
  case n if n.startsWith("about.html")                      => MergeStrategy.discard
  case n if n.startsWith("NOTICE")                          => MergeStrategy.discard
  case n if n.startsWith("LICENSE")                         => MergeStrategy.discard
  case n if n.startsWith("LICENSE.txt")                     => MergeStrategy.discard
  case n if n.startsWith("rootdoc.txt")                     => MergeStrategy.discard
  case n if n.startsWith("readme.html")                     => MergeStrategy.discard
  case n if n.startsWith("readme.txt")                      => MergeStrategy.discard
  case n if n.startsWith("license.html")                    => MergeStrategy.discard

  // Service provider configuration files
  case n if n.startsWith("META-INF/services/")              => MergeStrategy.first

  // System default properties
  case n if n.startsWith("META-INF/mimetypes.default")      => MergeStrategy.discard
  case n if n.startsWith("application.conf")                => MergeStrategy.discard
  case n if n.startsWith("application.properties")          => MergeStrategy.discard
  case n if n.startsWith("application.json")                => MergeStrategy.discard
  case n if n.startsWith("reference.conf")                  => MergeStrategy.concat
  case n if n.startsWith("library.properties")              => MergeStrategy.discard
  case n if n.startsWith("plugin.properties")               => MergeStrategy.concat
  case n if n.startsWith("mime.types")                      => MergeStrategy.discard
  case n if n.startsWith("logback.xml")                     => MergeStrategy.discard

  case n => MergeStrategy.deduplicate
}

excludeFilter in unmanagedSources in Compile := HiddenFileFilter

publishTo := {
  val artifactory = "http://192.168.101.29:8081/artifactory/vpon-"
  if (isSnapshot.value)
    Some("Vpon snapshots" at artifactory + "test")
  else
    Some("Vpon releases" at artifactory + "test")
}

publishMavenStyle := true

credentials += Credentials("Artifactory Realm", "192.168.101.29", "vpon-test", "vpon-test")
//credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
//  realm=Artifactory Realm
//  host=192.168.101.29
//  user=vpon-test
//  password=vpon-test