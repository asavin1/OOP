package scripts

import org.apache.commons.io.FileUtils
import org.jsoup.Jsoup

def shell = new GroovyShell()
def script = shell.parse(new File("src/main/resources/scripts/build.groovy"))
def results = script.run()

def htmlTemplate = new File(this.class.getResource("/index.html").getFile())
def htmlBody = Jsoup.parse(htmlTemplate).toString()
htmlBody += "\n\n"

for (def lab in results.keySet()) {
    htmlBody += "<table>\n"
    htmlBody += "    <caption>${lab}</caption>"
    htmlBody += """
    <tr>
        <th>Student</th>
        <th>Build</th>
        <th>Documentation</th>
        <th>Tests</th>
        <th>Points</th>
    </tr>\n"""
    for (student in results[lab].keySet()) {
        def build = results[lab][student]["build"]
        def javadoc = results[lab][student]["javadoc"]
        def test = results[lab][student]["test"]
        def result;
        if (build == '-' || javadoc == '-' || test == '-') {
            result = "0"
        } else {
            result = "1"
        }
        htmlBody += """
    <tr>
        <td>${student}</td>
        <td>${build}</td>
        <td>${javadoc}</td>
        <td>${test}</td>
        <td>${result}</td>
    </tr>\n"""
    }
    htmlBody += "</table>\n"
}

htmlBody += "</body>\n</html>"

def resultFile = new File("./report.html")
FileUtils.writeStringToFile(resultFile, htmlBody)

println "report.html was generated"
