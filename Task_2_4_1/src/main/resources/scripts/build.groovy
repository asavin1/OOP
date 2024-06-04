package scripts

import org.gradle.tooling.GradleConnector

// Получаем конфиги
def engine = new GroovyScriptEngine("src/main/resources/scripts")
def scriptClass = engine.loadScriptByName("tasks.groovy")
def taskConfig = scriptClass.getDeclaredConstructor().newInstance()
scriptClass = engine.loadScriptByName("students.groovy")
def studentsConfig = scriptClass.getDeclaredConstructor().newInstance()

def func(Set students, String lab) {
    println "-------------------"
    println lab + ':'

    def results = new LinkedHashMap()
    for (student in students) {
        def connector = GradleConnector.newConnector()
        studentPath = new File("repos/" + student);

        println "----------"
        println "${student}:"

        def studentResults = [
                build: '-',
                javadoc: '-',
                test: '-'
        ]

        def labPath = "./repositories/" + student + '/' + lab
        connector.forProjectDirectory(new File(labPath))
        def connection = connector.connect()
        def build = connection.newBuild()

        // Build
        try {
            build.forTasks('build')
                    .run()
            studentResults['build'] = '+'
            println "Build +"
        } catch (Exception e) {
            println "Build -"
        }

        // Покрытие
        try {
            build.forTasks('test')
                    .addArguments('-i')
                    .run()
            studentResults['test'] = '+'
            println 'TESTS COMPLETED '
        } catch (Exception e) {
            println 'TESTS FAILED '
        }

        // Java доки
        try {
            build.forTasks('javadoc')
                    .run()
            studentResults['javadoc'] = '+'
            println "Javadoc +"
        } catch (Exception e) {
            println "Javadoc -"
        }

        results[student] = studentResults
    }
    return results;
}

// Клонируем репы
def shell = new GroovyShell()
def script = shell.parse(new File("src/main/resources/scripts/clone.groovy"))
script.run()

def labResults = new LinkedHashMap();
for (lab in taskConfig.tasks) {
    def labResult = func(studentsConfig.students.keySet(), lab);
    labResults[lab] = labResult
}

println "-------------"
return labResults