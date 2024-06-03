package scripts

def engine = new GroovyScriptEngine("src/main/resources/scripts")
def scriptClass = engine.loadScriptByName("students.groovy")
def studentsConfig = scriptClass.getDeclaredConstructor().newInstance()

def cloneRepos(LinkedHashMap students) {
    println "Students:" + students.keySet()

    for (id in students.keySet()) {
        pathToCloning = "repositories/${id}"

        def student = students.get(id)
        def repo = student.repository
        println("Try to clone repository ${repo} " + "to the \"${pathToCloning}\" directory ")

        def gitCommand = "git clone ${student.repository} ${pathToCloning}"
        println("Git command: ${gitCommand}")

        def cloning = gitCommand.execute()
        def exitCode = cloning.waitFor()
        if (exitCode == 0) {
            println "Git command was successfully completed.\n"
        } else {
            println "Git command wasn't completed."
            def errorStream = cloning.errorStream
            def errorMessage = errorStream?.text
            println "Error message:\n ${errorMessage}"
        }
    }
}

cloneRepos(studentsConfig.students)