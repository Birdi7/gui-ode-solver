import javafx.scene.chart.NumberAxis
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
                "This application draw solutions " +
                        "for ${ComputationalMethodsManager.initialValues.initialFunction}. " +
                        "\nExact solution: ${ExactSolution}" +
                        "\nCreated by Egor Osokin, BS18-04 student" +
                        "\n\n"
            )
            left = vbox {
                fieldset("Initial values") {
                    for ((name, property) in ComputationalMethodsManager.initialValues.mapOfProperties) {
                        vbox {
                            label(name)
                            textfield(property) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                fieldset("Values for Max error") {
                    for ((name, property) in ComputationalMethodsManager.totalErrorInfo.mapOfProperties) {
                        vbox {
                            label(name)
                            textfield(property) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                button("Plot")
            }

            right = vbox {
                linechart("Solutions", x = NumberAxis(), y = NumberAxis()) {
                    series("Exact") {
                        for (entry in ExactSolution.computeFor(ComputationalMethodsManager.initialValues)) {
                            data(entry.key, entry.value)
                        }
                    }

//                    for (name in ComputationalMethodsManager.listOfMethods.keys) {
//                        series(name) {
//                            for (entry in ComputationalMethodsManager.compute(name)) {
//                                data(entry.key, entry.value)
//                            }
//                        }
//                    }
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}