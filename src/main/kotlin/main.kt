import javafx.stage.Stage
import tornadofx.*

class MyApp : App(MyView::class) {
    override fun start(stage: Stage) {
        stage.isResizable = false
        super.start(stage)
    }
}

class MyView : View() {
    override val root = form {
        borderpane {
            top = label(
                "This application draws solutions " +
                        "for ${ComputationalMethodsManager.initialValues.initialFunction}. " +
                        "Exact solution: ${ExactSolution}" +
                        "\nCreated by Egor Osokin, BS18-04 student" +
                        "\n\n"
            )
            right = ChartGenerator.getBoxWithAll()
            left = vbox {
                fieldset("Select charts") {
                    for ((name, method) in ComputationalMethodsManager.listOfMethods) {
                        checkbox(name, method.isSelectedProperty)
                    }
                }
                fieldset("Initial values") {
                    for ((name, property) in ComputationalMethodsManager.initialValues.mapOfProperties) {
                        vbox {
                            label(name)
                            textfield(property) {
                                filterInput { it.controlNewText.isDouble() }
                            }.bind(property)
                        }
                    }
                }
                fieldset("Values for total error") {
                    for ((name, property) in ComputationalMethodsManager.totalErrorInfo.mapOfProperties) {
                        vbox {
                            label(name)
                            textfield(property) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                button("Plot") {
                    action {
                        right = ChartGenerator.getBoxWithAll()
                    }
                }
            }

        }
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}