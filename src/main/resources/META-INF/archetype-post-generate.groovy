import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// The path where the project got generated
Path projectPath = Paths.get(request.outputDirectory, request.artifactId)

// The properties available to the archetype
Properties properties = request.properties

// includeEvent is true or not
String includeEvent = properties.get("includeEvent")

String entityName = properties.get("entityName")

// The Java package of the generated project, e.g. com.acme
String packageName = properties.get("package")

// Convert it into a path, e.g. com/acme
String packagePath = packageName.replace(".", "/")

if (includeEvent != "true") {
  // Delete the Event files
  Files.deleteIfExists projectPath.resolve("src/main/java/" + packagePath + "/model/" + entityName + "Event.java")
  Files.deleteIfExists projectPath.resolve("src/main/java/resources/public/index.html")
}

// Generate Maven Wrapper

dir = new File(new File(request.outputDirectory), request.artifactId)

def run(String cmd) {
    def process = cmd.execute(null, dir)
    process.waitForProcessOutput((Appendable)System.out, System.err)
    if (process.exitValue() != 0) {
        throw new Exception("Command '$cmd' exited with code: ${process.exitValue()}")
    }
}

run("mvn -N io.takari:maven:wrapper -DskipNotification")