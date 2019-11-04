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
                        "Created by Egor Osokin"
            )
            left = vbox {
                fieldset("Initial values") {
                    var pair = ComputationalMethodsManager.initialValues.mapOfProperties
                    for (pair in ComputationalMethodsManager.initialValues.mapOfProperties) {
                        vbox {
                            label(pair.key)
                            textfield(pair.value) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                fieldset("Values for Max error") {
                    for (pair in ComputationalMethodsManager.totalErrorInfo.mapOfProperties) {
                        vbox {
                            label(pair.key)
                            textfield(pair.value) {
                                filterInput { it.controlNewText.isDouble() }
                            }
                        }
                    }
                }
                button("Plot")
            }
            right = vbox {
                fieldset {
                    // todo: add charts
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    launch<MyApp>(args)
}